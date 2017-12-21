package cn.temobi.complex.dto;

import java.io.Serializable;

import cn.temobi.core.util.DateUtils;

@SuppressWarnings("serial")
public class PacketDto implements Serializable {
	private Long packageId;//表情包编号
	private String packageName;//表情包名字
	private String packageIcon;//表情包图标
	private String packageSize;//表情包大小
	private String packageState;//表情包状态 0未订购 1已订购
	private String packagePrice;//表情包价格
	private String packageDownloads;//表情包下载量
	private String packageCpName;//表情包所属cp名称
	private String packageIsNew;//表情包是否最新 0不是 1最新
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
	public String getPackageCpName() {
		return packageCpName;
	}
	public void setPackageCpName(String packageCpName) {
		this.packageCpName = packageCpName;
	}
	public String getPackageIsNew() {
		return packageIsNew;
	}
	public void setPackageIsNew(String packageIsNew) {
		this.packageIsNew = packageIsNew;
	}
}
