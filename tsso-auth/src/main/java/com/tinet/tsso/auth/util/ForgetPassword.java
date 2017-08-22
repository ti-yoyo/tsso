package com.tinet.tsso.auth.util;

import java.util.Hashtable;
import java.util.Map;

import com.tinet.tsso.auth.model.ForgetPasswordModel;

public class ForgetPassword {

	public static Map<String,ForgetPasswordModel> map =new Hashtable<String,ForgetPasswordModel>();

	public static Map<String, ForgetPasswordModel> getMap() {
		return map;
	}

	public static void setMap(Map<String, ForgetPasswordModel> map) {
		ForgetPassword.map = map;
	}

}
