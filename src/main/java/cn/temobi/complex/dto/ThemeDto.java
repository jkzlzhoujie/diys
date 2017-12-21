package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

public class ThemeDto extends IdEntity{

	private String themeName;
	private Long classifyId;
	private String themeUnlock;
	private Long loveNum;
	private String imageUrl;
	private String isLove;
	private int themeNum;
	public String getThemeName() {
		return themeName;
	}
	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}
	public Long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	public String getThemeUnlock() {
		return themeUnlock;
	}
	public void setThemeUnlock(String themeUnlock) {
		this.themeUnlock = themeUnlock;
	}
	public Long getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(Long loveNum) {
		this.loveNum = loveNum;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getIsLove() {
		return isLove;
	}
	public void setIsLove(String isLove) {
		this.isLove = isLove;
	}
	public int getThemeNum() {
		return themeNum;
	}
	public void setThemeNum(int themeNum) {
		this.themeNum = themeNum;
	}
}
