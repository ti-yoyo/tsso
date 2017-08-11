package com.tinet.tsso.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.entity.User;
import com.tinet.tsso.auth.model.PermissionParam;
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

	/**
	 * 按条件查询Permission
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseModel searchPermissionByParams(PermissionParam permissionParam) {

		Page<Permission> page = permissionService.selectByparam(permissionParam);

		return new ResponseModel.Builder().page(page).msg("查询成功").build();
	}

	/**
	 * 添加权限
	 * 
	 * @param permission
	 * @return
	 */
	@PostMapping
	public ResponseModel addPermission(Permission permission) {

		permissionService.create(permission);
		permission = permissionService.get(permission.getId());

		return new ResponseModel.Builder().msg("添加成功").result(permission).build();
	}

	/**
	 * 查询指定权限的所属用户及角色
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/role_user/{id}")
	public ResponseModel searchRoleAndUserByPermissionId(@PathVariable Integer id) {

		// 查询拥有某个权限的用户
		List<User> userList = userService.selectByPermissionId(id);

		// 查询拥有某个权限的角色
		List<Role> roleList = roleService.selectByPermissionId(id);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userList", userList);
		map.put("roleList", roleList);

		return new ResponseModel.Builder().result(map).msg("查询成功").build();
	}

	/**
	 * 
	 * @param id
	 *            要删除权限的id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deletePermission(@PathVariable Integer id) {

		List<Role> roleList = roleService.selectByPermissionId(id);
		if (roleList.size() != 0) {
			return new ResponseModel(HttpStatus.FORBIDDEN, "该权限被角色使用中不能删除");
		}
		
		permissionService.delete(id);

		return new ResponseModel.Builder().msg("删除成功").build();
	}

}
