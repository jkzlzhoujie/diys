package cn.temobi.complex.dto;

import com.huawei.sdp.pae.service.schema.CommentInfo;

public class UserDto {

	private String mobile; // 手机号
	private String avatar; // 头像地址
	private String integration; //积分
	private String manCurrency; //漫币
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getIntegration() {
		return integration;
	}
	public void setIntegration(String integration) {
		this.integration = integration;
	}
	public String getManCurrency() {
		return manCurrency;
	}
	public void setManCurrency(String manCurrency) {
		this.manCurrency = manCurrency;
	}
	
	public CommentInfo[] getCommentInfo() {
		return commentInfo;
	}
	public void setCommentInfo(CommentInfo[] commentInfo) {
		this.commentInfo = commentInfo;
	}
	private CommentInfo[] commentInfo;
}
