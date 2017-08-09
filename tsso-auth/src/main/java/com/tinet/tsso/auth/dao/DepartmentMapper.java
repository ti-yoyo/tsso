package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Department;

public interface DepartmentMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Department record);

    int insertSelective(Department record);

    Department selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Department record);

    int updateByPrimaryKey(Department record);


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