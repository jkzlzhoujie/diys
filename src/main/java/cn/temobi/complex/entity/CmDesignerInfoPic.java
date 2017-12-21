package cn.temobi.complex.entity;

public class CmDesignerInfoPic {
	private Long id;	
	private Long designerId;				//设计师ID号。
	private String imageUrl;				//用户图片
	private String imageUrlThumbnail;		//用户图片缩略图
	private	String createTime;				//用户创建时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getImageUrlThumbnail() {
		return imageUrlThumbnail;
	}
	public void setImageUrlThumbnail(String imageUrlThumbnail) {
		this.imageUrlThumbnail = imageUrlThumbnail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	
	
}
