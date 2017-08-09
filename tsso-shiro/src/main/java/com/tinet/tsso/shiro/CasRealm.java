package com.tinet.tsso.shiro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas30ServiceTicketValidator;
import org.jasig.cas.client.validation.TicketValidationException;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义实现的Realm方法，实现了shiro和cas登录和验证的整合
 * 
 * @author 李政
 * @date 2017年8月3日
 */
public class CasRealm extends AuthorizingRealm {

	// cas默认的remember me 的 name
	public static final String DEFAULT_REMEMBER_ME_ATTRIBUTE_NAME = "longTermAuthenticationRequestTokenUsed";
	public static final String DEFAULT_VALIDATION_PROTOCOL = "CAS";

	private static Logger logger = LoggerFactory.getLogger(CasRealm.class);

	// CAS server端的地址 (example : http://host:port/cas)
	private String casServerUrlPrefix;

	// CAS tickit 认证标志的url (example : http://host:port/mycontextpath/tsso)
	private String casService;

	// CAS 验证协议，默认是CAS
	private String validationProtocol = DEFAULT_VALIDATION_PROTOCOL;

	// CAS 默认的remember me 的 name
	private String rememberMeAttributeName = DEFAULT_REMEMBER_ME_ATTRIBUTE_NAME;

	// CAS ticket验证类
	private TicketValidator ticketValidator;

	// 认证成功的用户的默认角色
	private String defaultRoles;

	// 认证成功的用户的默认权限
	private String defaultPermissions;

	// 角色的key
	private String roleAttributeNames;

	// 权限的key
	private String permissionAttributeNames;

	public CasRealm() {
		setAuthenticationTokenClass(CasToken.class);
		// 不知道是否生效
		// setCachingEnabled(true);
		// setCacheManager(getCacheManager());
		// setAuthorizationCachingEnabled(true);
	}

	@Override
	protected void onInit() {
		super.onInit();
		ensureTicketValidator();
	}

	protected TicketValidator ensureTicketValidator() {
		if (this.ticketValidator == null) {
			this.ticketValidator = createTicketValidator();
		}
		return this.ticketValidator;
	}

	protected TicketValidator createTicketValidator() {
		String urlPrefix = getCasServerUrlPrefix();
		return new Cas30ServiceTicketValidator(urlPrefix);
	}

