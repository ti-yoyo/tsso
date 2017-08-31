package com.tinet.tsso.auth.service;

import com.tinet.tsso.auth.entity.LogLogin;
import com.tinet.tsso.auth.param.LogLoginParam;
import com.tinet.tsso.auth.util.Page;

/**
 * 登录日志的接口
 *
 * @author lizy
 * @date 207-8-25
 */
public interface LogLoginService {
    Page<LogLogin> selectByParam(LogLoginParam logLoginParam);
}
