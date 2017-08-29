package com.tinet.tsso.auth.dao;

import java.util.List;

import com.tinet.tsso.auth.entity.Setting;

public interface SettingMapper extends BaseMapper<Setting, Integer> {

	/**
	 * @return
	 */
	List<Setting> selectAll();

	/**
	 * @param setting
	 */
	void updateValueBykey(Setting setting);
}