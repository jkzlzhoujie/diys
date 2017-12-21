package cn.temobi.complex.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ResourceShare implements Serializable{
	private String resourceId;//资源ID
	private Long clientId;//客户端ID
	private Long userId;//客户端ID
	private Long productId;//客户端ID
	private Long themeId;//客户端ID
	private String themeUserId;//
	private String imageUrl;//
	private String imei;//imei
	private String version;//版本
	private String type;//1 用户使用主题并操作到保存或者分享完成 2用户使用前置相机并操作到保存或者分享完成 3后置 4图库
	private String useType;//1 分享 2本地保存
	private String shareType;//分享平台 1 QQ空间2qq 3新浪微博 4腾讯微博 5微信 6朋友圈 7短信 8 人人
	private String shareStyle;//1 图片分享 2 客户端分享
	private String expressIds;//图片使用的单个表情的ID，多个以逗号分隔
	private String chartletIds;//图片使用的单个贴图的ID，多个以逗号分隔
	private String yanWritings;//图片使用的单个颜文字的ID，多个以逗号分隔
	private String writings;//图片使用的文本内容，多个以#@*分隔
	private String createdWhen;//创建时间
	private String channel;
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getShareStyle() {
		return shareStyle;
	}
	public void setShareStyle(String shareStyle) {
		this.shareStyle = shareStyle;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUseType() {
		return useType;
	}
	public void setUseType(String useType) {
		this.useType = useType;
	}
	public String getExpressIds() {
		return expressIds;
	}
	public void setExpressIds(String expressIds) {
		this.expressIds = expressIds;
	}
	public String getChartletIds() {
		return chartletIds;
	}
	public void setChartletIds(String chartletIds) {
		this.chartletIds = chartletIds;
	}
	public String getYanWritings() {
		return yanWritings;
	}
	public void setYanWritings(String yanWritings) {
		this.yanWritings = yanWritings;
	}
	public String getWritings() {
		return writings;
	}
	public void setWritings(String writings) {
		this.writings = writings;
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
	public Long getThemeId() {
		return themeId;
	}
	public void setThemeId(Long themeId) {
		this.themeId = themeId;
	}
	public String getThemeUserId() {
		return themeUserId;
	}
	public void setThemeUserId(String themeUserId) {
		this.themeUserId = themeUserId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
