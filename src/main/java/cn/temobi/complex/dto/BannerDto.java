package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BannerDto implements Serializable{
	private String name;//banner名称
	private String picUrl;//banner地址,带http
	private Integer actionType;//点击动作类型，1:表情,2:外链到网址,3:活动页
	private String clickUrl;//点击的外链地址
	private Long clickId;//点击ID
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Integer getActionType() {
		return actionType;
	}
	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}
	public String getClickUrl() {
		return clickUrl;
	}
	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}
	public Long getClickId() {
		return clickId;
	}
	public void setClickId(Long clickId) {
		this.clickId = clickId;
	}
	
}
