package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.ComAuditTrail;
import com.tinet.tsso.auth.param.LogLoginParam;

public interface ComAuditTrailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ComAuditTrail record);

    int insertSelective(ComAuditTrail record);

    ComAuditTrail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ComAuditTrail record);

    int updateByPrimaryKey(ComAuditTrail record);
    
    Integer selectCountByParam(LogLoginParam logLoginParam);
    
    List<ComAuditTrail> selectByParam(LogLoginParam logLoginParam);
}