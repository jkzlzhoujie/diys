package cn.temobi.complex.entity;


/*
 * 背景图
 */
public class BackgroundPic extends IdEntity{

	private String name;		//名称
	private String imageUrl;	//图片地址
	private String type;		//类型 1 排行榜 背景
	private Integer status ;	//状态 1 上线 0 下线
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	

}
