package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.RoleMapper;
import com.tinet.tsso.auth.dao.UserMapper;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.model.UsernameAndUuidModel;
import com.tinet.tsso.auth.param.PasswordChangeParam;
import com.tinet.tsso.auth.param.PasswordParam;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.MailSenderUtil;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.PasswordHash;
import com.tinet.tsso.auth.util.ResetPasswordTmp;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 用户Service的实现类
 * 
 * @date 2017-08-09
 * @author lizy
 */
@Service
public class UserServiceImpl extends BaseServiceImp<User, Integer> implements UserService {


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${mail.from.address}")
	private String mailFrom;

	@Value("${tsso.cas.clientUrl}")
	private String changePasswordAddress;

	/**
	 * 为用户批量添加角色
	 */
	@Override
	@Transactional
	public void addRoles(Integer userId, List<Integer> roleIdList) {
		if (roleIdList.size() != 0) {
			for (int i = 0; i < roleIdList.size(); i++) {
				UserParam param = new UserParam();
				param.setId(userId);
				param.setRoleId(roleIdList.get(i));
				addRole(param);
			}
		}
	}

	/**
	 * 添加用户
	 */
	@Override
	public ResponseModel addUser(User user) {
		if (this.selectByUserName(user.getUsername()) != null) {
			return new ResponseModel.Builder().error("该帐号已经存在").status(HttpStatus.BAD_REQUEST).build();
		}
		/**
		 * 用户的账号和全名不能为空
		 */
		if (user.getUsername() == null || user.getFullName() == null || user.getEmail() == null) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("用户名和全名都不能为空").build();
		}
		user.setCreateTime(new Date());

		// 帐号唯一性校验
		Integer userCount = userMapper.selectCountByUserName(user.getUsername());
		if (!userCount.equals(0)) {
			return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("用户名已经被使用").build();
		}

		// 处理密码
		user = dealPassword(user, null);
		// 新建用户
		userMapper.insertSelective(user);

		// 存储用户数据，并发送邮件
		UUID uuid = UUID.randomUUID();
		UsernameAndUuidModel usernameAndUuidModel = new UsernameAndUuidModel();
		usernameAndUuidModel.setKey(uuid.toString());
		usernameAndUuidModel.setDate(new Date());

		ResetPasswordTmp.resetMap.put(user.getUsername(), usernameAndUuidModel);
		MailSenderUtil.sendMail(mailSender, mailFrom, user.getEmail(), "天润统一登录系统，重置密码邀请",
				getMailContent(user.getUsername(), uuid.toString()));

		user = userMapper.selectByPrimaryKey(user.getId());

		// 去除密码和盐等敏感信息
		user.setPassword(null);
		user.setPasswordSalt(null);

