package com.tinet.tsso.auth.controller;

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
import com.tinet.tsso.auth.model.PermissionModel;
import com.tinet.tsso.auth.model.UserModel;
import com.tinet.tsso.auth.param.PermissionParam;
import com.tinet.tsso.auth.service.LogActionService;
import com.tinet.tsso.auth.service.PermissionService;
import com.tinet.tsso.auth.service.RoleService;
import com.tinet.tsso.auth.service.UserService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 权限的Controller
 * 
 * @date 2017-08-10
 * @author lizy
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private LogActionService logActionService;

	/**
	 * 按条件查询权限
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseModel searchPermissionByParams(PermissionParam permissionParam) {

		Page<PermissionModel> page = permissionService.selectByparam(permissionParam);

		return new ResponseModel.Builder().page(page).msg("查询成功").build();
	}

	/**
	 * 添加权限
	 * 
	 * @param permission
	 * @return
	 */
	@PostMapping
	public ResponseModel addPermission(@RequestBody PermissionParam permissionParam) {

		Permission permission = new Permission();
		BeanUtils.copyProperties(permissionParam, permission);
		permission.setCreateTime(new Date());

		ResponseModel responseModel = permissionService.addPermission(permission);

		logActionService.addLogAction("添加权限", permission.toString(), responseModel.get("status").equals(200) ? 1 : 0);

		return responseModel;
	}

	/**
	 * 查询指定权限的所属用户
	 * 
	 * @param id
	 *            要查询的权限id
	 * @return
	 */
	@GetMapping("/{id}/user")
	public ResponseModel searchUserByPermissionId(@PathVariable Integer id) {
		// 查询拥有某个权限的用户
		List<UserModel> userList = userService.selectByPermissionId(id);

		return new ResponseModel.Builder().result(userList).msg("查询成功").build();
	}

	/**
	 * 查询指定权限的所属用户
	 * 
	 * @param id
	 *            要查询的权限id
	 * @return
	 */
	@GetMapping("/{id}/role")
	public ResponseModel searchRoleByPermissionId(@PathVariable Integer id) {
		// 查询拥有某个权限的角色
		List<Role> roleList = roleService.selectByPermissionId(id);
		return new ResponseModel.Builder().result(roleList).msg("查询成功").build();
	}

	/**
	 * 
	 * @param id
	 *            要删除权限的id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deletePermission(@PathVariable Integer id) {

		Permission permission = permissionService.get(id);

		
		permissionService.deletePermissionById(id);

		logActionService.addLogAction("删除权限", permission.toString(), 1);

		return new ResponseModel.Builder().msg("删除成功").build();
	}

	/**
	 * 权限的更新
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseModel updatePermission(@PathVariable Integer id, @RequestBody PermissionParam permissionParam) {

		Permission tmpPermission = permissionService.get(id);

		Permission permission = new Permission();

		BeanUtils.copyProperties(permissionParam, permission);
		permission.setId(id);

		logActionService.addLogAction("更新权限", tmpPermission.toString() + "更新为" + permissionService.get(id), 1);

		return permissionService.updatePermission(permission);
	}

}
