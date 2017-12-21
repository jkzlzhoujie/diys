package cn.temobi.complex.entity;

import java.util.Date;

//系统套餐
public class SysPackage extends IdEntity{

	private String name;		//名称
	private String code;		//编码
	private String content;		//内容
	private double price;		//价格
	private int score;			//积分
	private int experience;		//经验
	private int charm;
	private int originality;
	private int credit;
	private int status ;		//状态  1 有效 2 失效
	private int validDate;  	//有效期    /月
	private int isSuggest ;		//是否推荐 1 是 0 默认
	private Date creatWhen;
	private Date updateWhen;
	
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getValidDate() {
		return validDate;
	}
	public void setValidDate(int validDate) {
		this.validDate = validDate;
	}
	public Date getCreatWhen() {
		return creatWhen;
	}
	public void setCreatWhen(Date creatWhen) {
		this.creatWhen = creatWhen;
	}
	public Date getUpdateWhen() {
		return updateWhen;
	}
	public void setUpdateWhen(Date updateWhen) {
		this.updateWhen = updateWhen;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getIsSuggest() {
		return isSuggest;
	}
	public void setIsSuggest(int isSuggest) {
		this.isSuggest = isSuggest;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
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
	
	
	
	

}
