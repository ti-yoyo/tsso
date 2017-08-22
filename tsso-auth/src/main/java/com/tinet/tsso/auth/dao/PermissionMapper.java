package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.param.PermissionParam;

/**
 * 权限的Mapper
 * 
 * @date 2017-08-14
 * @author lizy
 */
public interface PermissionMapper extends BaseMapper<Permission, Integer> {

	/**
	 * 按照参数查询结果总数
	 * 
	 * @param permissionParam
	 * @return
	 */
	Integer selectCountByParam(PermissionParam permissionParam);

	/**
	 * 按照参数查询分页信息
	 * 
	 * @param permissionParam
	 * @return
	 */
	List<Permission> selectByParam(PermissionParam permissionParam);

	/**
	 * 查询该权限标识的数量
	 * @param key
	 * @return
	 */
	Integer selectByPermissionKey(String key);
}