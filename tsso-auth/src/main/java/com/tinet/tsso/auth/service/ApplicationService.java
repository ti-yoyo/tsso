package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 应用的Service
 * 
 * @date 2017-08-08
 * @author lizy
 */

public interface ApplicationService extends BaseService<Application, Integer> {

	/**
	 * 角色的全量查询
	 * 
	 * @return
	 */
	Page<Application> getAll();

	/**
	 * @param application
	 * @return
	 */
	ResponseModel addApplication(Application application);

	/**
	 * 删除应用，如果应用被权限占用，就不删除
	 * 
	 * @param id
	 * @return
	 */
	ResponseModel deleteApplicationById(Integer id);

}
