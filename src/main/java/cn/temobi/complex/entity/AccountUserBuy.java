package cn.temobi.complex.entity;

/**
 * 
 * @author Administrator
 *
 *	购买记录
 */
public class AccountUserBuy extends IdEntity{

	private Long userId;
	private Long retionId;			//关联Id
	private Long commodityId;		//商品类型Id
	private Long commodityInfoId;	//商品Id
	private String type;			//1 购买彩绘 2悬赏求p 3充值 4 开通特权 5 参与夺宝
	private String payType;			//0免费 1 微信 2 支付宝 3余额 4支付宝组合支付 5微信组合支付
	private double price;			//价格
	private String status = "0";	//0未支付1 已支付2支付失败  4 订单失效
	private String orderNo;			//生成的订单号，只做展示
	private String remark;			//备注
	private String addTime;
	private String updateTime;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRetionId() {
		return retionId;
	}
	public void setRetionId(Long retionId) {
		this.retionId = retionId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
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
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
