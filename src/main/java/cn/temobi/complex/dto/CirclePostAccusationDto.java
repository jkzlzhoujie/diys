package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

public class CirclePostAccusationDto extends IdEntity{

	private Long accusationUserId;
	private Long userId;
	private String type;
	private String content;
	private String createdWhen;
	private String accusationNickName;
	private String nickName;
	private String isDistort;
	private int accuErrCount;
	private int accuCount;
	private int createType;
	
	public Long getAccusationUserId() {
		return accusationUserId;
	}
	public void setAccusationUserId(Long accusationUserId) {
		this.accusationUserId = accusationUserId;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAccusationNickName() {
		return accusationNickName;
	}
	public void setAccusationNickName(String accusationNickName) {
		this.accusationNickName = accusationNickName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getIsDistort() {
		return isDistort;
	}
	public void setIsDistort(String isDistort) {
		this.isDistort = isDistort;
	}
	public int getCreateType() {
		return createType;
	}
	public void setCreateType(int createType) {
		this.createType = createType;
	}
	public int getAccuErrCount() {
		return accuErrCount;
	}
	public void setAccuErrCount(int accuErrCount) {
		this.accuErrCount = accuErrCount;
	}
	public int getAccuCount() {
		return accuCount;
	}
	public void setAccuCount(int accuCount) {
		this.accuCount = accuCount;
	}
}
