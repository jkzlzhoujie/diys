package cn.temobi.complex.entity;

import java.io.Serializable;

/**
 * 对应表emoji_material_download 表情包下载统计
 * @author hjf
 * 2014 五月 5 16:28:48
 */
@SuppressWarnings("serial")
public class MaterialDownload implements Serializable{
	private Long materialId;
	private Long clientId;
	private String imei;
	private String materialType;
	private String version;
	private String createWhen;
	public Long getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getCreateWhen() {
		return createWhen;
	}
	public void setCreateWhen(String createWhen) {
		this.createWhen = createWhen;
	}
	public String getMaterialType() {
		return materialType;
	}
	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}
}
