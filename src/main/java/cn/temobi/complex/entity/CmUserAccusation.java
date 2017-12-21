package cn.temobi.complex.entity;

public class CmUserAccusation extends IdEntity{

	private Long userId;
	private Long accusationUserId;
	private Long clientId;
	private int type;
	private String content;
	private String createdWhen;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
}
