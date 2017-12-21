package cn.temobi.complex.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.report.ReceivedsResult;
import cn.jpush.api.report.ReceivedsResult.Received;
import cn.temobi.complex.entity.Message;
import cn.temobi.core.service.ServiceBase;

/**
 * 横幅广告
 * @author hjf
 * 2014 三月 17 17:17:17
 */
@SuppressWarnings({ "serial", "unchecked" })
@Transactional
@Service("pushService")
public class PushService extends ServiceBase{
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private String masterSecret = "5df050b6c256f27e6e95e8ad";
	private String appKey="460ea97d1a2b97a8d61f4b09";
	
	public Map<String,Object> push(Message message) {
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

	     PushPayload payload = buildPushObject(message);
	     Map<String,Object>  rmap = new HashMap<String, Object>();
	     String status = "1";
	     try {
	         PushResult result = jpushClient.sendPush(payload);
	         long msg_id = result.msg_id;
	         log.info("Got result - " + result.msg_id);
	         rmap.put("msg_id", msg_id);
	     } catch (APIConnectionException e1) {
	    	 log.error("Connection error, should retry later", e1);
	    	 status="2";
	     } catch (APIRequestException e) {
	         // Should review the error, and fix the request
	    	 log.error("Should review the error, and fix the request", e);
	    	 log.info("HTTP Status: " + e.getStatus());
	    	 log.info("Error Code: " + e.getErrorCode());
	    	 log.info("Error Message: " + e.getErrorMessage());
	    	 status="2";
	     }
	     rmap.put("status", status);
	     return rmap;
	}
	
	public static PushPayload buildPushObject(Message message) {
		Map<String,String> map = new HashMap<String, String>();
		map.put("type", message.getType());
		map.put("themeId", message.getThemeId());
		map.put("materialId", message.getMaterialId());
		map.put("url", message.getUrl());
        return PushPayload.newBuilder().setPlatform(Platform.all()).setAudience(Audience.all())
        .setNotification(Notification.android(message.getContent(), message.getTitle(), map)).build();
    }
	
	public List<Received> getCount(String messageId)
	{
		 JPushClient jpushClient = new JPushClient(masterSecret, appKey);
	        try {
	            ReceivedsResult result = jpushClient.getReportReceiveds(messageId);
	            log.debug("Got result - " + result);
	            return result.received_list;
	        } catch (APIConnectionException e) {
	            // Connection error, should retry later
	        	log.error("Connection error, should retry later", e);

	        } catch (APIRequestException e) {
	            // Should review the error, and fix the request
	        	log.error("Should review the error, and fix the request", e);
	        	log.info("HTTP Status: " + e.getStatus());
	        	log.info("Error Code: " + e.getErrorCode());
	        	log.info("Error Message: " + e.getErrorMessage());
	        }
	        return null;
	}
	
	
	public static void main(String[] args) {
//		PushService pushService = new PushService();
//		Map<String,String> map = new HashMap<String, String>();
//		map.put("type", "1");
//		map.put("alert", "嘿嘿");
//		map.put("title", "标题");
//		Map<String,Object> rmap = pushService.push(map);
//		List<Received> list = pushService.getCount(rmap.get("msg_id").toString());
//		System.out.println(list.get(0).android_received);
	}
}
