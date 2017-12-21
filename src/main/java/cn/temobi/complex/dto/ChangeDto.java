package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ChangeDto implements Serializable{
	private String name;
	private String key;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
