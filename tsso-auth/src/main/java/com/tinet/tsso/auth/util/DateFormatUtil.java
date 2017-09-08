package com.tinet.tsso.auth.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @date 2017-09-07
 * @author lizy
 */
public class DateFormatUtil {

	public static String dataFormat(Date date) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return formatter.format(date);
	}
	
}
