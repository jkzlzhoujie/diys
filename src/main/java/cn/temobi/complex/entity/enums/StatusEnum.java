package cn.temobi.complex.entity.enums;

/**
 * 作品状态枚举类
 * @author chenjy
 *
 */
public enum StatusEnum {
	upload(1,"上传"),
	offline(2,"下线"),
	commercial(3,"商用");
	private Integer code;
	private String desc;
	private StatusEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}
	public Integer getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	public static StatusEnum getByCode(Integer code){
		for(StatusEnum value : StatusEnum.values()){
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
