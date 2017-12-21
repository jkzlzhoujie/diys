package cn.temobi.complex.entity;

/**
 * 对于表emoji_banner 横幅广告
 * @author hjf
 * 2014 三月 17 14:06:53
 */
@SuppressWarnings("serial")
public class Banner extends IdEntity{
	private String name;//banner名称
	private String picUrl;//banner地址,带http
	private String picUrl2;//banner2地址,带http
	private String startTime;//开始日期
	private String endTime;//结束日期
	private Integer validStartHour;//每日生效时间
	private Integer validEndHour;//每日失效时间
	private Integer sequence;//排序值
	private String actionType;//点击动作类型，1:表情,2:外链到网址,3:活动页
	private String clickUrl;//点击的外链地址
	private Long clickId;//点击ID
	private Integer status;//1正常 2冻结
	private String createdWhen;//创建时间
	private String type;//1横幅广告位 2列表广告位
	private Long applicationId;//应用
	private Integer system; //应用系统 1 Android 2 IOS
	private String content;
	private String extend;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public Integer getValidStartHour() {
		return validStartHour;
	}
	public void setValidStartHour(Integer validStartHour) {
		this.validStartHour = validStartHour;
	}
	public Integer getValidEndHour() {
		return validEndHour;
	}
	public void setValidEndHour(Integer validEndHour) {
		this.validEndHour = validEndHour;
	}
	public Integer getSequence() {
		return sequence;
	}
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getClickUrl() {
		return clickUrl;
	}
	public void setClickUrl(String clickUrl) {
		this.clickUrl = clickUrl;
	}
	public Long getClickId() {
		return clickId;
	}
	public void setClickId(Long clickId) {
		this.clickId = clickId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getExtend() {
		return extend;
	}
	public void setExtend(String extend) {
		this.extend = extend;
	}
	public String getPicUrl2() {
		return picUrl2;
	}
	public void setPicUrl2(String picUrl2) {
		this.picUrl2 = picUrl2;
	}
	public Long getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
	public Integer getSystem() {
		return system;
	}
	public void setSystem(Integer system) {
		this.system = system;
	}
	
	
	
	
}
