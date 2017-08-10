package com.tinet.tsso.auth.service;

import java.util.List;

import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserParam;
import com.tinet.tsso.auth.util.Page;

/**
 * @date 2017-08-09
 * @author lizy
 */

public interface UserService extends BaseService<User, Integer> {

	/**
	 * @param params
	 *            查询参数
	 * @return 用户查询的分页数据
	 */
	Page<User> selectByParams(UserParam params);

	/**
	 * @param userId
	 * @param roleIdList
	 */
	void addRoles(Integer userId, List<Integer> roleIdList);

	/**
	 * 更新用户的角色信息
	 * @param userId
	 * @param roleIdList
	 * @return
	 */
	List<Role> updataUserRoleList(Integer userId, List<Integer> roleIdList);




}
