package cn.temobi.complex.entity;

public class SysConfigParam extends IdEntity{

	private String cnParamName;
	private String enParamName;
	private String paramValue;
	private String remark;
	private String type;
	private String createdWhen;
	private String status;
	private String flag;
	private String expand1;
	private String expand2;
	private String expand3;
	private int sort;
	public String getCnParamName() {
		return cnParamName;
	}
	public void setCnParamName(String cnParamName) {
		this.cnParamName = cnParamName;
	}
	public String getEnParamName() {
		return enParamName;
	}
	public void setEnParamName(String enParamName) {
		this.enParamName = enParamName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getExpand1() {
		return expand1;
	}
	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}
	public String getExpand2() {
		return expand2;
	}
	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}
	public String getExpand3() {
		return expand3;
	}
	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
}
