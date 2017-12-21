package cn.temobi.complex.dto;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ZipJsonDto implements Serializable{
	private String emoticonId;//表情ID
	private String emoticonName;//表情名称
	private String emoticonStatic;//静态图地址
	private String emoticonDynamic;//动态图地址
	public String getEmoticonId() {
		return emoticonId;
	}
	public void setEmoticonId(String emoticonId) {
		this.emoticonId = emoticonId;
	}
	public String getEmoticonName() {
		return emoticonName;
	}
	public void setEmoticonName(String emoticonName) {
		this.emoticonName = emoticonName;
	}
	public String getEmoticonStatic() {
		return emoticonStatic;
	}
	public void setEmoticonStatic(String emoticonStatic) {
		this.emoticonStatic = emoticonStatic;
	}
	public String getEmoticonDynamic() {
		return emoticonDynamic;
	}
	public void setEmoticonDynamic(String emoticonDynamic) {
		this.emoticonDynamic = emoticonDynamic;
	}
	
}
