package cn.temobi.complex.entity;

public class AccountRedPacketLog extends IdEntity{

	private Long userId;
	private Long redPacketId;
	private double price;
	private String type;
	private String addTime;
	private String status;
	private String flag ="0";
	private Long clientId;
	private String imei;
	private String imsi;
	private Long joinId;
	private Long produceLogId;
	private double startAccountPrice;
	private double endAccountPrice;
	private String redType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRedPacketId() {
		return redPacketId;
	}
	public void setRedPacketId(Long redPacketId) {
		this.redPacketId = redPacketId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}
	public Long getProduceLogId() {
		return produceLogId;
	}
	public void setProduceLogId(Long produceLogId) {
		this.produceLogId = produceLogId;
	}
	public double getStartAccountPrice() {
		return startAccountPrice;
	}
	public void setStartAccountPrice(double startAccountPrice) {
		this.startAccountPrice = startAccountPrice;
	}
	public double getEndAccountPrice() {
		return endAccountPrice;
	}
	public void setEndAccountPrice(double endAccountPrice) {
		this.endAccountPrice = endAccountPrice;
	}
	public String getRedType() {
		return redType;
	}
	public void setRedType(String redType) {
		this.redType = redType;
	}
}
