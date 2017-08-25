package com.tinet.tsso.auth.dao;

import com.tinet.tsso.auth.entity.LogAction;
import com.tinet.tsso.auth.param.LogActionParam;

import java.util.List;

/**
 * 操作日志的action接口
 *
 * @author lizy
 * @date 2017-08-24
 */
public interface LogActionMapper extends BaseMapper<LogAction, Integer> {

    /**
     * 按照参数查询操作日志
     *
     * @param logActionParam
     * @return
     */
    List<LogAction> selectByParam(LogActionParam logActionParam);

    /**
     * 查询操作日志的数量
     *
     * @param logActionParam
     * @return
     */
    Integer selectCountByParam(LogActionParam logActionParam);
}