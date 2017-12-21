package cn.temobi.complex.dto;


import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.complex.entity.IdEntity;


/**
 * 圈子帖子回复与父级评论
 *
 */
public class CirclePostDiscDto extends IdEntity{

	private Long circlePostId;	//帖子ID
	private String picUrl ;		//内容图片地址
	private String text ;		//内容
	private String emoji ;		//表情
	private String discTime;	//评论时间
	private Long discUserId;	//评论人
	private String discNickName;//评论人昵称
	private String discHeadImageUrl;//头像url
	private int zanNum;  		//点赞数
	private int isZan;  	//是否点赞 1 是 0 否
	private int isCai;  	//是否点踩 1 是 0 否
	private int isFen;  	//是否分享 1 是 0 否
	
	private CirclePostDisc parentCirclePostDisc;//父级评论的实体
	private CirclePost circlePost;//帖子实体
	
	
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
	public CirclePostDisc getParentCirclePostDisc() {
		return parentCirclePostDisc;
	}
	public void setParentCirclePostDisc(CirclePostDisc parentCirclePostDisc) {
		this.parentCirclePostDisc = parentCirclePostDisc;
	}
	public CirclePost getCirclePost() {
		return circlePost;
	}
	public void setCirclePost(CirclePost circlePost) {
		this.circlePost = circlePost;
	}
	public int getIsZan() {
		return isZan;
	}
	public void setIsZan(int isZan) {
		this.isZan = isZan;
	}
	public int getIsCai() {
		return isCai;
	}
	public void setIsCai(int isCai) {
		this.isCai = isCai;
	}
	public int getIsFen() {
		return isFen;
	}
	public void setIsFen(int isFen) {
		this.isFen = isFen;
	}
	public String getEmoji() {
		return emoji;
	}
	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}
	
	

}
