package cn.temobi.complex.entity;

public class AccountUserLog extends IdEntity{

	private Long userId;
	private Long clientId;
	private Long otherId;		//其他ID
	private String imsi;
	private String imei;
	private String androidId;
	private String uuidString;
	private String macString;
	private String serialNumber;
	private String os;				//渠道
	private String channel;			//渠道
	private String machine;			//机型
	private String osVersion;		//客户端版本
	private String systemVersion;	//系统版本
	private String ip;
	private String apiName;			//访问接口名称
	private String addTime;
	
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
	public Long getOtherId() {
		return otherId;
	}
	public void setOtherId(Long otherId) {
		this.otherId = otherId;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getAndroidId() {
		return androidId;
	}
	public void setAndroidId(String androidId) {
		this.androidId = androidId;
	}
	public String getUuidString() {
		return uuidString;
	}
	public void setUuidString(String uuidString) {
		this.uuidString = uuidString;
	}
	public String getMacString() {
		return macString;
	}
	public void setMacString(String macString) {
		this.macString = macString;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	public String getSystemVersion() {
		return systemVersion;
	}
	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
}
