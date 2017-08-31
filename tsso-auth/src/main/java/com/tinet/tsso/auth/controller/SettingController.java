package com.tinet.tsso.auth.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tinet.tsso.auth.service.LogActionService;
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

	@Autowired
	private LogActionService logActionService;

	/**
	 * 查询所有设置
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseModel searchAllSetting() {

		return new ResponseModel.Builder().result(SettingProperties.settingMap).build();

	}

	/**
	 * 更新所有设置如果参数在数据库不存在就存储到数据库
	 * 
	 * @param map
	 * @return
	 */
	@PutMapping
	public ResponseModel updateSetting(@RequestBody HashMap<String, String> map) {

		settingService.changeSetting(map);

		logActionService.addLogAction("更新系统设置",
				"设置:" + SettingProperties.settingMap.toString() + "更新为" + map.toString(), 1);
		return new ResponseModel.Builder().result(SettingProperties.settingMap).build();

	}
}
