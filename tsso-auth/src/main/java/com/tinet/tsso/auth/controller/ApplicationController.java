package com.tinet.tsso.auth.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Application;
import com.tinet.tsso.auth.param.ApplicationParam;
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
	 * @return 包含状态信息以及添加应用的 id
	 */
	@PostMapping
	public ResponseModel add(@RequestBody ApplicationParam applicationParam) {

		Application application = new Application();
		BeanUtils.copyProperties(applicationParam, application);

		// 添加应用

		// return new ResponseModel.Builder().result(application).msg("添加成功").build();
		return applicationService.addApplication(application);
	}

	/**
	 * 查询全量应用
	 * 
	 * @return 包含状态信息、以及查询数据和查询到的应用的个数
	 */
	@GetMapping
	public ResponseModel searchAll() {

		// 查询全量应用及其个数
		Page<Application> page = applicationService.getAll();

		return new ResponseModel.Builder().result(page).msg("查询成功").build();
	}

	@GetMapping("/{id}")
	public ResponseModel getOneApplication(@PathVariable Integer id) {

		Application application=applicationService.get(id);
		
		if(application == null) {
			new ResponseModel.Builder().error("该应用不存在").status(HttpStatus.BAD_REQUEST).build();
		}
		
		return new ResponseModel.Builder().msg("查询成功").result(application).build();
	}
	
	/**
	 * 删除指定id的应用
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseModel deleteApplication(@PathVariable Integer id) {

		ResponseModel responseModel = applicationService.deleteApplicationById(id);

		return responseModel;
	}

	/**
	 * 更新应用
	 * 
	 * @param id
	 * @param applicationParam
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseModel updateApplication(@PathVariable Integer id, @RequestBody ApplicationParam applicationParam) {
		Application application = new Application();

		BeanUtils.copyProperties(applicationParam, application);

		application.setId(id);
		applicationService.update(application);

		return new ResponseModel.Builder().msg("修改成功").result(applicationService.get(id)).build();

	}

}
