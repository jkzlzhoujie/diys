package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class ApplicationCount extends IdEntity{
	private Long applicationId;//应用ID
	private Long clientId;//客户端ID
	private String createdWhen;//创建时间
	private String type;
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
