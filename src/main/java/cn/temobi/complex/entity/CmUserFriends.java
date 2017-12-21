package cn.temobi.complex.entity;

public class CmUserFriends extends IdEntity{

	private Long userId;
	private Long friendUserId;
	private String createdWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getFriendUserId() {
		return friendUserId;
	}
	public void setFriendUserId(Long friendUserId) {
		this.friendUserId = friendUserId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
