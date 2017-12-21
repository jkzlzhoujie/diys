package cn.temobi.complex.entity;

/**
 * 对应表 emoji_statistics 表情包的行为记录
 * @author hjf
 * 2014 三月 17 17:05:03
 */
@SuppressWarnings("serial")
public class Statistics extends IdEntity{
	private Long userId;//用户id
	private Long clientId;//设备id
	private String type;//统计类型
	private Long channelId;//渠道id
	private String downloadIp;//下载ip
	private String clientVersion;//客户端版本号
	private String createdWhen;//下载时间
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getDownloadIp() {
		return downloadIp;
	}
	public void setDownloadIp(String downloadIp) {
		this.downloadIp = downloadIp;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
