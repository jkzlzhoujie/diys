package cn.temobi.complex.action.newapi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.complex.service.AccountRedPacketLogService;
import cn.temobi.complex.service.AccountRedPacketService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.AccountWithdrawService;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/content")
public class ContentApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	@Resource(name="cmCircleServive")
	private CmCircleServive cmCircleServive;
	
	
	/**
	 * 玩首页 广告列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bannerIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bannerIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String columnType = request.getParameter("columnType");
	    	String system = request.getParameter("system");
	    	
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("type", type);
			passMap.put("columnType", columnType);
			passMap.put("system", system);
	    	getDefaultPara(request, passMap, null);
	    	String osVersion = CommonUtil.nvl(request.getParameter("osVersion"));
	    	passMap.put("osVersion", osVersion);
	    	return bannerService.bannerIndex(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * banner 点击接口
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/bannerClick", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bannerClick(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String applicationId = request.getParameter("applicationId");
	    	if(StringUtil.isEmpty(applicationId))
	    	{
	    		return object;
	    	}
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("applicationId", applicationId);
	    	getDefaultPara(request, passMap, null);
	    	return bannerService.bannerClick(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 玩首页 悬赏求P模块
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/demandPIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject demandPIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "8";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
			passMap.put("orderFried", "price");
			passMap.put("sequence", "desc");
			passMap.put("type", "index");
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.allDemandP(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 更多P图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allDemandP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject allDemandP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String orderFried = request.getParameter("orderFried");
	    	String isFefresh = request.getParameter("isFefresh");
	    	
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "6";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
			
			if(StringUtil.isNotEmpty(orderFried)){
				String checkType = request.getParameter("orderFried");
				passMap.put("checkType", checkType.trim());
				
				if("price".equals(orderFried)){
					passMap.put("orderFried", "price");
					passMap.put("sequence", "desc");
				}else if("priceAsc".equals(orderFried)){
					passMap.put("orderFried", "price");
					passMap.put("sequence", "asc");
					passMap.put("checkType", "priceAsc");
				}else if("hot".equals(orderFried)){
					passMap.put("orderFried", "join_products");
					passMap.put("sequence", "desc");
				}else{
					passMap.put("orderFried", "join_time");
					passMap.put("sequence", "desc");
					passMap.put("checkType", "time");
				}
			}else{
				passMap.put("checkType", "time");
			}
			
			if(StringUtil.isNotEmpty(isFefresh)){
				passMap.put("isFefresh", isFefresh.trim());
			}else{
				passMap.put("isFefresh", "0");
			}
			
			passMap.put("type", "all");
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.allDemandP(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 筛选P图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/findDemandP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject findDemandP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "6";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
			
			String seContent = request.getParameter("seContent");
	    	String label = request.getParameter("label");
	    	String timeCount = request.getParameter("timeCount");
	    	String startPrice = request.getParameter("startPrice");
	    	String endPrice = request.getParameter("endPrice");
	    	String isInvitation = request.getParameter("isInvitation");
	    	String isGet  = request.getParameter("isGet");
	    	
	    	if(StringUtil.isNotEmpty(seContent)){
	    		passMap.put("seContent", seContent);
	    	}
			if(StringUtil.isNotEmpty(label)){
				passMap.put("label", label);
			}
			if(StringUtil.isNotEmpty(timeCount)){
				passMap.put("timeCount", timeCount);
			}
			if(StringUtil.isNotEmpty(startPrice)){
				passMap.put("startPrice", startPrice);	
			}
			if(StringUtil.isNotEmpty(endPrice)){
				passMap.put("endPrice", endPrice);
			}
			if(StringUtil.isNotEmpty(isInvitation)){
				passMap.put("isInvitation", isInvitation);
			}
			if(StringUtil.isNotEmpty(isGet)){
				passMap.put("isGet", isGet);
			}
			
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.findDemandP(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	/**
	 * 玩首页 私人订制模块
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/colouredIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject colouredIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String version = request.getParameter("version");
			Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	return pmProductInfoService.colouredIndex(passMap, object , version);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 玩首页 兴趣圈模块
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/circleIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject circleIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
			passMap.put("type", "index");
	    	getDefaultPara(request, passMap, null);
	    	return cmCircleServive.circleIndex(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 玩首页 更多兴趣圈
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allCircle", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject allCircle(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
	    	getDefaultPara(request, passMap, null);
	    	return cmCircleServive.circleIndex(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 玩首页 大画家模块
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/drawIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject drawIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("pageSize", pageSize);
			passMap.put("pageNo", pageNo);
	    	getDefaultPara(request, passMap, null);
	    	return pmProductInfoService.drawIndex(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
}


