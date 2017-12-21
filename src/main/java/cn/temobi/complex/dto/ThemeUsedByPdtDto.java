package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

public class ThemeUsedByPdtDto extends IdEntity{

	private Long productId;
	private Long themeId;
	private String url;//图片url
	private String imageUrl;//主题图	
	private String createdWhen;//创建时间
	private int searchCount;
	private int praiseCount;
	private int discussCount;
	private int hotScore;
	private int magicScore;
	private String productLabel;
	private String createFrom; //0、系统后台1、用户
	private int jumpType; //0 详情页 1 H5页 2 url地址
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public int getSearchCount() {
		return searchCount;
	}
	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public int getHotScore() {
		return hotScore;
	}
	public void setHotScore(int hotScore) {
		this.hotScore = hotScore;
	}
	public int getMagicScore() {
		return magicScore;
	}
	public void setMagicScore(int magicScore) {
		this.magicScore = magicScore;
	}
	public String getProductLabel() {
		return productLabel;
	}
	public void setProductLabel(String productLabel) {
		this.productLabel = productLabel;
	}
	public String getCreateFrom() {
		return createFrom;
	}
	public void setCreateFrom(String createFrom) {
		this.createFrom = createFrom;
	}
	public int getJumpType() {
		return jumpType;
	}
	public void setJumpType(int jumpType) {
		this.jumpType = jumpType;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
