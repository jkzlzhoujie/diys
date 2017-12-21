package cn.temobi.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.service.ClientService;
import cn.temobi.complex.service.CmUserInfoService;

import com.tencent.xinge.Message;
import com.tencent.xinge.MessageIOS;
import com.tencent.xinge.XingeApp;


public class PushUtil {

	protected static Logger log = LoggerFactory.getLogger(PushUtil.class);
	private static ClientService clientService = (ClientService) SpringContextUtils.getBean("clientService");
	//正式
	private static long id = 2100119777;
	private static String key = "d237e98979cba351deb0a3bfbe3278ba";
	
	//正式
	private static long iOSId = 2200159203L;
	private static String iOSKey = "86daf8dde64b1d83777657d20c22b61e";
	
	//测试
//	private static long id = 2100135766;
//	private static String key = "d6f26b6ffa31d4786628c404988159ac";
	
	/**
	 * 极光推送
	 * @param pullMessage
	 */
	public static void setPullMessage(String alert){
	    String appKey = "54d81470fd98c5e1ad000f12";//极光推送appKey 
		String masterSecret = "fe02t1wcvxrezkb0cb1kzt3i1mrs0qru"; //极光推送masterSecret 
		long circle = 24;
		long timeToLive =  60 * 60 * circle; //24小时
        JPushClient jpush = new JPushClient(masterSecret, appKey, false, timeToLive);
		try {
			jpush.sendNotificationAll(alert);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	
	/**
	 * 信鸽推送
	 * @param pullMessage
	 */
	public static void pullOneMessage(String userId,String content,String type,String clickId,String url){
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			Client client = clientService.getByUser(map);
			if(StringUtil.isEmpty(client))
			{
				pullOneMessageAnd(userId, content, type, clickId, url);
			}
			if("0".equals(client.getOs()))
			{
				pullOneMessageIos(userId, content, type, clickId, url);
			}else{
				pullOneMessageAnd(userId, content, type, clickId, url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e.getMessage());
		}
	}
	
	/**
	 * 信鸽推送 分组
	 * @param pullMessage
	 */
	public static void pushAccountListMultiple(List<String> list,String content,String type,String clickId,String url){
		try {
			List<String> iosList = new ArrayList<String>();
			List<String> andList = new ArrayList<String>();
			Map<String,Object> map = new HashMap<String, Object>();
			if(list != null && list.size() > 0)
			{
				for(String userId:list){
					map.put("userId", userId);
					Client client = clientService.getByUser(map);
					if(StringUtil.isEmpty(client))
					{
						andList.add(userId);
					}
					if("0".equals(client.getOs()))
					{
						iosList.add(userId);
					}else{
						andList.add(userId);
					}
				}
			}
			
			if(andList != null && andList.size() > 0)
			{
				pushAccountListMultipleAnd(andList, content, type, clickId, url);
			}
			
			if(iosList != null && iosList.size() > 0)
			{
				pushAccountListMultipleIos(iosList, content, type, clickId, url);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e.getMessage());
		}
	
	}
	
	public static void pullAllMessage(String title,String content,String type,String clickId,String url){
		try {
			pullAllMessageAnd(title, content, type, clickId, url);
			pullAllMessageIos(title, content, type, clickId, url);
		} catch (Exception e) {
			e.printStackTrace();
			log.equals(e.getMessage());
		}
	}
	
	
	/**
	 * 信鸽推送
	 * @param pullMessage
	 */
	public static void pullOneMessageAnd(String userId,String content,String type,String clickId,String url){
//		if(StringUtil.isNotEmpty(userId) && !"132".equals(userId))
//		{
			XingeApp app = new XingeApp(id, key);
			Message msg = new Message();
			msg.setTitle("报告主人");
			msg.setContent(content);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("id", clickId);
			map.put("url", url);
			msg.setCustom(map);
			msg.setType(Message.TYPE_NOTIFICATION);
			app.pushSingleAccount(0, userId, msg);
//		}
	}
	
	/**
	 * 信鸽推送 分组
	 * @param pullMessage
	 */
	public static void pushAccountListMultipleAnd(List<String> list,String content,String type,String clickId,String url){
			XingeApp app = new XingeApp(id, key);
			Message msg = new Message();
			msg.setTitle("报告主人");
			msg.setContent(content);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);
			map.put("id", clickId);
			map.put("url", url);
			msg.setCustom(map);
			msg.setType(Message.TYPE_NOTIFICATION);
			JSONObject ret = app.createMultipush(msg);
			JSONObject ret1 = ret.getJSONObject("result");
			int pushId = ret1.getInt("push_id");
			app.pushAccountListMultiple(pushId, list);
	}
	
	public static void pullAllMessageAnd(String title,String content,String type,String clickId,String url){
		XingeApp app = new XingeApp(id, key);
		Message msg = new Message();
		msg.setTitle(title);
		msg.setContent(content);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("id", clickId);
		map.put("url", url);
		msg.setCustom(map);
		msg.setType(Message.TYPE_NOTIFICATION);
		app.pushAllDevice(0, msg);
	}
	
	
	/**
	 * 信鸽推送
	 * @param pullMessage
	 */
	public static void pullOneMessageIos(String userId,String content,String type,String clickId,String url){
//		if(StringUtil.isNotEmpty(userId) && !"132".equals(userId))
//		{
			XingeApp app = new XingeApp(iOSId, iOSKey);
			MessageIOS msgIos = new MessageIOS();
			msgIos.setAlert(content);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);   //推送的类型，在api文档上
			map.put("id", clickId);   //推送的点击ID，
			map.put("url", url);     //推送的点击URL
			map.put("title", "报告主人");  //推送的标题
			map.put("content", content);   //推送的内容
			msgIos.setCustom(map);
			JSONObject js = app.pushSingleAccount(0,"IOS"+userId, msgIos,XingeApp.IOSENV_DEV);
			System.out.println(js.toString());
//		}
	}
	
