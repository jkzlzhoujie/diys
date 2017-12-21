package cn.temobi.complex.entity;

import java.util.Date;

//圈子类别
public class CircleType extends IdEntity{

	private String name;		//名称
	private int status ;		//状态 1 上线 0 下线
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	

}
