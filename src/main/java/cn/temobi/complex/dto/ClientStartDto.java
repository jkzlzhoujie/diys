package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientStartDto implements Serializable{
	private Long clientId;//设备ID
	private String productDesc;//产品简介
	private String downUrl;//产品简介应用下载地址
	private String shopUrl;//产品点赞页面
	private String shareUrl;//分享后点击的URL
	private String shareImage;//分享的图片地址
	private String shareTitle;//分享的标题
	private String shareContent;//分享的内容
	private String videoUrl;//视频
	private int themeNum;
	private int classifyNum;
	private String zContent;//点赞对话框内容
	private String feebackContent;//提建议对话框内容
	private String shareNum;  //每分享几次弹出对话框
	private String godPUrl ;  //神p默认图
	private String h5Url ;  //h5地址
	private String playUrl ;  //小游戏地址
	private String flag ;  //是否展示开屏广告
	private String expand1 ;  //是否展示玩广告
	private String expand2 ;  //是否展示秀广告
	private String expand3 ;  //是否展示兴趣圈广告

	public Long getClientId() {
		return clientId;
	}

	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}

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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getzContent() {
		return zContent;
	}

	public void setzContent(String zContent) {
		this.zContent = zContent;
	}

	public String getFeebackContent() {
		return feebackContent;
	}

	public void setFeebackContent(String feebackContent) {
		this.feebackContent = feebackContent;
	}

	public String getShareNum() {
		return shareNum;
	}

	public void setShareNum(String shareNum) {
		this.shareNum = shareNum;
	}

	public String getGodPUrl() {
		return godPUrl;
	}

	public void setGodPUrl(String godPUrl) {
		this.godPUrl = godPUrl;
	}

	public String getH5Url() {
		return h5Url;
	}

	public void setH5Url(String h5Url) {
		this.h5Url = h5Url;
	}

	public String getPlayUrl() {
		return playUrl;
	}

	public void setPlayUrl(String playUrl) {
		this.playUrl = playUrl;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getExpand1() {
		return expand1;
	}

	public void setExpand1(String expand1) {
		this.expand1 = expand1;
	}

	public String getExpand2() {
		return expand2;
	}

	public void setExpand2(String expand2) {
		this.expand2 = expand2;
	}

	public String getExpand3() {
		return expand3;
	}

	public void setExpand3(String expand3) {
		this.expand3 = expand3;
	}
}
