package com.weichat.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.temobi.core.util.StringUtil;

import com.tencent.common.WeixinConfigure;



/**
* 类名: CommonUtil </br>
* 描述: 通用工具类  </br>
* 发布版本：V1.0  </br>
 */
public class CommonUtil {
    private static Logger log = LoggerFactory.getLogger(CommonUtil.class);

    // 凭证获取（GET）
    public final static String token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    public final static String oauthUrlFirst = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
    public final static String oauthUrlSecond = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
    public final static String oauthUrlThird = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN";
    public final static String getUserInfoStr = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
//    public final static String getUserInfoStr = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
    
    /**
     * 发送https请求
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化
            TrustManager[] tm = { new MyX509TrustManager() };
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            conn.setRequestMethod(requestMethod);

            // 当outputStr不为null时向输出流写数据
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("连接超时：{}", ce);
        } catch (Exception e) {
            log.error("https请求异常：{}", e);
        }
        return jsonObject;
    }

    /**
     * 获取接口访问凭证
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public String getToken() {
        Token token = null;
        String requestUrl = token_url.replace("APPID", WeixinConfigure.APPID).replace("APPSECRET",  WeixinConfigure.AppSecret);
        // 发起GET请求获取凭证
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
        if (null != jsonObject) {
            try {
                token = new Token();
                token.setAccessToken(jsonObject.getString("access_token"));
                token.setExpiresIn(Integer.valueOf(jsonObject.getString("expires_in")));
            } catch (JSONException e) {
                token = null;
                // 获取token失败
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
            }
        }
        //要注意，access_token需要缓存  
        return token.getAccessToken();
        
    }
    
    /**
     * URL编码（utf-8）
     * @param source
     * @return
     */
    public String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 根据内容类型判断文件扩展名
     * @param contentType 内容类型
     * @return
     */
    public String getFileExt(String contentType) {
        String fileExt = "";
        if ("image/jpeg".equals(contentType))
            fileExt = ".jpg";
        else if ("audio/mpeg".equals(contentType))
            fileExt = ".mp3";
        else if ("audio/amr".equals(contentType))
            fileExt = ".amr";
        else if ("video/mp4".equals(contentType))
            fileExt = ".mp4";
        else if ("video/mpeg4".equals(contentType))
            fileExt = ".mp4";
        return fileExt;
    }
    
    /**
     * 第一步：用户同意授权，获取code
     * @param appid 凭证
     * @param redirect_uri 跳转界面
     * @return
     */
    public String getOauthFirst(String redirect_uri) {
    	String requestUrl = oauthUrlFirst.replace("SCOPE", "snsapi_userinfo").replace("APPID", WeixinConfigure.APPID);
        
    	/* 回调页面url  */
        redirect_uri = "http://www.waiweiba.cn/sshc/client/weiChatAppointment/appointment";
        
        try {
        	requestUrl = requestUrl.replace("REDIRECT_URI", URLEncoder.encode(redirect_uri, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		System.out.println("view button 地址：" + requestUrl);
       return  requestUrl;
    }
    
    
    /**
     * 第二步：通过code换取网页授权access_token,openId
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public Token getOauthSecond(String code, Token token) {
    	String requestUrl = oauthUrlSecond.replace("CODE", code).replace("APPID", WeixinConfigure.APPID).replace("SECRET", WeixinConfigure.AppSecret);
         JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
         if (null != jsonObject) {
             try {
                 token.setAccessToken(jsonObject.getString("access_token"));
                 token.setRefreshToken(jsonObject.getString("refresh_token"));
                 token.setOpenid(jsonObject.getString("openid"));
                 token.setScope(jsonObject.getString("scope"));
                 token.setExpiresIn(Integer.valueOf(jsonObject.getString("expires_in")));
                 log.error("获取token:access_token= {} , openid:= {}", jsonObject.getString("access_token"), jsonObject.getString("openid"));
             } catch (JSONException e) {
                 token = null;
                 // 获取token失败
                 log.error("获取token失败 errcode:{},  errmsg:{}", jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
             }
         }
         return token;
    }
    
    /**
     * 第三步：刷新access_token（如果需要）
     * @param appid 凭证
     * @param refreshToken refreshToken
     * @return
     */
    public Token getOauthThird(String appid, String refreshToken, Token token) {
    	String requestUrl = oauthUrlThird.replace("APPID", WeixinConfigure.APPID).replace("REFRESH_TOKEN", refreshToken);
    	//请求  解析json 得到 openID
         JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);
         if (null != jsonObject) {
             try {
                 token = new Token();
                 token.setAccessToken(jsonObject.getString("access_token"));
                 token.setRefreshToken(jsonObject.getString("refresh_token"));
                 token.setOpenid(jsonObject.getString("openid"));
                 token.setScope(jsonObject.getString("scope"));
                 token.setExpiresIn(Integer.valueOf(jsonObject.getString("expires_in")));
                 log.error("获取token:access_token= {},  openid:= {}", jsonObject.getString("access_token"), jsonObject.getString("openid"));
             } catch (JSONException e) {
                 token = null;
                 // 获取token失败
                 log.error("获取token失败 errcode:{},  errmsg:{}", jsonObject.getString("errcode"), jsonObject.getString("errmsg"));
             }
         }
         return token;
    }
    
    /**
     * 获取用户信息
     * 
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WeixinUserInfo
     */
    public WeixinUser getUserInfo(String accessToken, String openId , WeixinUser weixinUser) {
		
        // 拼接请求地址
    	String requestUrl = getUserInfoStr ;
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                // 用户的标识
                weixinUser.setOpenId(jsonObject.getString("openid"));
                // 昵称
                weixinUser.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUser.setSex(Integer.valueOf(jsonObject.getString("sex")));
                // 用户所在国家
                weixinUser.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                weixinUser.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                weixinUser.setCity(jsonObject.getString("city"));
                // 用户的语言，简体中文为zh_CN
                weixinUser.setLanguage(jsonObject.getString("language"));
                // 用户头像
                weixinUser.setHeadImgUrl(jsonObject.getString("headimgurl"));
                
                log.error("获取用户信息:nickname {} headimgurl:{}", jsonObject.getString("nickname"), jsonObject.getString("headimgurl"));
            } catch (Exception e) {
//                if (0 == weixinUser.getSubscribe()) {
//                    log.error("用户{}已取消关注", weixinUser.getOpenId());
//                	System.out.println("用户{}已取消关注"+weixinUser.getOpenId());
//                	 log.error(e.getMessage());
//                } else {
//                }
                String errorCode = jsonObject.getString("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:{} , errmsg:{}", errorCode, errorMsg);
                System.out.println("获取用户信息失败 errcode:{} , errmsg:{}" + errorCode + errorMsg);
                log.error(e.getMessage());
            }
        }else{
        	 log.error("没有返回用户信息");
        }
        return weixinUser;
    }
    
    
    public String getNewJsapiTicket(String access_token){
    	String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+ access_token + "&type=jsapi";
    	JSONObject json = httpsRequest(url, "GET", null);
    	String jsapi_ticket = "";
    	if (json != null) {  
    		log.error("ticket返回 ：" + json);
    		if(json.containsKey("ticket") && StringUtil.isNotEmpty(json.getString("ticket"))){
    			jsapi_ticket = json.getString("ticket");  
    		}
    	}  
    	return jsapi_ticket ;
    }
    
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		CommonUtil commonUtil =  new CommonUtil();
		commonUtil.getOauthFirst("");
	}
	
    
    
    
    
}
