package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

@SuppressWarnings("serial")
public class WapResourceDto extends IdEntity {
	private String packetName;//表情包名称
	private String packetDesc;//表情包描述
	private String name;//表情名称
	private String price;//表情价格
	private String staticUrl;//静态图片地址
	private String dynamicUrl;//动态图片
	private String voiceUrl;//语音地址
	private String isOrder;//是否购买 1未购买 2已购买
	private String count;//分享量
	private String time;//购买时间
	
	public String getPacketName() {
		return packetName;
	}
	public void setPacketName(String packetName) {
		this.packetName = packetName;
	}
	public String getPacketDesc() {
		return packetDesc;
	}
	public void setPacketDesc(String packetDesc) {
		this.packetDesc = packetDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStaticUrl() {
		return staticUrl;
	}
	public void setStaticUrl(String staticUrl) {
		this.staticUrl = staticUrl;
	}
	public String getDynamicUrl() {
		return dynamicUrl;
	}
	public void setDynamicUrl(String dynamicUrl) {
		this.dynamicUrl = dynamicUrl;
	}
	public String getVoiceUrl() {
		return voiceUrl;
	}
	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}
	public String getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
