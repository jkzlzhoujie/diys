package cn.temobi.complex.entity;

public class CmFriendsRequest extends IdEntity{

	private Long userId;
	private Long requestUserId;
	private int status;
	private String dealTime;
	private String createdWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRequestUserId() {
		return requestUserId;
	}
	public void setRequestUserId(Long requestUserId) {
		this.requestUserId = requestUserId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
