package cn.temobi.complex.entity;

/**
 * 用户收货信息表
 * @author zhouj
 *
 */
public class CmUserReceive extends IdEntity{

	private String userId;//用户ID

	private String name;//收货人
	
	private String reAddress;//地址

	private String rePhone;//手机号
	
	private String rePostCode;//邮编

	private String note;//备注
	
	private int isDefault;//1: 是默认 0:非默认
	
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	
	public String getReAddress() {
		return reAddress;
	}

	public void setReAddress(String reAddress) {
		this.reAddress = reAddress;
	}

	public String getRePhone() {
		return rePhone;
	}

	public void setRePhone(String rePhone) {
		this.rePhone = rePhone;
	}

	public String getRePostCode() {
		return rePostCode;
	}

	public void setRePostCode(String rePostCode) {
		this.rePostCode = rePostCode;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(int isDefault) {
		this.isDefault = isDefault;
	}

	
	
	
	
}
