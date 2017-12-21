package cn.temobi.complex.entity;

public class Theme extends IdEntity{

	private String themeName;
	private Long classifyId;
	private String userId;
	private String themeUnlock;
	private Long loveNum;
	private String thumbnailUrl;
	private String imageUrl;
	private String status;
	private String h5Push;
	private String createdWhen;
	private Long sort;
	
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public String getThemeUnlock() {
		return themeUnlock;
	}
	public void setThemeUnlock(String themeUnlock) {
		this.themeUnlock = themeUnlock;
	}
	public Long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	public Long getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(Long loveNum) {
		this.loveNum = loveNum;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getH5Push() {
		return h5Push;
	}
	public void setH5Push(String h5Push) {
		this.h5Push = h5Push;
	}
}
