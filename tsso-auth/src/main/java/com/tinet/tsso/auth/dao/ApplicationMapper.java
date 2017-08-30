package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Application;

/**
 * 应用的mapper
 * 
 * @date 2017-08-14
 * @author lizy
 */
public interface ApplicationMapper extends BaseMapper<Application, Integer> {

	/**
	 * 按照id查询指定用用
	 */
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

	/**
	 * 统计key使用数量，用于保证key 唯一
	 * 
	 * @param key
	 * @return
	 */
	Integer selectCountByApplicationKey(String key);

}