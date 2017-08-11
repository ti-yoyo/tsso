package com.tinet.tsso.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.RoleMapper;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.service.RoleService;

/**
 * @date 2017-08-09
 * @author lizy
 */
@Service
public class RoleServiceImpl extends BaseServiceImp<Role, Integer>implements RoleService{


	@Autowired
	private RoleMapper roleMapper;
	
	@Override
	public void deleteRoleByUserId(Integer userId) {
		// TODO Auto-generated method stub
		roleMapper.deleteByUserId(userId);
	}

}
