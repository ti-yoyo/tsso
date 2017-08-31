package com.tinet.tsso.auth.service;

import java.util.List;
import java.util.Map;

import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.model.UsernameAndUuidModel;
import com.tinet.tsso.auth.param.PasswordChangeParam;
import com.tinet.tsso.auth.param.PasswordParam;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 用户的Service
 * 
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
	 * 
	 * @param userId
	 * @param roleIdList
	 * @return
	 */
	List<Role> updataUserRoleList(Integer userId, List<Integer> roleIdList);

	/**
	 * 通过权限id查询用户信息
	 * 
	 * @param id
	 * @return
	 */
	List<UserModel> selectByPermissionId(Integer permissionId);

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	ResponseModel addUser(User user);

	/**
	 * 按照用户名查询用户
	 * 
	 * @param username
	 * @return
	 */
	User selectByUserName(String username);

	/**
	 * 按照用户名更新密码
	 * 
	 * @param user
	 * @return
	 */
	User updatePasswordByUsername(User user);

	/**
	 * 更新用户
	 * 
	 * @param user
	 * @return
	 */
	ResponseModel updateUser(User user);

	/**
	 * @param passwordParam
	 * @param userModelMap
	 * @param username
	 * @param key
	 * @param effictiveTime
	 * @return
	 */
	ResponseModel setPassword(PasswordParam passwordParam, Map<String, UsernameAndUuidModel> userModelMap,
			String username, String key, Integer effictiveTime);

	/**
	 * 更新用户的密码
	 * 
	 * @param passwordChangeParam
	 * @param username
	 * @return
	 */
	ResponseModel updateUserPassword(PasswordChangeParam passwordChangeParam, String username);

}
