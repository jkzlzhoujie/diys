package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientVersionDto implements Serializable{
	private String versionCode;//版本号
	private String downUrl;//应用下载地址
	private String size;//应用大小
	private String desc;//更新信息
	private String name;//版本名称
	private Integer isForce;// 是否强制升级 0否 1是
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getIsForce() {
		return isForce;
	}
	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
