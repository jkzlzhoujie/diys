package cn.temobi.complex.action.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dto.ClientVersionDto;
import cn.temobi.complex.entity.ClientSp;
import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Feedback;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.service.ClientSpService;
import cn.temobi.complex.service.ClientVersionService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.FeedbackService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 客户端管理接口
 * @author hjf
 * 2014 五月 14 18:13:31
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/mg")
public class ClientManageApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="clientVersionService")
	private ClientVersionService clientVersionService;
	
	@Resource(name="clientSpService")
	private ClientSpService clientSpService;
	
	@Resource(name="feedbackService")
	private FeedbackService feedbackService;
	
	@Resource(name="systemSetingService")
	private SystemSetingService systemSetingService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String code = request.getParameter("code");
	    	String channel = request.getParameter("channel");
	    	if(StringUtil.isNotEmpty(code) && StringUtil.isNotEmpty(channel)) {
	    		Map<String, String> map = new HashMap<String, String>();
	    		
	    		Map<String,Object> changmap = new HashMap<String, Object>();
	    		changmap.put("enParamName", channel);
				List<SysConfigParam> list = sysConfigParamService.findByMap(changmap);
				if(list.size() > 0)
				{
					SysConfigParam sysConfigParam = list.get(0);
					if("2".equals(sysConfigParam.getStatus()))
					{
						object.setCode(Constant.RESPONSE_ERROR_10013);//没有更新
		    			object.setDesc("暂无新版本");
		    			return object;
					}
				}else{
					changmap.put("enParamName", "default");
					list = sysConfigParamService.findByMap(changmap);
					if(list.size() > 0)
					{
						SysConfigParam sysConfigParam = list.get(0);
						if("2".equals(sysConfigParam.getStatus()))
						{
							object.setCode(Constant.RESPONSE_ERROR_10013);//没有更新
			    			object.setDesc("暂无新版本");
			    			return object;
						}
					}
				}
				String os = request.getParameter("os");
				if(StringUtil.isEmpty(os)){
					map.put("os", "1");
				}else{
					map.put("os", os);
				}
	    		map.put("code", code);
	    		ClientVersion clientVersion = clientVersionService.getNewVersion(map);
	    		if(StringUtil.isNotEmpty(clientVersion)) {
	    			ClientVersionDto dto = new ClientVersionDto();
	    			dto.setDownUrl(host+clientVersion.getDownUrl());
	    			dto.setSize(clientVersion.getSize());
	    			dto.setIsForce(clientVersion.getIsForce());
	    			dto.setDesc(clientVersion.getDesc());
	    			dto.setVersionCode(clientVersion.getCode());
	    			dto.setName(clientVersion.getName());
	    			object.setResponse(dto);
	    			object.setCode(Constant.RESPONSE_SUCCESS_CODE);//有更新
	    			object.setDesc("成功");
	    		}else {
	    			object.setCode(Constant.RESPONSE_ERROR_10013);//没有更新
	    			object.setDesc("暂无新版本");
	    		}
	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/sp", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject startPage(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String code = request.getParameter("code");
	    	String channel = request.getParameter("channel");
	    	String os = request.getParameter("os");
	    	Map<String, String> map = new HashMap<String, String>();
	    	if(StringUtil.isEmpty(os))
	    	{
	    		map.put("type", "3");
	    	}else{
	    		map.put("type", os);
	    		map.put("currentDate", DateUtils.getCurrDateStr());
	    		map.put("versionCode", code);
	    		map.put("clientChannel", channel);
	    	}
    		
	    	List<ClientSp> list = clientSpService.findByMap(map);
	    	if(list!= null && list.size() > 0) {
    			object.setResponse(host+list.get(0).getImageUrl());
    			object.setCode(Constant.RESPONSE_SUCCESS_CODE);//有更新
    			object.setDesc("成功");
    		}else {
    			Map<String, String> twoMap = new HashMap<String, String>();
    			twoMap.put("type", os);
    			twoMap.put("versionCode", code);
    			twoMap.put("currentDate", DateUtils.getCurrDateStr());
    			twoMap.put("channel", "isNull");
    			List<ClientSp> twolist = clientSpService.findByMap(twoMap);
    			if(twolist!= null && twolist.size() > 0) {
        			object.setResponse(host+twolist.get(0).getImageUrl());
        			object.setCode(Constant.RESPONSE_SUCCESS_CODE);//有更新
        			object.setDesc("成功");
        		}else {
        			Map<String, String> deMap = new HashMap<String, String>();
        			deMap.put("type", "3");
        			List<ClientSp> delist = clientSpService.findByMap(deMap);
        			if(delist!= null && delist.size() > 0) {
            			object.setResponse(host+delist.get(0).getImageUrl());
            			object.setCode(Constant.RESPONSE_SUCCESS_CODE);//有更新
            			object.setDesc("成功");
            		}else {
            			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
            			object.setDesc("没有数据");
            		}
        		}
    		}
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 反馈
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/feedback", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject feedback(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String phone = request.getParameter("phone");
	    	String imei = request.getParameter("imei");
	    	String version = request.getParameter("version");
	    	String clientId = request.getParameter("clientId");
	    	String content = request.getParameter("content");
	    	String machine = request.getParameter("machine");
	    	String questionType = request.getParameter("questionType");
	    	String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(phone) && StringUtil.isNotEmpty(content)) {
				Map<String,Object> mapOneDay = new HashMap<String, Object>();
				mapOneDay.clear();
				mapOneDay.put("clientIdOfOneDay", clientId);
				mapOneDay.put("content", content);
				feedbackService.findByMap(mapOneDay);
				int numOneDay = feedbackService.findFeedbackCount(mapOneDay).intValue();
		    	if(numOneDay >= 1){
		    		object.setCode(Constant.RESPONSE_ERROR_10007);
		    		object.setDesc("一天内不允许发表多条相同的反馈");
		    		return object;
		    	}
				
				Feedback feedback = new Feedback();
				feedback.setClientId(Long.parseLong(clientId));
				feedback.setImei(imei);
				feedback.setVersion(version);
				feedback.setPhone(HtmlUtils.htmlEscape(phone));
				feedback.setQuestionType(questionType);
				feedback.setMachine(machine);
				feedback.setType("1");
				feedback.setContent(HtmlUtils.htmlEscape(content));
				Map<String,String> map = new HashMap<String, String>();
				map.put("clientId", clientId);
				map.put("userId", userId);
				feedbackService.saveAll(feedback, map);
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
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
	 * 为X秀点赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/zXxiu", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject zXxiu(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	String clientId = request.getParameter("clientId");
	    	String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(clientId) && StringUtil.isNotEmpty(userId)) {
				
				Map<String,String> map = new HashMap<String, String>();
				map.put("clientId", clientId);
				map.put("userId", userId);
				cmUserInfoService.zXiu(map);
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
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
	 * 反馈类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/fbType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject fbType(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			SystemSeting systemSeting = systemSetingService.getById(null);
			if(StringUtil.isNotEmpty(systemSeting.getFeedbackType()))
			{
				List<String> list = Arrays.asList(systemSeting.getFeedbackType().split(","));
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				object.setResponse(list);
			}else{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
    			object.setDesc("没有数据");
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		return object;
	}
	
}
