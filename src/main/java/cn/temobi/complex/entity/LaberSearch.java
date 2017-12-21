package cn.temobi.complex.entity;

public class LaberSearch extends IdEntity{

	private String laberName;
	private Long clientId;
	private String createdWhen;
	
	public String getLaberName() {
		return laberName;
	}
	public void setLaberName(String laberName) {
		this.laberName = laberName;
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
