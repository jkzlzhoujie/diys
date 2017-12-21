package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class PmTopicCase extends IdEntity{
	private Long topicId;
	private String srcProductUrl;
	private String psProductUrl;
	private int caseType;
	private String createdWhen;
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	public String getSrcProductUrl() {
		return srcProductUrl;
	}
	public void setSrcProductUrl(String srcProductUrl) {
		this.srcProductUrl = srcProductUrl;
	}
	public String getPsProductUrl() {
		return psProductUrl;
	}
	public void setPsProductUrl(String psProductUrl) {
		this.psProductUrl = psProductUrl;
	}
	public int getCaseType() {
		return caseType;
	}
	public void setCaseType(int caseType) {
		this.caseType = caseType;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
