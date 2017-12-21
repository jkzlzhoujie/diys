package cn.temobi.complex.entity;

@SuppressWarnings("serial")
public class PmCommodityInfo extends IdEntity{
	
	private String name;
	private String originalPrice;
	private String price;
	private String addTime;
	private String status;
	private Long commodityId;
	private int isNeedReceive;	//是否需要收货信息 1需要 0 不需要
	private int maxAmount;		//限日最大销售量
	 
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(String originalPrice) {
		this.originalPrice = originalPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public Long getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIsNeedReceive() {
		return isNeedReceive;
	}
	public void setIsNeedReceive(int isNeedReceive) {
		this.isNeedReceive = isNeedReceive;
	}
	public int getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(int maxAmount) {
		this.maxAmount = maxAmount;
	}
}
