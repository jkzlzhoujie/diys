package cn.temobi.complex.entity;

public class AccountRedPacket extends IdEntity{

	private Long userId;
	private double price;			//金额
	private String addTime;	
	private String updateTime;		//领取时间
	private Long joinId;
	private Long redpacketLog;		//领取记录ID
	private int num;				//编号
	private String status = "0";	//0 未领取 1已领取
	private String flag = "0";		//0 正常 1不正常
	private String type;			//1 彩绘红包 2渠道红包
	private Long clientId;
	private String imei;
	private String imsi;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Long getRedpacketLog() {
		return redpacketLog;
	}
	public void setRedpacketLog(Long redpacketLog) {
		this.redpacketLog = redpacketLog;
	}
}
