package com.tencent.common;

/**
 * User: janseny
 * Date: 2018/01/06
 * Time: 14:40
 * 喵网红公众号
 * 这里放置各种配置数据
 */
public class NetRedConfigure {

	//sdk的版本号
	private static final String sdkVersion = "java sdk 1.0.1";

	//这个就是自己要保管好的私有Key了（切记只能放在自己的后台代码里，不能放在任何可能被看到源代码的客户端程序中）
	// 每次自己Post数据给API的时候都要用这个key来对所有字段进行签名，生成的签名会放在Sign这个字段，API收到Post数据的时候也会用同样的签名算法对Post过来的数据进行签名和验证
	// 收到API的返回的时候也要用这个key来对返回的数据算下签名，跟API的Sign数据进行比较，如果值不一致，有可能数据被第三方给篡改
	private static String key = "ba922a07c2586cd59fwed03acf938c8d";

	//微信分配的公众号ID（开通公众号之后可以获取到）
	public final static String APPID = "wx7a7bc6c16b83c47d";
	public final static String AppSecret = "7e1e2d26a1032f33f9601d7efa330a02"; 
	
	//微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
	private static String appID = "wx7a7bc6c16b83c47d";
	
	private static String mchID = "1484209152";

	//机器IP
	private static String ip = "";
	public static String UNIFIEDORDER_API = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static String HttpsRequestClassName = "com.tencent.common.HttpsRequest";

	public static void setKey(String key) {
		NetRedConfigure.key = key;
	}

	public static void setAppID(String appID) {
		NetRedConfigure.appID = appID;
	}

	public static void setMchID(String mchID) {
		NetRedConfigure.mchID = mchID;
	}

	public static void setIp(String ip) {
		NetRedConfigure.ip = ip;
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
