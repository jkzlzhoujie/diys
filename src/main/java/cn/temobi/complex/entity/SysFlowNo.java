package cn.temobi.complex.entity;

public class SysFlowNo extends IdEntity{

	private int flowType;
	private String flowDate;
	private int flowSeq;
	private String createdTime;
	private String updateTime;
	public int getFlowType() {
		return flowType;
	}
	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}
	public String getFlowDate() {
		return flowDate;
	}
	public void setFlowDate(String flowDate) {
		this.flowDate = flowDate;
	}
	public int getFlowSeq() {
		return flowSeq;
	}
	public void setFlowSeq(int flowSeq) {
		this.flowSeq = flowSeq;
	}
	public String getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
