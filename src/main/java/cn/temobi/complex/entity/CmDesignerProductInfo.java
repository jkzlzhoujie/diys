package cn.temobi.complex.entity;

import java.util.List;

public class CmDesignerProductInfo {
	
	private Long id;						//作品ID
	private Long designerId;				//作品所属设计师ID
	private String name;					//作品名称
	private String productDescribe;			//作品描述
	private String saleNum;					//作品描述
	private int isVirtual;					//是否虚拟商品，0：虚拟商品，1实物商品
	private String productImgUrl;			//商品图片
	private String productImgUrlThumbnail;	//商品图片缩略图
	private String createTime;				//商品创建时间
	private String updateTime;				//商品更新时间
	private int status;						//商品状态，0：下架，1：上架，2：删除
	private List<CmDesignerProductFormat> cmDesignerProductFormatList ;//商品规格
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Long designerId) {
		this.designerId = designerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductDescribe() {
		return productDescribe;
	}
	public void setProductDescribe(String productDescribe) {
		this.productDescribe = productDescribe;
	}
	public String getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(String saleNum) {
		this.saleNum = saleNum;
	}
	public int getIsVirtual() {
		return isVirtual;
	}
	public void setIsVirtual(int isVirtual) {
		this.isVirtual = isVirtual;
	}
	public String getProductImgUrl() {
		return productImgUrl;
	}
	public void setProductImgUrl(String productImgUrl) {
		this.productImgUrl = productImgUrl;
	}
	public String getProductImgUrlThumbnail() {
		return productImgUrlThumbnail;
	}
	public void setProductImgUrlThumbnail(String productImgUrlThumbnail) {
		this.productImgUrlThumbnail = productImgUrlThumbnail;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<CmDesignerProductFormat> getCmDesignerProductFormatList() {
		return cmDesignerProductFormatList;
	}
	public void setCmDesignerProductFormatList(
			List<CmDesignerProductFormat> cmDesignerProductFormatList) {
		this.cmDesignerProductFormatList = cmDesignerProductFormatList;
	}
	
	

	

}
