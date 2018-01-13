package cn.temobi.complex.entity;

import java.util.Date;
import java.util.List;

public class NetRedUser extends IdEntity{

    private static final long serialVersionUID = -3629212650369687264L;
    private String name; //姓名
	private String phone;//手机号
	private String province;//省份
	private String city;//城市
	private String town;//区县
	private String selfIntroduction;//自我介绍
	private Integer liveExperience;//直播经验  1 是 2 否
	private Integer fansAmount;//粉丝数  1 ： 0-500  2：500-1000 3：1000以上 4 其他
	private String webSit;//展示地址
	private String firstImage;//首图
	private String welcomeWord;//欢迎词
	private String thanksWord;//感谢词
	private String callTanksWord;//答谢感谢词
	private Integer gameRounds;//第几轮
	private Date createTime;//报名时间
	private Long weichatUserId;//公众号关注用户
	private String[] lablesArr;
	private String[] imagesArr;
	
	private String createTimeStr;//报名时间
	private Integer count;
	private Integer callCount;
	private Integer rank;
	private String area;//区域 1 厦门 2 福州 3 泉州 4漳州
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	public String getSelfIntroduction() {
		return selfIntroduction;
	}
	public void setSelfIntroduction(String selfIntroduction) {
		this.selfIntroduction = selfIntroduction;
	}
	public Integer getLiveExperience() {
		return liveExperience;
	}
	public void setLiveExperience(Integer liveExperience) {
		this.liveExperience = liveExperience;
	}
	public Integer getFansAmount() {
		return fansAmount;
	}
	public void setFansAmount(Integer fansAmount) {
		this.fansAmount = fansAmount;
	}
	public String getWebSit() {
		return webSit;
	}
	public void setWebSit(String webSit) {
		this.webSit = webSit;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getWelcomeWord() {
		return welcomeWord;
	}
	public void setWelcomeWord(String welcomeWord) {
		this.welcomeWord = welcomeWord;
	}
	public String getThanksWord() {
		return thanksWord;
	}
	public void setThanksWord(String thanksWord) {
		this.thanksWord = thanksWord;
	}
	public String getCallTanksWord() {
		return callTanksWord;
	}
	public void setCallTanksWord(String callTanksWord) {
		this.callTanksWord = callTanksWord;
	}
	public Integer getGameRounds() {
		return gameRounds;
	}
	public void setGameRounds(Integer gameRounds) {
		this.gameRounds = gameRounds;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getWeichatUserId() {
		return weichatUserId;
	}
	public void setWeichatUserId(Long weichatUserId) {
		this.weichatUserId = weichatUserId;
	}
	public String[] getLablesArr() {
		return lablesArr;
	}
	public void setLablesArr(String[] lablesArr) {
		this.lablesArr = lablesArr;
	}
	public String[] getImagesArr() {
		return imagesArr;
	}
	public void setImagesArr(String[] imagesArr) {
		this.imagesArr = imagesArr;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getCallCount() {
		return callCount;
	}
	public void setCallCount(Integer callCount) {
		this.callCount = callCount;
	}
	public Integer getRank() {
		return rank;
	}
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	
	
	
	
   
	
	
}
