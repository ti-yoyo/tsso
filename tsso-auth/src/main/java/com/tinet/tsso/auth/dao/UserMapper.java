package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.param.UserParam;

/**
 * 用户的Mapper
 * 
 * @date 2017-08-14
 * @author lizy
 */
public interface UserMapper extends BaseMapper<User, Integer> {

	/**
	 * @param params
	 *            查询符合条件的所有数据的个数，即该出没有start和limit限制分页
	 * @return
	 */
	Integer selectCountByParams(UserParam params);

	/**
	 * @param params
	 *            返回符合条件的数据列表
	 * @return
	 */
	List<User> selectByParams(UserParam params);

	/**
	 * 
	 * @param params
	 *            为指定id的User添加权限roleId
	 */
	void addRole(UserParam params);

	/**
	 * @param roleId
	 *            统计指定角色的用户数量
	 * @return
	 */
	Integer selectCountUserByRoleId(Integer roleId);

	/**
	 * 通过权限d查询用户
	 * 
	 * @param permissionId
	 * @return
	 */
	List<User> selectByPermissionId(Integer permissionId);

	/**
	 * 查询指定用户名的数量
	 * @param username
	 * @return
	 */
	Integer selectCountByUserName(String username);

}