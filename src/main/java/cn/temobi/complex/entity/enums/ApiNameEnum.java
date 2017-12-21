package cn.temobi.complex.entity.enums;

/**
 * apiName
 * @author chenjy
 *
 */
public enum ApiNameEnum {
	
	
	createAcount("createAcount","创建账户"),
	withdraw("withdraw","提现"),
	recharge("recharge","充值"),
	getRed("getRed","获取红包"),
	sign("sign","签到"),
	priceP("priceP","悬赏求P"),
	colouredD("colouredD","彩绘订制"),
	getPPrice("getPPrice","获得悬赏求P金额"),
	payPrivilege("payPrivilege","开通特权付款")
	;
	private String code;
	private String desc;
	private ApiNameEnum(String code,String desc){
		this.code = code;
		this.desc = desc;
	}
	public String getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}
	
	public static ApiNameEnum getByCode(String code){
		for(ApiNameEnum value : ApiNameEnum.values()){
			if(value.getCode().equals(code)){
				return value;
			}
		}
		return null;
	}
}
