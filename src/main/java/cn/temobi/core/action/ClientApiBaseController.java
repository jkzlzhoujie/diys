package cn.temobi.core.action;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.IpUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;



/**
 * @author salim
 * @created 2013-5-30
 * @package com.palminfo.core.action
 */
@SuppressWarnings("serial")
public class ClientApiBaseController extends SimpleController {

	/**
	 * 解析JSON 返回JSONObject对象
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public JSONObject analyticJSON(HttpServletRequest request) throws IOException {
		String param = request.getParameter("p");
		if(StringUtil.isNotEmpty(param)) {
			return JSONObject.fromObject(param);
		}
		return null;
	}
	
	/**
	 * object 返回设置
	 * @param object
	 * @param code
	 */
	public ResponseObject initResponseObject() {
		ResponseObject object = new ResponseObject();
		object.setCode("");
		object.setResponse("");
		object.setDesc("");
		object.setDate(DateUtils.getCurrentDateTime("yyyyMMddHHmmss"));
		return object;
	}
	
	public static String genMac(String paramString1, String paramString2)
			throws Exception {
		byte arrayOfByte1[] = paramString1.getBytes("UTF-8");
		Mac localMac = Mac.getInstance("HmacMD5");
		byte arrayOfByte2[] = hexStringToBytes(paramString2);
		SecretKeySpec localSecretKeySpec = new SecretKeySpec(arrayOfByte2,
				"DESede");
		localMac.init(localSecretKeySpec);
		byte arrayOfByte3[] = localMac.doFinal(arrayOfByte1);
		return bytesToHexString(arrayOfByte3);
	}

	public static byte[] hexStringToBytes(String s) {
		if (s == null || s.equals(""))
			return null;
		s = s.toUpperCase();
		int i = s.length() / 2;
		char ac[] = s.toCharArray();
		byte abyte0[] = new byte[i];
		for (int j = 0; j < i; j++) {
			int k = j * 2;
			abyte0[j] = (byte) (charToByte(ac[k]) << 4 | charToByte(ac[k + 1]));
		}
		return abyte0;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static String bytesToHexString(byte abyte0[]) {
		StringBuilder stringbuilder = new StringBuilder("");
		if (abyte0 == null || abyte0.length <= 0)
			return null;
		for (int i = 0; i < abyte0.length; i++) {
			int j = abyte0[i] & 255;
			String s = Integer.toHexString(j);
			if (s.length() < 2)
				stringbuilder.append(0);
			stringbuilder.append(s);
		}
		return stringbuilder.toString();
	}

	public static String gainSeqNum() {
		return RandomUtils.getRandomStr(6) + DateUtils.getCurrentDateTime("yyyyMMddHHmmssSSS");
	}
	
	public static String getIpAddr(HttpServletRequest request) throws Exception{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String phoneConvert(String phone) {
		if(StringUtil.isNotEmpty(phone)) {
			if(phone.startsWith("86")) {
				phone = phone.substring(2);
			}else if(phone.startsWith("+86")) {
				phone = phone.substring(3);
			}
		}
		return phone;
	}
	
	
	public static void getDefaultPara(HttpServletRequest request,Map<String,Object> map,String apiName) {
		Long clientId = CommonUtil.nvlLong(request.getParameter("clientId"));
    	String imei = CommonUtil.nvl(request.getParameter("imei"));
    	String imsi = CommonUtil.nvl(request.getParameter("imsi"));
    	String androidId = CommonUtil.nvl(request.getParameter("androidId"));
    	String uuidString = CommonUtil.nvl(request.getParameter("uuidString"));
    	String macString = CommonUtil.nvl(request.getParameter("macString"));
    	String serialNumber = CommonUtil.nvl(request.getParameter("serialNumber"));
    	Object object = request.getAttribute("cmUserInfo");
    	String os = CommonUtil.nvl(request.getParameter("os"));
    	String channel = CommonUtil.nvl(request.getParameter("channel"));
    	String machine = CommonUtil.nvl(request.getParameter("machine"));
    	String osVersion = CommonUtil.nvl(request.getParameter("osVersion"));
    	String systemVersion = CommonUtil.nvl(request.getParameter("systemVersion"));
    	String ip = CommonUtil.nvl(IpUtil.getIp(request));
    	AccountUserLog accountUserLog = null;
    	CmUserInfo cmUserInfo = null;
    	if(StringUtil.isNotEmpty(object))
    	{
    		cmUserInfo = (CmUserInfo) object;
    	}
    	accountUserLog = new AccountUserLog();
    	accountUserLog.setClientId(clientId);
    	if(StringUtil.isNotEmpty(object))
    	{
    		accountUserLog.setUserId(cmUserInfo.getId());
    	}
    	accountUserLog.setImei(imei);
    	accountUserLog.setImsi(imsi);
    	accountUserLog.setAndroidId(androidId);
    	accountUserLog.setApiName(apiName);
    	accountUserLog.setUuidString(uuidString);
    	accountUserLog.setMacString(macString);
    	accountUserLog.setSerialNumber(serialNumber);
    	accountUserLog.setOs(os);
    	accountUserLog.setChannel(channel);
    	accountUserLog.setOsVersion(osVersion);
    	accountUserLog.setSystemVersion(systemVersion);
    	accountUserLog.setMachine(machine);
    	accountUserLog.setIp(ip);
    	map.put("clientId", clientId);
    	map.put("imei", imei);
    	map.put("imsi", imsi);
    	map.put("androidId", androidId);
    	map.put("uuidString", uuidString);
    	map.put("macString", macString);
    	map.put("serialNumber", serialNumber);
    	map.put("os", os);
    	map.put("cmUserInfo", cmUserInfo);
    	map.put("accountUserLog", accountUserLog);
    	map.put("ip", ip);
	}
	

}
