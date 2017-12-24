package cn.temobi.complex.entity;

/**
 */
@SuppressWarnings("serial")
public class WxSign{
	private String paytimestamp;
	private String paypackage;
	private String paynonceStr;
	private String paySign;
	
	public String getPaytimestamp() {
		return paytimestamp;
	}
	public void setPaytimestamp(String paytimestamp) {
		this.paytimestamp = paytimestamp;
	}
	public String getPaypackage() {
		return paypackage;
	}
	public void setPaypackage(String paypackage) {
		this.paypackage = paypackage;
	}
	public String getPaynonceStr() {
		return paynonceStr;
	}
	public void setPaynonceStr(String paynonceStr) {
		this.paynonceStr = paynonceStr;
	}
	public String getPaySign() {
		return paySign;
	}
	public void setPaySign(String paySign) {
		this.paySign = paySign;
	}

}
