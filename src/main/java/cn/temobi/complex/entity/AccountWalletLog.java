package cn.temobi.complex.entity;

public class AccountWalletLog extends IdEntity{

	private Long userId;
	private String orderNo;		//订单号
	private String type;		//1 充值 2消费
	private String useType;		//1 购买彩绘 2悬赏求P 3提现 4 特权支付  5 夺宝
	private String havaType;	//1 单次充值 2获得悬赏 3赏金退回 4系统充值  5 系统退款   
	private String payType;		//1 微信 2 支付宝 3余额 6话费
	private double price;		//金额
	private String status ="0"; //状态 0未支付1 已支付2支付失败3已取消 4已删除
	private String addTime;
	private Long clientId;
	private String imei;
	private String imsi;
	private Long joinId;		//彩绘记录ID
	private Long produceLogId;	//生成记录的数据ID
	private Long withdrawId;	//提现ID
	private Long accountBuyId;	//购买ID
	private String flag = "0";	//0正常 2不正常
	private double startAccountPrice;//操作前金额
	private double endAccountPrice;	//操作后金额
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getHavaType() {
		return havaType;
	}
	public void setHavaType(String havaType) {
		this.havaType = havaType;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
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
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
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
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Long getAccountBuyId() {
		return accountBuyId;
	}
	public void setAccountBuyId(Long accountBuyId) {
		this.accountBuyId = accountBuyId;
	}
	public Long getWithdrawId() {
		return withdrawId;
	}
	public void setWithdrawId(Long withdrawId) {
		this.withdrawId = withdrawId;
	}
}
