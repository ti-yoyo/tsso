package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Setting;

public interface SettingMapper {

	/**
	 * @return
	 */
	List<Setting> selectAll();

	/**
	 * @param setting
	 */
	void updateValueBykey(Setting setting);
}