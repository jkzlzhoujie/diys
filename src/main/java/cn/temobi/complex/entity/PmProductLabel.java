package cn.temobi.complex.entity;

//作品标签表
public class PmProductLabel extends IdEntity{

	private Long productId;//作品ID
	private Long labelId;//标签ID
	private String labelName;
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}


}
