package cn.temobi.complex.entity;

/**
 * 对应表emoji_packet 资源包
 * @author hjf
 * 2014 三月 17 11:27:40
 */
@SuppressWarnings("serial")
public class MaterialResource extends IdEntity{
	private String name;//名称
	private Long materialId;//资源包Id
	private String imageUrl;//图片地址
	private String imageName;//图片名称
	private String laberName;//标签名
	private String type;//1 h5页面 2 url地址
	private String url;//url地址
	private String content;//h5页面内容
	private String createdWhen;//创建时间
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
	public String getImageName() {
		return imageName;
	}
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
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
