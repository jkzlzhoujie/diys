package cn.temobi.complex.entity;

public class CmGroup extends IdEntity{

	
	private String name;//组名
	private String remark;//备注
	private String createWhen;//创建时间
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateWhen() {
		return createWhen;
	}
	public void setCreateWhen(String createWhen) {
		this.createWhen = createWhen;
	}


}