		return new ResponseModel.Builder().result(user).msg("添加成功").build();
	}

	/**
	 * 按照参数查询用户
	 */
	@Override
	public Page<UserModel> selectByParams(UserParam params) {

		if (params.getLimit() == null) {
			params.setLimit(10);
		}
		if (params.getStart() == null) {
			params.setStart(0);
		}

		// 符合条件的User总数
		Integer totalSize = userMapper.selectCountByParams(params);
		// 符合条件的User列表
		List<User> userList = userMapper.selectByParams(params);

		List<UserModel> pageData = new ArrayList<UserModel>();

		// 符合条件的角色查询
		for (int i = 0; i < userList.size(); i++) {
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(userList.get(i), userModel);

			if (userList.get(i).getDepartment() != null) {
				userModel.setDepartmentName(userList.get(i).getDepartment().getName());
			}

			List<Role> roleList = roleMapper.getRoleByUser(userList.get(i));
			userModel.setRoleList(roleList);

			pageData.add(userModel);
		}
		return new Page<UserModel>(totalSize, pageData);
	}

	/**
	 * 查询指定权限额用户列表
	 */
	@Override
	public List<UserModel> selectByPermissionId(Integer permissionId) {
		List<User> userList = userMapper.selectByPermissionId(permissionId);

		List<UserModel> userModelList = new ArrayList<UserModel>();

		for (int i = 0; i < userList.size(); i++) {
			User user = userList.get(i);
			UserModel userModel = new UserModel();
			BeanUtils.copyProperties(user, userModel);

			userModel.setDepartmentId(user.getDepartmentId());
			userModel.setDepartmentName(user.getDepartment().getName());

			userModelList.add(userModel);
		}
		return userModelList;
	}

	/**
	 * 按照用户名查询用户
	 */
	@Override
	public User selectByUserName(String username) {
		List<User> userList = userMapper.selectByUsername(username);
		if (userList == null || userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * 设置和重置用户密码的实现方法
	 * 
	 * @param passwordParam
	 *            密码和确认密码
	 * @param map
	 *            存储用户标识的key
	 * @param username
	 *            帐号
	 * @param key
	 *            标识
	 * @param effictiveTime
	 *            有效时长
	 * @return
	 */
	@Override
	public ResponseModel setPassword(PasswordParam passwordParam, Map<String, UsernameAndUuidModel> map,
			String username, String key, Integer effictiveTime) {

		if (passwordParam.getPassword() == null || passwordParam.getRepassword() == null) {
			return new ResponseModel.Builder().error("密码和确认密码都不能为空").status(HttpStatus.BAD_REQUEST).build();
		} else if (!passwordParam.getPassword().equals(passwordParam.getRepassword())) {
			return new ResponseModel.Builder().error("密码和确认密码不同").status(HttpStatus.FORBIDDEN).build();
		}
		if (username == null) {
			return new ResponseModel.Builder().error("请求错误").status(HttpStatus.FORBIDDEN).build();
		}

		UsernameAndUuidModel userNameAndUuidModel = map.get(username);

		if (userNameAndUuidModel == null || !userNameAndUuidModel.getKey().equals(key)) {
			return new ResponseModel.Builder().error("请求错误").status(HttpStatus.BAD_REQUEST).build();
		}

		Date nowDate = new Date();
		long dateDiffer = nowDate.getTime() - userNameAndUuidModel.getDate().getTime();
		// 确认不超时
		if (dateDiffer > 60000 * effictiveTime) {
			map.remove(username);
			return new ResponseModel.Builder().error("链接已失效").status(HttpStatus.BAD_REQUEST).build();
		}
		// 根据用户名查询用户
		User user = this.selectByUserName(username);

		if (user == null) {
			return new ResponseModel.Builder().error("用户名错误").status(HttpStatus.BAD_REQUEST).build();
		}
		user.setPassword(passwordParam.getPassword());

		user = this.updatePasswordByUsername(user);

		map.remove(username);

		return new ResponseModel.Builder().msg("操作成功").result(passwordParam.getPassword()).build();
	}

	/**
	 * @param userId
	 *            用户id
	 * @param roleIdList
	 *            角色的id列表
	 * @return 返回角色的详细信息
	 */
	@Override
	@Transactional
	public List<Role> updataUserRoleList(Integer userId, List<Integer> roleIdList) {

		// 删除所有的角色
		roleMapper.deleteByUserId(userId);

		List<Role> roleList = new ArrayList<Role>();
		for (int i = 0; i < roleIdList.size(); i++) {
			UserParam param = new UserParam();
			param.setId(userId);
			param.setRoleId(roleIdList.get(i));
			userMapper.addRole(param); // 添加角色

			Role role = roleMapper.selectByPrimaryKey(roleIdList.get(i));// 查询角色信息
			roleList.add(role);
		}
		return roleList;
	}

	/**
	 * 更新用户密码通过用户名
	 */
	@Override
	public User updatePasswordByUsername(User user) {

		user = dealPassword(user, user.getPasswordSalt());

		userMapper.updatePasswordByUsername(user);

		return user;
	}

	/**
	 * 更新用户
	 */
	@Override
	public ResponseModel updateUser(User user) {
		// 防止用户自杀
		Subject subject = SecurityUtils.getSubject();
		List<Object> principals = subject.getPrincipals().asList();
		List<User> userList = userMapper.selectByUsername(principals.get(0).toString());
		User u = userList.get(0);
		if (u == null || (u.getId() == user.getId() && user.getStatus() != 1)) {
			return new ResponseModel.Builder().error("您不能将自己停用").status(HttpStatus.BAD_REQUEST).build();
		}
		// username唯一性校验
		if (user.getUsername() != null) {
			User tmpUser = userMapper.selectByPrimaryKey(user.getId());
			if (!tmpUser.getUsername().equals(user.getUsername())) {
				Integer userCount = userMapper.selectCountByUserName(user.getUsername());
				if (!userCount.equals(0)) {
					return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("用户名已经被使用").build();
				}
			}

		}

		userMapper.updateByPrimaryKeySelective(user);

		UserParam userParam = new UserParam();
		userParam.setId(user.getId());
		Page<UserModel> page = selectByParams(userParam);
		page.getPageData().get(0).setPassword("****");

		return new ResponseModel.Builder().msg("更新成功").result(page.getPageData().get(0)).build();
	}

	/**
	 * 修改密码
	 */
	@Override
	public ResponseModel updateUserPassword(PasswordChangeParam passwordChangeParam, String username) {
		// 参数都不能为空
		if (username == null || passwordChangeParam.getPassword() == null
				|| passwordChangeParam.getNewPassword() == null || passwordChangeParam.getConfirmPassword() == null) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).build();
		}
		// 新密码和确认密码一致
		if (!passwordChangeParam.getNewPassword().equals(passwordChangeParam.getConfirmPassword())) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("新密码和确认密码不一致！").build();
		}

		User user = this.selectByUserName(username);
		if (user == null) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("账户不存在！").build();
		}

		// 密码匹配
		String encodePassword = this.encodePassword(passwordChangeParam.getPassword(), user.getPasswordSalt());
		if (!user.getPassword().equals(encodePassword)) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("密码错误").build();
		}

		String encodeNewPassword = this.encodePassword(passwordChangeParam.getNewPassword(), user.getPasswordSalt());
		user.setPassword(encodeNewPassword);

		userMapper.updatePasswordByUsername(user);

		return new ResponseModel.Builder().msg("密码修改成功").build();
	}

	/**
	 * 添加单个角色
	 * 
	 * @param param
	 */
	private void addRole(UserParam param) {
		userMapper.addRole(param);
	}

	/**
	 * 对明文密码进行加密
	 * 
	 * @param user
	 */
	private User dealPassword(User user, String salt) {
		if (salt == null) {
			String uuidString = UUID.randomUUID().toString();
			salt = uuidString.substring(uuidString.length() - 10, uuidString.length());
		}
		// 密码进行加密
		String encodePassword = this.encodePassword(user.getPassword() == null ? "" : user.getPassword(), salt);

		user.setPassword(encodePassword);
		user.setPasswordSalt(salt);
		return user;
	}

	/**
	 * 加密方法
	 * 
	 * @param userPassword
	 * @param salt
	 * @return
	 */

	private String encodePassword(String userPassword, String salt) {
		// 创建用户加密的对象
		PasswordHash passwordHash = new PasswordHash();
		passwordHash.setAlgorithmName("SHA-256");
		passwordHash.setHashIterations(6);
		// 截取uuid的最后10位作为密码的盐
		String encodePassword = passwordHash.toHex(userPassword == null ? "" : userPassword, salt);

		return encodePassword;
	}

	/**
	 * 生成邮件内容
	 * 
	 * @param username
	 * @param key
	 * @return
	 */
	private String getMailContent(String username, String key) {
		// 邮件格式为：
		// 您重置密码的链接为：http://auth.tinetcloud.com/api/password/password_modify?username=lizy&key=7c3668d8-6ad4-4230-9d2a-634d5a79ae61
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("您好,").append(username).append("先生/女士：").append("<br/>")
				.append("天润同意登录系统邀请您设置密码,请访问此链接，输入您的新密码(该链接有效时长为3天)：<br/>").append("<a href=\"")
				.append(changePasswordAddress).append("/password_set").append("?username=").append(username)
				.append("&key=").append(key).append("\">").append(changePasswordAddress).append("/password_set")
				.append("?username=").append(username).append("&key=").append(key).append("</a>").append("<br/>");

		return stringBuffer.toString();
	}
}
