package com.tinet.tsso.auth.service;

import java.util.List;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.model.RoleModel;
import com.tinet.tsso.auth.param.RoleParam;
import com.tinet.tsso.auth.param.UserAndRoleParam;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 角色的Service
 * 
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
	Page<RoleModel> selectRoleByParams(RoleParam roleParam);

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

	/**
	 * 新建角色
	 * 
	 * @param role
	 * @return
	 */
	ResponseModel createRole(Role role);

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @return
	 */
	ResponseModel updateRole(Role role);

	/**
	 * 根据用户id查询角色信息
	 * 
	 * @param userId
	 * @return
	 */
	List<Role> selectRoleByUserId(Integer userId);

	/**
	 * 删除指定角色
	 * 
	 * @param roleId
	 */
	void deleteRole(Integer roleId);

	/**
	 * 删除角色
	 * @param userAndRoleParam
	 */
	void deleteOneUserForRole(UserAndRoleParam userAndRoleParam);

}
