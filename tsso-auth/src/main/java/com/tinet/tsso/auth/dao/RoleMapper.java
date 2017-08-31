package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.param.RoleParam;
import com.tinet.tsso.auth.param.UserAndRoleParam;

/**
 * 角色的mapper
 * 
 * @date 2017-08-14
 * @author lizy
 */
public interface RoleMapper extends BaseMapper<Role, Integer> {

	/**
	 * 
	 * @param u
	 *            通过用户的id查询角色信息
	 * @return
	 */
	List<Role> getRoleByUser(User u);

	/**
	 * @param userId
	 *            删除指定用户的全部角色
	 */
	void deleteByUserId(Integer userId);

	/**
	 * 根据参数查询角色数量（不包括分页的limit和start）
	 * 
	 * @param roleParam
	 * @return
	 */
	Integer selectCountByParams(RoleParam roleParam);

	/**
	 * 根据参数进行分页查询
	 * 
	 * @param roleParam
	 * @return
	 */
	List<Role> selectByParams(RoleParam roleParam);

	/**
	 * 为指定roleId添加角色
	 * 
	 * @param roleParam
	 */
	void addUser(RoleParam roleParam);

	/**
	 * 查询指定角色，指定用户id的数量 该功能用于防止重复
	 * 
	 * @param roleParam
	 * @return
	 */
	Integer selectUserRoleByUserIdAndRoleId(RoleParam roleParam);

	/**
	 * 删除某个角色的全部权限
	 * 
	 * @param roleId
	 */
	void deletePermissionForRoleId(Integer roleId);

	/**
	 * 为指定角色id添加角色
	 * 
	 * @param roleId
	 * @param integer
	 */
	void addPermissionForRole(RoleParam roleParam);

	/**
	 * @param permissionId
	 * @return
	 */
	List<Role> selectByPermissionId(Integer permissionId);

	/**
	 * 根据权限的标识查询
	 * 
	 * @param key
	 * @return
	 */
	Integer selectCountByRoleKey(String key);

	/**
	 * 删除角色所拥有的指定角色
	 * 
	 * @param roleId
	 */
	void deleteUserForRoleId(Integer roleId);

	/**
	 * 删除某个角色的一个用户
	 * 
	 * @param userAndRoleParam
	 */
	void deleteOneUserForRole(UserAndRoleParam userAndRoleParam);

}