package com.tinet.tsso.auth.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.service.SettingService;
import com.tinet.tsso.auth.util.ResponseModel;
import com.tinet.tsso.auth.util.SettingProperties;

/**
 * 邮件服务器设置
 * 
 * @date 2017-08-23
 * @author lizy
 */
@RestController
@RequestMapping("/api/setting")
public class SettingController {

	@Autowired
	private SettingService settingService;

	@GetMapping
	public ResponseModel searchAllSetting() {

		return new ResponseModel.Builder().result(SettingProperties.settingMap).build();

	}

	@PutMapping
	public ResponseModel updateSetting(@RequestBody HashMap<String, String> map) {

		settingService.changeSetting(map);

		return new ResponseModel.Builder().result(SettingProperties.settingMap).build();

	}
}
