package cn.temobi.complex.entity;

public class SystemSeting extends IdEntity{

	private int themeNum;
	private int classifyNum;
	private String productDesc;
	private String downUrl;
	private String shopUrl;
	private String shareUrl;
	private String shareImage;
	private String shareTitle;
	private String shareContent;
	private String feedbackType;
	private String hotLaber;
	public int getThemeNum() {
		return themeNum;
	}
	public void setThemeNum(int themeNum) {
		this.themeNum = themeNum;
	}
	public int getClassifyNum() {
		return classifyNum;
	}
	public void setClassifyNum(int classifyNum) {
		this.classifyNum = classifyNum;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getShopUrl() {
		return shopUrl;
	}
	public void setShopUrl(String shopUrl) {
		this.shopUrl = shopUrl;
	}
	public String getShareUrl() {
		return shareUrl;
	}
	public void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	public String getShareImage() {
		return shareImage;
	}
	public void setShareImage(String shareImage) {
		this.shareImage = shareImage;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getShareContent() {
		return shareContent;
	}
	public void setShareContent(String shareContent) {
		this.shareContent = shareContent;
	}
	public String getFeedbackType() {
		return feedbackType;
	}
	public void setFeedbackType(String feedbackType) {
		this.feedbackType = feedbackType;
	}
	public String getHotLaber() {
		return hotLaber;
	}
	public void setHotLaber(String hotLaber) {
		this.hotLaber = hotLaber;
	}
}
