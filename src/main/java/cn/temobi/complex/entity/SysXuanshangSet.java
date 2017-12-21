package cn.temobi.complex.entity;


//系统悬赏设置
public class SysXuanshangSet extends IdEntity{

	private String name;	//名称
	private String value;	//值
	private String type;	//1 悬赏金额
	private Integer status ;//状态 1 上线 0 下线
	private String code; 	//编码
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}
