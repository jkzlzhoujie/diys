package cn.temobi.complex.entity;


@SuppressWarnings("serial")
public class CmTalenInfo extends IdEntity{
	private Long userId;
	private int talenScore;
	private int talenSeq;
	private String remark;
	private String type;
	private String createdWhen;//创建时间
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getTalenScore() {
		return talenScore;
	}
	public void setTalenScore(int talenScore) {
		this.talenScore = talenScore;
	}
	public int getTalenSeq() {
		return talenSeq;
	}
	public void setTalenSeq(int talenSeq) {
		this.talenSeq = talenSeq;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
