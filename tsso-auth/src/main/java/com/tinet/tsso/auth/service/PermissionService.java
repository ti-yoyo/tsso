package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionParam;
import com.tinet.tsso.auth.util.Page;

/**
 * @date 2017-08-10
 * @author lizy
 */

public interface PermissionService extends BaseService<Permission, Integer>{

	/**
	 * 通过权限参数查询权限信息
	 * 
	 * @param permissionParam
	 * @return
	 */
	Page<Permission> selectByparam(PermissionParam permissionParam);

}
