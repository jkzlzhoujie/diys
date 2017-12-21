package cn.temobi.complex.action.newapi;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.complex.service.AccountRedPacketLogService;
import cn.temobi.complex.service.AccountRedPacketService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.AccountWithdrawService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/userAccount")
public class UserAccountApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="accountUserService")
	private AccountUserService accountUserService;
	
	@Resource(name="accountWalletLogService")
	private AccountWalletLogService accountWalletLogService;
	
	@Resource(name="accountRedPacketLogService")
	private AccountRedPacketLogService accountRedPacketLogService;
	
	@Resource(name="accountWithdrawService")
	private AccountWithdrawService accountWithdrawService;
	
	@Resource(name="accountRedPacketService")
	private AccountRedPacketService accountRedPacketService;
	
	/**
	 * 钱包
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myWallet", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myWallet(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, ApiNameEnum.createAcount.getCode());
	    	return accountUserService.myWallet(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 查询余额
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryBalance", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject queryBalance(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap,null);
	    	return accountUserService.queryBalance(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 充值
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/recharge", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject recharge(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String payType = request.getParameter("payType");
	    	if(StringUtil.isEmpty(payType))
	    	{
	    		return object;
	    	}
	    	String price = request.getParameter("price");
	    	if(StringUtil.isEmpty(price))
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();	
	    	passMap.put("price", price);
	    	passMap.put("payType", payType);
	    	
	    	getDefaultPara(request, passMap, ApiNameEnum.recharge.getCode());
	    	return accountWalletLogService.recharge(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 提现
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/withdraw", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject withdraw(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String payType = request.getParameter("payType");
	    	if(StringUtil.isEmpty(payType))
	    	{
	    		return object;
	    	}
	    	String price = request.getParameter("price");
	    	if(StringUtil.isEmpty(price))
	    	{
	    		return object;
	    	}
	    	String account = request.getParameter("account");
//	    	if(StringUtil.isEmpty(account))
//	    	{
//	    		return object;
//	    	}
	    	
	    	String msgCode = request.getParameter("msgCode");
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	passMap.put("payType", payType);
	    	passMap.put("price", price);
	    	passMap.put("account", account);
	    	passMap.put("msgCode", msgCode);
	    	getDefaultPara(request, passMap, ApiNameEnum.withdraw.getCode());
	    	return accountWithdrawService.withdraw(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 钱包流水
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myWalletLog", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myWalletLog(HttpServletRequest request) {
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
			getDefaultPara(request, passMap,null);
	    	return accountWalletLogService.myWalletLog(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 红包
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myRed", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myRed(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap,ApiNameEnum.createAcount.getCode());
	    	return accountUserService.myRed(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 红包流水
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myRedLog", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myRedLog(HttpServletRequest request) {
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
	    	return accountRedPacketLogService.myRedLog(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 签到或者获取红包
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRedorSign", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getRedorSign(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
			getDefaultPara(request, passMap,null);
	    	return accountRedPacketService.getRedorSign(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 领取红包
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/receiveRed", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject receiveRed(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
			getDefaultPara(request, passMap,null);
	    	return accountRedPacketService.receiveRed(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 签到
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userSign", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject userSign(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
			getDefaultPara(request, passMap,null);
	    	return accountRedPacketService.userSign(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 用户上一次提现记录
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lastAccount", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject lastAccount(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	return accountWithdrawService.lastAccount(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
}


