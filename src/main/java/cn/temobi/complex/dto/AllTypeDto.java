package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AllTypeDto implements Serializable{
	private String name;//名称

	private Long id;//ID

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
