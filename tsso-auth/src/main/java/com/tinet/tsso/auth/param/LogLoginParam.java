package com.tinet.tsso.auth.param;

import java.util.Date;

import com.tinet.tsso.auth.util.StringToDate;

/**
 * @author lizy
 * @date 2017-08-25
 */
public class LogLoginParam {


    private String username;//操作人帐号
    private String fullName;//全名

    private String startTime;//起始时间
    private String endTime;//结束时间

    private Integer start;//分页的起始位置
    private Integer limit;//分页大小


    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }


    public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
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
