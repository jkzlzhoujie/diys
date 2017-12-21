package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class WxDiy extends IdEntity{
	private String serverId;
	private String imgVal;
	private String bgSrc;
	private String ySrc;
	private String resourceId;
	private String addTime;
	public String getServerId() {
		return serverId;
	}
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}
	public String getImgVal() {
		return imgVal;
	}
	public void setImgVal(String imgVal) {
		this.imgVal = imgVal;
	}
	public String getBgSrc() {
		return bgSrc;
	}
	public void setBgSrc(String bgSrc) {
		this.bgSrc = bgSrc;
	}
	public String getySrc() {
		return ySrc;
	}
	public void setySrc(String ySrc) {
		this.ySrc = ySrc;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
}
