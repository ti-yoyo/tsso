package com.tinet.tsso.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.service.ApplicationService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 应用Controller
 * 
 * @date 2017-08-08
 * @author lizy
 */
@RestController
@RequestMapping("/api/application")
public class ApplicationController {

	@Autowired
	private ApplicationService applicationService;

	/**
	 * 添加应用
	 * 
	 * @param application
	 *            要添加的应用
	 * 
	 * @return 包含状态信息以及添加应用的 id
	 */
	@PostMapping
	public ResponseModel add(Application application) {
		//添加应用
		applicationService.create(application);
		
		//按照应用查询应用信息并返回
		application = applicationService.get(application.getId());

		return new ResponseModel.Builder().result(application).msg("添加成功").build();
	}
	
	/**
	 * 查询全量应用
	 * 
	 * @return 包含状态信息、以及查询数据和查询到的应用的个数
	 */
	@GetMapping
	public ResponseModel searchAll(){
		
		//查询全量应用及其个数
		Page<Application> page=applicationService.getAll();
		
		return new ResponseModel.Builder().result(page).msg("查询成功").build();
	}
	
}
