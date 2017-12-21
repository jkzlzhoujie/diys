package cn.temobi.complex.entity;

public class PmProductPraises extends IdEntity{

	private Long productId;
	private Long praiseUserId;	//用户登录状态下点赞需要记录该id,否则只记录设备id
	private Long clientId;
	private int type;			//0 点赞 1 点踩
	private String remark;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
