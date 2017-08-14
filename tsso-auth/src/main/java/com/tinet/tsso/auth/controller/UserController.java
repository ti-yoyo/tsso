package com.tinet.tsso.auth.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.UserParam;
import com.tinet.tsso.auth.service.RoleService;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.PasswordHash;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 用户的Controller
 * 
 * @date 2017-08-09
 * @author lizy
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 用户查询：包含用户的角色信息等
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping
	public ResponseModel searchByParams(UserParam params) {

		Page<User> userPage = userService.selectByParams(params);

		// 去除返回数据的用户密码和盐等敏感信息
		List<User> userList = userPage.getPageData();
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setPassword(null);
			userList.get(i).setPasswordSalt(null);
		}
		userPage.setPageData(userList);

		return new ResponseModel.Builder().page(userPage).msg("查询成功").build();

	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping
	public ResponseModel addUser(User user) {

		if (user.getUsername() == null || user.getFullName() == null) {
			new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("用户名和全名都不能为空").build();
		}
		user.setCreateTime(new Date());
		// 对user密码进行加密处理
		user = dealPassword(user);

		// 创建用户
		userService.create(user);

		// 查询新添加的用户
		user = userService.get(user.getId());

		// 去除密码和盐等敏感信息
		user.setPassword(null);
		user.setPasswordSalt(null);

		return new ResponseModel.Builder().result(user).msg("添加成功").build();

	}

	/**
	 * 修改用户的角色信息
	 * 
	 * @param userId
	 *            用户id
	 * @param roleIdList
	 *            角色列表
	 * @return
	 */
	@PutMapping("/{userId}/role/")
	public ResponseModel updateUserRole(@PathVariable Integer userId, @RequestBody List<Integer> roleIdList) {

		if (userId == null) {
			new ResponseModel.Builder().error("用户Id不能为空").build();
		}
		List<Role> roleList = userService.updataUserRoleList(userId, roleIdList);

		return new ResponseModel.Builder().result(roleList).msg("角色更新成功").build();

	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deleteUser(@PathVariable Integer id) {
		// 删除该用户拥有的角色
		roleService.deleteRoleByUserId(id);
		// 删除用户
		userService.delete(id);

		return new ResponseModel.Builder().msg("删除成功").build();
	}

	/**
	 * 更新用户信息
	 * 
	 * @param id
	 *            要更新用户的id
	 * @param user
	 *            要更新的用户信息
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseModel updateUser(@PathVariable Integer id, User user) {

		if (id == null) {
			return new ResponseModel.Builder().error("id不能为空").build();
		}
		user.setId(id);
		userService.update(user);

		return this.getOneUserByUserId(id);
	}

	/**
	 * 获取指定id的用户的详细信息
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseModel getOneUserByUserId(@PathVariable Integer id) {
		// 查询该角色完整信息
		UserParam param = new UserParam();
		param.setId(id);
		Page<User> page = userService.selectByParams(param);

		if (page.getPageData() == null) {
			return new ResponseModel.Builder().error("该用户不存在").build();
		}
		return new ResponseModel.Builder().result(page.getPageData().get(0)).build();
	}

	/**
	 * 对明文密码进行加密
	 * 
	 * @param user
	 */
	private User dealPassword(User user) {

		// 创建用户加密的对象
		PasswordHash passwordHash = new PasswordHash();
		passwordHash.setAlgorithmName("SHA-256");
		passwordHash.setHashIterations(6);
		// 截取uuid的最后10位作为密码的盐
		String uuidString = UUID.randomUUID().toString();
		String salt = uuidString.substring(uuidString.length() - 10, uuidString.length());
		// 密码进行加密
		String encodePassword = passwordHash.toHex(user.getPassword() == null ? "" : user.getPassword(), salt);

		user.setPassword(encodePassword);
		user.setPasswordSalt(salt);
		return user;
	}

}
