package cn.temobi.complex.entity;

/**
 * 对应表emoji_packet 资源包
 * @author hjf
 * 2014 三月 17 11:27:40
 */
@SuppressWarnings("serial")
public class Material extends IdEntity{
	private String name;//包名称
	private String desc;//简介
	private String size;//包大小
	private double price;//价格
	private String downUrl;//下载地址
	private String zipPath;//解压路径
	private String resourceCount;//图片数
	private String thumbnailUrl;//缩略图地址
	private String business;//联盟商家
	private String businessUrl;//商家地址
	private String businessContent;//商家H5地址
	private String businessType;//1 h5页面 2 url地址
	private String commodityStatus;//1 正常 2热卖
	private int type;// 1贴图 2背景 3主题 4文字
	private int status;// 1上线 2下线           
	private int sort;// 排序值
	private int downloadNum;// 下载次数
	private String createdWhen;//创建时间
	private String updateWhen;//创建时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDownUrl() {
		return downUrl;
	}
	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	public String getResourceCount() {
		return resourceCount;
	}
	public void setResourceCount(String resourceCount) {
		this.resourceCount = resourceCount;
	}
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getUpdateWhen() {
		return updateWhen;
	}
	public void setUpdateWhen(String updateWhen) {
		this.updateWhen = updateWhen;
	}
	public String getZipPath() {
		return zipPath;
	}
	public void setZipPath(String zipPath) {
		this.zipPath = zipPath;
	}
	public int getDownloadNum() {
		return downloadNum;
	}
	public void setDownloadNum(int downloadNum) {
		this.downloadNum = downloadNum;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getBusinessUrl() {
		return businessUrl;
	}
	public void setBusinessUrl(String businessUrl) {
		this.businessUrl = businessUrl;
	}
	public String getBusinessContent() {
		return businessContent;
	}
	public void setBusinessContent(String businessContent) {
		this.businessContent = businessContent;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getCommodityStatus() {
		return commodityStatus;
	}
	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}
}
