package cn.temobi.complex.entity;

@SuppressWarnings("serial")
public class ClientLogin extends IdEntity{
	private Long clientUserId;//客户端用户ID
	private Long clientId;//设备ID
	private String remoteIp;//访问IP
	private String channel;//访问渠道
	private String netEnvironment;//网络环境（1: cmwap; 2:cmnet; 3:wifi; 4:other）
	private String version;//客户端版本号
	private String duration;//注销时间
	private String createdWhen;//创建时间
	public Long getClientUserId() {
		return clientUserId;
	}
	public void setClientUserId(Long clientUserId) {
		this.clientUserId = clientUserId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	public String getNetEnvironment() {
		return netEnvironment;
	}
	public void setNetEnvironment(String netEnvironment) {
		this.netEnvironment = netEnvironment;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
