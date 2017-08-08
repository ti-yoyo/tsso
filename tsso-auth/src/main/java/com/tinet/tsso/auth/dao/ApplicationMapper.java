package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Application;

public interface ApplicationMapper extends BaseMapper<Application, Integer>{

    Integer insert(Application application);
    
    Application selectByPrimaryKey(Integer id);

    /**
     * 
     * @return 返回所有应用的个数
     */
	Integer selectCount();

	/**
	 * 
	 * @return 所有的应用
	 */
	List<Application> selectAll();

}