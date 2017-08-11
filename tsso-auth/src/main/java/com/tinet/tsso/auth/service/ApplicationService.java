package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.util.Page;

/**
 * @date 2017-08-08
 * @author lizy
 */

public interface ApplicationService extends BaseService<Application, Integer>{

	Page<Application> getAll();

}
