package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Role;

/**
 * @date 2017-08-09
 * @author lizy
 */

public interface RoleService extends BaseService<Role, Integer>{

	/**
	 * @param userId 删除指定用户的全部角色
	 */
	void deleteRoleByUserId(Integer userId);

}
