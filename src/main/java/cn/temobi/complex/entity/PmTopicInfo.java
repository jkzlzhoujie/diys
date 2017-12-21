package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class PmTopicInfo extends IdEntity{
	private String topicName;
	private String bannerUrl;
	private int topicType;
	private int productLimit;
	private int lookCount;
	private int praiseCount;
	private int shareCount;
	private int discussCount;
	private int joinUsers;
	private int joinProducts;
	private int psProducts;
	private int operatorUsers;
	private int operatorProducts;
	private int operatorPsProducts;
	private String remark;
	private String createdWhen;
	public String getTopicName() {
		return topicName;
	}
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
	public String getBannerUrl() {
		return bannerUrl;
	}
	public void setBannerUrl(String bannerUrl) {
		this.bannerUrl = bannerUrl;
	}
	public int getTopicType() {
		return topicType;
	}
	public void setTopicType(int topicType) {
		this.topicType = topicType;
	}
	public int getProductLimit() {
		return productLimit;
	}
	public void setProductLimit(int productLimit) {
		this.productLimit = productLimit;
	}
	public int getLookCount() {
		return lookCount;
	}
	public void setLookCount(int lookCount) {
		this.lookCount = lookCount;
	}
	public int getPraiseCount() {
		return praiseCount;
	}
	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}
	public int getShareCount() {
		return shareCount;
	}
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}
	public int getDiscussCount() {
		return discussCount;
	}
	public void setDiscussCount(int discussCount) {
		this.discussCount = discussCount;
	}
	public int getJoinUsers() {
		return joinUsers;
	}
	public void setJoinUsers(int joinUsers) {
		this.joinUsers = joinUsers;
	}
	public int getJoinProducts() {
		return joinProducts;
	}
	public void setJoinProducts(int joinProducts) {
		this.joinProducts = joinProducts;
	}
	public int getPsProducts() {
		return psProducts;
	}
	public void setPsProducts(int psProducts) {
		this.psProducts = psProducts;
	}
	public int getOperatorUsers() {
		return operatorUsers;
	}
	public void setOperatorUsers(int operatorUsers) {
		this.operatorUsers = operatorUsers;
	}
	public int getOperatorProducts() {
		return operatorProducts;
	}
	public void setOperatorProducts(int operatorProducts) {
		this.operatorProducts = operatorProducts;
	}
	public int getOperatorPsProducts() {
		return operatorPsProducts;
	}
	public void setOperatorPsProducts(int operatorPsProducts) {
		this.operatorPsProducts = operatorPsProducts;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
}
