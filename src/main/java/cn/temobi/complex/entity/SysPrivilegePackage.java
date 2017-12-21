package cn.temobi.complex.entity;

import java.util.Date;

//系统特权套餐
public class SysPrivilegePackage extends IdEntity{

	private String privilegeId;	//特权ID
	private String packageId;	//套餐ID
	private String packageName;	//套餐名称
	private Date creatWhen;
	private Date updateWhen;
	
	
	public String getPackageId() {
		return packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	public Date getCreatWhen() {
		return creatWhen;
	}
	public void setCreatWhen(Date creatWhen) {
		this.creatWhen = creatWhen;
	}
	public Date getUpdateWhen() {
		return updateWhen;
	}
	public void setUpdateWhen(Date updateWhen) {
		this.updateWhen = updateWhen;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(String privilegeId) {
		this.privilegeId = privilegeId;
	}
	
	
	
	

}
