package com.tinet.tsso.auth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tinet.tsso.auth.dao.SettingMapper;
import com.tinet.tsso.auth.entity.Setting;
import com.tinet.tsso.auth.service.SettingService;
import com.tinet.tsso.auth.util.SettingProperties;

/**
 * 设置的实现类
 * 
 * @date 2017-08-23
 * @author lizy
 */
@Service
public class SettingServiceImpl implements SettingService {

	@Autowired
	private SettingMapper settingMapper;

	@Override
	public void init() {
		List<Setting> settingList = settingMapper.selectAll();

		Map<String, String> map = new HashMap<>();

		for (Setting s : settingList) {
			map.put(s.getKey(), s.getValue());
		}
		SettingProperties.settingMap = map;
	}

	/**
	 * 修改设置
	 */
	@Override
	public void changeSetting(HashMap<String, String> map) {
		Map<String, String> tmpMap = SettingProperties.settingMap;
		for (String key : map.keySet()) {
			if (tmpMap.get(key) != null) {
				Setting setting = new Setting();
				if (!tmpMap.get(key).equals(map.get(key))) {
					setting.setKey(key);
					setting.setValue(map.get(key));
					settingMapper.updateValueBykey(setting);
				}
			}
		}
		init();
	}

}
