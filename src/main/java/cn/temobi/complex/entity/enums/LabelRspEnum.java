package cn.temobi.complex.entity.enums;

public enum LabelRspEnum {
	SUCCESS(0,"成功"),
	ERROR(-1,"处理错误"),
	INNER_SYS_EXCEPTION(1,"系统内部异常"),
	INNER_SERVICE_EXCEPTION(2,"服务内部异常"),
	AUTH_EXCEPTION(3,"权限异常"),
	COMMAND_EXCEPTION(4,"报文异常");
	private Integer code;
	private String desc;
	
	private LabelRspEnum(Integer code,String desc){
		this.code = code;
		this.desc = desc;
	}
	public Integer getCode(){
		return code;
	}
	public String getDesc(){
		return desc;
	}
	
	public static LabelRspEnum getByCode(Integer code){
		for(LabelRspEnum value : LabelRspEnum.values()){
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
