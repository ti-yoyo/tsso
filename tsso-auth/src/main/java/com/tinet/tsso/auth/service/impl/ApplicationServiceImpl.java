package com.tinet.tsso.auth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.ApplicationMapper;
import com.tinet.tsso.auth.dao.PermissionMapper;
import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.param.PermissionParam;
import com.tinet.tsso.auth.service.ApplicationService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 应用的Serice的实现类
 * 
 * @date 2017-08-08
 * @author lizy
 */
@Service
public class ApplicationServiceImpl extends BaseServiceImp<Application, Integer> implements ApplicationService {

	@Autowired
	private ApplicationMapper applicationMapper;

	@Autowired
	private PermissionMapper permissionMapper;
	

	/**
	 * 查询应用的分页信息
	 */
	@Override
	public Page<Application> getAll() {

		Integer totalSize = applicationMapper.selectCount();

		List<Application> pageData = applicationMapper.selectAll();

		return new Page<Application>(totalSize, pageData);
	}

	/**
	 * 添加应用
	 */
	@Override
	public ResponseModel addApplication(Application application) {

		if (application.getKey() == null || application.getName() == null || application.getStatus() == null) {
			return new ResponseModel.Builder().status(HttpStatus.BAD_REQUEST).error("应用标识、应用名、应用状态均不能为空").build();
		}
		Integer applicationCount = applicationMapper.selectCountByApplicationKey(application.getKey());
		
		if(!applicationCount.equals(0)) {
			return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("该角色标识已经被使用").build();
		}

		application.setCreateTime(new Date());
		// 添加应用
		applicationMapper.insertSelective(application);
		// 按照应用查询应用信息并返回
		application = applicationMapper.selectByPrimaryKey(application.getId());
		
		return new ResponseModel.Builder().result(application).msg("添加成功").build();
	}

	/**
	 * 删除应用 ，如果应用有权限在使用中，就不删除，否则删除
	 */
	@Override
	public ResponseModel deleteApplicationById(Integer id) {
		PermissionParam permissionParams = new PermissionParam();
		permissionParams.setApplicationId(id);
		Integer permissionNum = permissionMapper.selectCountByParam(permissionParams);
		if (permissionNum != 0) {
			return new ResponseModel.Builder().status(HttpStatus.FORBIDDEN).error("删除失败，应用正在被权限使用中").build();
		}
		applicationMapper.deleteByPrimaryKey(id);
		return new ResponseModel.Builder().msg("删除成功").build();
	}
}
