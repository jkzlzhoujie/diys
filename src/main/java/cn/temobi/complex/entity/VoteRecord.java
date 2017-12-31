package cn.temobi.complex.entity;

import java.util.Date;

public class VoteRecord extends IdEntity{

	private long netRedUserId;//网红用户
	private long voteUserId;//投票用户
	private Date createTime;
	private int count;//票数
	private int callCount;//打call数
	private int type;//投票类型  1 送花 2 打call
	
	//扩展
	private String netRedUserName;//网红用户名称
	private String firstImage;//网红用户头像
	private String voteUserUserNickName;//投票用户昵称
	private String headImgUrl;//投票用户头像
	private int countPer;//票数
	private int callCountPer;//打call数
	private String createTimeStr;
	private String typeName;
	
	
	
	public long getNetRedUserId() {
		return netRedUserId;
	}
	public void setNetRedUserId(long netRedUserId) {
		this.netRedUserId = netRedUserId;
	}
	public long getVoteUserId() {
		return voteUserId;
	}
	public void setVoteUserId(long voteUserId) {
		this.voteUserId = voteUserId;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getCallCount() {
		return callCount;
	}
	public void setCallCount(int callCount) {
		this.callCount = callCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getNetRedUserName() {
		return netRedUserName;
	}
	public void setNetRedUserName(String netRedUserName) {
		this.netRedUserName = netRedUserName;
	}
	public String getFirstImage() {
		return firstImage;
	}
	public void setFirstImage(String firstImage) {
		this.firstImage = firstImage;
	}
	public String getVoteUserUserNickName() {
		return voteUserUserNickName;
	}
	public void setVoteUserUserNickName(String voteUserUserNickName) {
		this.voteUserUserNickName = voteUserUserNickName;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	public int getCountPer() {
		return countPer;
	}
	public void setCountPer(int countPer) {
		this.countPer = countPer;
	}
	public int getCallCountPer() {
		return callCountPer;
	}
	public void setCallCountPer(int callCountPer) {
		this.callCountPer = callCountPer;
	}
	public String getCreateTimeStr() {
		return createTimeStr;
	}
	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	
	
	
	

}
