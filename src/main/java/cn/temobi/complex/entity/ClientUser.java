package cn.temobi.complex.entity;

/**
 * 对应表emoji_client_user 漫赏表情注册用户
 * @author hjf
 * 2014 三月 17 14:52:24
 */
@SuppressWarnings("serial")
public class ClientUser extends IdEntity{
	private Long clientId;//设备ID
	private String username;//用户名
	private String channel;//客户端渠道渠道号
	private String version;//客户端版本号
	private String area;//归属省份
	private String city;//归属城市
	private String type;//用户来源:1融合通信 2漫赏表情 3wap版
	private String createdWhen;//创建时间
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
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
	
}
