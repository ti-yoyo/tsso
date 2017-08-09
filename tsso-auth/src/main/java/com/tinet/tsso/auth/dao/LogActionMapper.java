package com.tinet.tsso.auth.dao;

import com.tinet.tsso.auth.entity.LogAction;

public interface LogActionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogAction record);

    int insertSelective(LogAction record);

    LogAction selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogAction record);

    int updateByPrimaryKey(LogAction record);
}