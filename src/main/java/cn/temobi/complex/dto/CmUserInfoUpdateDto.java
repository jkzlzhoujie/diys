package cn.temobi.complex.dto;

public class CmUserInfoUpdateDto{

	private Long id;
	private Long clientId;
	private String userName;
	private String password;
	private String mobile;
	private String nickName;
	private String headImageUrl;   //头像url
	private Integer level;
	private Integer score;  
	private Integer experience;
	private Integer charm;
	private Integer originality;
	private Integer credit;
	private Integer sex;
	private String constellation;	//星座
	private String birth;
	private String tokenId;
	private String area;			//省份
	private String city;
	private String registerFrom; 	//注册来源:0 系统后台 1 android  2  ios  3wap 
	private String lastLoginTime;	//最近登录时间
	private Integer isOnline;			//是否在线    0  否， 1  是
	private String lastActiveTime;	//最近活动时间
	private String qqUserId;
	private String machine;
	private Integer fansCount;
	private Integer attentionsCount;
	private Integer praisesCount;		//获赞数
	private Integer friendsCount;		//好友数
	private Integer productCount;		//作品数
	private Integer discussCount;		//评论数
	private Integer isAccusation; 		//是否被举报  0 否 默认 1 是
	private Integer accuErrCount;		//举报错误次数
	private Integer accuCount;
	private String weixinUserId;	//微信登录id
	private String weiboUserId;     //新浪微博登录id
	private String clientChannel;   //渠道号
	private String clientVersion;	//客户端版本号
	private String createdWhen;
	private Integer userType;           //0 默认，普通用户 1、设计师 2、画家
	private Integer isBan;				//是否禁评 ，禁发帖   默认 0 否  1 是
	private String imei;
	private String privilegeLevel;	//特权等级   默认 general，VIP
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getExperience() {
		return experience;
	}
	public void setExperience(Integer experience) {
		this.experience = experience;
	}
	public Integer getCharm() {
		return charm;
	}
	public void setCharm(Integer charm) {
		this.charm = charm;
	}
	public Integer getOriginality() {
		return originality;
	}
	public void setOriginality(Integer originality) {
		this.originality = originality;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getConstellation() {
		return constellation;
	}
	public void setConstellation(String constellation) {
		this.constellation = constellation;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRegisterFrom() {
		return registerFrom;
	}
	public void setRegisterFrom(String registerFrom) {
		this.registerFrom = registerFrom;
	}
	public String getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public Integer getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}
	public String getLastActiveTime() {
		return lastActiveTime;
	}
	public void setLastActiveTime(String lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	public String getQqUserId() {
		return qqUserId;
	}
	public void setQqUserId(String qqUserId) {
		this.qqUserId = qqUserId;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public Integer getFansCount() {
		return fansCount;
	}
	public void setFansCount(Integer fansCount) {
		this.fansCount = fansCount;
	}
	public Integer getAttentionsCount() {
		return attentionsCount;
	}
	public void setAttentionsCount(Integer attentionsCount) {
		this.attentionsCount = attentionsCount;
	}
	public Integer getPraisesCount() {
		return praisesCount;
	}
	public void setPraisesCount(Integer praisesCount) {
		this.praisesCount = praisesCount;
	}
	public Integer getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}
	public Integer getProductCount() {
		return productCount;
	}
	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}
	public Integer getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(Integer discussCount) {
		this.discussCount = discussCount;
	}
	public Integer getIsAccusation() {
		return isAccusation;
	}
	public void setIsAccusation(Integer isAccusation) {
		this.isAccusation = isAccusation;
	}
	public Integer getAccuErrCount() {
		return accuErrCount;
	}
	public void setAccuErrCount(Integer accuErrCount) {
		this.accuErrCount = accuErrCount;
	}
	public Integer getAccuCount() {
		return accuCount;
	}
	public void setAccuCount(Integer accuCount) {
		this.accuCount = accuCount;
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
	public String getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(String clientChannel) {
		this.clientChannel = clientChannel;
	}
	public String getClientVersion() {
		return clientVersion;
	}
	public void setClientVersion(String clientVersion) {
		this.clientVersion = clientVersion;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public Integer getUserType() {
		return userType;
	}
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	public Integer getIsBan() {
		return isBan;
	}
	public void setIsBan(Integer isBan) {
		this.isBan = isBan;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getPrivilegeLevel() {
		return privilegeLevel;
	}
	public void setPrivilegeLevel(String privilegeLevel) {
		this.privilegeLevel = privilegeLevel;
	}
}
