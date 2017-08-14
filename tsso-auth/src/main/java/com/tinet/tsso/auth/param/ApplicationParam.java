package com.tinet.tsso.auth.param;

/**
 * 应用Application的请求参数
 * @date 2017-08-11
 * @author lizy
 */

public class ApplicationParam {
    private String key; //Application的标识

    private String name;//应用名

    private Integer status; //应用的状态

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}
