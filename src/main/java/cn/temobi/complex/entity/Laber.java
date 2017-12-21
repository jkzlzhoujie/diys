package cn.temobi.complex.entity;

public class Laber extends IdEntity{

	private String name;
	private String type;//1 表情/颜文字2 贴图 3 主题 4 作品/用户
	private String status;//1 管理员添加 2用户添加
	private Long clientId;//用户ID
	private String addTime;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	
}
