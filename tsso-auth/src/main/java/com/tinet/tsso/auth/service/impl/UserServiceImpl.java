package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.RoleMapper;
import com.tinet.tsso.auth.dao.UserMapper;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.PasswordHash;
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
	 * 添加单个角色
	 * 
	 * @param param
	 */
	private void addRole(UserParam param) {
		userMapper.addRole(param);
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
	 * 添加用户
	 */
	@Override
	public ResponseModel addUser(User user) {
		/**
		 * 用户的账号和全名不能为空
		 */
		if (user.getUsername() == null || user.getFullName() == null) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("用户名和全名都不能为空").build();
		}
		user.setCreateTime(new Date());

		Integer userCount = userMapper.selectCountByUserName(user.getUsername());
		if (!userCount.equals(0)) {
			return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("用户名已经被使用").build();
		}

		user = dealPassword(user, null);

		userMapper.insertSelective(user);

		user = userMapper.selectByPrimaryKey(user.getId());

		// 去除密码和盐等敏感信息
		user.setPassword(null);
		user.setPasswordSalt(null);

		return new ResponseModel.Builder().result(user).msg("添加成功").build();
	}

	/**
	 * 对明文密码进行加密
	 * 
	 * @param user
	 */
	private User dealPassword(User user, String uuidString) {

		// 创建用户加密的对象
		PasswordHash passwordHash = new PasswordHash();
		passwordHash.setAlgorithmName("SHA-256");
		passwordHash.setHashIterations(6);
		// 截取uuid的最后10位作为密码的盐
		if (uuidString == null) {
			uuidString = UUID.randomUUID().toString();
		}
		String salt = uuidString.substring(uuidString.length() - 10, uuidString.length());
		// 密码进行加密
		String encodePassword = passwordHash.toHex(user.getPassword() == null ? "" : user.getPassword(), salt);

		user.setPassword(encodePassword);
		user.setPasswordSalt(salt);
		return user;
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
	 * 更新用户密码通过用户名
	 */
	@Override
	public User updatePasswordByUsername(User user) {
		
		user =dealPassword(user,user.getPasswordSalt());
		
		userMapper.updatePasswordByUsername(user);
		
		return user;
	}

}
