package cn.temobi.complex.entity;

public class SysScore extends IdEntity{
	private String name;
	private String type;
	private int dayNum;
	private int doneNum = 0;
	private int integral;
	private int integralLimit;
	private int experience;
	private int experienceLimit;
	private int charm;
	private int originality;
	private int credit;
	private String flag;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	public int getExperienceLimit() {
		return experienceLimit;
	}
	public void setExperienceLimit(int experienceLimit) {
		this.experienceLimit = experienceLimit;
	}
	public int getCharm() {
		return charm;
	}
	public void setCharm(int charm) {
		this.charm = charm;
	}
	public int getOriginality() {
		return originality;
	}
	public void setOriginality(int originality) {
		this.originality = originality;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public int getIntegralLimit() {
		return integralLimit;
	}
	public void setIntegralLimit(int integralLimit) {
		this.integralLimit = integralLimit;
	}
	public int getDayNum() {
		return dayNum;
	}
	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}
	public int getDoneNum() {
		return doneNum;
	}
	public void setDoneNum(int doneNum) {
		this.doneNum = doneNum;
	}
}
