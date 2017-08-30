package com.tinet.tsso.auth.controller;

import com.tinet.tsso.auth.entity.LogLogin;
import com.tinet.tsso.auth.param.LogLoginParam;
import com.tinet.tsso.auth.service.LogLoginService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆日志的Controller
 *
 * @author lizy
 * @date 2017-08-25
 */
@RestController
@RequestMapping("/api/log_login")
public class LogLoginController {

	@Autowired
	private LogLoginService logLoginService;

	/**
	 * 登陆日志的查询方法
	 *
	 * @param logLoginParam
	 * @return
	 */
	@GetMapping
	public ResponseModel getLogAction(LogLoginParam logLoginParam) {

		Page<LogLogin> page = logLoginService.selectByParam(logLoginParam);

		return new ResponseModel.Builder().msg("查询成功").result(page).build();
	}

}
