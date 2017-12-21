package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OrderRecordDto implements Serializable{
	private String packetName;//表情包名称
	private String phone;//手机号
	private String imei;//imei
	private String imsi;//imsi
	private String version;//版本号
	private String machine;//机器型号
	private String netEnvironment;//网络环境
	private String remoteIp;//访问IP
	private String channel;//渠道号
	private String price;//价格
	private String createdWhen;//订购时间
	public String getPacketName() {
		return packetName;
	}
	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
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
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getNetEnvironment() {
		return netEnvironment;
	}
	public void setNetEnvironment(String netEnvironment) {
		this.netEnvironment = netEnvironment;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
