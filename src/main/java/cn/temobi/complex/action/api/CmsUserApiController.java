package cn.temobi.complex.action.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dto.CmUserInfoDto;
import cn.temobi.complex.dto.CmUserInfoUpdateDto;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserReceive;
import cn.temobi.complex.entity.CouponMobile;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.complex.service.BlackListNickNameService;
import cn.temobi.complex.service.BlackListService;
import cn.temobi.complex.service.BlackListUserEquipmentService;
import cn.temobi.complex.service.CirclePostService;
import cn.temobi.complex.service.ClientService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserReceiveService;
import cn.temobi.complex.service.CouponMobileService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.SysIndustryInfoService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.easemob.api.EasemobIUtil;
import cn.temobi.core.util.BCDXTest;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.NameUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.MD5;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/cmUser")
public class CmsUserApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="couponMobileService")
	private CouponMobileService couponMobileService;
	
	@Resource(name="cmUserExtendInfoService")
	private CmUserExtendInfoService cmUserExtendInfoService;
	
	@Resource(name="clientService")
	private ClientService clientService;
	
	@Resource(name="sysIndustryInfoService")
	private SysIndustryInfoService sysIndustryInfoService;
	
	@Resource(name="laberService")
	private LaberService laberService;
	
	@Resource(name="blackListNickNameService")
	private BlackListNickNameService blackListNickNameService;
	
	@Resource(name="cmUserReceiveService")
	private CmUserReceiveService cmUserReceiveService;
	
	@Resource(name="circlePostService")
	private CirclePostService circlePostService;
	
	/**
	 * 登陆
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject login(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	{
	    		return object;
	    	}
	    	String password = request.getParameter("password");
	    	if(StringUtil.isEmpty(password))
	    	{
	    		return object;
	    	}
	    	String imei = request.getParameter("imei");
	    	
	    	
	    	//确保10s 不重复提交操作
	    	String mobileStr = (String) CacheHelper.getInstance().get("login@mobile"+mobile);
			if(mobileStr == null || !mobile.trim().equals(mobileStr) ){
				CacheHelper.getInstance().set(10,"login@mobile"+mobile, mobile);
			}else{
				object.setCode(Constant.RESPONSE_ERROR_10014);
		    	object.setDesc("亲，操作太快哦");
		    	return object;
			}
			
	    	long t1 = System.currentTimeMillis();
	    	String clientId = request.getParameter("clientId");
	    	List<CmUserInfo> list = new ArrayList<CmUserInfo>();
	    	log.error(" 验证手机和密码 mobile=" + mobile + ", clientId= " + clientId);
	    	CmUserInfo tempCmUserInfo = (CmUserInfo) CacheHelper.getInstance().get("cmUserInfo@mobile"+mobile.trim());
	    	String pass = MD5.getMD5(password);
	    	if(tempCmUserInfo != null && tempCmUserInfo.getPassword() != null){
	    		if(tempCmUserInfo.getPassword().equals(pass)){
	    			list.add(tempCmUserInfo);
	    		}else{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("账号密码错误");
					return object;
	    		}
	    	}else{
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("mobile", mobile);
	    		map.put("password", pass);
	    		list = cmUserInfoService.findByMap(map);
	    	}
	    	
	    	long t2 = System.currentTimeMillis();
	    	
	    	if(list == null || list.size() <= 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("账号密码错误");
	    	}else{
	    		log.error(" 验证手机和密码 mobile=" + mobile + ", clientId= " + clientId + ",t2-t1=" +(t2-t1) );
	    		CmUserInfo cmUserInfo = list.get(0);
	    		CmUserInfoUpdateDto updateUser =  new CmUserInfoUpdateDto();
	    		updateUser.setId(cmUserInfo.getId());
	    		
	    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateStr());
	    		updateUser.setLastLoginTime(DateUtils.getCurrDateStr());
	    		
	    		if(StringUtil.isNotEmpty(clientId)){
	    			Client client = clientService.getById(Long.parseLong(clientId));
	    	    	if(StringUtil.isEmpty(client)){
	    	    		return object;
	    	    	}
	    	    	cmUserInfo.setMachine(client.getMachine());
	    	    	cmUserInfo.setClientId(Long.parseLong(clientId));
	    	    	updateUser.setMachine(client.getMachine());
	    	    	updateUser.setClientId(Long.parseLong(clientId));
	    		}
	    		
	    		long t3 = System.currentTimeMillis();
	    		
	    		String tokenId  = UUIDGenerator.getUUID();
	    		cmUserInfo.setTokenId(tokenId);
	    		updateUser.setTokenId(tokenId);
	    		if(StringUtil.isNotEmpty(imei)){
	    			cmUserInfo.setImei(imei);
	    			updateUser.setImei(imei);
		    	}
//	    		cmUserInfoService.update(cmUserInfo);
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    		
	    		long t4 = System.currentTimeMillis();
	    		
	    		log.error(" 更新成功 mobile=" + mobile +", t2-t1="+(t2-t1)+",t3-t2="+(t3-t2)+",t4-t3="+(t4-t3));
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		String userMobileKey = "cmUserInfo@mobile"+mobile.trim();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
	    		
	    		long t5 = System.currentTimeMillis();
	    		
	    		cmUserInfoDto.setTokenId(tokenId);
	    		log.error("登录成功  mobile=" + mobile + ",tokenId=" + tokenId + ",t5-t4=" + (t5-t4));
	    		object.setResponse(cmUserInfoDto);
	    		object.setDesc("成功");
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 获取验证码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCode", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getCode(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			object.setDesc("手机验证码暂时停止使用！抱歉给您带来不便，有事请联系客服！");
	    	return object;
	    	
//	    	String mobile = request.getParameter("mobile");
//	    	if(StringUtil.isEmpty(mobile))
//	    	{
//	    		return object;
//	    	}
//	    	String type = request.getParameter("type");
//	    	if(StringUtil.isEmpty(type))
//	    	{
//	    		return object;
//	    	}
//	    	Map<String,Object> map = new HashMap<String, Object>();
//	    	map.put("mobile", mobile);
//	    	List<CmUserInfo> list = cmUserInfoService.findByMap(map);
//	    	if("1".equals(type))
//	    	{
//	    		if(list != null && list.size() > 0)
//		    	{
//		    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
//					object.setDesc("您已注册，请登录");
//					return object;
//		    	}
//	    	}else if("2".equals(type))
//	    	{
////	    		if(list == null || list.size() <= 0)
////		    	{
////		    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
////					object.setDesc("您未注册，请注册");
////					return object;
////		    	}
//	    	}
//	    	int num = (int) (Math.random()*1000+1000);
//	    	long time = System.currentTimeMillis();
//	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
//	    	if(StringUtil.isNotEmpty(oldCode))
//	    	{
//	    		String[] arr = oldCode.split("\\|");
//	    		long remain = time - Long.parseLong(arr[1]);
//	    		if(remain - 60*1000 < 0)
//	    		{
//	    			return object;
//	    		}
//	    	}
//	    	String code = BCDXTest.send2(mobile, num);
//	    	if("000000".equals(code))
//	    	{
//	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
//	    		CachedValueAndTime(mobile, num+"|"+time, 5*60);
//	    		object.setDesc("发送成功");
//	    	}else{
//	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
//				object.setDesc("发送失败");
//	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	/**
	 * 校验验证码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/checkMsgCode", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject checkMsgCode(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String msgCode = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(msgCode)){
	    		return object;
	    	}
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile)){
	    		return object;
	    	}
	    	
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode)){
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	
	    	if(oldCode.split("\\|")[0].equals(msgCode)){
	    		object.setDesc("成功");
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		return object;
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
				return object;
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	/**
	 * 找回密码  初始密码 123456
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPwd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getPwd(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	{
	    		return object;
	    	}
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Client client = clientService.getById(Long.parseLong(clientId));
	    	if(StringUtil.isEmpty(client))
	    	{
	    		return object;
	    	}
	    	String osVersion = request.getParameter("osVersion");
	    	String channel = request.getParameter("channel");
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(oldCode.split("\\|")[0].equals(code))
	    	{
	    		Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("mobile", mobile);
		    	List<CmUserInfo> list = cmUserInfoService.findByMap(map);
		    	CmUserInfo cmUserInfo = null;
		    	if(list == null || list.size() <= 0)
		    	{
		    		cmUserInfo = new CmUserInfo();
		    		cmUserInfo.setMobile(mobile);
		    		cmUserInfo.setMachine(client.getMachine());
		    		cmUserInfo.setClientId(Long.parseLong(clientId));
		    		cmUserInfo.setClientChannel(channel);
		    		cmUserInfo.setNickName(NameUtil.getRanName());
		    		cmUserInfo.setClientVersion(osVersion);
		    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateTimeStr());
		    		cmUserInfo.setRegisterFrom("1");
		    		cmUserInfo.setLevel(1);
		    		int a = (int) (Math.random()*123)+1;
		    		cmUserInfo.setHeadImageUrl(host+"/diys/rs/default/logo/"+a+".jpg");
		    		
		    		Map<String,Object> passMap = new HashMap<String, Object>();
					getDefaultPara(request, passMap,null);
		    		cmUserInfoService.saveAll(cmUserInfo,passMap);
		    		
		    		EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
		    	}else{
		    		cmUserInfo = list.get(0);
		    	}
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		cmUserInfoService.update(cmUserInfo);
	    		object.setResponse(getCmUserInfoDto(cmUserInfo));
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 注册
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/register", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject register(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	{
	    		return object;
	    	}
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Client client = clientService.getById(Long.parseLong(clientId));
	    	if(StringUtil.isEmpty(client))
	    	{
	    		return object;
	    	}
	    	String osVersion = request.getParameter("osVersion");
	    	String channel = request.getParameter("channel");
	    	String imei = request.getParameter("imei");
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.clear();
	    	map.put("mobile", mobile);
	    	List<CmUserInfo> list = cmUserInfoService.findByMap(map);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("您已注册，请登录");
				return object;
	    	}
	    	
	    	if(oldCode.split("\\|")[0].equals(code))
	    	{
	    		object.setDesc("成功");
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		CmUserInfo cmUserInfo = new CmUserInfo();
	    		cmUserInfo.setMobile(mobile);
	    		cmUserInfo.setMachine(client.getMachine());
	    		cmUserInfo.setClientId(Long.parseLong(clientId));
	    		cmUserInfo.setClientChannel(channel);
	    		cmUserInfo.setNickName(NameUtil.getRanName());
	    		cmUserInfo.setClientVersion(osVersion);
	    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		cmUserInfo.setRegisterFrom("1");
	    		if(StringUtil.isNotEmpty(imei))
		    	{
	    			cmUserInfo.setImei(imei);
		    	}
	    		cmUserInfo.setLevel(1);
	    		int a = (int) (Math.random()*123)+1;
	    		cmUserInfo.setHeadImageUrl(host+"/diys/rs/default/logo/"+a+".jpg");
	    		String tokenId  = UUIDGenerator.getUUID();
	    		cmUserInfo.setTokenId(tokenId);
	    		
	    		Map<String,Object> passMap = new HashMap<String, Object>();
				getDefaultPara(request, passMap,null);
	    		cmUserInfoService.saveAll(cmUserInfo,passMap);
	    		
	    		EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
	    		
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		String userMobileKey = "cmUserInfo@mobile"+mobile.trim();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
	    		cmUserInfoDto.setTokenId(tokenId);
	    		object.setResponse(cmUserInfoDto);
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 绑定手机号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bindingMobile", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bindingMobile(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	{
	    		return object;
	    	}
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	if(StringUtil.isNotEmpty(cmUserInfo.getMobile()))
	    	{
	    		return object;
	    	}
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(oldCode.split("\\|")[0].equals(code))
	    	{
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("mobile", mobile);
	    		List<CmUserInfo> list = cmUserInfoService.findByMap(map);
	    		if(list != null && list.size() > 0)
	    		{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("该手机号已被绑定");
					return object;
	    		}
	    		
	    		CacheHelper.getInstance().remove(mobile); 
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    		updateUser.setId(cmUserInfo.getId());
	    		updateUser.setMobile(mobile);
//	    		cmUserInfoService.update(cmUserInfo);
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 手机号 解绑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/unBindingMobile", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject unBindingMobile(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	return object;
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code))
	    	return object;
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	return object;
	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	return object;
	    	
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode)){
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(oldCode.split("\\|")[0].equals(code)){
	    		CacheHelper.getInstance().remove(mobile); 
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    		updateUser.setId(cmUserInfo.getId());
	    		updateUser.setMobile("");
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    		cmUserInfo.setMobile("");
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		String userMobileKey = "cmUserInfo@mobile"+mobile.trim();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		CacheHelper.getInstance().remove(userMobileKey);
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    	
	    	object.setDesc("成功");
    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 绑定第三方账号
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bindingThirdAccount", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bindingThirdAccount(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String key = request.getParameter("key");
	    	if(StringUtil.isEmpty(key)){
	    		return object;
	    	}
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null){
	    		return object;
	    	}
	    	
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    	updateUser.setId(cmUserInfo.getId());
	    	if("1".equals(type))
	    	{
	    		map.put("qqUserId", key);
	    		updateUser.setQqUserId(key);
	    		cmUserInfo.setQqUserId(key);
	    		
	    	}else if("2".equals(type))
	    	{
	    		map.put("weixinUserId", key);
	    		updateUser.setWeixinUserId(key);
	    		cmUserInfo.setWeixinUserId(key);
	    	}else if("3".equals(type))
	    	{
	    		map.put("weiboUserId", key);
	    		updateUser.setWeiboUserId(key);
	    		cmUserInfo.setWeiboUserId(key);
	    	}else{
	    		return object;
	    	}
	    	
    		List<CmUserInfo> list = cmUserInfoService.findByMap(map);
    		if(list != null && list.size() > 0)
    		{
    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("该账号已被绑定");
				return object;
    		}
    		cmUserInfoService.updatePartCmUser(updateUser);
    		
    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
    		CacheHelper.getInstance().set(60*60*48,"cmUserInfo@thirdKey"+key.trim(), cmUserInfo);
    		if(cmUserInfo.getMobile() != null){
    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
    		}
    		object.setDesc("绑定成功");
    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 第三方账号解绑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/unBindingThirdAccount", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject unBindingThirdAccount(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null){
	    		return object;
	    	}
	    	
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    	updateUser.setId(cmUserInfo.getId());
	    	if("1".equals(type))
	    	{
	    		updateUser.setQqUserId(null);
	    		cmUserInfo.setQqUserId(null);
	    		CacheHelper.getInstance().remove("cmUserInfo@thirdKey"+cmUserInfo.getQqUserId());
	    	}else if("2".equals(type))
	    	{
	    		updateUser.setWeixinUserId(null);
	    		cmUserInfo.setWeixinUserId(null);
	    		CacheHelper.getInstance().remove("cmUserInfo@thirdKey"+cmUserInfo.getWeixinUserId());
	    	}else if("3".equals(type))
	    	{
	    		updateUser.setWeiboUserId(null);
	    		cmUserInfo.setWeiboUserId(null);
	    		CacheHelper.getInstance().remove("cmUserInfo@thirdKey"+cmUserInfo.getWeiboUserId());
	    	}else{
	    		return object;
	    	}
	    	
	    	String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();;
    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
	    	CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
    		if(cmUserInfo.getMobile() != null){
    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
    		}
	    	
    		cmUserInfoService.updatePartCmUser(updateUser);
    		object.setDesc("成功");
    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updatePwd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updatePwd(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	long t1 = System.currentTimeMillis();
	    	String oldPwd = request.getParameter("oldPwd");
	    	String newPwd = request.getParameter("newPwd");
	    	if(StringUtil.isEmpty(newPwd))
	    	{
	    		return object;
	    	}
	    	String confirmPwd = request.getParameter("confirmPwd");
	    	if(StringUtil.isEmpty(confirmPwd))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	if(!newPwd.equals(confirmPwd))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String password = cmUserInfo.getPassword();
	    	if(!MD5.getMD5(oldPwd).equals(password))
    		{
    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("密码错误");
				return object;
    		}
	    	CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
    		updateUser.setId(cmUserInfo.getId());
    		updateUser.setPassword(MD5.getMD5(newPwd));
    		cmUserInfo.setPassword(MD5.getMD5(newPwd));
    		cmUserInfoService.updatePartCmUser(updateUser);
    		
    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
    		if(cmUserInfo.getMobile() != null){
    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
    		}
    		long t2 = System.currentTimeMillis();
    		log.error("用户" + cmUserInfo.getId() + ",更新密码耗时：" +(t2-t1));
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 设置密码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setPwd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject setPwd(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	long t1 = System.currentTimeMillis();
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code))
	    	{
	    		return object;
	    	}
	    	String newPwd = request.getParameter("newPwd");
	    	if(StringUtil.isEmpty(newPwd))
	    	{
	    		return object;
	    	}
	    	String confirmPwd = request.getParameter("confirmPwd");
	    	if(StringUtil.isEmpty(confirmPwd))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	if(!newPwd.equals(confirmPwd))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String oldCode = "";
	    	if(StringUtil.isNotEmpty(cmUserInfo.getMobile()))
	    	{
	    		oldCode = (String) CacheHelper.getInstance().get(cmUserInfo.getMobile());
	    	}
	    	if(StringUtil.isEmpty(oldCode))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(oldCode.split("\\|")[0].equals(code))
	    	{
	    		CacheHelper.getInstance().remove(cmUserInfo.getMobile());
	    		cmUserInfo.setPassword(MD5.getMD5(newPwd));
	    		
		    	CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    		updateUser.setId(cmUserInfo.getId());
	    		updateUser.setPassword(MD5.getMD5(newPwd));
	    		
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    		
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		if(cmUserInfo.getMobile() != null){
	    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
	    		}
	    		object.setResponse(cmUserInfoDto);
	    		long t2 = System.currentTimeMillis();
	    		log.error("用户" + cmUserInfo.getId() + ",更新密码耗时：" +(t2-t1));
		    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    	object.setDesc("成功");
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 修改新密码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateNewPwd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateNewPwd(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	long t1 = System.currentTimeMillis();
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile)){
	    		return object;
	    	}
	    	String code = request.getParameter("msgCode");
	    	if(StringUtil.isEmpty(code)){
	    		return object;
	    	}
	    	String newPwd = request.getParameter("newPwd");
	    	if(StringUtil.isEmpty(newPwd)){
	    		return object;
	    	}
	    	String confirmPwd = request.getParameter("confirmPwd");
	    	if(StringUtil.isEmpty(confirmPwd)){
	    		return object;
	    	}
	    	
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode)){
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(oldCode.split("\\|")[0].equals(code))
	    	{
	    		Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("mobile", mobile);
		    	List<CmUserInfo> list = cmUserInfoService.findByMap(map);
		    	CmUserInfo cmUserInfo = null;
		    	if(list == null || list.size() <= 0){
		    		object.setCode(Constant.RESPONSE_ERROR_10004);
					object.setDesc("用户不存在");
		    	}else{
		    		cmUserInfo = list.get(0);
		    	}
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    		updateUser.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		updateUser.setId(cmUserInfo.getId());
	    		updateUser.setPassword(MD5.getMD5(newPwd));
	    		
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    		
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		if(cmUserInfo.getMobile() != null){
	    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
	    		}
	    		object.setResponse(getCmUserInfoDto(cmUserInfo));
	    		long t2 = System.currentTimeMillis();
	    		log.error("用户" + cmUserInfo.getId() + ",更新密码耗时：" +(t2-t1));
		    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    	object.setDesc("成功");
	    	}else{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码错误");
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 *	获取个人信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getUser(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	object.setDesc("成功");
	    	if("xxiukefu".equals(userId))
	    	{
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("nickName", "X秀客服");
	    		map.put("headImageUrl","http://pic.weiju360.cn/diys/rs/otherImg/kf1.jpg");
	    		map.put("userId", userId);
	    		object.setResponse(map);
	    	}else{
	    		CmUserInfo	cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setResponse(getCmUserInfoDto(cmUserInfo));
	    	}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 第三方登陆
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/thirdLogin", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject thirdLogin(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("loginType");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	String key = request.getParameter("key");
	    	if(StringUtil.isEmpty(key)){
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	//确保10s 不重复提交操作
	    	String clientIdStr = (String) CacheHelper.getInstance().get("thirdLogin@register"+clientId);
			if(clientIdStr == null || !clientId.trim().equals(clientIdStr) ){
				CacheHelper.getInstance().set(10,"thirdLogin@register"+clientId, clientId);
			}else{
				object.setCode(Constant.RESPONSE_ERROR_10014);
		    	object.setDesc("亲，操作太快哦");
		    	return object;
			}
	    	Client client = clientService.getById(Long.parseLong(clientId));
	    	if(StringUtil.isEmpty(client)){
	    		return object;
	    	}
	    	String sex = request.getParameter("sex");
	    	if(StringUtil.isEmpty(sex)){
	    		sex = "0";
	    	}
	    	String imei = request.getParameter("imei");
	    	String nickName = request.getParameter("nickName");
	    	String headImageUrl = request.getParameter("headImageUrl");
	    	String osVersion = request.getParameter("osVersion");
	    	String channel = request.getParameter("channel");
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	CmUserInfo cmUserInfo = new CmUserInfo();
	    	CmUserInfoUpdateDto updateUser = new CmUserInfoUpdateDto();
	    	if("1".equals(type))
	    	{
	    		map.put("qqUserId", key);
	    		cmUserInfo.setQqUserId(key);
	    		updateUser.setQqUserId(key);
	    		
	    	}else if("2".equals(type))
	    	{
	    		map.put("weixinUserId", key);
	    		cmUserInfo.setWeixinUserId(key);
	    		updateUser.setWeixinUserId(key);
	    	}else if("3".equals(type))
	    	{
	    		map.put("weiboUserId", key);
	    		cmUserInfo.setWeiboUserId(key);
	    		updateUser.setWeiboUserId(key);
	    	}else{
	    		return object;
	    	}
	    	List<CmUserInfo> list = new ArrayList<CmUserInfo>();
	    	CmUserInfo tempCmUserInfo = (CmUserInfo) CacheHelper.getInstance().get("cmUserInfo@thirdKey"+key.trim());
	    	if(tempCmUserInfo != null){
	    		list.add(tempCmUserInfo);
	    	}else{
	    		list = cmUserInfoService.findByMap(map);
	    	}
	    	if(list != null && list.size() > 0)
	    	{
	    		cmUserInfo = list.get(0);
	    		String tokenId  = UUIDGenerator.getUUID();
	    		
	    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		cmUserInfo.setClientId(Long.parseLong(clientId));
	    		cmUserInfo.setMachine(client.getMachine());
	    		cmUserInfo.setTokenId(tokenId);
	    		
	    		updateUser.setId(list.get(0).getId());
	    		updateUser.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		updateUser.setClientId(Long.parseLong(clientId));
	    		updateUser.setMachine(client.getMachine());
	    		updateUser.setTokenId(tokenId);
	    		
	    		if(StringUtil.isNotEmpty(imei)){
	    			cmUserInfo.setImei(imei);
	    			updateUser.setImei(imei);
		    	}
	    		cmUserInfoService.updatePartCmUser(updateUser);
	    		
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		CacheHelper.getInstance().set(60*60*48,"cmUserInfo@thirdKey"+key.trim(), cmUserInfo);
	    		cmUserInfoDto.setTokenId(tokenId);
	    		object.setResponse(cmUserInfoDto);
	    	}else{
	    		if(StringUtil.isEmpty(nickName))
	    		{
	    			cmUserInfo.setNickName(NameUtil.getRanName());
	    		}
	    		if(StringUtil.isEmpty(headImageUrl)){
	    			int a = (int) (Math.random()*123)+1;
		    		cmUserInfo.setHeadImageUrl(host+"/diys/rs/default/logo/"+a+".jpg");
	    		}
	    		cmUserInfo.setClientId(Long.parseLong(clientId));
	    		cmUserInfo.setClientChannel(channel);
	    		cmUserInfo.setNickName(nickName);
	    		cmUserInfo.setMachine(client.getMachine());
	    		cmUserInfo.setClientVersion(osVersion);
	    		cmUserInfo.setSex(Integer.parseInt(sex));
	    		cmUserInfo.setHeadImageUrl(headImageUrl);
	    		cmUserInfo.setLastLoginTime(DateUtils.getCurrDateTimeStr());
	    		cmUserInfo.setRegisterFrom("1");
	    		cmUserInfo.setLevel(1);
	    		
	    		String tokenId  = UUIDGenerator.getUUID();
	    		cmUserInfo.setTokenId(tokenId);
	    		if(StringUtil.isNotEmpty(imei))
		    	{
	    			cmUserInfo.setImei(imei);
		    	}
	    		
	    		Map<String,Object> passMap = new HashMap<String, Object>();
				getDefaultPara(request, passMap,null);
	    		cmUserInfoService.saveAllScore(cmUserInfo,passMap);
	    		
	    		EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
	    		
	    		
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
	    		CacheHelper.getInstance().set(60*60*48,"cmUserInfo@thirdKey"+key.trim(), cmUserInfo);
	    		cmUserInfoDto.setTokenId(tokenId);
	    		object.setResponse(cmUserInfoDto);
	    	}
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 第三方绑定
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bindingThird", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bindingThird(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("loginType");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String key = request.getParameter("key");
	    	if(StringUtil.isEmpty(key))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	if("1".equals(type))
	    	{
	    		if(StringUtil.isEmpty(cmUserInfo.getQqUserId()))
	    		{
	    			return object;
	    		}
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("qqUserId", cmUserInfo.getQqUserId());
	    		List<CmUserInfo> list = cmUserInfoService.findByMap(map);
	    		if(list != null && list.size() > 0)
	    		{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("该qq已被绑定");
					return object;
	    		}
	    		cmUserInfo.setQqUserId(key);
	    		cmUserInfoService.userBinding(cmUserInfo, clientId, "1011");
	    	}else if("2".equals(type))
	    	{
	    		if(StringUtil.isEmpty(cmUserInfo.getWeixinUserId()))
	    		{
	    			return object;
	    		}
	    		
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("weixinUserId", cmUserInfo.getWeixinUserId());
	    		List<CmUserInfo> list = cmUserInfoService.findByMap(map);
	    		if(list != null && list.size() > 0)
	    		{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("该微信已被绑定");
					return object;
	    		}
	    		cmUserInfo.setWeixinUserId(key);
	    		cmUserInfoService.userBinding(cmUserInfo, clientId, "1013");
	    	}else if("3".equals(type))
	    	{
	    		if(StringUtil.isEmpty(cmUserInfo.getWeiboUserId()))
	    		{
	    			return object;
	    		}
	    		
	    		Map<String,Object> map = new HashMap<String, Object>();
	    		map.put("weiboUserId", cmUserInfo.getWeiboUserId());
	    		List<CmUserInfo> list = cmUserInfoService.findByMap(map);
	    		if(list != null && list.size() > 0)
	    		{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("该微博已被绑定");
					return object;
	    		}
	    		cmUserInfo.setWeiboUserId(key);
	    		cmUserInfoService.userBinding(cmUserInfo, clientId, "1012");
	    	}
	    	String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
	    	CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
    		CacheHelper.getInstance().set(60*60*48,"cmUserInfo@thirdKey"+key.trim(), cmUserInfo);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 更新资料
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateUser(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	
	    	String sex = request.getParameter("sex");
	    	String nickName = request.getParameter("nickName");
	    	String signature = request.getParameter("signature");
	    	String education = request.getParameter("education");
	    	String career = request.getParameter("career");
	    	String qq = request.getParameter("qq");
	    	String type = request.getParameter("type");
	    	String birth = request.getParameter("birth");
	    	if(nickName != null && nickName.length()>10)
	    	{
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		cmUserInfoDto.setTokenId(cmUserInfo.getTokenId());
	    		object.setResponse(cmUserInfoDto);
		    	object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
		    	object.setDesc("昵称长度过长");
		    	return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("content", nickName);
	    	int count = blackListNickNameService.checkContent(map).intValue();
	    	if(count>0)
	    	{
	    		CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
	    		cmUserInfoDto.setTokenId(cmUserInfo.getTokenId());
	    		object.setResponse(cmUserInfoDto);
		    	object.setCode(Constant.RESPONSE_ERROR_10003);
		    	object.setDesc("昵称中包含敏感字符");
		    	return object;
	    	}
	    	
	    	String headImageUrl = "";
	    	if(StringUtil.isNotEmpty(type) && "1".equals(type))
	    	{
	    		headImageUrl = FileUtil.uploadLogo(request, response,"logo");
	    	}
	    	map.clear();
	    	map.put("sex", sex);
	    	map.put("nickName", nickName);
	    	map.put("signature", signature);
	    	map.put("education", education);
	    	map.put("career", career);
	    	map.put("qq", qq);
	    	map.put("birth", birth);
	    	map.put("headImageUrl", headImageUrl);
	    	cmUserInfoService.updateAll(cmUserInfo, clientId, map);
	    	
	    	CmUserInfoDto cmUserInfoDto = getCmUserInfoDto(cmUserInfo);
    		cmUserInfoDto.setTokenId(cmUserInfo.getTokenId());
    		object.setResponse(cmUserInfoDto);
    		
    		String userIdKey = "cmUserInfo@userId"+cmUserInfo.getId();
    		String userMobileKey = "cmUserInfo@mobile"+cmUserInfo.getMobile();
    		CacheHelper.getInstance().set(60*60*24,userIdKey, cmUserInfo);
    		if(cmUserInfo.getMobile() != null){
    			CacheHelper.getInstance().set(60*60*48,userMobileKey, cmUserInfo);
    		}
    		
    		
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 上传封面
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateCover", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateCover(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String coverThumbnail = FileUtil.uploadLogo(request, response,"logo");
	    	
	    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(cmUserInfo.getId());
	    	if(cmUserExtendInfo != null)
	    	{
	    		if(StringUtil.isNotEmpty(coverThumbnail))
		    	{
	    			cmUserExtendInfo.setCoverThumbnail(host+coverThumbnail);
	    			cmUserInfoService.updateExtendScore(cmUserInfo,clientId,cmUserExtendInfo);
		    	}
	    	}
	    	object.setResponse(cmUserExtendInfo.getCoverThumbnail());
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 封面点赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/zCover", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject zCover(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String zUserId = request.getParameter("zUserId");
	    	if(StringUtil.isEmpty(zUserId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmUserInfo zCmUserInfo = cmUserInfoService.getById(Long.parseLong(zUserId));
	    	if(zCmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	Object zObject = CacheHelper.getInstance().get(clientId+"Cover"+userId);
	    	if(StringUtil.isNotEmpty(zObject))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("已赞");
				return object;
	    	}
	    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(cmUserInfo.getId());
	    	if(cmUserExtendInfo != null)
	    	{
	    		cmUserExtendInfo.setCoverPraises(cmUserExtendInfo.getCoverPraises()+1);
	    		cmUserExtendInfoService.update(cmUserExtendInfo);
	    		CachedValueAndTime(clientId+"Cover"+userId, "1", 48);
	    	}
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 行业
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSysIndustryInfo", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getSysIndustryInfo(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String industryId = request.getParameter("industryId");
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	if(StringUtil.isNotEmpty(industryId))
	    	{
	    		map.put("parentIndustryId", industryId);
	    	}else{
	    		map.put("parentIndustryId", "0");
	    	}
	    	List<SysIndustryInfo> list = sysIndustryInfoService.findByMap(map);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    	object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	public CmUserInfoDto getCmUserInfoDto(CmUserInfo cmUserInfo)
	{
		long t1 = System.currentTimeMillis();
		Map<String,Object> map = new HashMap<String, Object>();
    	map.put("isDefault", "1");
    	map.put("userId", cmUserInfo.getId() );
    	List<CmUserReceive> cmUserReceiveList = cmUserReceiveService.findByMap(map);
    	long t2 = System.currentTimeMillis();
    	
		CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(cmUserInfo.getId());
		long t3 = System.currentTimeMillis();
		
		if(StringUtil.isEmpty(cmUserExtendInfo))
		{
			cmUserExtendInfo = new CmUserExtendInfo();
    		cmUserExtendInfo.setUserId(cmUserInfo.getId());
    		int b = (int) (Math.random()*73)+1;
    		cmUserExtendInfo.setCoverThumbnail(host+"/diys/rs/default/cover/"+b+".jpg");
    		Map<String,Object> laberMap = new HashMap<String, Object>();
    		laberMap.put("type", "4");
    		laberMap.put("limit", 3);
    		List<String> laberList = laberService.findRand(laberMap);
    		cmUserExtendInfo.setAttentionLabel(StringUtils.join(laberList.toArray(), ","));
    		cmUserExtendInfoService.save(cmUserExtendInfo);
		}
		long t4 = System.currentTimeMillis();
		CmUserInfoDto cmUserInfoDto = new CmUserInfoDto();
		String tokenId  = UUIDGenerator.getUUID();
		cmUserInfoDto.setTokenId(tokenId);
		cmUserInfoDto.setQqUserId(cmUserInfo.getQqUserId());
		cmUserInfoDto.setWeiboUserId(cmUserInfo.getWeiboUserId());
		cmUserInfoDto.setWeixinUserId(cmUserInfo.getWeixinUserId());
		cmUserInfoDto.setUserId(cmUserInfo.getId());
		cmUserInfoDto.setBirth(cmUserInfo.getBirth());
		cmUserInfoDto.setSex(cmUserInfo.getSex());
		cmUserInfoDto.setMobile(cmUserInfo.getMobile());
		cmUserInfoDto.setNickName(cmUserInfo.getNickName());
		cmUserInfoDto.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
		cmUserInfoDto.setCareer(cmUserExtendInfo.getCareer());
		cmUserInfoDto.setiMpwd(Constant.DEFAULT_PWD);
		cmUserInfoDto.setLevel(cmUserInfo.getLevel());
		cmUserInfoDto.setFansCount(cmUserInfo.getFansCount());
		cmUserInfoDto.setPraisesCount(cmUserInfo.getPraisesCount())  ;
		cmUserInfoDto.setProductCount(cmUserInfo.getProductCount()) ;
		cmUserInfoDto.setDiscussCount(cmUserInfo.getDiscussCount()) ;
		cmUserInfoDto.setAttentionsCount(cmUserInfo.getAttentionsCount()) ;
		cmUserInfoDto.setCoverThumbnail(cmUserExtendInfo.getCoverThumbnail()) ;
		cmUserInfoDto.setAttentionLabel(cmUserExtendInfo.getAttentionLabel()) ;
		cmUserInfoDto.setCity(cmUserInfo.getCity());
		if(StringUtil.isNotEmpty(cmUserInfo.getPrivilegeLevel())){
			cmUserInfoDto.setPrivilegeLevel(cmUserInfo.getPrivilegeLevel());
		}else{
			cmUserInfoDto.setPrivilegeLevel("general");
		}
		
		if(cmUserReceiveList!=null && cmUserReceiveList.size() >0){
			cmUserInfoDto.setName(cmUserReceiveList.get(0).getName());
			cmUserInfoDto.setRePhone(cmUserReceiveList.get(0).getRePhone());
			cmUserInfoDto.setReAddress(cmUserReceiveList.get(0).getReAddress());
			cmUserInfoDto.setRePostCode(cmUserReceiveList.get(0).getRePostCode());
		}
		
		if(StringUtil.isEmpty(cmUserInfo.getPassword()))
		{
			cmUserInfoDto.setIsSetPwd("0");
		}else{
			cmUserInfoDto.setIsSetPwd("1");
		}
		if(cmUserExtendInfo != null)
		{
			cmUserInfoDto.setEducation(cmUserExtendInfo.getEducation());
			cmUserInfoDto.setQq(cmUserExtendInfo.getQq());
			cmUserInfoDto.setSignature(HtmlUtils.htmlUnescape(cmUserExtendInfo.getSignature()));
		}
		
		cmUserInfoDto.setIdentity(MD5.getMD5("xxiu"+cmUserInfo.getId()).substring(0, 8).toLowerCase());
		map.clear();
    	map.put("userId", cmUserInfo.getId() );
    	map.put("isAccusation", 0);//未举报
		map.put("status", 1);
		int circlePostCount = circlePostService.getCount(map).intValue();
		
		long t5 = System.currentTimeMillis();
//		log.error("t2-t1="+(t2-t1)+",t3-t2="+(t3-t2)+",t4-t3="+(t4-t3)+",t5-t1 sumTime="+(t5-t1));
		cmUserInfoDto.setCirclePostCount(circlePostCount);
		
		return cmUserInfoDto;
	}
	
	
	/**
	 * 获取验证码
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitMo", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject submitMo(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String mobile = request.getParameter("mobile");
	    	if(StringUtil.isEmpty(mobile))
	    	{
	    		return object;
	    	}
	    	String code = request.getParameter("code");
	    	if(StringUtil.isEmpty(code))
	    	{
	    		return object;
	    	}
	    	String oldCode = (String) CacheHelper.getInstance().get(mobile);
	    	if(StringUtil.isEmpty(oldCode))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10003);
				object.setDesc("验证码过期");
				return object;
	    	}
	    	if(!oldCode.split("\\|")[0].equals(code))
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10004);
				object.setDesc("验证码错误");
				return object;
	    	}
	    	CouponMobile cm = new CouponMobile();
	    	cm.setMobile(mobile);
	    	couponMobileService.save(cm);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	
	/**
	 * 获取收货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUserReceive", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getUserReceive(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("userId", userId);
	    	List<CmUserReceive> cmUserReceiveList = cmUserReceiveService.findByMap(map);
	    	object.setResponse(cmUserReceiveList);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 添加收货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addUserReceive", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addUserReceive(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	CmUserReceive cmUserReceive = new CmUserReceive();
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}else{
	    		cmUserReceive.setUserId(userId);
	    	}
	    	String name = request.getParameter("name");
	    	if(StringUtil.isNotEmpty(name))
	    	{
	    		cmUserReceive.setName(name);
	    	}
	    	String reAddress = request.getParameter("reAddress");
	    	if(StringUtil.isNotEmpty(reAddress))
	    	{
	    		cmUserReceive.setReAddress(reAddress);
	    	}
	    	String rePhone = request.getParameter("rePhone");
	    	if(StringUtil.isNotEmpty(rePhone))
	    	{
	    		cmUserReceive.setRePhone(rePhone);
	    	}
	    	String rePostCode = request.getParameter("rePostCode");
	    	if(StringUtil.isNotEmpty(rePostCode))
	    	{
	    		cmUserReceive.setRePostCode(rePostCode);
	    	}
	    	String note = request.getParameter("note");
	    	if(StringUtil.isNotEmpty(note))
	    	{
	    		cmUserReceive.setNote(note);
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("userId", userId);
	    	int count =  cmUserReceiveService.getCount(map).intValue();
	    	if(StringUtil.isNotEmpty(reAddress))
	    	{
	    		map.put("reAddressAll", reAddress.trim());
	    	}else{
	    		map.put("reAddressAll", "");
	    	}
	    	int sameCount =  cmUserReceiveService.getCount(map).intValue();
	    	if(sameCount>0){
	    		 object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
	    		 object.setDesc("您已添加此收货地址");
	    		 return object;
	    	}
	    	if(count>0){
	    		cmUserReceive.setIsDefault(0);
	    	}else{
	    		cmUserReceive.setIsDefault(1);
	    	}
	    	int num = cmUserReceiveService.save(cmUserReceive);
	    	 if(num >0 ){
	    		 object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		 object.setDesc("成功");
	    	 }else{
	    		 object.setCode(Constant.RESPONSE_ERROR_CODE);
	    		 object.setDesc("服务器有点忙，请稍后再试");
	    	 }
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 修改收货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateUserReceive", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateUserReceive(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String id = request.getParameter("userReceiveId");
	    	if(StringUtil.isEmpty(id))
	    	{
	    		return object;
	    	}
	    	CmUserReceive cmUserReceive = cmUserReceiveService.getById(Long.parseLong(id));
	    	String name = request.getParameter("name");
	    	if(StringUtil.isNotEmpty(name))
	    	{
	    		cmUserReceive.setName(name);
	    	}
	    	String reAddress = request.getParameter("reAddress");
	    	if(StringUtil.isNotEmpty(reAddress))
	    	{
	    		cmUserReceive.setReAddress(reAddress);
	    	}
	    	String rePhone = request.getParameter("rePhone");
	    	if(StringUtil.isNotEmpty(rePhone))
	    	{
	    		cmUserReceive.setRePhone(rePhone);
	    	}
	    	String rePostCode = request.getParameter("rePostCode");
	    	if(StringUtil.isNotEmpty(rePostCode))
	    	{
	    		cmUserReceive.setRePostCode(rePostCode);
	    	}
	    	String note = request.getParameter("note");
	    	if(StringUtil.isNotEmpty(note))
	    	{
	    		cmUserReceive.setNote(note);
	    	}
	    	String isDefault = request.getParameter("isDefault");
	    	if(StringUtil.isNotEmpty(isDefault))
	    	{
	    		cmUserReceive.setIsDefault(Integer.valueOf(isDefault));
	    	}
	    	 int num = cmUserReceiveService.update(cmUserReceive);
	    	 if(num >0 ){
	    		 object.setResponse(cmUserReceive);
	    		 object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		 object.setDesc("成功");
	    	 }else{
	    		 object.setCode(Constant.RESPONSE_ERROR_CODE);
	    		 object.setDesc("服务器有点忙，请稍后再试");
	    	 }
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 删除收货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delUserReceive", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delUserReceive(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userReceiveId = request.getParameter("userReceiveId");
	    	if(StringUtil.isEmpty(userReceiveId))
	    	{
	    		return object;
	    	}
	    	cmUserReceiveService.delete(userReceiveId);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 设置为默认收货信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDefaultUserReceive", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject setDefaultUserReceive(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userReceiveId = request.getParameter("userReceiveId");
	    	if(StringUtil.isEmpty(userReceiveId))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	CmUserReceive cmUserReceive = cmUserReceiveService.setDefault(userId,userReceiveId);
	    	 if(cmUserReceive != null){
	    		 object.setResponse(cmUserReceive);
	    		 object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		 object.setDesc("成功");
	    	 }else{
	    		 object.setCode(Constant.RESPONSE_ERROR_CODE);
	    		 object.setDesc("服务器有点忙，请稍后再试");
	    	 }
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
}


