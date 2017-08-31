package com.tinet.tsso.auth.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizy
 * @date 2017-08-25
 */
public class StringToDate {
    public static Date dealStringToDate(String dateString){
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return  date;
    }
}
