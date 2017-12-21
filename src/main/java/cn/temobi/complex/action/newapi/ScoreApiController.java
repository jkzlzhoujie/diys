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
import cn.temobi.complex.service.SysScoreService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/score")
public class ScoreApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysScoreService")
	private SysScoreService sysScoreService;
	
	
	/**
	 * 一次性任务列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/taskDisposableList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject taskList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	return sysScoreService.taskDisposableList(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 每日任务列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/taskDayList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject taskDayList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	return sysScoreService.taskDayList(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
}


