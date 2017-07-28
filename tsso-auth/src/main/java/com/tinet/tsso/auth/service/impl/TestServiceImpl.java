package com.tinet.tsso.auth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.TestMapper;
import com.tinet.tsso.auth.entity.Test;
import com.tinet.tsso.auth.model.TestParam;
import com.tinet.tsso.auth.service.TestService;

/**
 * 
 * @date 2017-07-28
 * @author wangll
 */
@Service
public class TestServiceImpl extends BaseServiceImp<Test, Integer> implements TestService {
	@Autowired
	private TestMapper testMapper;

	@Override
	public List<Test> search(TestParam param) {
		return testMapper.search(param);
	}
}
