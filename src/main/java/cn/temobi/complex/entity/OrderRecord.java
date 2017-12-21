package cn.temobi.complex.entity;

/**
 * 对应表emoji_order_record 用户购买行为记录
 * @author hjf
 * 2014 三月 17 15:39:27
 */
@SuppressWarnings("serial")
public class OrderRecord extends IdEntity{
	private Long userId;//用户id
	private Long clientId;//设备id
	private Long packetId;//表情包id
	private Long emoticonId;//单个表情id
	private String remoteIp;//访问IP
	private String channel;//访问渠道
	private String version;//客户端版本号
	private String netEnvironment;//网络环境（1: cmwap; 2:cmnet; 3:wifi; 4:other）
	private String price;//价格
	private String resultCode;//返回错误码
	private String createdWhen;//购买时间
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getPacketId() {
		return packetId;
	}
	public void setPacketId(Long packetId) {
		this.packetId = packetId;
	}
	public Long getEmoticonId() {
		return emoticonId;
	}
	public void setEmoticonId(Long emoticonId) {
		this.emoticonId = emoticonId;
	}
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
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
	public String getNetEnvironment() {
		return netEnvironment;
	}
	public void setNetEnvironment(String netEnvironment) {
		this.netEnvironment = netEnvironment;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
