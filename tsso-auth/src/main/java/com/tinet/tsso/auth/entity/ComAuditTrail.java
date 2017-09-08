package com.tinet.tsso.auth.entity;

import java.util.Date;

public class ComAuditTrail {
    private Long id;

    private String audAction;

    private String applicCd;

    private String audClientIp;

    private Date audDate;

    private String audResource;

    private String audServerIp;

    private String audUser;
    
    private User user;//操作用户
    
    private Application application;//操作应用

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAudAction() {
        return audAction;
    }

    public void setAudAction(String audAction) {
        this.audAction = audAction == null ? null : audAction.trim();
    }

    public String getApplicCd() {
        return applicCd;
    }

    public void setApplicCd(String applicCd) {
        this.applicCd = applicCd == null ? null : applicCd.trim();
    }

    public String getAudClientIp() {
        return audClientIp;
    }

    public void setAudClientIp(String audClientIp) {
        this.audClientIp = audClientIp == null ? null : audClientIp.trim();
    }

    public Date getAudDate() {
        return audDate;
    }

    public void setAudDate(Date audDate) {
        this.audDate = audDate;
    }

    public String getAudResource() {
        return audResource;
    }

    public void setAudResource(String audResource) {
        this.audResource = audResource == null ? null : audResource.trim();
    }

    public String getAudServerIp() {
        return audServerIp;
    }

    public void setAudServerIp(String audServerIp) {
        this.audServerIp = audServerIp == null ? null : audServerIp.trim();
    }

    public String getAudUser() {
        return audUser;
    }

    public void setAudUser(String audUser) {
        this.audUser = audUser == null ? null : audUser.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}