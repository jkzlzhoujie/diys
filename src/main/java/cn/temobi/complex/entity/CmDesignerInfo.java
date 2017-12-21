package cn.temobi.complex.entity;

import java.util.List;

public class CmDesignerInfo {
	
	private Long id;							//设计师编号
	private Long userId;    					//用户ID
	private String lifeImageUrl;  				//生活照片
	private String lifeImageUrlThumbnail;  		//生活照片缩略图
	private String realName;  					//真实姓名
	private String school;  					//毕业院校
	private String finishSchoolTime;  			//毕业时间
	private String finishSchoolPhoto;  			//毕业证照
	private String finishSchoolPhotoThumbnail;  //毕业证照缩略图
	private String idCardNum;  					//身份证号码
	private String idCardUrl1;  				//身份证图1
	private String idCardUrl1Thumbnail;  		//身份证图1缩略图
	private String idCardUrl2;  				//身份证图2
	private String idCardUrl2Thumbnail;  		//身份证图2缩略图
	private String sesameCredit;  				//芝麻信用认证 0：未认证，1：认证
	private int status;  						//设计师状态 0：待申核，1：审核中，2通过
	private String createTime;					//创建时间
	private String updateTime;					//最后更新时间
	private String labels;						//技能标签
	private int shopStatus;						//店铺状态，0：关闭，1：正常接单，默认为1
	private List<CmDesignerInfoPic> cmDesignerInfoPicList ;//设计师作品
	private List<CmDesignerHonor> cmDesignerHonorList ;//设计师荣誉
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getLifeImageUrl() {
		return lifeImageUrl;
	}
	public void setLifeImageUrl(String lifeImageUrl) {
		this.lifeImageUrl = lifeImageUrl;
	}
	public String getLifeImageUrlThumbnail() {
		return lifeImageUrlThumbnail;
	}
	public void setLifeImageUrlThumbnail(String lifeImageUrlThumbnail) {
		this.lifeImageUrlThumbnail = lifeImageUrlThumbnail;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getFinishSchoolTime() {
		return finishSchoolTime;
	}
	public void setFinishSchoolTime(String finishSchoolTime) {
		this.finishSchoolTime = finishSchoolTime;
	}
	public String getFinishSchoolPhoto() {
		return finishSchoolPhoto;
	}
	public void setFinishSchoolPhoto(String finishSchoolPhoto) {
		this.finishSchoolPhoto = finishSchoolPhoto;
	}
	public String getFinishSchoolPhotoThumbnail() {
		return finishSchoolPhotoThumbnail;
	}
	public void setFinishSchoolPhotoThumbnail(String finishSchoolPhotoThumbnail) {
		this.finishSchoolPhotoThumbnail = finishSchoolPhotoThumbnail;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public String getIdCardUrl1() {
		return idCardUrl1;
	}
	public void setIdCardUrl1(String idCardUrl1) {
		this.idCardUrl1 = idCardUrl1;
	}
	public String getIdCardUrl1Thumbnail() {
		return idCardUrl1Thumbnail;
	}
	public void setIdCardUrl1Thumbnail(String idCardUrl1Thumbnail) {
		this.idCardUrl1Thumbnail = idCardUrl1Thumbnail;
	}
	public String getIdCardUrl2() {
		return idCardUrl2;
	}
	public void setIdCardUrl2(String idCardUrl2) {
		this.idCardUrl2 = idCardUrl2;
	}
	public String getIdCardUrl2Thumbnail() {
		return idCardUrl2Thumbnail;
	}
	public void setIdCardUrl2Thumbnail(String idCardUrl2Thumbnail) {
		this.idCardUrl2Thumbnail = idCardUrl2Thumbnail;
	}
	public String getSesameCredit() {
		return sesameCredit;
	}
	public void setSesameCredit(String sesameCredit) {
		this.sesameCredit = sesameCredit;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getLabels() {
		return labels;
	}
	public void setLabels(String labels) {
		this.labels = labels;
	}
	public List<CmDesignerInfoPic> getCmDesignerInfoPicList() {
		return cmDesignerInfoPicList;
	}
	public void setCmDesignerInfoPicList(
			List<CmDesignerInfoPic> cmDesignerInfoPicList) {
		this.cmDesignerInfoPicList = cmDesignerInfoPicList;
	}
	public List<CmDesignerHonor> getCmDesignerHonorList() {
		return cmDesignerHonorList;
	}
	public void setCmDesignerHonorList(List<CmDesignerHonor> cmDesignerHonorList) {
		this.cmDesignerHonorList = cmDesignerHonorList;
	}
	public int getShopStatus() {
		return shopStatus;
	}
	public void setShopStatus(int shopStatus) {
		this.shopStatus = shopStatus;
	}
	
	
	
}
