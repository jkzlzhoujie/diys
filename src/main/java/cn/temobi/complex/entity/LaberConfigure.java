package cn.temobi.complex.entity;

public class LaberConfigure extends IdEntity{

	private String laberTotal;
	private String laberSmall;
	private String type;
	private int sort;
	public String getLaberTotal() {
		return laberTotal;
	}
	public void setLaberTotal(String laberTotal) {
		this.laberTotal = laberTotal;
	}
	public String getLaberSmall() {
		return laberSmall;
	}
	public void setLaberSmall(String laberSmall) {
		this.laberSmall = laberSmall;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
