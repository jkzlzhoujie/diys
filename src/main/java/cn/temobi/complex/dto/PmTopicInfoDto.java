package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

/**
 */
@SuppressWarnings("serial")
public class PmTopicInfoDto extends IdEntity{
	private String topicName;
	private String bannerUrl;
	private int topicType;
	private int operatorUsers;
	private String remark;
	private String createdWhen;
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public int getTopicType() {
		return topicType;
	}
	public void setTopicType(int topicType) {
		this.topicType = topicType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public int getOperatorUsers() {
		return operatorUsers;
	}
	public void setOperatorUsers(int operatorUsers) {
		this.operatorUsers = operatorUsers;
	}
}
