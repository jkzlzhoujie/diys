package com.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class SmsMessageUtil {
	
	final String accessKeyId = "LTAIF0jqlwF0QIjl";//你的accessKeyId,
	final String accessKeySecret = "9UBngXj6p6E6KrQqUpo3pVQIxL43qr";//你的accessKeySecret
	final String signName = "红U传媒";//你的短信签名
	final String register_templateCode = "SMS_119260097";// 注册 短信模板code
	final String update_password_templateCode = "SMS_119260096";//密码修改
	final String authentication_templateCode = "SMS_119260101";//身份认证
	
	
	public boolean sendMessage(String phone,String code,String templateType){
		try {
			//设置超时时间-可自行调整
			System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
			System.setProperty("sun.net.client.defaultReadTimeout", "10000");
			//初始化ascClient需要的几个参数
			final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
			final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
			//替换成你的AK
			
			//初始化ascClient,暂时不支持多region（请勿修改）
			IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,accessKeySecret);
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
			IAcsClient acsClient = new DefaultAcsClient(profile);
			 //组装请求对象
			 SendSmsRequest request = new SendSmsRequest();
			 //使用post提交
			 request.setMethod(MethodType.POST);
			 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			 request.setPhoneNumbers(phone);
			 //必填:短信签名-可在短信控制台中找到
			 request.setSignName(signName);
			 //必填:短信模板-可在短信控制台中找到
			 request.setTemplateCode(getTemplateCode(templateType));
			 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			 
			 String content = "{\"code\":\"s\"}";
			 content= content.replaceAll("s", code);
			 request.setTemplateParam(content);
			 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
			 //request.setSmsUpExtendCode("90997");
			 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			 request.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse;
			sendSmsResponse = acsClient.getAcsResponse(request);
			System.out.println(sendSmsResponse.getMessage());
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//请求成功
				return true;
			}else{
				return false;
			}
		} catch (ServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	private String getTemplateCode(String templateType){
		String templateCode = register_templateCode;
		if(templateType.equals("authentication")){
			templateCode = authentication_templateCode;
		}else if(templateType.equals("update_password")){
			templateCode = update_password_templateCode;
		}else{
			templateCode = register_templateCode;
		}
		return templateCode;
	}
	
	
	public static void main(String [] args){
		SmsMessageUtil sms = new SmsMessageUtil();
		sms.sendMessage("18206060967","152658","");
	}

}
