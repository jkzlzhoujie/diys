package cn.temobi.complex.dto;


import java.util.Date;

public class VoteRecordDto{

	private Long netRedUserId;//网红用户
	private String netRedUserName;//网红用户名称
	private Long voteUserId;//投票用户
	private String voteUserUserNickName;//投票用户昵称
	private Date addTime;
	private Long count;//票数
	private Long callCount;//打call数
	private int type;//投票类型  1 送花 2 打call
	private String typeName;
	
	
	public Long getNetRedUserId() {
		return netRedUserId;
	}
	public void setNetRedUserId(Long netRedUserId) {
		this.netRedUserId = netRedUserId;
	}
	
	public Long getVoteUserId() {
		return voteUserId;
	}
	public void setVoteUserId(Long voteUserId) {
		this.voteUserId = voteUserId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public Long getCount() {
		return count;
	}
	
	public Long getCallCount() {
		return callCount;
	}
	public void setCallCount(Long callCount) {
		this.callCount = callCount;
	}
	public void setCount(Long count) {
		this.count = count;
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
	public String getVoteUserUserNickName() {
		return voteUserUserNickName;
	}
	public void setVoteUserUserNickName(String voteUserUserNickName) {
		this.voteUserUserNickName = voteUserUserNickName;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	
	
	
	

}
