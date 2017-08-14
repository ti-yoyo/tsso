package com.tinet.tsso.auth.service;

import java.util.List;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.model.RoleParam;
import com.tinet.tsso.auth.util.Page;

/**
 * @date 2017-08-09
 * @author lizy
 */

public interface RoleService extends BaseService<Role, Integer> {

	/**
	 * @param userId
	 *            删除指定用户的全部角色
	 */
	void deleteRoleByUserId(Integer userId);

	/**
	 * 通过参数查询角色信息
	 * 
	 * @param roleParam
	 * @return
	 */
	Page<Role> selectRoleByParams(RoleParam roleParam);

	/**
	 * 为角色添加用户
	 * 
	 * @param roleId
	 *            角色id
	 * @param userId
	 *            用户id
	 */
	void addUser(Integer roleId, Integer userId);

	/**
	 * 查询指定角色的用户数量
	 * 
	 * @param roleId
	 * @return
	 */
	Integer selectUserCount(Integer roleId);

	/**
	 * 删除某个角色的全部权限
	 * 
	 * @param roleId
	 *            指定角色的id
	 */
	void deletePermissionByRoleId(Integer roleId);

	/**
	 * @param roleId
	 *            添加角色的id
	 * @param permissionIdList
	 *            添加的权限id列表
	 */
	void addPermission(Integer roleId, List<Integer> permissionIdList);

	/**
	 * 更新指定角色的权限列表
	 * 
	 * @param roleId
	 * @param permissionIdList
	 * @return 权限列表
	 */
	List<Permission> updatePermissionList(Integer roleId, List<Integer> permissionIdList);

	/**
	 * 通过权限id查询角色
	 * 
	 * @param id
	 * @return
	 */
	List<Role> selectByPermissionId(Integer permissionId);

}
