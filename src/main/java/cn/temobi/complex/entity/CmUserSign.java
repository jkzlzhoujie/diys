package cn.temobi.complex.entity;

public class CmUserSign extends IdEntity{

	private Long userId;
	private int monthNum;
	private int totalNum;
	private int totalIntegral;
	private int monthIntegral;
	private int continuityNum;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public int getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public int getTotalIntegral() {
		return totalIntegral;
	}
	public void setTotalIntegral(int totalIntegral) {
		this.totalIntegral = totalIntegral;
	}
	public int getMonthIntegral() {
		return monthIntegral;
	}
	public void setMonthIntegral(int monthIntegral) {
		this.monthIntegral = monthIntegral;
	}
	public int getContinuityNum() {
		return continuityNum;
	}
	public void setContinuityNum(int continuityNum) {
		this.continuityNum = continuityNum;
	}
}
