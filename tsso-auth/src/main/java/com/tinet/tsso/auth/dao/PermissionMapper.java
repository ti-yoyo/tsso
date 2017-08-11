package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionParam;

public interface PermissionMapper extends BaseMapper<Permission, Integer>{

    int insert(Permission record);


    Permission selectByPrimaryKey(Integer id);



	/**
	 * 按照参数查询结果总数
	 * @param permissionParam
	 * @return
	 */
	Integer selectCountByParam(PermissionParam permissionParam);

	/**
	 * 按照参数查询分页信息
	 * @param permissionParam
	 * @return
	 */
	List<Permission> selectByParam(PermissionParam permissionParam);
}