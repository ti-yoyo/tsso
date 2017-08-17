package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Department;

/**
 * 部门的Mapper
 * 
 * @date 2017-08-14
 * @author lizy
 */
public interface DepartmentMapper {

	/**
	 * 
	 * @return 所有部门的数量
	 */
	Integer selectCount();

	/**
	 * 
	 * @return 所有的部门信息
	 */
	List<Department> selectAll();
}