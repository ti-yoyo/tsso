package com.tinet.tsso.auth.service;

import java.util.List;

import com.tinet.tsso.auth.entity.Test;
import com.tinet.tsso.auth.param.TestParam;

/**
 * 
 * @date 2017-07-28
 * @author wangll
 */
public interface TestService extends BaseService<Test, Integer> {

	List<Test> search(TestParam param);
}
