package cn.temobi.complex.entity;

import java.util.Date;

//系统特权内容类型
public class SysPrivilegeContentType extends IdEntity{

	private String typeCode;	//类型编码
	private String content;		//内容
	private Integer status ;	//状态 状态 1 上线 2 下线
	private String imageUrl;	//图标地址
	private String note;		//内容
	private Date creatWhen;
	private Date updateWhen;
	
	
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatWhen() {
		return creatWhen;
	}
	public void setCreatWhen(Date creatWhen) {
		this.creatWhen = creatWhen;
	}
	public Date getUpdateWhen() {
		return updateWhen;
	}
	public void setUpdateWhen(Date updateWhen) {
		this.updateWhen = updateWhen;
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
	
	
	

}
