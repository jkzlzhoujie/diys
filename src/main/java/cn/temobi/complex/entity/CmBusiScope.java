package cn.temobi.complex.entity;

/**
 *
 */
@SuppressWarnings("serial")
public class CmBusiScope extends IdEntity{
	private Long userId;
	private Long typeId;
	private Long firstTypeId;
	private String createdWhen;//创建时间
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	public Long getFirstTypeId() {
		return firstTypeId;
	}
	public void setFirstTypeId(Long firstTypeId) {
		this.firstTypeId = firstTypeId;
	}
	
}
