package cn.temobi.complex.entity;
//素材使用统计
public class MaterialCount extends IdEntity{

	private String resImageUrl;
	private long materialCount;
	public String getResImageUrl() {
		return resImageUrl;
	}
	public void setResImageUrl(String resImageUrl) {
		this.resImageUrl = resImageUrl;
	}
	public long getMaterialCount() {
		return materialCount;
	}
	public void setMaterialCount(long materialCount) {
		this.materialCount = materialCount;
	}
}
