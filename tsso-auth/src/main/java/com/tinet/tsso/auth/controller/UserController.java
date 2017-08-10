package com.tinet.tsso.auth.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * 用户的查询包含用户的角色信息等
	 * 
	 * @param params
	 * @return
	 */
	@GetMapping
	public ResponseModel searchByParams(UserParam params) {

		Page<User> userPage = userService.selectByParams(params);

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

		userService.create(user);

		user = userService.get(user.getId());

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
	@PutMapping("/role/{userId}")
	public ResponseModel updateUserRole(@PathVariable Integer userId,@RequestBody List<Integer> roleIdList) {

		
		if (userId == null) {
			new ResponseModel.Builder().error("用户Id不能为空").build();
		}
		List<Role> roleList = userService.updataUserRoleList(userId,roleIdList);

		return new ResponseModel.Builder().result(roleList).msg("角色更新成功").build();

	}
	
	/**
	 * 删除用户
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deleteUser(@PathVariable Integer id){
		//删除该用户拥有的角色
		roleService.deleteRoleByUserId(id);
		//删除用户
		userService.delete(id);
		
		return new ResponseModel.Builder().msg("删除成功").build();
	}
	
	/**
	 * 更新用户信息
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseModel updateUser(@PathVariable Integer id ,User user){
		
		if(id == null ){
			return new ResponseModel.Builder().error("id不能为空").build();
		}
		user.setId(id);
		userService.update(user);
		
		return this.getOneUserByUserId(id);
	}
	/**
	 * 获取指定id的详细信息
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseModel getOneUserByUserId(@PathVariable Integer id){
		//查询该角色完整信息
		UserParam param=new UserParam();
		param.setId(id);
		Page<User> page= userService.selectByParams(param);
		
		if(page.getPageData() == null){
			return new ResponseModel.Builder().error("该用户不存在").build();
		}
		return new ResponseModel.Builder().result(page.getPageData().get(0)).build();
	}

}
