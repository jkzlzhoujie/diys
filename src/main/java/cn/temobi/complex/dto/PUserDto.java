package cn.temobi.complex.dto;

public class PUserDto {

	private String time;
	private int userNum;
	private int pUserNum;
	private int priceUserNum;
	private String priceRate;
	private String priceRateTo;
	private double totalPrice;
	private double totalPriceTo;
	private double maxPrice;
	private double minPrice;
	private double avgPrice;
	private int countUserP;
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getUserNum() {
		return userNum;
	}
	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}
	public int getpUserNum() {
		return pUserNum;
	}
	public void setpUserNum(int pUserNum) {
		this.pUserNum = pUserNum;
	}
	public int getPriceUserNum() {
		return priceUserNum;
	}
	public void setPriceUserNum(int priceUserNum) {
		this.priceUserNum = priceUserNum;
	}
	public String getPriceRate() {
		return priceRate;
	}
	public void setPriceRate(String priceRate) {
		this.priceRate = priceRate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public double getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}
	public double getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}
	public double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(double avgPrice) {
		this.avgPrice = avgPrice;
	}
	public double getTotalPriceTo() {
		return totalPriceTo;
	}
	public void setTotalPriceTo(double totalPriceTo) {
		this.totalPriceTo = totalPriceTo;
	}
	public String getPriceRateTo() {
		return priceRateTo;
	}
	public void setPriceRateTo(String priceRateTo) {
		this.priceRateTo = priceRateTo;
	}
	public int getCountUserP() {
		return countUserP;
	}
	public void setCountUserP(int countUserP) {
		this.countUserP = countUserP;
	}
}
