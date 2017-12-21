package cn.temobi.complex.entity;


//圈子帖子图片内容
public class CirclePostPic extends IdEntity{

	private Long circlePostId;	//帖子ID
	private String picUrl;		//内容图片地址
	private String thumbnail;	//缩略图
	
	
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public Long getCirclePostId() {
		return circlePostId;
	}
	public void setCirclePostId(Long circlePostId) {
		this.circlePostId = circlePostId;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	

}
