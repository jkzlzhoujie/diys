package cn.temobi.complex.entity;

/**
 * 对于表emoji_banner 横幅广告
 * @author hjf
 * 2014 三月 17 14:06:53
 */
@SuppressWarnings("serial")
public class BusinessCount extends IdEntity{
	private Long materialId;//素材包ID
	private Long materialResId;//素材ID
	private Long clientId;//客户端ID
	private String business;//商家名称
	private String type;//1 diy页面 2评论页面
	private String location;//1 素材包的来自哪里点击 2单个素材的商家链接点击
	private String createdWhen;//创建时间
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getMaterialResId() {
		return materialResId;
	}
	public void setMaterialResId(Long materialResId) {
		this.materialResId = materialResId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
}
