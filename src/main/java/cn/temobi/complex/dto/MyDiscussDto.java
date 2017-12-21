package cn.temobi.complex.dto;

import java.util.List;

import cn.temobi.complex.entity.PmProductDiscuss;

@SuppressWarnings("serial")
public class MyDiscussDto{
	
	private Long productId;
	private Long userId;
	private String thumbnail;
	private int searchCount;
	private int praiseCount;
	private int discussCount;
	List<PmProductDiscuss> list;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<PmProductDiscuss> getList() {
		return list;
	}

	public void setList(List<PmProductDiscuss> list) {
		this.list = list;
	}
}
