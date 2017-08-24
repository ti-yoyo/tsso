package com.tinet.tsso.auth.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.tinet.tsso.auth.service.SettingService;

/**
 * 初始化设置
 * 
 * @date 2017-08-23
 * @author lizy
 */
@Component
public class InitSetting implements CommandLineRunner{
	
	@Autowired
	private SettingService settingService; 

	@Override
	public void run(String... args) throws Exception {
		settingService.init();
	}

}
