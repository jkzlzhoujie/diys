package cn.temobi.complex.entity;


/**
 * 圈子帖子评论
 *
 */
public class CirclePostDisc extends IdEntity{

	private Long circlePostId;	//帖子ID
	private String picUrl ;		//内容图片地址
	private String thumbnail;	//缩略图
	private String text ;		//内容
	private String emoji ;		//表情
	
	private String discTime;	//评论时间
	private Long discUserId;	//评论人
	private String discNickName;//评论人昵称
	private String discHeadImageUrl;//头像url
	
	private int zanNum;  		//点赞数
	private int type;			//类型 1 评论 2 回复
	private int isAccusation;  	//是否被举报 1 是 0 否
	private Long parentId;		//回复的父级ID
	private int status ;		//状态  1 正常 0 管理员删除 2 自己删除
 	
	
	
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDiscTime() {
		return discTime;
	}
	public void setDiscTime(String discTime) {
		this.discTime = discTime;
	}
	public int getZanNum() {
		return zanNum;
	}
	public void setZanNum(int zanNum) {
		this.zanNum = zanNum;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getIsAccusation() {
		return isAccusation;
	}
	public void setIsAccusation(int isAccusation) {
		this.isAccusation = isAccusation;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getCirclePostId() {
		return circlePostId;
	}
	public void setCirclePostId(Long circlePostId) {
		this.circlePostId = circlePostId;
	}
	public String getDiscHeadImageUrl() {
		return discHeadImageUrl;
	}
	public void setDiscHeadImageUrl(String discHeadImageUrl) {
		this.discHeadImageUrl = discHeadImageUrl;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getDiscUserId() {
		return discUserId;
	}
	public void setDiscUserId(Long discUserId) {
		this.discUserId = discUserId;
	}
	public String getDiscNickName() {
		return discNickName;
	}
	public void setDiscNickName(String discNickName) {
		this.discNickName = discNickName;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	public String getEmoji() {
		return emoji;
	}
	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}
	
	
	

}
