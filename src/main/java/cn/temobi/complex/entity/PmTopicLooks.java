package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class PmTopicLooks extends IdEntity{
	private Long topicId;
	private Long lookUserId;
	private Long clientId;
	private String createdWhen;
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public Long getLookUserId() {
		return lookUserId;
	}
	public void setLookUserId(Long lookUserId) {
		this.lookUserId = lookUserId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
