package cn.temobi.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.core.common.Constant;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import com.alipay.util.AlipayCore;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ReqData;
import com.tencent.protocol.pay_protocol.ResData;

public class OrderUtil {

	protected static Logger log = LoggerFactory.getLogger(OrderUtil.class);

	public static Map<String, Object> getWeixinInfo(Map<String, Object> map,double price) {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String productDes = map.get("productDes").toString();
		String productDetail = map.get("productDetail").toString();
		String productExt = "";
		String orderNo = map.get("orderNo").toString();
		String ip = map.get("ip").toString();
		ReqData reqData = new ReqData(productDes, productExt, orderNo,(new Double(price*100)).intValue(), Constant.wx_notify_url, ip, "APP", productDetail);
		try {
			String responseString = WXPay.requestUnifiedorderService(reqData);
			log.error(responseString);
			if (StringUtil.isNotEmpty(responseString)) {
				ResData resData = (ResData) Util.getObjectFromXML(responseString,
						ResData.class);
				returnMap.put("prepayid", resData.getPrepay_id());
				returnMap.put("appid", Configure.getAppid());
				returnMap.put("partnerid", Configure.getMchid());
				returnMap.put("noncestr", RandomStringGenerator
						.getRandomStringByLength(32));
				returnMap.put("timestamp", DateUtils.parse(
						DateUtils.getCurrDateTimeStr(),
						DateUtils.YYYY_MM_DD_HH_MM_SS).getTime() / 1000);
				returnMap.put("package", "Sign=WXPay");
				returnMap.put("sign", Signature.getSign(returnMap));
				returnMap.put("packageStr", "Sign=WXPay");
				returnMap.put("orderNo", orderNo);
				return returnMap;
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
		returnMap.put("code", "1");
		return returnMap;
	}
	
	
	public static Map<String, Object> getAliInfo(Map<String, Object> map,double price) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String productDes = map.get("productDes").toString();
		String productDetail = map.get("productDetail").toString();
		String orderNo = map.get("orderNo").toString();
		
		returnMap.put("service", AlipayConfig.service);
		returnMap.put("partner", AlipayConfig.partner);
		returnMap.put("_input_charset", AlipayConfig.input_charset);
		returnMap.put("notify_url",Constant.alipay_notify_url);
		returnMap.put("out_trade_no",orderNo);
		returnMap.put("subject",productDes);
		returnMap.put("payment_type","1");
		returnMap.put("seller_id",AlipayConfig.seller_id);
		returnMap.put("total_fee",price);
		returnMap.put("body",productDetail);
		returnMap.put("it_b_pay","30m");
		returnMap.put("return_url","m.alipay.com");
		
		Map<String, String> aliMap = new HashMap<String, String>();
		aliMap.put("service", AlipayConfig.service);
		aliMap.put("partner", AlipayConfig.partner);
		aliMap.put("_input_charset", AlipayConfig.input_charset);
		aliMap.put("notify_url", Constant.alipay_notify_url);
		aliMap.put("out_trade_no",orderNo);
		aliMap.put("subject",productDes);
		aliMap.put("payment_type","1");
		aliMap.put("seller_id",AlipayConfig.seller_id);
		aliMap.put("total_fee",price+"");
		aliMap.put("body",productDetail);
		aliMap.put("it_b_pay","30m");
		aliMap.put("return_url","m.alipay.com");
		Map<String, String> sParaNew = AlipayCore.paraFilter(aliMap);
        String preSignStr = AlipayCore.getOrderInfo(sParaNew);
        String sign = RSA.sign(preSignStr, AlipayConfig.private_key, AlipayConfig.input_charset);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			returnMap.put("code", "1");
			return returnMap;
		}
        returnMap.put("sign", sign);
		returnMap.put("sign_type", AlipayConfig.sign_type);
		returnMap.put("orderNo", orderNo);
		return returnMap;
	}

}
