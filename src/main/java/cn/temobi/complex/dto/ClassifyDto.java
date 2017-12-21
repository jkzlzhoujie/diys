package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ClassifyDto implements Serializable{
	private Long classifyId;//分类ID
	private int themeNum;//分类主题标示
	private String name;//名称
	private int loveNum;//喜欢的次数
	public Long getClassifyId() {
		return classifyId;
	}
	public void setClassifyId(Long classifyId) {
		this.classifyId = classifyId;
	}
	public int getThemeNum() {
		return themeNum;
	}
	public void setThemeNum(int themeNum) {
		this.themeNum = themeNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLoveNum() {
		return loveNum;
	}
	public void setLoveNum(int loveNum) {
		this.loveNum = loveNum;
	}
	
	
}
