package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionModel;
import com.tinet.tsso.auth.param.PermissionParam;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 权限的Service
 * 
 * @date 2017-08-10
 * @author lizy
 */

public interface PermissionService extends BaseService<Permission, Integer> {

	/**
	 * 通过权限参数查询权限信息
	 * 
	 * @param permissionParam
	 * @return
	 */
	Page<PermissionModel> selectByparam(PermissionParam permissionParam);

	/**
	 * 添加应用
	 * 
	 * @param permission
	 * @return
	 */
	ResponseModel addPermission(Permission permission);

	/**
	 * 更新权限
	 * 
	 * @param permission
	 * @return
	 */
	ResponseModel updatePermission(Permission permission);

	/**
	 * 删除指定权限，并删除角色权限的关联
	 * 
	 * @param id
	 */
	void deletePermissionById(Integer id);

}
