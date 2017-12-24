package com.tencent.common;

/**
 * Date: 2017/10/29
 * Time: 14:40
 * 这里放置微信公众号各种配置数据
 */
public class WeixinConfigure {

	//sdk的版本号
	private static final String sdkVersion = "java sdk 1.0.1";

	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
	private static String key = "ba922a07c2586cd59fwed03acf938c8d";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	public final static String APPID = "wx7a7bc6c16b83c47d";
	public final static String AppSecret = "886487a13204d88c55214bbaff76b0c3"; 
	
	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String appID = "wxffc37c6417e29cd8";
	private static String mchID = "1414077202";

	//机器IP
	private static String ip = "";
	public static String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";
	

	public static void setKey(String key) {
		WeixinConfigure.key = key;
	}

	public static void setAppID(String appID) {
		WeixinConfigure.appID = appID;
	}

	public static void setMchID(String mchID) {
		WeixinConfigure.mchID = mchID;
	}

	public static void setIp(String ip) {
		WeixinConfigure.ip = ip;
	}

	public static String getKey(){
		return key;
	}
	
	public static String getAppid(){
		return appID;
	}
	
	public static String getMchid(){
		return mchID;
	}

	public static String getIP(){
		return ip;
	}

	public static void setHttpsRequestClassName(String name){
		HttpsRequestClassName = name;
	}

	public static String getSdkVersion(){
		return sdkVersion;
	}

}
