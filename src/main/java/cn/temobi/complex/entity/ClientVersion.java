package cn.temobi.complex.entity;

/**
 * 对应表emoji_client_version 客户端版本管理
 * @author hjf
 * 2014 三月 17 15:01:24
 */
@SuppressWarnings("serial")
public class ClientVersion extends IdEntity{
	private String name;//版本名称
	private String code;//版本编号
	private String channelId;//渠道ID
	private Integer os;//操作系统
	private String size;//应用大小
	private String downUrl;//应用下载地址
	private String desc;//更新日志
	private Integer isForce;//强制升级标识   0否 1是
	private String createdWhen;//创建时间
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public Integer getOs() {
		return os;
	}
	public void setOs(Integer os) {
		this.os = os;
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
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Integer getIsForce() {
		return isForce;
	}
	public void setIsForce(Integer isForce) {
		this.isForce = isForce;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
