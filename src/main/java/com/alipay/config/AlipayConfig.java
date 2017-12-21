package com.alipay.config;

import cn.temobi.core.common.Constant;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.3
 *日期：2012-08-10
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
	
 *提示：如何获取安全校验码和合作身份者ID
 *1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *2.点击“商家服务”(https://b.alipay.com/order/myOrder.htm)
 *3.点击“查询合作者身份(PID)”、“查询安全校验码(Key)”

 *安全校验码查看时，输入支付密码后，页面呈灰色的现象，怎么办？
 *解决方法：
 *1、检查浏览器配置，不让浏览器做弹框屏蔽设置
 *2、更换浏览器或电脑，重新登录查询。
 */

public class AlipayConfig {
	
	//商户PID
	public static final String partner = "2088021129553512";
	//商户收款账号
	public static final String seller_id = "pay@weiju360.cn";
	//商户私钥，pkcs8格式
	public static final String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJUUbhMRoLXQ4NU8MbzxuXRrx2OzkCwPeyrAVuOB75nEFTZYERtRjXR749EqWtHnx9sw4floOLYVOU2YZysaQCe3NwarCuZIXG9ds1O07ht55B0q1GAR4y2AcSV5giOzmQisv3vW+sUdZLDRNfwxi3u6eX8YQUreghE2hld08A8PAgMBAAECgYAdQyVYGN2y0o1PTcF1lP0SHRXGPDjQbTl/6sEjZx3g0NxZDnBNLHTXqHnPVMD+8sAIBlBU5GA9XW0iDggyLbTpyhtdQy3ZL2iwJXS4wldpWXCxHkjBmhHeA6JHBzxRgwwNbR28fp0ez56pJdgpZc5vDISPv84XUavJ+Z7zn3RqAQJBAMNfoCFTvNGECyTR60yNstSQKhMXpojyK5CjgeTvF12wSdcE1nmKvlLCQjRKPOIEfw/StiOjlzn2eqLh4GBON5UCQQDDV0D+qyKDi1h6YCtzhHmBJeOC2BwvZlbXmTU4zmqY0sQQuxdtfC1iPzuT+rpBrg32Ctbg6u7fEgsBokqJAnMTAkEAprH3YZgd2bHNBExrc/TOqVib3ZBnUh82FoG7uYZaLGDWj7Qb/rXnb5s5e33/9mQZuXlyWHQi+pyXxFPu6pyyBQJAKmb8UsuvOEK9OAM8K10sSt6Anlxu8dMTMsWtCeG0veuyZ7dVBI0aBOOY+SxJ1gwEN85uinA03fM6tGYYK9l+RQJALXTmIqguDhdpqh0CxJ7IEzA6I23aDOMSg6V/3goc4rLcePtKFtAMAG5vxV34SQ6BPAErbDY7VxIZOIhGUWADtw==";
	//支付宝公钥
	public static final String ali_public_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	
	// 调试用，创建TXT日志文件夹路径
	public static String log_path = "D:\\";

	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
	
	// 签名方式 不需修改
	public static String sign_type = "RSA";
	
	public static String service = "mobile.securitypay.pay";
	public static String notify_url = Constant.HOST_URL+"/diys/client/order/alipaynotify";

}
