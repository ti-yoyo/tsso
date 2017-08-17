package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.PermissionMapper;
import com.tinet.tsso.auth.dao.RoleMapper;
import com.tinet.tsso.auth.dao.UserMapper;
import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.entity.Role;
import com.tinet.tsso.auth.model.RoleModel;
import com.tinet.tsso.auth.param.RoleParam;
import com.tinet.tsso.auth.service.RoleService;
import com.tinet.tsso.auth.util.Page;

/**
 * 角色Service的实现类
 * 
 * @date 2017-08-09
 * @author lizy
 */
@Service
public class RoleServiceImpl extends BaseServiceImp<Role, Integer> implements RoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 删除指定用户的角色信息
	 */
	@Override
	@Transactional
	public void deleteRoleByUserId(Integer userId) {
		roleMapper.deleteByUserId(userId);
	}

	/**
	 * 查询角色信息
	 */
	@Override
	public Page<RoleModel> selectRoleByParams(RoleParam roleParam) {
		if (roleParam.getStart() == null) {
			roleParam.setStart(0);
		}
		if (roleParam.getLimit() == null) {
			roleParam.setLimit(10);
		}

		Integer totalSize = roleMapper.selectCountByParams(roleParam);

		List<Role> roleList = roleMapper.selectByParams(roleParam);

		List<RoleModel> pageData = new ArrayList<RoleModel>();

		// 统计每个角色的用户数
		for (int i = 0; i < roleList.size(); i++) {

			RoleModel roleModel = new RoleModel();
			Integer roleId = roleList.get(i).getId();
			BeanUtils.copyProperties(roleList.get(i), roleModel);
			Integer userCount = userMapper.selectCountUserByRoleId(roleId);
			roleModel.setUserCount(userCount);
			pageData.add(roleModel);
		}

		return new Page<RoleModel>(totalSize, pageData);
	}

	/**
	 * 为角色添加用户
	 */
	@Override
	public void addUser(Integer roleId, Integer userId) {
		RoleParam roleParam = new RoleParam();
		roleParam.setId(roleId);
		roleParam.setUserId(userId);

		if (roleMapper.selectUserRoleByUserIdAndRoleId(roleParam).equals(0)) {// 当指定的用户角色关联不存在时才进行插入
			roleMapper.addUser(roleParam);
		}
	}

	/**
	 * 查询一个角色id的用户数量
	 */
	@Override
	public Integer selectUserCount(Integer roleId) {

		RoleParam roleParam = new RoleParam();
		roleParam.setId(roleId);

		Integer userCount = userMapper.selectCountUserByRoleId(roleId);

		return userCount;
	}

	/**
	 * 
	 */
	@Override
	@Transactional
	public void deletePermissionByRoleId(Integer roleId) {
		roleMapper.deletePermissionByRoleId(roleId);
	}

	/**
	 * 为角色添加权限
	 */
	@Override
	@Transactional
	public void addPermission(Integer roleId, List<Integer> permissionIdList) {

		for (int i = 0; i < permissionIdList.size(); i++) {
			RoleParam param = new RoleParam();
			roleMapper.addPermissionForRole(param);
		}

	}

	@Override
	@Transactional
	public List<Permission> updatePermissionList(Integer roleId, List<Integer> permissionIdList) {

		roleMapper.deletePermissionByRoleId(roleId);

		List<Permission> permissionList = new ArrayList<>();
		for (int i = 0; i < permissionIdList.size(); i++) {
			RoleParam param = new RoleParam();
			param.setId(roleId);
			Integer permissionId = permissionIdList.get(i);
			param.setPermissionId(permissionId);

			roleMapper.addPermissionForRole(param);// 添加权限

			permissionList.add(permissionMapper.selectByPrimaryKey(permissionId));// 获取该权限的详细信息
		}

		return permissionList;

	}

	/**
	 * 按照权限Id查询用户
	 */
	@Override
	public List<Role> selectByPermissionId(Integer permissionId) {
		List<Role> roleList = roleMapper.selectByPermissionId(permissionId);
		return roleList;
	}
}
