package cn.temobi.complex.dto;

import cn.temobi.complex.entity.IdEntity;

public class CmUserGroupDto extends IdEntity{

	private String name;//组名
	private String remark;//备注
	private String createWhen;//创建时间
	private Long userId;
	private String nickName;//组名
	private Long groupId;//分组id
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getCreateWhen() {
		return createWhen;
	}
	public void setCreateWhen(String createWhen) {
		this.createWhen = createWhen;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