	/**
	 * 信鸽推送 分组
	 * @param pullMessage
	 */
	public static void pushAccountListMultipleIos(List<String> list,String content,String type,String clickId,String url){
			XingeApp app = new XingeApp(iOSId, iOSKey);
			MessageIOS msgIos = new MessageIOS();
			msgIos.setAlert(content);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", type);   //推送的类型，在api文档上
			map.put("id", clickId);   //推送的点击ID，
			map.put("url", url);     //推送的点击URL
			map.put("title", "报告主人");  //推送的标题
			map.put("content", content);   //推送的内容
			msgIos.setCustom(map);
			JSONObject ret = app.createMultipush(msgIos,XingeApp.IOSENV_DEV);
			JSONObject ret1 = ret.getJSONObject("result");
			int pushId = ret1.getInt("push_id");
			JSONObject js = app.pushAccountListMultiple(pushId, list);
			System.out.println(js.toString());
	}
	
	public static void pullAllMessageIos(String title,String content,String type,String clickId,String url){
		XingeApp app = new XingeApp(iOSId, iOSKey);
		MessageIOS msgIos = new MessageIOS();
		msgIos.setAlert(content);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("type", type);   //推送的类型，在api文档上
		map.put("id", clickId);   //推送的点击ID，
		map.put("url", url);     //推送的点击URL
		map.put("title", title);  //推送的标题
		map.put("content", content);   //推送的内容
		msgIos.setCustom(map);
		JSONObject js = app.pushAllDevice(0, msgIos,XingeApp.IOSENV_DEV);
	}
	
	public static void main(String[] args) {
		//pullAllMessageIos("推送标题", "推送内容","15", "", "");
		//pullOneMessageIos("66", "test","15", "", "");
		List<String> list = new ArrayList<String>();
		list.add("66");
		pushAccountListMultipleIos(list,  "test2分组","15", "", "");
	}
}
