package com.tinet.tsso.auth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.tinet.tsso.auth.dao.BaseMapper;
import com.tinet.tsso.auth.service.BaseService;

public abstract class BaseServiceImp<T, ID> implements BaseService<T, ID> {

	@Autowired
	protected BaseMapper<T, ID> baseMapper;

	@Override
	public T get(ID id) {
		return baseMapper.selectByPrimaryKey(id);
	}

	@Transactional
	@Override
	public Integer create(T t) {
		return baseMapper.insertSelective(t);
	}

	@Transactional
	@Override
	public Integer update(T t) {
		return baseMapper.updateByPrimaryKeySelective(t);
	}

	@Transactional
	@Override
	public Integer delete(ID id) {
		return baseMapper.deleteByPrimaryKey(id);
	}
}
