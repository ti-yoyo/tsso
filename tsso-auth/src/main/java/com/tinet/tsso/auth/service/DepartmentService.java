package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Department;
import com.tinet.tsso.auth.util.Page;

/**
 * 部门的Service
 * 
 * @date 2017-08-09
 * @author lizy
 */

public interface DepartmentService extends BaseService<Department, Integer> {

	/**
	 * 部门信息的全量查询
	 * 
	 * @return
	 */
	Page<Department> searchAll();

}
