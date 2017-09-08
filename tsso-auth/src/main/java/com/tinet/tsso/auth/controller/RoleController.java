package com.tinet.tsso.auth.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.RoleModel;
import com.tinet.tsso.auth.param.RoleParam;
import com.tinet.tsso.auth.param.UserAndRoleParam;
import com.tinet.tsso.auth.param.UserParam;
import com.tinet.tsso.auth.service.LogActionService;
import com.tinet.tsso.auth.service.PermissionService;
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

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private LogActionService logActionService;

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

		ResponseModel responseModel = roleService.createRole(role);

		logActionService.addLogAction("添加角色", role.toString(), responseModel.get("status").equals(200) ? 1 : 0);

		return responseModel;
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

		User user = userService.get(userAndRole.getUserId());

		roleService.addUser(userAndRole.getRoleId(), userAndRole.getUserId());

		logActionService.addLogAction("为角色添加用户", user.toString(), 1);

		// 查询指定id的用户
		UserParam param = new UserParam();
		param.setId(userAndRole.getUserId());
		Page<User> page = userService.selectByParams(param);

		if (page.getPageData() == null) {
			return new ResponseModel.Builder().error("该用户不存在").build();
		}

		// 去除密码等敏感信息
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

		Role role = roleService.get(roleId);

		roleService.deleteRole(roleId);

		logActionService.addLogAction("删除角色", role.toString(), 1);
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
		Role role = roleService.get(roleId);

		List<Permission> oldPermissionList = new ArrayList<>();
		for (int i = 0; i < permissionIdList.size(); i++) {
			oldPermissionList.add(permissionService.get(permissionIdList.get(i)));
		}

		List<Permission> permissionList = roleService.updatePermissionList(roleId, permissionIdList);

		logActionService.addLogAction("更新角色的权限列表", role.toString(), 1);

		return new ResponseModel.Builder().msg("更新成功").result(permissionList).build();
	}

	/**
	 * 更新角色
	 * 
	 * @param id
	 * @param roleParam
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseModel updateRole(@PathVariable("id") Integer id, @RequestBody RoleParam roleParam) {

		Role tmpRole = roleService.get(id);

		Role role = new Role();
		BeanUtils.copyProperties(roleParam, role);
		role.setId(id);

		logActionService.addLogAction("更新角色", tmpRole + "更新为" + roleService.get(id), 1);
		return roleService.updateRole(role);
	}

	/**
	 * 更新角色
	 * 
	 * @param id
	 * @param roleParam
	 * @return
	 */
	@DeleteMapping("/{roleId}/user/{userId}")
	public ResponseModel deleteUserForRole(@PathVariable Integer roleId, @PathVariable Integer userId) {

		Role role = roleService.get(roleId);
		User user = userService.get(userId);
		
		UserAndRoleParam userAndRoleParam = new UserAndRoleParam();

		userAndRoleParam.setRoleId(roleId);
		userAndRoleParam.setUserId(userId);
		roleService.deleteOneUserForRole(userAndRoleParam);
		
		user.setPassword("****");
		user.setPasswordSalt("****");
		logActionService.addLogAction("删除角色的指定用户", "角色" + role.toString() + "删除了用户" + user.toString(), 1);
		
		return new ResponseModel.Builder().msg("删除成功").build();
	}

}
