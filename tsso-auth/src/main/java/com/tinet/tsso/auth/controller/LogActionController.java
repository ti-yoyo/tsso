package com.tinet.tsso.auth.controller;

import com.tinet.tsso.auth.entity.LogAction;
import com.tinet.tsso.auth.param.LogActionParam;
import com.tinet.tsso.auth.service.LogActionService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 操作日志的Action
 *
 * @author lizy
 * @date 2017-08-24
 */
@RestController
@RequestMapping("/api/log_action")
public class LogActionController {

	@Autowired
	private LogActionService logActionService;

	/**
	 *
	 * 查询操作日志的方法
	 * 
	 * @param logActionParam
	 * @return
	 */
	@GetMapping
	public ResponseModel getLogAction(LogActionParam logActionParam) {

		Page<LogAction> page = logActionService.selectByParam(logActionParam);

		return new ResponseModel.Builder().msg("查询成功").result(page).build();
	}

}
