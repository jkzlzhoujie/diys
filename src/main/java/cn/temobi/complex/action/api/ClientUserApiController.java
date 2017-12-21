package cn.temobi.complex.action.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.ClientStartDto;
import cn.temobi.complex.dto.ClientUserDto;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.ClientLogin;
import cn.temobi.complex.entity.ClientUser;
import cn.temobi.complex.entity.Province;
import cn.temobi.complex.entity.StartStatistics;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.service.ClientLoginService;
import cn.temobi.complex.service.ClientService;
import cn.temobi.complex.service.ClientUserService;
import cn.temobi.complex.service.ProvinceService;
import cn.temobi.complex.service.StartStatisticsService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.IpUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 客户端用户管理接口
 * @author hjf
 * 2014 五月 7 15:04:43
 */
@SuppressWarnings("serial")
@Controller

@RequestMapping("/cu")
public class ClientUserApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="clientUserService")
	private ClientUserService clientUserService;
	
	@Resource(name="provinceService")
	private ProvinceService provinceService;
	
	@Resource(name="clientService")
	private ClientService clientService;
	
	@Resource(name="clientLoginService")
	private ClientLoginService clientLoginService;
	
	@Resource(name="startStatisticsService")
	private StartStatisticsService startStatisticsService;
	
	@Resource(name="systemSetingService")
	private SystemSetingService systemSetingService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	/**
	 * 客户端启动行为记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/start", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject startForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String imei = request.getParameter("imei");
	    	String imsi = request.getParameter("imsi");
	    	String machine = request.getParameter("machine");
	    	String os = request.getParameter("os");
	    	String osVersion = request.getParameter("osVersion");
	    	String systemVersion = request.getParameter("systemVersion");
	    	String channel = request.getParameter("channel");
	    	if(StringUtil.isEmpty(channel))
	    	{
	    		channel = "0";
	    	}
	    	if(StringUtil.isNotEmpty(imei)) {
	    		Map<String, String> map = new HashMap<String, String>();
	    		map.put("imei", imei);
	    		if(StringUtil.isNotEmpty(imsi)) 
	    		map.put("imsi", imsi);
	    		List<Client> list = clientService.findByMap(map);
    			Client client = new Client();
    			client.setImei(imei);
    			client.setImsi(imsi);
    			client.setMachine(machine);
    			client.setOs(os);
    			client.setAppVersion(systemVersion);
    			client.setOsVersion(osVersion);
    			client.setIp(IpUtil.getIp(request));
    			client.setChannel(channel);
    			
    			clientService.saveAll(list, client);
    			
	    		ClientStartDto dto = new ClientStartDto();
	    		dto.setClientId(list.get(0).getId());
	    		SystemSeting systemSeting = systemSetingService.getById(null);
	    		dto.setClassifyNum(systemSeting.getClassifyNum());
	    		dto.setDownUrl(systemSeting.getDownUrl());
	    		dto.setProductDesc(systemSeting.getProductDesc());
	    		dto.setShareContent(systemSeting.getShareContent());
	    		dto.setShareImage(host+systemSeting.getShareImage());
	    		dto.setShopUrl(systemSeting.getShopUrl());
	    		dto.setShareUrl(systemSeting.getShareUrl());
	    		dto.setThemeNum(systemSeting.getThemeNum());
	    		dto.setShareTitle(systemSeting.getShareTitle());
	    		
	    		map.put("type", "0");
	    		List<SysConfigParam> scpList = sysConfigParamService.findByMap(map);
	    		if(scpList != null && scpList.size() > 0)
	    		{
	    			for(SysConfigParam sysConfigParam:scpList)
	    			{
	    				if("videoUrl".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setVideoUrl(sysConfigParam.getParamValue());
	    				}else if("zContent".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setzContent(sysConfigParam.getParamValue());
	    				}else if("feebackContent".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setFeebackContent(sysConfigParam.getParamValue());
	    				}else if("shareNum".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setShareNum(sysConfigParam.getParamValue());
	    				}else if("godPUrl".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setGodPUrl(sysConfigParam.getParamValue());
	    				}else if("h5Url".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setH5Url(sysConfigParam.getParamValue());
	    				}else if("playUrl".equals(sysConfigParam.getEnParamName()))
	    				{
	    					dto.setPlayUrl(sysConfigParam.getParamValue());
	    				}
	    				
	    				if("0".equals(os))
	    				{
	    					if("iosDownUrl".equals(sysConfigParam.getEnParamName()))
		    				{
	    						dto.setDownUrl(sysConfigParam.getParamValue());
		    				}else if("iosShopUrl".equals(sysConfigParam.getEnParamName()))
		    				{
		    					dto.setShopUrl(sysConfigParam.getParamValue());
		    				}else if("iosShareUrl".equals(sysConfigParam.getEnParamName()))
		    				{
		    					dto.setShareUrl(sysConfigParam.getParamValue());
		    				}
	    				}
	    				
	    			}
	    		}
	    		Map<String,Object> changmap = new HashMap<String, Object>();
	    		changmap.put("enParamName", channel);
				List<SysConfigParam> changlist = sysConfigParamService.findByMap(changmap);
				dto.setFlag("1");
				if(changlist != null && changlist.size() > 0)
	    		{
	    			SysConfigParam sysConfigParam = changlist.get(0);
	    			dto.setFlag(sysConfigParam.getFlag());
	    			dto.setExpand1(sysConfigParam.getExpand1());
	    			dto.setExpand2(sysConfigParam.getExpand2());
	    			dto.setExpand3(sysConfigParam.getExpand3());
	    		}else{
	    			changmap.put("enParamName", "default");
					changlist = sysConfigParamService.findByMap(changmap);
					if(changlist != null && changlist.size() > 0)
		    		{
		    			SysConfigParam sysConfigParam = changlist.get(0);
		    			dto.setFlag(sysConfigParam.getFlag());
		    			dto.setExpand1(sysConfigParam.getExpand1());
		    			dto.setExpand2(sysConfigParam.getExpand2());
		    			dto.setExpand3(sysConfigParam.getExpand3());
		    		}
	    		}
				
	    		
	    		object.setResponse(dto);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
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
	 * 用户登陆行为记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/login", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject loginForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String clientId = request.getParameter("clientId");
	    	String channel = request.getParameter("channel");
	    	String version = request.getParameter("version");
	    	String netEnvironment = request.getParameter("netEnvironment");
	    	String token = request.getParameter("token");
	    	if(StringUtil.isNotEmpty(clientId) && !"0".equals(clientId)) {
				String phone = "";
	    		ClientUser clientUser = clientUserService.getByUsername(phone);
	    		if(StringUtil.isEmpty(clientUser)) {
	    			clientUser = new ClientUser();
	    			clientUser.setUsername(phone);
	    			clientUser.setChannel(channel);
	    			clientUser.setVersion(version);
	    			clientUser.setClientId(Long.valueOf(clientId));
	    			String segment = phone.substring(0,7);//截取前7位
					Province province = provinceService.getBySegment(segment);//查看号码段的省份
					if(StringUtil.isNotEmpty(province)) {
						clientUser.setArea(province.getProvinceName());
						clientUser.setCity( province.getCityName());
					}
					clientUser.setType("1");
	    			clientUserService.save(clientUser);
	    		}else {
	    			long clientId1 = clientUser.getClientId();
	    			long clientId2 = Long.valueOf(clientId);
	    			if(clientId1 != clientId2) {
	    				clientUser.setClientId(clientId2);
	    				clientUserService.update(clientUser);
	    			}
	    		}
	    		String remoteIp = getIpAddr(request);
	    		ClientLogin clientLogin = new ClientLogin();
	    		clientLogin.setChannel(channel);
	    		clientLogin.setClientId(Long.valueOf(clientId));
	    		clientLogin.setClientUserId(clientUser.getId());
	    		clientLogin.setNetEnvironment(netEnvironment);
	    		clientLogin.setRemoteIp(remoteIp);
	    		clientLogin.setVersion(version);
	    		clientLoginService.save(clientLogin);
	    		ClientUserDto dto = new ClientUserDto();
	    		dto.setLoginId(clientLogin.getId());
	    		dto.setPhone(phone);
	    		object.setResponse(dto);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("成功");
	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@RequestMapping(value = "/v1/exit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject exitForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String loginId = request.getParameter("loginId");
	    	if(StringUtil.isNotEmpty(loginId)) {
	    		ClientLogin clientLogin = new ClientLogin();
	    		clientLogin.setId(Long.valueOf(loginId));
	    		clientLogin.setDuration(DateUtils.getCurrDateTimeStr());
	    		clientLoginService.update(clientLogin);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("成功");
	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
}
