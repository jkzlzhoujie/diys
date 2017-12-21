package cn.temobi.complex.entity;

public class CirclePostPraises extends IdEntity{

	private Long circlePostId;
	private Long clientId;
	private Long userId;
	private int type;			//0点赞 1点踩 2 分享
	private String remark;
	private String createdWhen;
	
	
	public Long getCirclePostId() {
		return circlePostId;
	}
	public void setCirclePostId(Long circlePostId) {
		this.circlePostId = circlePostId;
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
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
}
