package cn.temobi.complex.action.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.entity.SysAdvert;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.complex.schedule.RankingJob;
import cn.temobi.complex.service.SysAdvertService;
import cn.temobi.complex.service.SysColumnService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/sys")
public class SysApiController extends ClientApiBaseController{
	
	String host1 = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url1"); 
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	@Resource(name="sysAdvertService")
	private SysAdvertService sysAdvertService;
	
	@Resource(name="sysColumnService")
	private SysColumnService sysColumnService;
	
	
	/**
	 * 广告
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/advert", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject advert(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("status", "0");
	    	List<SysAdvert> list = sysAdvertService.findByMap(map);
	    	if(list != null && list.size() > 0)
	    	{
	    		for(SysAdvert sysAdvert:list){
	    			sysAdvert.setLogo(host+sysAdvert.getLogo());
	    			sysAdvert.setDepict(html+sysAdvert.getDepict()+html2);
	    			if("1".equals(sysAdvert.getType()))
	    			{
	    				sysAdvert.setType("2");
		    			sysAdvert.setUrl(host1+"/diys/client/v42/hdIndex?name=hdIndex"+sysAdvert.getId());
		    			sysAdvert.setDepict("");
	    			}
	    		}
	    	}
			object.setResponse(list);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 栏目
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/column", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject column(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String osVersion = request.getParameter("osVersion");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("isOnline", "1");//未下线
	    	List<SysColumn> list = sysColumnService.findByMap(map);
	    	//修复 6.01版本bug
	    	if(StringUtil.isNotEmpty(osVersion)){
	    		if(Integer.valueOf(osVersion) == 38 && list.size() > 7  ){
	    			for (int i = 1; i <= list.size(); i++) {
						if(i>7){
							list.remove(1);
						}
					}
	    		}
	    	}
			object.setResponse(list);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
}
