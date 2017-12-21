package cn.temobi.complex.entity;

import java.util.List;

//圈内帖子
public class CirclePost extends IdEntity{

	private String title;		//标题
	private String text;		//内容文本
	private Long userId;		//用户ID
	private String nickName;	//用户昵称
	private String headImageUrl;//头像url
	private Long circleId;		//圈子ID
	private String circleName;	//圈子名称
	private int status ;		//状态 1 正常 0 理员删除 2 自己删除
	private int discussNum;  	//评论数
	private int zanNum;  		//点赞数
	private int caiNum;  		//点踩 数
	private int shareNum;  		//分享数
	private int isAccusation;  	//是否被举报 1 是 0 否
	private String createdWhen;	//发布时间
	
	private int isZan;  	//是否点赞 1 是 0 否
	private int isCai;  	//是否点踩 1 是 0 否
	private int isFen;  	//是否分享 1 是 0 否
	private List<String> circlePostPicList;//帖子图片
	private String circleUserId;//圈主用户ID
	private int flag;	   	 //0 正常 1用户解散 2管理员删除
	
	private int picNum;		//作品数量，需从图片列表去查。
	
	
	public int getPicNum() {
		return picNum;
	}
	public void setPicNum(int picNum) {
		this.picNum = picNum;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getHeadImageUrl() {
		return headImageUrl;
	}
	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}
	public Long getCircleId() {
		return circleId;
	}
	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDiscussNum() {
		return discussNum;
	}
	public void setDiscussNum(int discussNum) {
		this.discussNum = discussNum;
	}
	public int getZanNum() {
		return zanNum;
	}
	public void setZanNum(int zanNum) {
		this.zanNum = zanNum;
	}
	public int getShareNum() {
		return shareNum;
	}
	public void setShareNum(int shareNum) {
		this.shareNum = shareNum;
	}
	public int getIsAccusation() {
		return isAccusation;
	}
	public void setIsAccusation(int isAccusation) {
		this.isAccusation = isAccusation;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public List<String> getCirclePostPicList() {
		return circlePostPicList;
	}
	public void setCirclePostPicList(List<String> circlePostPicList) {
		this.circlePostPicList = circlePostPicList;
	}
	public String getCircleName() {
		return circleName;
	}
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public int getCaiNum() {
		return caiNum;
	}
	public void setCaiNum(int caiNum) {
		this.caiNum = caiNum;
	}
	public String getCircleUserId() {
		return circleUserId;
	}
	public void setCircleUserId(String circleUserId) {
		this.circleUserId = circleUserId;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	
	
	

}
