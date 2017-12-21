package cn.temobi.complex.dto;

import org.springframework.web.util.HtmlUtils;


public class TwoDiscussDto{

	private String discussUserId;
	private String discussNickName;
	private int type;
	private String content;
	private String imageUrl;
	private String thumbnail;
	private String receiveUserId;
	private String receiveNickName;
	private String createdWhen;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = HtmlUtils.htmlUnescape(content);
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getReceiveUserId() {
		return receiveUserId;
	}
	public void setReceiveUserId(String receiveUserId) {
		this.receiveUserId = receiveUserId;
	}
	public String getReceiveNickName() {
		return receiveNickName;
	}
	public void setReceiveNickName(String receiveNickName) {
		this.receiveNickName = receiveNickName;
	}
	public String getDiscussUserId() {
		return discussUserId;
	}
	public void setDiscussUserId(String discussUserId) {
		this.discussUserId = discussUserId;
	}
	public String getDiscussNickName() {
		return discussNickName;
	}
	public void setDiscussNickName(String discussNickName) {
		this.discussNickName = discussNickName;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
