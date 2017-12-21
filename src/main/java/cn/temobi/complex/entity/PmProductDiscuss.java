package cn.temobi.complex.entity;

import java.util.List;

public class PmProductDiscuss extends IdEntity{

	private Long productId;
	private Long userId;
	private Long discussUserId;
	private int floorNum;
	private int type;
	private String content;
	private String imageUrl;
	private int level;
	private Long parentDiscussId;
	private int praises;
	private int replys;
	private int hotScore;
	private int isRead;
	private Long replyUserId;
	private String replyTime;
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
	public Long getDiscussUserId() {
		return discussUserId;
	}
	public void setDiscussUserId(Long discussUserId) {
		this.discussUserId = discussUserId;
	}
	public int getFloorNum() {
		return floorNum;
	}
	public void setFloorNum(int floorNum) {
		this.floorNum = floorNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public Long getParentDiscussId() {
		return parentDiscussId;
	}
	public void setParentDiscussId(Long parentDiscussId) {
		this.parentDiscussId = parentDiscussId;
	}
	public int getPraises() {
		return praises;
	}
	public void setPraises(int praises) {
		this.praises = praises;
	}
	public int getReplys() {
		return replys;
	}
	public void setReplys(int replys) {
		this.replys = replys;
	}
	public int getHotScore() {
		return hotScore;
	}
	public void setHotScore(int hotScore) {
		this.hotScore = hotScore;
	}
	public Long getReplyUserId() {
		return replyUserId;
	}
	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}
	public String getReplyTime() {
		return replyTime;
	}
	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}
}
