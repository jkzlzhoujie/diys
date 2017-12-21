package cn.temobi.complex.entity;

public class AccountUser extends IdEntity{

	private Long userId;
	private double wallet;				//钱包余额
	private double redPacket;			//红包余额
	private String updateTime;
	private String addTime;
	private String status ="0";
	private String type = "0";			//0 普通用户 1付费用户
	private String alipayAccount;		//支付宝账号
	private String tencentAccount;		//微信账号
	private Long clientId;
	private String imei;
	private String imsi;
	private String os;
	private String channel;				//渠道
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public double getWallet() {
		return wallet;
	}
	public void setWallet(double wallet) {
		this.wallet = wallet;
	}
	public double getRedPacket() {
		return redPacket;
	}
	public void setRedPacket(double redPacket) {
		this.redPacket = redPacket;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAlipayAccount() {
		return alipayAccount;
	}
	public void setAlipayAccount(String alipayAccount) {
		this.alipayAccount = alipayAccount;
	}
	public String getTencentAccount() {
		return tencentAccount;
	}
	public void setTencentAccount(String tencentAccount) {
		this.tencentAccount = tencentAccount;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
