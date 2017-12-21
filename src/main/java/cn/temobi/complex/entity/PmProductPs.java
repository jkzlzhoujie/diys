package cn.temobi.complex.entity;

public class PmProductPs extends IdEntity{

	private Long productId;
	private Long userId;
	private Long psUserId;
	private int floorNum;
	private String content;
	private String imageUrl;
	private int level;
	private Long parentPsId;
	private int isRead;
	private String createdWhen;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getPsUserId() {
		return psUserId;
	}
	public void setPsUserId(Long psUserId) {
		this.psUserId = psUserId;
	}
	public int getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(int floorNum) {
		this.floorNum = floorNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getParentPsId() {
		return parentPsId;
	}
	public void setParentPsId(Long parentPsId) {
		this.parentPsId = parentPsId;
	}
	public int getIsRead() {
		return isRead;
	}
	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
