package com.tinet.tsso.auth.controller;

import java.util.Date;
import java.util.List;

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
import org.springframework.web.context.annotation.RequestScope;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.model.RoleModel;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.param.RoleParam;
import com.tinet.tsso.auth.param.UserAndRoleParam;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.service.RoleService;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 角色的Controller
 * 
 * @date 2017-08-09
 * @author lizy
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	/**
	 * 根据参数查询分页信息
	 * 
	 * @param roleParam
	 * @return
	 */
	@GetMapping
	public ResponseModel searchRoleByParams(RoleParam roleParam) {

		Page<RoleModel> page = roleService.selectRoleByParams(roleParam);

		return new ResponseModel.Builder().result(page).msg("查询成功").build();
	}

	/**
	 * 新建角色
	 * 
	 * @param role
	 * @return
	 */
	@PostMapping
	public ResponseModel addRole(@RequestBody RoleParam roleParam) {
		
		Role role = new Role();
		BeanUtils.copyProperties(roleParam, role);
		
		role.setCreateTime(new Date());
		// 添加角色
		roleService.create(role);
		
		return this.searchOneByRoleId(role.getId());
	}

	/**
	 * 为角色添加用户
	 * 
	 * @param roleId
	 *            角色id
	 * @param userId
	 *            用户id
	 * @return 用户的详细信息
	 */
	@PostMapping("/user")
	public ResponseModel addUserForRole(@RequestBody UserAndRoleParam userAndRole) {

		roleService.addUser(userAndRole.getRoleId(), userAndRole.getUserId());

		// 查询指定id的用户
		UserParam param = new UserParam();
		param.setId(userAndRole.getUserId());
		Page<UserModel> page = userService.selectByParams(param);

		if (page.getPageData() == null) {
			return new ResponseModel.Builder().error("该用户不存在").build();
		}
		//去除密码等敏感信息
		page.getPageData().get(0).setPassword(null);
		return new ResponseModel.Builder().result(page.getPageData().get(0)).build();
	}

	/**
	 * 按照Id查询单个角色
	 * 
	 * @param roleId
	 * @return 查询单个角色
	 */
	@GetMapping("/{roleId}")
	public ResponseModel searchOneByRoleId(@PathVariable Integer roleId) {

		RoleParam roleParam = new RoleParam();
		roleParam.setId(roleId);

		Page<RoleModel> page = roleService.selectRoleByParams(roleParam);

		return new ResponseModel.Builder().result(page.getPageData().get(0)).msg("查询成功").build();

	}

	/**
	 * 删除角色的全部权限关联并删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deleteRole(@PathVariable("id") Integer roleId) {

		Integer userCount = roleService.selectUserCount(roleId);

		if (!userCount.equals(0)) {
			return new ResponseModel.Builder().error("删除失败,该角色被用户使用中").status(HttpStatus.FORBIDDEN).build();
		}

		roleService.deletePermissionByRoleId(roleId);
		roleService.delete(roleId);

		return new ResponseModel.Builder().msg("删除成功").build();

	}

	/**
	 * 更新角色的权限列表
	 * 
	 * @param roleId
	 *            角色id
	 * @param permissionIdList
	 *            权限id列表
	 * @return
	 */
	@PutMapping("/{id}/permission")
	public ResponseModel updatePermissionForRole(@PathVariable("id") Integer roleId,
			@RequestBody List<Integer> permissionIdList) {

		List<Permission> permissionList = roleService.updatePermissionList(roleId, permissionIdList);

		return new ResponseModel.Builder().msg("更新成功").result(permissionList).build();
	}

}
