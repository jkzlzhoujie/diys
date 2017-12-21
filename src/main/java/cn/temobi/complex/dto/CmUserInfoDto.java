package cn.temobi.complex.dto;

public class CmUserInfoDto{

	private Long userId;
	private String mobile;
	private String nickName;
	private String headImageUrl;
	private int sex;
	private String birth;
	private String qqUserId;
	private String weixinUserId;
	private String weiboUserId;
	private String signature;
	private String tokenId;
	private String qq;
	private String education;
	private String createdWhen;
	private String iMpwd;
	private String isSetPwd;
	private String career;
	private String identity;
	
	private String city;//城市
	private int level;// 等级
	private int fansCount;//粉丝
	private int praisesCount;//赞数
	private int productCount;//作品数
	private int discussCount;//评论数
	private int attentionsCount;//访客数
	private String coverThumbnail;//背景图
	private String attentionLabel;//标签
	private int circlePostCount;//帖子数量
	
	private String name;//收货人
	private String reAddress;//地址
	private String rePhone;//手机号
	private String rePostCode;//邮编
	private String privilegeLevel;	//特权等级   默认 general，VIP
	
	
	
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getQqUserId() {
		return qqUserId;
	}
	public void setQqUserId(String qqUserId) {
		this.qqUserId = qqUserId;
	}
	public String getWeixinUserId() {
		return weixinUserId;
	}
	public void setWeixinUserId(String weixinUserId) {
		this.weixinUserId = weixinUserId;
	}
	public String getWeiboUserId() {
		return weiboUserId;
	}
	public void setWeiboUserId(String weiboUserId) {
		this.weiboUserId = weiboUserId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getiMpwd() {
		return iMpwd;
	}
	public void setiMpwd(String iMpwd) {
		this.iMpwd = iMpwd;
	}
	public String getIsSetPwd() {
		return isSetPwd;
	}
	public void setIsSetPwd(String isSetPwd) {
		this.isSetPwd = isSetPwd;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCareer() {
		return career;
	}
	public void setCareer(String career) {
		this.career = career;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getFansCount() {
		return fansCount;
	}
	public void setFansCount(int fansCount) {
		this.fansCount = fansCount;
	}
	public int getPraisesCount() {
		return praisesCount;
	}
	public void setPraisesCount(int praisesCount) {
		this.praisesCount = praisesCount;
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
	public String getAttentionLabel() {
		return attentionLabel;
	}
	public void setAttentionLabel(String attentionLabel) {
		this.attentionLabel = attentionLabel;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReAddress() {
		return reAddress;
	}
	public void setReAddress(String reAddress) {
		this.reAddress = reAddress;
	}
	public String getRePhone() {
		return rePhone;
	}
	public void setRePhone(String rePhone) {
		this.rePhone = rePhone;
	}
	public String getRePostCode() {
		return rePostCode;
	}
	public void setRePostCode(String rePostCode) {
		this.rePostCode = rePostCode;
	}
	public int getCirclePostCount() {
		return circlePostCount;
	}
	public void setCirclePostCount(int circlePostCount) {
		this.circlePostCount = circlePostCount;
	}
	public String getPrivilegeLevel() {
		return privilegeLevel;
	}
	public void setPrivilegeLevel(String privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}
}
