package com.tinet.tsso.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.entity.Department;
import com.tinet.tsso.auth.service.DepartmentService;
import com.tinet.tsso.auth.util.Page;
import com.tinet.tsso.auth.util.ResponseModel;

/**
 * 部门的Controller
 * 
 * @date 2017-08-09
 * @author lizy
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {

	@Autowired
	private DepartmentService deaprtmentService;

	/**
	 * 查询所有的部门
	 * @return
	 */
	@GetMapping
	public ResponseModel searchAll() {

		Page<Department> page = deaprtmentService.searchAll();
		
		return new ResponseModel.Builder().page(page).msg("查询成功").build();
	}

}
