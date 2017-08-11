package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.RoleMapper;
import com.tinet.tsso.auth.dao.UserMapper;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserParam;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;

/**
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
	public Page<User> selectByParams(UserParam params) {

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

		// 符合条件的角色查询
		for (int i = 0; i < userList.size(); i++) {
			List<Role> roleList = roleMapper.getRoleByUser(userList.get(i));
			userList.get(i).setRoleList(roleList);
		}
		return new Page<User>(totalSize, userList);
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

}
