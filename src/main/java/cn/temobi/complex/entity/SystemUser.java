package cn.temobi.complex.entity;

/**
 * 对应表bl_system_user
 * @author hujf 2013-5-31下午02:07:24
 */
@SuppressWarnings("serial")
public class SystemUser extends IdEntity{
	private String username;//帐号
	private String password;//密码
	private String rights;//用户权限
	private String realName;//姓名
	private int status;//状态 0正常 1冻结
	private String createdWhen;//创建时间
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRights() {
		return rights;
	}
	public void setRights(String rights) {
		this.rights = rights;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreatedWhen() {
		return createdWhen;
	}
	public void setCreatedWhen(String createdWhen) {
		this.createdWhen = createdWhen;
	}
	
}
