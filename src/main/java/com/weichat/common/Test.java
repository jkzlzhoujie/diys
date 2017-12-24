package com.weichat.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		 // 获取接口访问凭证
//		String APPID = "wxffc37c6417e29cd8";
//		String AppSecret = "ba922a07c2766cd59f5ed03acf938c8d";
		
		String APPID = "wx7a7bc6c16b83c47d";
		String AppSecret = "886487a13204d88c55214bbaff76b0c3";
		CommonUtil commonUtil = new CommonUtil();
        String accessToken = commonUtil.getToken();
        /**
         * 获取用户信息
         */
        WeixinUser user = getUserInfo(accessToken, "ooK-yuJvd9gEegH6nRIen-gnLrVw");
        System.out.println("OpenID：" + user.getOpenId());
        System.out.println("关注状态：" + user.getSubscribe());
        System.out.println("关注时间：" + user.getSubscribeTime());
        System.out.println("昵称：" + user.getNickname());
        System.out.println("性别：" + user.getSex());
        System.out.println("国家：" + user.getCountry());
        System.out.println("省份：" + user.getProvince());
        System.out.println("城市：" + user.getCity());
        System.out.println("语言：" + user.getLanguage());
        System.out.println("头像：" + user.getHeadImgUrl());
		
	}
	
	/**
     * 获取用户信息
     * 
     * @param accessToken 接口访问凭证
     * @param openId 用户标识
     * @return WeixinUser
     */
    public static WeixinUser getUserInfo(String accessToken, String openId) {
    	String APPID = "wxffc37c6417e29cd8";
		String AppSecret = "ba922a07c2766cd59f5ed03acf938c8d"; 
		CommonUtil CommonUtil = new CommonUtil();
    	
//    	首先你的网站入口必须是微信服务号（开通认证、拥有获取用户openid权限；订阅号是不行的）。
//    	网页通过微信的Oauth2认证链接。
//
//    	然后通过服务号菜单链接进入网站，
//    	如： https://open.weixin.qq.com/connect/oauth2/authorize?appid=YOURAPPID&redirect_uri=http://YOUWEBSITE/oauth2.php&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
//
//    	自己根据微信提供的oauth2接口文档，编写内容，（网上有相关官方SDK）
//    	按下面的步骤：
//    	1.获取用户openid
//    	2.获取accesson_token
//    	3.获取用户信息
//    	4.授权注册用户,若已存在该用户则直接进入网站。
    	
//    	链接 ：  http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
    	
    	
//    	第一步：用户同意授权，获取code
//    	在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开如下页面：

        String requestUrlFirst = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect";
        requestUrlFirst = requestUrlFirst.replace("SCOPE", "snsapi_userinfo").replace("APPID", APPID);
        String redirect_uri = "www.waiweiba.cn/sshc/client/health/healthIndex";
        try {
			requestUrlFirst = requestUrlFirst.replace("REDIRECT_URI", URLEncoder.encode(redirect_uri, "utf-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
        
//  实例  	https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx520c15f417810387&redirect_uri=https%3A%2F%2Fchong.qq.com%2Fphp%2Findex.php%3Fd%3D%26c%3DwxAdapter%26m%3DmobileDeal%26showwxpaytitle%3D1%26vb2ctag%3D4_2030_5_1194_60&response_type=code&scope=snsapi_base&state=123#wechat_redirect

//        用户同意授权后
//        如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
//        若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE
        
        
//      获取code后，请求以下链接获取access_token ，OPENID： 
//        https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code
        String code = "";
		String requestUrlOpenId = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        requestUrlFirst = requestUrlFirst.replace("CODE", code).replace("APPID", APPID).replace("SECRET", AppSecret);
        // 获取用户信息
        //请求  解析json 得到 openID
        JSONObject jsonObjectOpenId = CommonUtil.httpsRequest(requestUrlOpenId, "GET", null);
        if (null != jsonObjectOpenId) {
            try {
            	openId = jsonObjectOpenId.getString("openid");
            } catch (Exception e) {
            	
            }
        }
    	
    	WeixinUser weixinUser = null;
        // 拼接请求地址
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
        requestUrl = requestUrl.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        JSONObject jsonObject = CommonUtil.httpsRequest(requestUrl, "GET", null);

        if (null != jsonObject) {
            try {
                weixinUser = new WeixinUser();
                // 用户的标识
                weixinUser.setOpenId(jsonObject.getString("openid"));
                // 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
                weixinUser.setSubscribe(jsonObject.getInt("subscribe"));
                // 用户关注时间
                weixinUser.setSubscribeTime(jsonObject.getString("subscribe_time"));
                // 昵称
                weixinUser.setNickname(jsonObject.getString("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUser.setSex(jsonObject.getInt("sex"));
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
            } catch (Exception e) {
                if (0 == weixinUser.getSubscribe()) {
//                    log.error("用户{}已取消关注", weixinUser.getOpenId());
                	  System.out.println("用户{}已取消关注"+weixinUser.getOpenId());
                } else {
                    int errorCode = jsonObject.getInt("errcode");
                    String errorMsg = jsonObject.getString("errmsg");
//                    log.error("获取用户信息失败 errcode:{} errmsg:{}", errorCode, errorMsg);
                      System.out.println("获取用户信息失败 errcode:{} errmsg:{}" + errorCode + errorMsg);
                }
            }
        }
        return weixinUser;
    }


}
