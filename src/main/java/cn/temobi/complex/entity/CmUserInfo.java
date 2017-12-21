package cn.temobi.complex.entity;

public class CmUserInfo extends IdEntity{

	private Long clientId;
	private String userName;
	private String password;
	private String mobile;
	private String nickName;
	private String headImageUrl;   //头像url
	private int level;
	private int score;  
	private int experience;
	private int charm;
	private int originality;
	private int credit;
	private int sex;
	private String constellation;	//星座
	private String birth;
	private String tokenId;
	private String area;			//省份
	private String city;
	private String registerFrom; 	//注册来源:0 系统后台 1 android  2  ios  3wap 
	private String lastLoginTime;	//最近登录时间
	private int isOnline;			//是否在线    0  否， 1  是
	private String lastActiveTime;	//最近活动时间
	private String qqUserId;
	private String machine;
	private int fansCount;
	private int attentionsCount;
	private int praisesCount;		//获赞数
	private int friendsCount;		//好友数
	private int productCount;		//作品数
	private int discussCount;		//评论数
	private int isAccusation; 		//是否被举报  0 否 默认 1 是
	private int accuErrCount;		//举报错误次数
	private int accuCount;
	private String weixinUserId;	//微信登录id
	private String weiboUserId;     //新浪微博登录id
	private String clientChannel;   //渠道号
	private String clientVersion;	//客户端版本号
	private String createdWhen;
	private int userType;           //0 默认，普通用户 1、设计师 2、画家
	private int isBan;				//是否禁评 ，禁发帖   默认 0 否  1 是
	private String imei;
	private String privilegeLevel;	//特权等级   默认 general，VIP
	
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
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
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
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
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
	public String getLastActiveTime() {
		return lastActiveTime;
	}
	public void setLastActiveTime(String lastActiveTime) {
		this.lastActiveTime = lastActiveTime;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
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
	public int getFriendsCount() {
		return friendsCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
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
	public int getPraisesCount() {
		return praisesCount;
	}
	public void setPraisesCount(int praisesCount) {
		this.praisesCount = praisesCount;
	}
	public int getIsAccusation() {
		return isAccusation;
	}
	public void setIsAccusation(int isAccusation) {
		this.isAccusation = isAccusation;
	}
	public int getAccuErrCount() {
		return accuErrCount;
	}
	public void setAccuErrCount(int accuErrCount) {
		this.accuErrCount = accuErrCount;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getCharm() {
		return charm;
	}
	public void setCharm(int charm) {
		this.charm = charm;
	}
	public int getOriginality() {
		return originality;
	}
	public void setOriginality(int originality) {
		this.originality = originality;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public int getIsBan() {
		return isBan;
	}
	public void setIsBan(int isBan) {
		this.isBan = isBan;
	}
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}
	public int getAccuCount() {
		return accuCount;
	}
	public void setAccuCount(int accuCount) {
		this.accuCount = accuCount;
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
