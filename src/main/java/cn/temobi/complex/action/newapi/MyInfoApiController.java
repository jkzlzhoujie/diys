package cn.temobi.complex.action.newapi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/myInfo")
public class MyInfoApiController extends ClientApiBaseController{
	
	
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	
	
	/**
	 * 我的悬赏求P
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "3";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.myP(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 我的P图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pOther", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject pOther(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "3";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.pOther(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
}


