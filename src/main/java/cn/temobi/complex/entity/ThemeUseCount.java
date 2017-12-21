package cn.temobi.complex.entity;

//主题被引用次数
public class ThemeUseCount {

	private Long id;
	private Long useCount;
	private String imageUrl;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUseCount() {
		return useCount;
	}
	public void setUseCount(Long useCount) {
		this.useCount = useCount;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
