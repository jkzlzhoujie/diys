package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShareDto implements Serializable{
	private String imei;//imei
	private String shareType;//shareType
	private String useType;//useType
	private String image;//image
	private String imageUrl;//主题图
	private String expressNum;//expressNum
	private String chartletNum;//chartletNum
	private String yanWritingNum;//yanWritingNum
	private String addTime;//addTime
	private String writingNum;//writingNum
	private String machine;//writingNum
	private Long themeId;//
	private String themeUserId;//
	private String channel;//
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getExpressNum() {
		return expressNum;
	}
	public void setExpressNum(String expressNum) {
		this.expressNum = expressNum;
	}
	public String getChartletNum() {
		return chartletNum;
	}
	public void setChartletNum(String chartletNum) {
		this.chartletNum = chartletNum;
	}
	public String getYanWritingNum() {
		return yanWritingNum;
	}
	public void setYanWritingNum(String yanWritingNum) {
		this.yanWritingNum = yanWritingNum;
	}
	public String getWritingNum() {
		return writingNum;
	}
	public void setWritingNum(String writingNum) {
		this.writingNum = writingNum;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getThemeUserId() {
		return themeUserId;
	}
	public void setThemeUserId(String themeUserId) {
		this.themeUserId = themeUserId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
