package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

@SuppressWarnings("serial")
public class WapUserDto extends IdEntity{
	private String username;//用户名

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}
