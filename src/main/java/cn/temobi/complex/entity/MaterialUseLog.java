package cn.temobi.complex.entity;

public class MaterialUseLog extends IdEntity{

	private long materialId; //素材ID
	private long materialResId; //素材资源ID
	private String otherId; //使用记录ID
	private String addTime;
	private String type; //使用类型 1 评论 2 diy
	private String materialType; //贴图 2颜文字 3表情
	public long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}
	public long getMaterialResId() {
		return materialResId;
	}
	public void setMaterialResId(long materialResId) {
		this.materialResId = materialResId;
	}
	public String getOtherId() {
		return otherId;
	}
	public void setOtherId(String otherId) {
		this.otherId = otherId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
}
