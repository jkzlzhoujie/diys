package cn.temobi.complex.entity;

@SuppressWarnings("serial")
public class Order extends IdEntity{
	private String addTime;
	private Long clientId;		//客户端ID
	private Long userId;		//用户ID
	private Long accountBuyId;	//账户购买Id
	private String amount;		//价格
	private String payType;		//类型 1微信 2 支付宝
	private String orderNo;		//订单号
	private Long productId;		//作品iD
	private Long commodityId;	//商品ID
	private Long commodityInfoId;//商品类型ID
	private String type;		//1 私人订制 2悬赏求P 3充值  4 开通特权  5 参与夺宝
	private String status = "0";//0未支付1 已支付2支付失败3已取消 4已删除
	private String nickName;
	private int num;
	private String freight;
	private String remark;		//备注
	
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
	public Long getCommodityInfoId() {
		return commodityInfoId;
	}
	public void setCommodityInfoId(Long commodityInfoId) {
		this.commodityInfoId = commodityInfoId;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getAccountBuyId() {
		return accountBuyId;
	}
	public void setAccountBuyId(Long accountBuyId) {
		this.accountBuyId = accountBuyId;
	}
}
