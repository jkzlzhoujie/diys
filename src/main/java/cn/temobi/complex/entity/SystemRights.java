package cn.temobi.complex.entity;

/**
 * 对应表bl_system_rights
 * @author hjf
 * 2014-3-5 下午03:28:18
 */
@SuppressWarnings("serial")
public class SystemRights extends IdEntity{
	private String rightsName;//用户角色名称
	private String rightsCode;//用户角色编码
	private String createdWhen;//创建时间
	public String getRightsName() {
		return rightsName;
	}
	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}
	public String getRightsCode() {
		return rightsCode;
	}
	public void setRightsCode(String rightsCode) {
		this.rightsCode = rightsCode;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
