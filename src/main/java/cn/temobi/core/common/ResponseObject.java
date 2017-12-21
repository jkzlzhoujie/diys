package cn.temobi.core.common;

import java.io.Serializable;

/**
 * @author salim
 * @created 2012-6-27
 * @package com.plaminfo.group.dto
 */
@SuppressWarnings("serial")
public class ResponseObject implements Serializable{
	
	private String code;
	
	private Object response;
	
	private String desc;
	
	private String date;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
