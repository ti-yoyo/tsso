package com.tinet.tsso.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.DepartmentMapper;
import com.tinet.tsso.auth.entity.Department;
import com.tinet.tsso.auth.service.DepartmentService;
import com.tinet.tsso.auth.util.Page;

/**
 * 部门Service 的实现类
 * 
 * @date 2017-08-09
 * @author lizy
 */

@Service
public class DepartmentServiceImpl extends BaseServiceImp<Department, Integer> implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	/**
	 * 部门的全量查询
	 */
	@Override
	public Page<Department> searchAll() {

		Integer totalSize = departmentMapper.selectCount();

		List<Department> pageData = departmentMapper.selectAll();

		return new Page<Department>(totalSize, pageData);
	}

}
