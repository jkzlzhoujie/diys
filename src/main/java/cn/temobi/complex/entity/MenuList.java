package cn.temobi.complex.entity;


//菜单列表
public class MenuList extends IdEntity{

	private String name;		//名称
	private String imageUrl;	//图标地址
	private String linkUrl;		//链接地址
	private String jumpType;	//跳转类型( 系统管理 -  类型设置里面配置 )'
	private Integer status ;	//状态 1 上线 0 下线
	private Integer serial;  	//序号
	private Integer applicationId;//应用ID号
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getJumpType() {
		return jumpType;
	}
	public void setJumpType(String jumpType) {
		this.jumpType = jumpType;
	}
	public Integer getSerial() {
		return serial;
	}
	public void setSerial(Integer serial) {
		this.serial = serial;
	}
	public Integer getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	

}
