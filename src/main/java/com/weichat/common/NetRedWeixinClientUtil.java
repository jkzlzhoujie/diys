package com.weichat.common;

import java.io.UnsupportedEncodingException;  
import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Formatter;  
import java.util.HashMap;  
import java.util.Map;  
import java.util.UUID;  
  
import javax.servlet.http.HttpServletRequest;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tencent.common.NetRedConfigure;

import cn.temobi.core.util.StringUtil;
  
import net.sf.json.JSONObject;  
  
public class NetRedWeixinClientUtil {  
      
	private static Logger log = LoggerFactory.getLogger(WeixinClientUtil.class);
	/**
	 * 方法名：getWxConfig</br> 
	 * 详述：获取微信的配置信息 </br> 
	 * @param request 
	 * @return 说明返回值含义 
	 * @throws 说明发生此异常的条件 
	 */
    public static Map<String, Object> getWxConfig(HttpServletRequest request,String requestUrl,String access_token,String jsapi_ticket) {  
        Map<String, Object> ret = new HashMap<String, Object>();  
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); // 必填，生成签名的时间戳  
        String nonceStr = UUID.randomUUID().toString(); // 必填，生成签名的随机串  
          
        CommonUtil commonUtil = new CommonUtil();
        //要注意，access_token,jsapi_ticket需要缓存  
        if(StringUtil.isEmpty(access_token)){
        	access_token = commonUtil.getToken();  
        	 ret.put("new_access_token", access_token);  
        }
        if(StringUtil.isEmpty(jsapi_ticket)){
        	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";  
        	JSONObject json = commonUtil.httpsRequest(url, "GET", null);  
        	if (json != null) {  
        		jsapi_ticket = json.getString("ticket");  
        	}  
        	 ret.put("new_jsapi_ticket", jsapi_ticket);  
        }
        String signature = "";  
        // 注意这里参数名必须全部小写，且必须有序  
        String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;  
        log.error("sign = " + sign);
        try {  
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");  
            crypt.reset();  
            crypt.update(sign.getBytes("UTF-8"));  
            signature = byteToHex(crypt.digest());  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
            e.printStackTrace();  
        }  
        ret.put("appId", NetRedConfigure.APPID);  
        ret.put("timestamp", timestamp);  
        ret.put("nonceStr", nonceStr);  
        ret.put("signature", signature);  
        return ret;  
    }  
  
      
    /** 
    * 方法名：byteToHex</br> 
    * 详述：字符串加密辅助方法 </br> 
    * @param hash 
    * @return 说明返回值含义 
    * @throws 说明发生此异常的条件 
     */  
    private static String byteToHex(final byte[] hash) {  
        Formatter formatter = new Formatter();  
        for (byte b : hash) {  
            formatter.format("%02x", b);  
        }  
        String result = formatter.toString();  
        formatter.close();  
        return result;  
    }  
    
    
}  