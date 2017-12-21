package cn.temobi.complex.entity;

public class PmProductAccusation extends IdEntity{

	private Long productId;
	private Long accusationUserId;	//举报用户id
	private Long clientId;
	private Long userId;			//被举报用户
	private String type;
	private String content;
	private String productUrl;
	private String createdWhen;
	private int isDistort;			// 0 未处理   1 不属实 2 属实
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getAccusationUserId() {
		return accusationUserId;
	}
	public void setAccusationUserId(Long accusationUserId) {
		this.accusationUserId = accusationUserId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIsDistort() {
		return isDistort;
	}
	public void setIsDistort(int isDistort) {
		this.isDistort = isDistort;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getProductUrl() {
		return productUrl;
	}
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
}
