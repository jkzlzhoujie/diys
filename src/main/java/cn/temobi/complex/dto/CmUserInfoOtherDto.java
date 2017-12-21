package cn.temobi.complex.dto;

import java.util.List;

import cn.temobi.complex.entity.CmUserPrivilege;

public class CmUserInfoOtherDto{

	private Long userId;
	private int fansCount;
	private int attentionsCount;
	private int level;
	private String coverThumbnail;
	private int coverPraises;
	private String headImageUrl;
	private String city;
	private String birth;
	private String career;
	private String age;
	private int sex;
	private int isZ;
	private int isRead;
	private int productCount;
	private int discussCount;
	private int praisesCount;
	private int circlePostCount;//帖子数量
	private int collectionNum;//收藏数量
	private String attentionLabel;
	private String nickName;
	private String signature;
	private String introduce;
	private String identityInfo;
	private String productType;
	private List<String> list;
	private String priLevel;		//特权等级    general普通会员
	private List<CmUserPrivilege> cmUserPrivilegeList;//特权列表
	private int isCollect;			//1 已收藏 0 未收藏
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getFansCount() {
		return fansCount;
	}
	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}
	public int getAttentionsCount() {
		return attentionsCount;
	}
	public void setAttentionsCount(int attentionsCount) {
		this.attentionsCount = attentionsCount;
	}
	public String getCoverThumbnail() {
		return coverThumbnail;
	}
	public void setCoverThumbnail(String coverThumbnail) {
		this.coverThumbnail = coverThumbnail;
	}
	public int getCoverPraises() {
		return coverPraises;
	}
	public void setCoverPraises(int coverPraises) {
		this.coverPraises = coverPraises;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getAttentionLabel() {
		return attentionLabel;
	}
	public void setAttentionLabel(String attentionLabel) {
		this.attentionLabel = attentionLabel;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public int getProductCount() {
		return productCount;
	}
	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public int getIsZ() {
		return isZ;
	}
	public void setIsZ(int isZ) {
		this.isZ = isZ;
	}
	public int getPraisesCount() {
		return praisesCount;
	}
	public void setPraisesCount(int praisesCount) {
		this.praisesCount = praisesCount;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getIdentityInfo() {
		return identityInfo;
	}
	public void setIdentityInfo(String identityInfo) {
		this.identityInfo = identityInfo;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public List<String> getList() {
		return list;
	}
	public void setList(List<String> list) {
		this.list = list;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getPriLevel() {
		return priLevel;
	}
	public void setPriLevel(String priLevel) {
		this.priLevel = priLevel;
	}
	public List<CmUserPrivilege> getCmUserPrivilegeList() {
		return cmUserPrivilegeList;
	}
	public void setCmUserPrivilegeList(List<CmUserPrivilege> cmUserPrivilegeList) {
		this.cmUserPrivilegeList = cmUserPrivilegeList;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	public int getCirclePostCount() {
		return circlePostCount;
	}
	public void setCirclePostCount(int circlePostCount) {
		this.circlePostCount = circlePostCount;
	}
	public int getCollectionNum() {
		return collectionNum;
	}
	public void setCollectionNum(int collectionNum) {
		this.collectionNum = collectionNum;
	}
	
	
}
