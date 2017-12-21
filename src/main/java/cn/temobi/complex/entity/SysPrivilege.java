package cn.temobi.complex.entity;

import java.util.Date;
import java.util.List;

//系统特权
public class SysPrivilege extends IdEntity{

	private String name;		//名称
	private String code;		//编码
	private Integer status ;	//状态 1 上线  2 下线
	private String level;		//等级
	private String note;		//内容
	private String imageUrl;	//图标地址
	private String grayImageUrl;//灰色图标地址
	private Date creatWhen;
	private Date updateWhen;
	private List<SysPackage> sysPackageList;
	private List<SysPrivilegeContentType> sysPrivilegeContentTypeList;
	private String effectTime ;		//生效时间
	private String failureTime ;	//失效时间
	private Integer isNew ;			//开通类型  0 未开通 1 新开通  2 续费'
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<SysPackage> getSysPackageList() {
		return sysPackageList;
	}
	public void setSysPackageList(List<SysPackage> sysPackageList) {
		this.sysPackageList = sysPackageList;
	}
	public List<SysPrivilegeContentType> getSysPrivilegeContentTypeList() {
		return sysPrivilegeContentTypeList;
	}
	public void setSysPrivilegeContentTypeList(
			List<SysPrivilegeContentType> sysPrivilegeContentTypeList) {
		this.sysPrivilegeContentTypeList = sysPrivilegeContentTypeList;
	}
	public String getEffectTime() {
		return effectTime;
	}
	public void setEffectTime(String effectTime) {
		this.effectTime = effectTime;
	}
	public String getFailureTime() {
		return failureTime;
	}
	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}
	public Integer getIsNew() {
		return isNew;
	}
	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	public String getGrayImageUrl() {
		return grayImageUrl;
	}
	public void setGrayImageUrl(String grayImageUrl) {
		this.grayImageUrl = grayImageUrl;
	}
	
	
	

}
