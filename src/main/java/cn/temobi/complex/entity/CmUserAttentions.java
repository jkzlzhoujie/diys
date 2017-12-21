package cn.temobi.complex.entity;

public class CmUserAttentions extends IdEntity{

	private Long userId;
	private Long attentionUserId;
	private String createdWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getAttentionUserId() {
		return attentionUserId;
	}
	public void setAttentionUserId(Long attentionUserId) {
		this.attentionUserId = attentionUserId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
