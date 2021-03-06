package com.tinet.tsso.auth.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.PermissionMapper;
import com.tinet.tsso.auth.entity.Permission;
import com.tinet.tsso.auth.model.PermissionModel;
import com.tinet.tsso.auth.param.PermissionParam;
import com.tinet.tsso.auth.service.PermissionService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * Permission的Service实现类
 * 
 * @date 2017-08-10
 * @author lizy
 */
@Service
public class PermissionServiceimpl extends BaseServiceImp<Permission, Integer> implements PermissionService {

	@Autowired
	private PermissionMapper permissionMapper;

	/**
	 * 按照参数查询Permission
	 */
	@Override
	public Page<PermissionModel> selectByparam(PermissionParam permissionParam) {

		Integer totalSize = permissionMapper.selectCountByParam(permissionParam);
		List<Permission> permissionList = permissionMapper.selectByParam(permissionParam);

		List<PermissionModel> pageData = new ArrayList<PermissionModel>();
		for (int i = 0; i < permissionList.size(); i++) {
			PermissionModel permissionModel = new PermissionModel();

			BeanUtils.copyProperties(permissionList.get(i), permissionModel);
			if (permissionList.get(i).getApplication() != null) {
				permissionModel.setApplicationId(permissionList.get(i).getApplication().getId());
				permissionModel.setApplicationKey(permissionList.get(i).getApplication().getKey());
				permissionModel.setApplicationName(permissionList.get(i).getApplication().getName());
			}

			pageData.add(permissionModel);

		}

		return new Page<PermissionModel>(totalSize, pageData);
	}

	/**
	 * 添加权限
	 */
	@Override
	public ResponseModel addPermission(Permission permission) {

		Integer permissionCount = permissionMapper.selectByPermissionKey(permission.getKey());

		/**
		 * 唯一性校验
		 */
		if (!permissionCount.equals(0)) {
			return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("该权限key已经被使用").build();
		}

		permissionMapper.insertSelective(permission);

		permission = permissionMapper.selectByPrimaryKey(permission.getId());

		PermissionModel permissionModel = new PermissionModel();
		BeanUtils.copyProperties(permission, permissionModel);
		if (permission.getApplication() != null) {
			permissionModel.setApplicationId(permission.getApplication().getId());
			permissionModel.setApplicationKey(permission.getApplication().getKey());
			permissionModel.setApplicationName(permission.getApplication().getName());
		}

		return new ResponseModel.Builder().msg("添加成功").result(permissionModel).build();
	}

	/**
	 * 更新权限
	 */
	@Override
	public ResponseModel updatePermission(Permission permission) {
		/**
		 * 唯一性校验
		 */
		if (permission.getKey() != null) {
			Permission tempPermission = permissionMapper.selectByPrimaryKey(permission.getId());
			if (!tempPermission.getKey().equals(permission.getKey())) {
				return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("权限标识不可修改").build();
			}
		}

		permissionMapper.updateByPrimaryKeySelective(permission);

		PermissionModel PermissionModel = new PermissionModel();
		permission = permissionMapper.selectByPrimaryKey(permission.getId());
		BeanUtils.copyProperties(permission, PermissionModel);
		PermissionModel.setApplicationKey(permission.getApplication().getKey());
		PermissionModel.setApplicationName(permission.getApplication().getName());

		return new ResponseModel.Builder().msg("更新成功").result(PermissionModel).build();
	}

	/**
	 * 删除指定权限，并删除角色权限关联
	 */
	@Transactional
	@Override
	public void deletePermissionById(Integer permissionId) {

		permissionMapper.deleteRoleForPermission(permissionId);
		permissionMapper.deleteByPrimaryKey(permissionId);
	}

}
