package cn.temobi.complex.entity;

public class CmUserFans extends IdEntity{

	private Long userId;
	private Long fansUserId;
	private String createdWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFansUserId() {
		return fansUserId;
	}
	public void setFansUserId(Long fansUserId) {
		this.fansUserId = fansUserId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
