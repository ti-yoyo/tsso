package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Department;
import com.tinet.tsso.auth.util.Page;

/**
 * @date 2017-08-09
 * @author lizy
 */

public interface DepartmentService extends BaseService<Department, Integer>{

	/**
	 * @return
	 */
	Page<Department> searchAll();

}
