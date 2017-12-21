package cn.temobi.complex.entity;

import java.io.Serializable;

/**
 * 获取归属地
 * @author hjf
 * 2014 三月 28 10:23:37
 */
@SuppressWarnings("serial")
public class Province implements Serializable{
	private String segment;//号码段
	private String provinceId;//省份ID
	private String provinceName;//省份名称
	private String cityId;//城市ID
	private String cityName;//城市名称
	public String getSegment() {
		return segment;
	}
	public void setSegment(String segment) {
		this.segment = segment;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
