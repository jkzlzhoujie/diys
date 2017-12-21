package cn.temobi.complex.entity;

public class CmCircleUserLog extends IdEntity{

	private Long circleId;
	private Long userId;
	private String addTime;
	private Long clientId;
	private String nickName;//昵称
	private String name;//圈名
	public Long getCircleId() {
		return circleId;
	}
	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
