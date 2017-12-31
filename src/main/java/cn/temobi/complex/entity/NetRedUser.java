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
	private int liveExperience;//直播经验  1 是 2 否
	private int fansAmount;//粉丝数  1 ： 0-500  2：500-1000 3：1000以上 4 其他
	private String webSit;//展示地址
	private String firstImage;//首图
	private String welcomeWord;//欢迎词
	private String thanksWord;//感谢词
	private String callTanksWord;//答谢感谢词
	private int gameRounds;//第几轮
	private Date createTime;//报名时间
	private long weichatUserId;//公众号关注用户
	private String[] lablesArr;
	private String[] imagesArr;
	
	private String createTimeStr;//报名时间
	private int count;
	private int callCount;
	private int rank;
	
	
	
	
    public int getCount() {
		return count;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}
	public long getCallCount() {
		return callCount;
	}
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
	public int getLiveExperience() {
		return liveExperience;
	}
	public void setLiveExperience(int liveExperience) {
		this.liveExperience = liveExperience;
	}
	public int getFansAmount() {
		return fansAmount;
	}
	public void setFansAmount(int fansAmount) {
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
	public int getGameRounds() {
		return gameRounds;
	}
	public void setGameRounds(int gameRounds) {
		this.gameRounds = gameRounds;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public long getWeichatUserId() {
		return weichatUserId;
	}
	public void setWeichatUserId(long weichatUserId) {
		this.weichatUserId = weichatUserId;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
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
	
	
}
