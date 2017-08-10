package com.tinet.tsso.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void addRoles(Integer userId, List<Integer> roleIdList) {
		if (roleIdList.size() != 0) {
			for (int i = 0; i < roleIdList.size(); i++) {
				UserParam param =new UserParam();
				param.setId(userId);
				param.setRoleId(roleIdList.get(i));
				addRole(param);
			}
		}
	}

	private void addRole(UserParam param) {
		userMapper.addRole(param);
		
	}

}
