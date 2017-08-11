package com.tinet.tsso.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.PermissionMapper;
import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionParam;
import com.tinet.tsso.auth.service.PermissionService;
import com.tinet.tsso.auth.util.Page;

/**
 * Permission的Service实现类
 * 
 * @date 2017-08-10
 * @author lizy
 */
@Service
public class PermissionServiceimpl extends BaseServiceImp<Permission, Integer>implements PermissionService{

	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 按照参数查询Permission
	 */
	@Override
	public Page<Permission> selectByparam(PermissionParam permissionParam) {

		Integer totalSize = permissionMapper.selectCountByParam(permissionParam);
		List<Permission> pageData = permissionMapper.selectByParam(permissionParam);

		return new Page<Permission>(totalSize, pageData);
	}

}
