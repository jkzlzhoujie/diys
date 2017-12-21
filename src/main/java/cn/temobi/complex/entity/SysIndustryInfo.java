package cn.temobi.complex.entity;

/**
 * @author hjf
 * 2014 三月 17 14:06:53
 */
@SuppressWarnings("serial")
public class SysIndustryInfo extends IdEntity{
	private String name;
	private int level;
	private Long parentIndustryId;//
	private String createdWhen;//
	private String imageUrl;//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public Long getParentIndustryId() {
		return parentIndustryId;
	}
	public void setParentIndustryId(Long parentIndustryId) {
		this.parentIndustryId = parentIndustryId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
