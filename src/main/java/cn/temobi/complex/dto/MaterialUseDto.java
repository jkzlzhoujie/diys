package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

public class MaterialUseDto extends IdEntity{
	
	private long materialId; //素材ID
	private int useSeq;
	private String remark;
	private String addTime;
	private String type; //1 评论
	private String name;//素材名称
	public long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(long materialId) {
		this.materialId = materialId;
	}
	public int getUseSeq() {
		return useSeq;
	}
	public void setUseSeq(int useSeq) {
		this.useSeq = useSeq;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
