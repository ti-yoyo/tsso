package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;

public interface RoleMapper extends BaseMapper<Role, Integer>{

    int insert(Role record);


    Role selectByPrimaryKey(Integer id);


/**
 * 
 * @param u 通过用户的id查询角色信息
 * @return
 */
	List<Role> getRoleByUser(User u);

	/**
	 * @param userId 删除指定用户的全部角色
	 */
	void deleteByUserId(Integer userId);
}