package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

@SuppressWarnings("serial")
public class OrderDto extends IdEntity{
	private String addTime;
	private Long clientId;
	private Long userId;
	private String amount;
	private String payType;
	private String orderNo;
	private Long productId;
	private Long commodityId;
	private Long commodityInfoId;
	private Long accountBuyId;
	private String type;
	private String status;
	private String nickName;
	private String url;
	private int num;
	private String freight;
	private String commodityName;
	private String commodityType;
	private String commodityThumbnail;
	
	private String phoneShell;
	private String receivePerson;
	private String receiveAddress;
	
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public String getCommodityThumbnail() {
		return commodityThumbnail;
	}
	public void setCommodityThumbnail(String commodityThumbnail) {
		this.commodityThumbnail = commodityThumbnail;
	}
	public Long getAccountBuyId() {
		return accountBuyId;
	}
	public void setAccountBuyId(Long accountBuyId) {
		this.accountBuyId = accountBuyId;
	}
	public String getPhoneShell() {
		return phoneShell;
	}
	public void setPhoneShell(String phoneShell) {
		this.phoneShell = phoneShell;
	}
	public String getReceivePerson() {
		return receivePerson;
	}
	public void setReceivePerson(String receivePerson) {
		this.receivePerson = receivePerson;
	}
	public String getReceiveAddress() {
		return receiveAddress;
	}
	public void setReceiveAddress(String receiveAddress) {
		this.receiveAddress = receiveAddress;
	}
}
