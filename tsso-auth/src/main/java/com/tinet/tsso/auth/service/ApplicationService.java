package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.util.Page;

/**
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
	Application addApplication(Application application);


}
