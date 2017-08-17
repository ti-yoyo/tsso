package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Test;
import com.tinet.tsso.auth.param.TestParam;

public interface TestMapper extends BaseMapper<Test, Integer> {

	List<Test> search(TestParam param);

}