package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

@SuppressWarnings("serial")
public class OrderFinanceDto extends IdEntity{
	private String orderNo;
	private Long joinId;
	private Long accountBuyId;
	private String designUrl;
	private String balancePrice;
	private String status;
	private String price;
	private String clientChannel;
	private String addTime;
	private String payType;
	private String remark;
	private String sjNickName;
	private Long acceptUserId;
	private Long userId;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getBalancePrice() {
		return balancePrice;
	}
	public void setBalancePrice(String balancePrice) {
		this.balancePrice = balancePrice;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSjNickName() {
		return sjNickName;
	}
	public void setSjNickName(String sjNickName) {
		this.sjNickName = sjNickName;
	}
	public Long getAcceptUserId() {
		return acceptUserId;
	}
	public void setAcceptUserId(Long acceptUserId) {
		this.acceptUserId = acceptUserId;
	}
	public Long getJoinId() {
		return joinId;
	}
	public void setJoinId(Long joinId) {
		this.joinId = joinId;
	}
	public Long getAccountBuyId() {
		return accountBuyId;
	}
	public void setAccountBuyId(Long accountBuyId) {
		this.accountBuyId = accountBuyId;
	}
	public String getDesignUrl() {
		return designUrl;
	}
	public void setDesignUrl(String designUrl) {
		this.designUrl = designUrl;
	}
	
}
