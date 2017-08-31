package com.tinet.tsso.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
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
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.service.LogActionService;
import com.tinet.tsso.auth.service.RoleService;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;
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

	@Autowired
	private LogActionService logActionService;

	/**
	 * 用户查询：包含用户的角色信息等
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping
	public ResponseModel searchByParams(UserParam params) {

		Page<UserModel> userPage = userService.selectByParams(params);

		// 去除返回数据的用户密码和盐等敏感信息
		List<UserModel> userList = userPage.getPageData();
		for (int i = 0; i < userList.size(); i++) {
			userList.get(i).setPassword(null);
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
	public ResponseModel addUser(@RequestBody UserParam userParam) {
		User user = new User();
		BeanUtils.copyProperties(userParam, user);

		ResponseModel responseModel = userService.addUser(user);
		
		user.setPassword("****");
		user.setPasswordSalt("****");

		logActionService.addLogAction("添加用户", user.toString(), responseModel.get("status").equals(200) ? 1 : 0);

		return responseModel;
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
	@PutMapping("/{userId}/role")
	public ResponseModel updateUserRole(@PathVariable Integer userId, @RequestBody List<Integer> roleIdList) {

		User user = userService.get(userId);
		user.setPassword("****");
		user.setPasswordSalt("****");

		if (userId == null) {
			return new ResponseModel.Builder().error("用户Id不能为空").build();
		}
		List<Role> roleList = userService.updataUserRoleList(userId, roleIdList);

		logActionService.addLogAction("更新用户角色", "用户:" + user.toString() + "的角色", 1);

		return new ResponseModel.Builder().result(roleList).msg("角色更新成功").build();

	}

	/**
	 * 删除用户
	 * 
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deleteUser(@PathVariable Integer id) {
		// 防止用户自杀
		Subject subject = SecurityUtils.getSubject();
		List<Object> principals = subject.getPrincipals().asList();

		User u = userService.selectByUserName(principals.get(0).toString());
		u.setPassword("****");
		u.setPasswordSalt("****");
		if (u == null || (u.getId() == id)) {
			logActionService.addLogAction("删除用户", "用户:" + u.toString() + "删除自己", 0);
			return new ResponseModel.Builder().error("您不能删除自己").status(HttpStatus.BAD_REQUEST).build();
		}
		// 删除该用户拥有的角色
		roleService.deleteRoleByUserId(id);
		// 删除用户
		userService.delete(id);
		logActionService.addLogAction("删除用户", u.toString(), 1);
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
	public ResponseModel updateUser(@PathVariable Integer id, @RequestBody User user) {

		User oldUser = userService.get(id);
		if (id == null) {
			return new ResponseModel.Builder().error("id不能为空").build();
		}

		user.setId(id);
		ResponseModel responseModel = userService.updateUser(user);

		User updateUser = userService.get(id);

		if (responseModel.get("status").equals(200)) {
			logActionService.addLogAction("更新用户", "用户:" + oldUser.toString() + "更新为" + updateUser.toString(), 1);
		} else {
			logActionService.addLogAction("更新用户", "用户:" + oldUser.toString() + "更新为" + user.toString(), 0);
		}

		return responseModel;
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
		Page<UserModel> page = userService.selectByParams(param);

		for (int i = 0; i < page.getPageData().size(); i++) {
			page.getPageData().get(i).setPassword(null);
		}
		return new ResponseModel.Builder().result(page.getPageData().get(0)).build();
	}

	@GetMapping("/user_info")
	public ResponseModel selectRoles() {
		Subject subject = SecurityUtils.getSubject();
		List<Object> principals = subject.getPrincipals().asList();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("username", principals.get(0));
		return new ResponseModel.Builder().result(map).build();
	}

}
