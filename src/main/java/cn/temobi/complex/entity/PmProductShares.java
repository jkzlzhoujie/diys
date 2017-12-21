package cn.temobi.complex.entity;

public class PmProductShares extends IdEntity{

	private Long productId;
	private Long praiseUserId;
	private Long clientId;
	private Long shareDetailId;
	private String createdWhen;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getPraiseUserId() {
		return praiseUserId;
	}
	public void setPraiseUserId(Long praiseUserId) {
		this.praiseUserId = praiseUserId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public Long getShareDetailId() {
		return shareDetailId;
	}
	public void setShareDetailId(Long shareDetailId) {
		this.shareDetailId = shareDetailId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
