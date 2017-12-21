package cn.temobi.complex.entity;

public class CmDesignerHonor {
	
	private Long id;					//荣誉编号
	private Long designerId;			//设计师编号
	private String honorTime;			//获奖时间
	private String honorText;			//获奖内容
	private String imageUrl;			//证书照片
	private String imageUrlThumbnail;	//证书照片编略图
	private String createTime;			//创建时间
	
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
	public String getHonorTime() {
		return honorTime;
	}
	public void setHonorTime(String honorTime) {
		this.honorTime = honorTime;
	}
	public String getHonorText() {
		return honorText;
	}
	public void setHonorText(String honorText) {
		this.honorText = honorText;
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
