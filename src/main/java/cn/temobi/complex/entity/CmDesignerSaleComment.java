package cn.temobi.complex.entity;

public class CmDesignerSaleComment {
	
	private Long id;				//评论编号
	private String orderNO;			//所属订单号
	private int conformityLevel;	//设计符合等级
	private int serviceLevel;		//服务质量等级
	private int priceLevel;			//价格合理等级
	private int sendLevel;			//发图速度等级
	private int totalLevel;			//总体满意度
	private int anonymous;			//是否匿名评价
	private String commentText;		//是否匿名评价
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNO() {
		return orderNO;
	}
	public void setOrderNO(String orderNO) {
		this.orderNO = orderNO;
	}
	public int getConformityLevel() {
		return conformityLevel;
	}
	public void setConformityLevel(int conformityLevel) {
		this.conformityLevel = conformityLevel;
	}
	public int getServiceLevel() {
		return serviceLevel;
	}
	public void setServiceLevel(int serviceLevel) {
		this.serviceLevel = serviceLevel;
	}
	public int getPriceLevel() {
		return priceLevel;
	}
	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}
	public int getSendLevel() {
		return sendLevel;
	}
	public void setSendLevel(int sendLevel) {
		this.sendLevel = sendLevel;
	}
	public int getTotalLevel() {
		return totalLevel;
	}
	public void setTotalLevel(int totalLevel) {
		this.totalLevel = totalLevel;
	}
	public int getAnonymous() {
		return anonymous;
	}
	public void setAnonymous(int anonymous) {
		this.anonymous = anonymous;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	

}
