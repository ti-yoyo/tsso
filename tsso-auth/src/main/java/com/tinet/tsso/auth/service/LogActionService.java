package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.LogAction;
import com.tinet.tsso.auth.param.LogActionParam;
import com.tinet.tsso.auth.util.Page;

/**
 * 操作日志的接口
 *
 * @author lizy
 * @date 2017-08-24
 */
public interface LogActionService extends BaseService<LogAction, Integer> {

    /**
     * 添加操作日志
     *
     * @param operateDesc   操作描述
     * @param operateObject 操作对象
     * @param result        操作结果
     */
    public void addLogAction(String operateDesc, String operateObject, Integer result);

    /**
     * 查询操作日志
     *
     * @param logActionParam 查询的参数
     * @return
     */
    Page<LogAction> selectByParam(LogActionParam logActionParam);
}
