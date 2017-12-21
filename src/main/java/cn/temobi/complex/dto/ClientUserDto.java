package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClientUserDto implements Serializable {
	private Long loginId;//登陆ID
	private String phone;//手机号
	public Long getLoginId() {
		return loginId;
	}

	public void setLoginId(Long loginId) {
		this.loginId = loginId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
