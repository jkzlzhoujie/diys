package cn.temobi.complex.entity;

import java.util.Date;

public class AccessRecord extends IdEntity{

	private Long netRedUserId;//网红用户
	private Long attentionUserId;//关注用户
	private Date createTime;
	private int count;//
	
	
	public Long getNetRedUserId() {
		return netRedUserId;
	}
	public void setNetRedUserId(Long netRedUserId) {
		this.netRedUserId = netRedUserId;
	}
	public Long getAttentionUserId() {
		return attentionUserId;
	}
	public void setAttentionUserId(Long attentionUserId) {
		this.attentionUserId = attentionUserId;
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
	
	
	

}
