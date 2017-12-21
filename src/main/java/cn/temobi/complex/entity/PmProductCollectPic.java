package cn.temobi.complex.entity;


//作品图片集
public class PmProductCollectPic extends IdEntity{

	private Long productId;		//作品ID
	private String url;			//内容图片地址
	private String thumbnail;	//缩略图
	
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	

}
