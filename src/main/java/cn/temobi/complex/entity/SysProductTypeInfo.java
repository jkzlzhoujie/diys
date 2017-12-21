package cn.temobi.complex.entity;


public class SysProductTypeInfo extends IdEntity {
	private Integer level;
	private String name;
	private Long parentId;
	private String path;
	private String time;
	private String typeColor;
	
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getTypeColor() {
		return typeColor;
	}
	public void setTypeColor(String typeColor) {
		this.typeColor = typeColor;
	}
}
