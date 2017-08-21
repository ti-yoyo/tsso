package com.tinet.tsso.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 处理前端静态页面，对所有/index/**返回index.html，可解决刷新404错误
 * @author 侯法超
 * @date 2017年5月24日
 *
 */
@Controller
public class IndexController {
	
	@RequestMapping(value="/index/**")
	public String index() {
		return "/index.html";
	}
	@RequestMapping(value="/password_find")
	public String changePassword() {
		return "/index.html";
	}
}