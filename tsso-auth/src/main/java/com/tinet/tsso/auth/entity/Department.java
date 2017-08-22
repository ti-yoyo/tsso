package com.tinet.tsso.auth.entity;

import java.util.Date;
/**
 * 部门实体类
 * @date 2017-08-14
 * @author lizy
 */
public class Department {
    private Integer id;//部门的id

    private String name;//部门名

    private Date createTime;//部门的创建时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}