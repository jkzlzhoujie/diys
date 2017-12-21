package cn.temobi.complex.entity;

/**
 * 对应表emoji_client 漫赏表情用户设备
 * @author hjf
 * 2014 三月 17 14:45:39
 */
@SuppressWarnings("serial")
public class Client extends IdEntity {
	private String imei;//IMEI码
	private String imsi;//IMSI码
	private String machine;//机器型号
	private String os;//0:ios 1:android
	private String osVersion;//app版本
	private String appVersion;//系统版本
	private String ip;//客户端ip
	private String channel;//渠道
	private String createdWhen;//创建时间
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
}
