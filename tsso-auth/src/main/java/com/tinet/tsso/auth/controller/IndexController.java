package com.tinet.tsso.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 解决页面刷新404错误
 * 
 * @date 2017-08-21
 * @author lizy
 */
@Controller
public class IndexController {

	@RequestMapping(value = "/index/**")
	public String index() {
		return "/index.html";
	}

	@RequestMapping(value = "/password_find")
	public String passwordFind() {
		return "/index.html";
	}

	@RequestMapping(value = "/password_modify")
	public String passwordModify() {
		return "/index.html";
	}
}