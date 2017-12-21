package cn.temobi.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.CheckVerCodeRequest;
import com.taobao.api.domain.SendMessageRequest;
import com.taobao.api.domain.SendVerCodeRequest;
import com.taobao.api.request.OpenSmsCheckvercodeRequest;
import com.taobao.api.request.OpenSmsSendmsgRequest;
import com.taobao.api.request.OpenSmsSendvercodeRequest;
import com.taobao.api.response.OpenSmsCheckvercodeResponse;
import com.taobao.api.response.OpenSmsSendmsgResponse;
import com.taobao.api.response.OpenSmsSendvercodeResponse;

public class BCDXTest {
	private static String appkey = "23259993";
	private static String secret = "9a05706487f1add75fbeb36ba7e091c3";
	private static String url = "http://gw.api.taobao.com/router/rest";// api请求地址

	protected static Logger log = LoggerFactory.getLogger(BCDXTest.class);
	
	public static void main(String[] args) throws ApiException {
		//check();
		//send();
		//send2();
		String temp = "{'open_sms_sendmsg_response':{'result':{'code':1,'datas':{'task_id':673020748437230709},'message':'SUCCESS','successful':true},'request_id':'15py8lxy6o94c'}}";
		JSONObject jobject = JSONObject.fromObject(temp);
		JSONObject jobject2 = JSONObject.fromObject(jobject.get("open_sms_sendmsg_response"));
		JSONObject jobject3 = JSONObject.fromObject(jobject2.get("result"));
		boolean successful = (Boolean) jobject3.get("successful");
		System.out.println(successful);
	}
	
	public static void send(){
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenSmsSendvercodeRequest req = new OpenSmsSendvercodeRequest();
		// json
		// req.setSendVerCodeRequest("{\"external_id\":\"wb121212\",\"template_id\":\"290902781\",\"signature_id\":\"10\",\"mobile\":\"13501**522\",\"expire_time\":\"60\"}");
		// 对象
		SendVerCodeRequest sendVerCodeRequest = new SendVerCodeRequest();
		// sendVerCodeRequest.setExpireTime(60L); //非必填参数可以直接注释掉
		// sendVerCodeRequest.setExternalId("wb121212");//
		// 自定义id，用于开发者关联自己的数据，非必填参数
		sendVerCodeRequest.setTemplateId(834l);// 填写自己的模板id
		sendVerCodeRequest.setSignatureId(781L);// 填写自己的签名id，注意于应用对应
		sendVerCodeRequest.setMobile("18054819755");// 填写真实的手机号
		req.setSendVerCodeRequest(sendVerCodeRequest);
		OpenSmsSendvercodeResponse response;
		try {
			response = client.execute(req);
			System.out.println(response.getBody());// 测试阶段建议打印完整的返回内容，便于排查
			if (response.isSuccess()) {// api请求成功
				response.getResult().getMessage();
				response.getResult().getSuccessful();// 短信发送成功
			} else {
				response.getResult().getMessage();
				response.getResult().getSuccessful();
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void check(){
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenSmsCheckvercodeRequest req = new OpenSmsCheckvercodeRequest();

		// req.setCheckVerCodeRequest("{\"ver_code\":\"233769\",\"mobile\":\"13501**1522\"}");
		// 对象
		CheckVerCodeRequest checkVerCodeRequest = new CheckVerCodeRequest();
		// 验证验证码的入参要注意 发验证码的入参 若传了domain参数，验证时的入参也要传。
		checkVerCodeRequest.setVerCode("8762132");
		checkVerCodeRequest.setMobile("18054819755");
		req.setCheckVerCodeRequest(checkVerCodeRequest);

		OpenSmsCheckvercodeResponse response;
		try {
			response = client.execute(req);
			System.out.println(response.getBody());
			if (response.isSuccess()) {
				response.getResult().getMessage();
				response.getResult().getSuccessful();
			} else {
				response.getResult().getMessage();
				response.getResult().getSuccessful();
			}
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String send2(String mobile,int code){
		TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
		OpenSmsSendmsgRequest req = new OpenSmsSendmsgRequest();
		SendMessageRequest obj117714 = new SendMessageRequest();
		obj117714.setTemplateId(834l);
		obj117714.setSignatureId(781L);
		obj117714.setContextString("{\"code\":\""+code+"\"}");
		//obj117714.setExternalId("demo");
		obj117714.setMobile(mobile);
		obj117714.setDeviceLimit(123L);
//		obj117714.setSessionLimit(123L);
//		obj117714.setDeviceLimitInTime(123L);
//		obj117714.setMobileLimit(123L);
//		obj117714.setSessionLimitInTime(123L);
//		obj117714.setMobileLimitInTime(123L);
//		obj117714.setSessionId("demo");
//		obj117714.setDomain("demo");
//		obj117714.setDeviceId("demo");
		req.setSendMessageRequest(obj117714);
		OpenSmsSendmsgResponse rsp;
		try {
			rsp = client.execute(req);
			String reMsg = rsp.getBody();
			System.out.println(reMsg);
			JSONObject jobject = JSONObject.fromObject(reMsg);
			JSONObject jobject2 = JSONObject.fromObject(jobject.get("open_sms_sendmsg_response"));
			JSONObject jobject3 = JSONObject.fromObject(jobject2.get("result"));
			boolean successful = (Boolean) jobject3.get("successful");
			if(successful)
			{
				return "000000";
			}
			log.error(reMsg);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "000001";
	}
	
	
}
