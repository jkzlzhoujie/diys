package cn.temobi.complex.entity;

/**
 * 对于表emoji_client_sp 启动页广告
 * @author hjf
 * 2014 三月 17 14:06:53
 */
@SuppressWarnings("serial")
public class ClientSp extends IdEntity{
	private String name;
	private String imageUrl;
	private String type;
	private String clientChannel;
	private String versionCode;
	private String createdWhen;//创建时间
	private String startTime;//开始日期
	private String endTime;//结束日期
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}
	public String getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
