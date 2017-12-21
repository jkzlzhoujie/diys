package cn.temobi.complex.entity;

public class CmCircleUser extends IdEntity{

	private Long circleId;
	private Long userId;
	private String addTime;
	private String flag;
	private int sort;
	private String nickName;//昵称
	private String name;//圈名
	public Long getCircleId() {
		return circleId;
	}
	public void setCircleId(Long circleId) {
		this.circleId = circleId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddTime() {
		return addTime;
	}
	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
