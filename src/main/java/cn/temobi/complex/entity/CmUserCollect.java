package cn.temobi.complex.entity;

import java.util.Date;

//用户收藏
public class CmUserCollect extends IdEntity{

	private Long userId;		//用户ID
	private Long productId;		//作品Id
	private Long authorId;		//作者Id
	private Long topicId;  		//主题 P图悬赏ID 或其他主题ID
	private Integer type ;		//类型 1 作品 2 作者 3 主题 悬赏求P		
	private Date creatWhen;
	private Date updateWhen;
	
	
	public Date getCreatWhen() {
		return creatWhen;
	}
	public void setCreatWhen(Date creatWhen) {
		this.creatWhen = creatWhen;
	}
	public Date getUpdateWhen() {
		return updateWhen;
	}
	public void setUpdateWhen(Date updateWhen) {
		this.updateWhen = updateWhen;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getAuthorId() {
		return authorId;
	}
	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}
	public Long getTopicId() {
		return topicId;
	}
	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}
	
	
	
	

}
