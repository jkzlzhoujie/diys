package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ShareCountDto implements Serializable{
	private String shareCount;//writingNum
	private String allShareCount;//writingNum
	private Long themeId;//
	private String themeUserId;//
	private String imageUrl;//
	private String createdWhen;//
	public String getShareCount() {
		return shareCount;
	}
	public void setShareCount(String shareCount) {
		this.shareCount = shareCount;
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
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getAllShareCount() {
		return allShareCount;
	}
	public void setAllShareCount(String allShareCount) {
		this.allShareCount = allShareCount;
	}
}
