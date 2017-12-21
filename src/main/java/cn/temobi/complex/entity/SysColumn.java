package cn.temobi.complex.entity;

public class SysColumn extends IdEntity {

	public String name;
	public String type;
	public String releaseType;
	public String status;
	public int sort;
	public String addTime;
	public int isOnline;
	public int browseStyle;//1 九宫格 2 瀑布流 带点赞 
	// 3 瀑布流 带底部色彩  4 瀑布流 带底部信息 5 渐变九宫格
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReleaseType() {
		return releaseType;
	}
	public void setReleaseType(String releaseType) {
		this.releaseType = releaseType;
	}
	public int getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}
	public int getBrowseStyle() {
		return browseStyle;
	}
	public void setBrowseStyle(int browseStyle) {
		this.browseStyle = browseStyle;
	}
}
