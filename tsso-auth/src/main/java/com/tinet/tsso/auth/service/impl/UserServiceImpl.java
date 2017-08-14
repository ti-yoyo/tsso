package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

		roleMapper.deleteByUserId(userId);
		;// 删除所有的角色

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
	public List<User> selectByPermissionId(Integer permissionId) {
		List<User> userList = userMapper.selectByPermissionId(permissionId);
		return userList;
	}

	/**
	 * 添加用户
	 */
	@Override
	public User addUser(User user) {
		userMapper.insertSelective(user);
		user = userMapper.selectByPrimaryKey(user.getId());

		return user;
	}

	/**
	 * 对明文密码进行加密
	 * 
	 * @param user
	 */
	private User dealPassword(User user) {

		// 创建用户加密的对象
		PasswordHash passwordHash = new PasswordHash();
		passwordHash.setAlgorithmName("SHA-256");
		passwordHash.setHashIterations(6);
		// 截取uuid的最后10位作为密码的盐
		String uuidString = UUID.randomUUID().toString();
		String salt = uuidString.substring(uuidString.length() - 10, uuidString.length());
		// 密码进行加密
		String encodePassword = passwordHash.toHex(user.getPassword() == null ? "" : user.getPassword(), salt);

		user.setPassword(encodePassword);
		user.setPasswordSalt(salt);
		return user;
	}

}
