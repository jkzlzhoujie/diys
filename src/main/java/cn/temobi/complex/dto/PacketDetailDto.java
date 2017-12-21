package cn.temobi.complex.dto;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class PacketDetailDto implements Serializable {
	private Long packageId;//表情包编号
	private String packageName;//表情包名字
	private String packageIcon;//表情包图标
	private String packageSize;//表情包大小
	private String packageState;//表情包状态0未订购 1已订购
	private String packagePrice;//表情包价格
	private String packageDownloads;//表情包下载量
	private String packageUseTime;//表情包使用期限
	private String packageCpName;//表情包所属cp名称
	private String packageDesc;//表情包简介
	private List<EmojiItemsDto> emojiItems;//表情列表数组
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getPackageIcon() {
		return packageIcon;
	}
	public void setPackageIcon(String packageIcon) {
		this.packageIcon = packageIcon;
	}
	public String getPackageSize() {
		return packageSize;
	}
	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}
	public String getPackageState() {
		return packageState;
	}
	public void setPackageState(String packageState) {
		this.packageState = packageState;
	}
	public String getPackagePrice() {
		return packagePrice;
	}
	public void setPackagePrice(String packagePrice) {
		this.packagePrice = packagePrice;
	}
	public String getPackageDownloads() {
		return packageDownloads;
	}
	public void setPackageDownloads(String packageDownloads) {
		this.packageDownloads = packageDownloads;
	}
	public String getPackageUseTime() {
		return packageUseTime;
	}
	public void setPackageUseTime(String packageUseTime) {
		this.packageUseTime = packageUseTime;
	}
	public String getPackageCpName() {
		return packageCpName;
	}
	public void setPackageCpName(String packageCpName) {
		this.packageCpName = packageCpName;
	}
	public String getPackageDesc() {
		return packageDesc;
	}
	public void setPackageDesc(String packageDesc) {
		this.packageDesc = packageDesc;
	}
	public List<EmojiItemsDto> getEmojiItems() {
		return emojiItems;
	}
	public void setEmojiItems(List<EmojiItemsDto> emojiItems) {
		this.emojiItems = emojiItems;
	}
	
}
