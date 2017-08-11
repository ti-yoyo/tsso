package com.tinet.tsso.auth.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class Page<T> implements Serializable {
	
	/**
	 * 分页总数
	 */
	private int totalSize;
	
	/**
	 * 分页列表
	 */
	private List<T> pageData=new ArrayList<>();
	
	
	
	public Page(){
		
	}

	/**
	 * @param totalSize 分页总数
	 * @param pageData 分页数据列表
	 */
	public Page(int totalSize, List<T> pageData) {
		super();
		this.totalSize = totalSize;
		this.pageData = pageData;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public List<T> getPageData() {
		return pageData;
	}

	public void setPageData(List<T> pageData) {
		this.pageData = pageData;
	}
	
}
