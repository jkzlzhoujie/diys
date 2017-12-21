package cn.temobi.complex.entity;

import java.util.Date;

//用户特权
public class CmUserPrivilege extends IdEntity{

	private Long privilegeId;		//特权ID
	private String privilegeName;	//特权名称
	private String privilegeCode;	//特权编码
	private String imageUrl;		//图标地址
	private Long packageId;			//套餐ID
	private String packageName;		//套餐名称
	private String packageCode;		//套餐编码
	private Long userId;			//用户ID
	private String userNickName;	//用户昵称
	private String effectTime ;		//生效时间
	private String failureTime ;	//失效时间
	private Integer isNew ;			//开通类型 1 新开通  2 续费'
	private Date creatWhen;
	private Date updateWhen;
	
	public String getPrivilegeName() {
		return privilegeName;
	}
	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
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
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public Long getPrivilegeId() {
		return privilegeId;
	}
	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageCode() {
		return packageCode;
	}
	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
	
	
	
	
	

}
