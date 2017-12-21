package cn.temobi.complex.entity;

/**
 * 对应表 emoji_start_statistics 用户启动行为统计
 * @author hjf
 * 2014 三月 17 17:00:06
 */
@SuppressWarnings("serial")
public class StartStatistics extends IdEntity{
	private String startIp;//启动ip
	private String version;//客户端版本号
	private Long userId;//用户id
	private String channelId;//渠道id
	private Long clientId;//设备id
	private String sourceId;//来源id,1:正常;2:QQ;3:微信悬浮栏
	private String createdWhen;//启动时间
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
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
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
