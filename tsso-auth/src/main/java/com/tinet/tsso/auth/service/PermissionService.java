package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionModel;
import com.tinet.tsso.auth.param.PermissionParam;
import com.tinet.tsso.auth.util.Page;

/**
 * 权限的Service
 * 
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
	Page<PermissionModel> selectByparam(PermissionParam permissionParam);

	/**
	 *添加应用
	 * @param permissionParam
	 * @return
	 */
	Permission addPermission(PermissionParam permissionParam);

}
