package cn.temobi.complex.entity;

import java.util.Date;

public class NetRedGameBanner extends IdEntity{
	
	private String imageUrl;//图片地址
	private String note;
	private int type;//1 大赛轮播 2 大赛介绍
	private int status;//状态 1 正常 0 失效',
	private Date createTime;
	private String CreateTimeStr;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateTimeStr() {
		return CreateTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		CreateTimeStr = createTimeStr;
	}
	
	
	
	
	
	
	

}