	/**
	 * Authenticates a user and retrieves its information.
	 * 
	 * 用户登录验证，使用shiro-cas的实现，没有做改动
	 * 
	 * @param token
	 *            the authentication token
	 * @throws AuthenticationException
	 *             if there is an error during authentication.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		CasToken casToken = (CasToken) token;
		if (token == null) {
			return null;
		}

		String ticket = (String) casToken.getCredentials();
		if (!StringUtils.hasText(ticket)) {
			return null;
		}

		TicketValidator ticketValidator = ensureTicketValidator();

		try {
			// contact CAS server to validate service ticket
			Assertion casAssertion = ticketValidator.validate(ticket, getCasService());
			// get principal, user id and attributes
			AttributePrincipal casPrincipal = casAssertion.getPrincipal();
			String userId = casPrincipal.getName();
			logger.debug("Validate ticket : {} in CAS server : {} to retrieve user : {}", new Object[] { ticket,
					getCasServerUrlPrefix(), userId });

			Map<String, Object> attributes = casPrincipal.getAttributes();
			// refresh authentication token (user id + remember me)
			casToken.setUserId(userId);
			String rememberMeAttributeName = getRememberMeAttributeName();
			String rememberMeStringValue = (String) attributes.get(rememberMeAttributeName);
			boolean isRemembered = rememberMeStringValue != null && Boolean.parseBoolean(rememberMeStringValue);
			if (isRemembered) {
				casToken.setRememberMe(true);
			}
			// create simple authentication info
			List<Object> principals = CollectionUtils.asList(userId, attributes);
			PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, getName());
			return new SimpleAuthenticationInfo(principalCollection, ticket);
		} catch (TicketValidationException e) {
			throw new CasAuthenticationException("Unable to validate ticket [" + ticket + "]", e);
		}
	}

	/**
	 * Retrieves the AuthorizationInfo for the given principals (the CAS
	 * previously authenticated user : id + attributes).
	 * 
	 * 自定义实现的用户授权验证方法
	 * 
	 * @param principals
	 *            the primary identifying principals of the AuthorizationInfo
	 *            that should be retrieved.
	 * @return the AuthorizationInfo associated with this principals.
	 */
	@Override
	@SuppressWarnings("unchecked")
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		// 获取用户信息
		SimplePrincipalCollection principalCollection = (SimplePrincipalCollection) principals;
		List<Object> listPrincipals = principalCollection.asList();
		Map<String, String> attributes = (Map<String, String>) listPrincipals.get(1);
		// 创建 simple authorization info
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		// 添加默认角色
		addRoles(simpleAuthorizationInfo, split(defaultRoles));
		// 添加默认权限
		addPermissions(simpleAuthorizationInfo, split(defaultPermissions));
		// 从逗号分隔的attribute里获取角色
		List<String> attributeNames = split(roleAttributeNames);
		for (String attributeName : attributeNames) {
			String value = attributes.get(attributeName);
			addRoles(simpleAuthorizationInfo, split(value));
		}
		// 从逗号分隔的attribute里获取权限
		attributeNames = split(permissionAttributeNames);
		for (String attributeName : attributeNames) {
			String value = attributes.get(attributeName);
			addPermissions(simpleAuthorizationInfo, split(value));
		}
		return simpleAuthorizationInfo;
	}

	/**
	 * 把逗号分隔的字符串转换成list
	 * 
	 * @param s
	 *            the input string
	 * @return the list of not empty and trimmed strings
	 */
	private List<String> split(String s) {
		List<String> list = new ArrayList<String>();
		String[] elements = StringUtils.split(s, ',');
		if (elements != null && elements.length > 0) {
			for (String element : elements) {
				if (StringUtils.hasText(element)) {
					list.add(element.trim());
				}
			}
		}
		return list;
	}

	/**
	 * 添加角色
	 * 
	 * @param simpleAuthorizationInfo
	 * @param roles
	 *            the list of roles to add
	 */
	private void addRoles(SimpleAuthorizationInfo simpleAuthorizationInfo, List<String> roles) {
		for (String role : roles) {
			simpleAuthorizationInfo.addRole(role);
		}
	}

	/**
	 * 添加权限
	 * 
	 * @param simpleAuthorizationInfo
	 * @param permissions
	 *            the list of permissions to add
	 */
	private void addPermissions(SimpleAuthorizationInfo simpleAuthorizationInfo, List<String> permissions) {
		for (String permission : permissions) {
			simpleAuthorizationInfo.addStringPermission(permission);
		}
	}

	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public String getCasService() {
		return casService;
	}

	public void setCasService(String casService) {
		this.casService = casService;
	}

	public String getValidationProtocol() {
		return validationProtocol;
	}

	public void setValidationProtocol(String validationProtocol) {
		this.validationProtocol = validationProtocol;
	}

	public String getRememberMeAttributeName() {
		return rememberMeAttributeName;
	}

	public void setRememberMeAttributeName(String rememberMeAttributeName) {
		this.rememberMeAttributeName = rememberMeAttributeName;
	}

	public String getDefaultRoles() {
		return defaultRoles;
	}

	public void setDefaultRoles(String defaultRoles) {
		this.defaultRoles = defaultRoles;
	}

	public String getDefaultPermissions() {
		return defaultPermissions;
	}

	public void setDefaultPermissions(String defaultPermissions) {
		this.defaultPermissions = defaultPermissions;
	}

	public String getRoleAttributeNames() {
		return roleAttributeNames;
	}

	public void setRoleAttributeNames(String roleAttributeNames) {
		this.roleAttributeNames = roleAttributeNames;
	}

	public String getPermissionAttributeNames() {
		return permissionAttributeNames;
	}

	public void setPermissionAttributeNames(String permissionAttributeNames) {
		this.permissionAttributeNames = permissionAttributeNames;
	}
}
