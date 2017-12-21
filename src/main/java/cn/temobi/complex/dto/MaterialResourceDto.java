package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

/**
 * 对应表emoji_packet 资源包
 * @author hjf
 * 2014 三月 17 11:27:40
 */
@SuppressWarnings("serial")
public class MaterialResourceDto extends IdEntity{
	private String name;//名称
	private Long materialId;//资源包Id
	private String imageUrl;//图片地址
	private String materialName;//资源包名
	private String laberName;//标签
	private String type;//
	private String url;//
	private String content;//
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getMaterialName() {
		return materialName;
	}
	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}
	public String getLaberName() {
		return laberName;
	}
	public void setLaberName(String laberName) {
		this.laberName = laberName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
