package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

@SuppressWarnings("serial")
public class WapPacketDto extends IdEntity{
	private String name;//包名称
	private String posterUrl;//广告图地址
	private String thumbnailUrl;//缩略图地址
	private Long count;//表情包下载数
	private String rsCount;//图片数量
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPosterUrl() {
		return posterUrl;
	}
	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getRsCount() {
		return rsCount;
	}
	public void setRsCount(String rsCount) {
		this.rsCount = rsCount;
	}
	
}
