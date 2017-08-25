package com.tinet.tsso.auth.param;

import com.tinet.tsso.auth.util.StringToDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lizy
 * @date 2017-08-25
 */
public class LogLoginParam {


    private String userName;//操作人帐号
    private String fullName;//全名

    private String startTime;//起始时间
    private String endTime;//结束时间

    private Integer start;//分页的起始位置
    private Integer limit;//分页大小

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getStartTime() {
        if(startTime == null){
            return null;
        }
        else{
            return StringToDate.dealStringToDate(startTime);
        }
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        if(endTime == null){
            return null;
        }
        else{
            return StringToDate.dealStringToDate(endTime);
        }
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
