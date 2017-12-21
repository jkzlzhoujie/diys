package cn.temobi.complex.entity;

@SuppressWarnings("serial")
public class OrderFinance extends IdEntity{
	private String orderNo;
	private String balancePrice;
	private String status;
	private Long joinId;
	private Long accountBuyId;
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
}
