package cn.temobi.complex.action.newapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.service.CmTalenInfoService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

import com.alipay.util.AlipayNotify;
import com.salim.cache.CacheHelper;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ReturnData;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/orderNew")
public class OrderNewApiController extends ClientApiBaseController{
	
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="cmTalenInfoService")
	private CmTalenInfoService cmTalenInfoService;
	
	
	
	/**
	 * 玩首页 悬赏求p提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pricePSubmit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject pricePSubmit(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String price = request.getParameter("price");
	    	if(StringUtil.isEmpty(price))
	    	{
	    		return object;
	    	}
	    	String depict = request.getParameter("depict");
	    	if(StringUtil.isEmpty(depict))
	    	{
	    		return object;
	    	}
	    	String url = request.getParameter("url");
	    	if(StringUtil.isEmpty(url))
	    	{
	    		return object;
	    	}
	    	String thumbnail = request.getParameter("thumbnail");
	    	if(StringUtil.isEmpty(thumbnail))
	    	{
	    		return object;
	    	}	 
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	String labelId = request.getParameter("labelId");
	    	if(StringUtil.isNotEmpty(thumbnail))
	    	{
	    		passMap.put("labelId", labelId);
	    	}
			passMap.put("price", price);
			passMap.put("depict", depict);
			passMap.put("url", url);
			passMap.put("thumbnail", thumbnail);
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.pricePSubmit(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 玩首页 获取支付参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPayParameter", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getPayParameter(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String payType = request.getParameter("payType");
	    	if(StringUtil.isEmpty(payType))
	    	{
	    		return object;
	    	}
	    	String accountBuyId = request.getParameter("accountBuyId");
	    	if(StringUtil.isEmpty(accountBuyId))
	    	{
	    		return object;
	    	}
	    	
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("payType", payType);
			passMap.put("accountBuyId", accountBuyId);
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.getPayParameter(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 玩首页 确认悬赏
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/confirmP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject confirmP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String joinId = request.getParameter("joinId");
	    	if(StringUtil.isEmpty(joinId))
	    	{
	    		return object;
	    	}
	    	String psId = request.getParameter("psId");
	    	if(StringUtil.isEmpty(psId))
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("joinId", joinId);
			passMap.put("psId", psId);
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.confirmP(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 玩首页 私人订制提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/colouredDSubmit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject colouredDSubmit(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String depict = request.getParameter("depict");
	    	if(StringUtil.isEmpty(depict))
	    	{
	    		return object;
	    	}
	    	String url = request.getParameter("url");
	    	if(StringUtil.isEmpty(url))
	    	{
	    		return object;
	    	}
	    	String thumbnail = request.getParameter("thumbnail");
	    	if(StringUtil.isEmpty(thumbnail))
	    	{
	    		return object;
	    	}	 
	    	String contact = request.getParameter("contact");
	    	if(StringUtil.isEmpty(contact))
	    	{
	    		return object;
	    	}
	    	
	    	String qqContact = request.getParameter("qqContact");
	    	if(StringUtil.isEmpty(qqContact))
	    	{
	    		return object;
	    	}
	    	
	    	String phoneShell = request.getParameter("phoneShell");
	    	String receivePerson   = request.getParameter("receivePerson");
	    	String receiveAddress  = request.getParameter("receiveAddress");
	    	
	    	
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("type", type);
			passMap.put("depict", depict);
			passMap.put("url", url);
			passMap.put("thumbnail", thumbnail);
			passMap.put("contact", contact);
			passMap.put("qqContact", qqContact);
			
			if(StringUtil.isNotEmpty(phoneShell))
	    	{
				passMap.put("phoneShell", phoneShell);
	    	}
	    	if(StringUtil.isNotEmpty(receivePerson  ))
	    	{
	    		passMap.put("receivePerson", receivePerson);
	    	}
	    	
	    	if(StringUtil.isNotEmpty(receiveAddress))
	    	{
	    		passMap.put("receiveAddress", receiveAddress);
	    	}
	    	
	    	getDefaultPara(request, passMap, null);
	    	return pmTopicJoinProductsService.colouredDSubmit(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/weixinNotify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void weixinNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream inputStream = request.getInputStream(); // 模拟接收请求xml
			String body = IOUtils.toString(inputStream, "UTF-8");
			log.error("微信支付回调接口" + body);
			if (StringUtil.isNotBlank(body)) {
				ReturnData returnData = (ReturnData) Util.getObjectFromXML(
						body, ReturnData.class);
				
				String orderNo =  returnData.getOut_trade_no();
				String code = "";
				if (returnData.getResult_code().equals("SUCCESS")) {
					code = "000000";
				} else{
					code = "100000";
				}
				Order order = orderService.callBack(orderNo, code);
				
				if (StringUtil.isNotEmpty(order) && returnData.getResult_code().equals("SUCCESS")) {
					try {
//						PushUtil.pullOneMessage("67", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("68", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("69", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("71", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("411", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("640", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
					
						orderService.executeFuc(order);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				} 
				
				
				
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/aliNotify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void aliNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取请求参数中所有的信息
			Map<String, String> reqParam = getAllRequestParam(request);
			log.error("支付宝支付回调接口" + reqParam.toString());
			boolean tempB = AlipayNotify.verify(reqParam);
			if(tempB && !reqParam.get("trade_status").equals("WAIT_BUYER_PAY"))
			{
				String orderNo =  reqParam.get("out_trade_no");
				String code = "";
				if (reqParam.get("trade_status").equals("TRADE_SUCCESS")) {
					code = "000000";
				} else{
					code = "100000";
				}
				Order order = orderService.callBack(orderNo, code);
				
				
				if (StringUtil.isNotEmpty(order) && reqParam.get("trade_status").equals("TRADE_SUCCESS")) {
					try {
//						PushUtil.pullOneMessage("67", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("68", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("69", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("71", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("411", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("640", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
					
						orderService.executeFuc(order);
						
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				} 
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		String data = "success";
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的订单详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orderDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject orderDetail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String accountBuyId = request.getParameter("accountBuyId");
	    	if(StringUtil.isEmpty(accountBuyId))
	    	{
	    		return object;
	    	}
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("accountBuyId", accountBuyId);
	    	getDefaultPara(request, passMap, null);
	    	return orderService.orderDetail(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 我的订单
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/orderList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject orderList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isNotEmpty(pageNo)){
	    		passMap.put("pageNo", pageNo);
	    	}
	    	
	    	getDefaultPara(request, passMap, null);
	    	return orderService.orderList(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 订单取消
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelOrder", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject cancelOrder(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String accountBuyId = request.getParameter("accountBuyId");
	    	if(StringUtil.isEmpty(accountBuyId))
	    	{
	    		return object;
	    	}
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("accountBuyId", accountBuyId);
	    	getDefaultPara(request, passMap, null);
	    	return orderService.cancelOrder(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 订单删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delOrder", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delOrder(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String accountBuyId = request.getParameter("accountBuyId");
	    	if(StringUtil.isEmpty(accountBuyId))
	    	{
	    		return object;
	    	}
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("accountBuyId", accountBuyId);
	    	getDefaultPara(request, passMap, null);
	    	return orderService.delOrder(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 获取请求参数中所有的信息
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
	
	
	/**
	 * 换一换  设计师和达人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/changeDesignAndTalen", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject changeDesignAndTalen(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	
	    	String count = request.getParameter("count");
	    	if(StringUtil.isEmpty(count)){
	    		return object;
	    	}
	    	int pageNum = Integer.valueOf(count);
	    	
	    	List<Long> userIdList = new ArrayList<Long>();
			if(userIdList == null || userIdList.size() <= 0){
				Map<String,Object> passMap = new HashMap<String, Object>();
				passMap.put("userType", 1);//设计师
				List<CmUserInfo> cmUserInfoList = cmUserInfoService.findByMap(passMap);
				passMap.clear();
				passMap.put("type", 1);//达人
				List<CmTalenInfo> cmTalenInfoList = cmTalenInfoService.findByMap(passMap);
				for (CmUserInfo cmUserInfo : cmUserInfoList) {
					userIdList.add(cmUserInfo.getId());
				}
				for (CmTalenInfo cmTalenInfo : cmTalenInfoList) {
					userIdList.add(cmTalenInfo.getUserId());
				}
			}
			
			List<Long> seaid = new ArrayList<Long>();
			List<CmUserInfoListDto> deAnddarenList = new ArrayList<CmUserInfoListDto>();
			if(userIdList.size() > 0){
				int userSize = userIdList.size();
				int totalPage = userSize/8;
				int startNum = 0;
				
				//换一换 每次取8个，最后一组不够8个，从头开始补足，
				while(totalPage <= pageNum){
					for (int i = 0; i < userSize; i++) {
						userIdList.add(userIdList.get(i));
					}
					totalPage = userIdList.size()/8;//新的总页数
					if(totalPage >= pageNum ){
						break;
					}
				}
				
				startNum = pageNum*8 ;
				
				for (int i = 0; i < userIdList.size(); i++) {
					if( i+startNum >= 8*(pageNum+1) ){
						break;
					}
					seaid.add(userIdList.get(i+startNum));
				}
				
				if(seaid != null && seaid.size() > 0){
					Map<String, Object> searchMap = new HashMap<String, Object>();
					searchMap.put("list", seaid);
					searchMap.put("ids", StringUtils.join(seaid.toArray(), ","));
					deAnddarenList = cmUserInfoService.findByList(searchMap);
				}
			}
			
			object.setResponse(deAnddarenList);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功！");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 *  一键邀请设计师和达人 P图悬赏
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/invitationDesignP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject invitationDesignP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String joinId = request.getParameter("joinId");
	    	if(StringUtil.isEmpty(joinId)){
	    		return object;
	    	}
	    	String inviUserIds = request.getParameter("inviUserIds");
	    	if(StringUtil.isEmpty(inviUserIds)){
	    		return object;
	    	}
	    	PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(Long.valueOf(joinId ));
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
	    	String content = "亲，"+cmUserInfo.getNickName()+"发布了一张悬赏，" +pmTopicJoinProducts.getPrice()+"元，邀请你来P！";
	    	String inviUserId[] = inviUserIds.split(",");
	    	for (int i = 0; i < inviUserId.length; i++) {
	    		PushUtil.pullOneMessage(inviUserId[i],content, "28", pmTopicJoinProducts.getId().toString(), "");
			}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功！");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/testNotify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody void testNotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			List<String> list = new ArrayList<String>();
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[CCB_DEBIT]]></bank_type><cash_fee><![CDATA[300]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[3qm4lzv63p094qsixudwfnsf8umncv0x]]></nonce_str><openid><![CDATA[oOl5Ts9Rmd9wIew9y4KFdSR8N-Fw]]></openid><out_trade_no><![CDATA[65544050067052861047763519672806]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[5E983E349DF060DA3E4B0BE78960C3F4]]></sign><time_end><![CDATA[20160304175009]]></time_end><total_fee>300</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1002770242201603043722214405]]></transaction_id></xml>");
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[ABC_DEBIT]]></bank_type><cash_fee><![CDATA[500]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[pext3se679ilapf16fu29x62oxhlop11]]></nonce_str><openid><![CDATA[oOl5Ts7VOArmbXKqE7tCTvjmPXd8]]></openid><out_trade_no><![CDATA[30413102917226904521911567087103]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[91E8A9B3195AF9ADEAB294DF08F97B9E]]></sign><time_end><![CDATA[20160304175327]]></time_end><total_fee>500</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1009010242201603043722257844]]></transaction_id></xml>");
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[300]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[ca574wj6qnb5b05xf6n8ij8nmgts7ox4]]></nonce_str><openid><![CDATA[oOl5TszVMuo4WD3L4CRslqFfN-W0]]></openid><out_trade_no><![CDATA[48936194006225539097477088566385]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[093395C2EB322D860B372D52D22A4155]]></sign><time_end><![CDATA[20160304175344]]></time_end><total_fee>300</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1006380242201603043722552862]]></transaction_id></xml>");
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[100]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[osipc12nq3gddm6x7zt61cbhl77zvk2b]]></nonce_str><openid><![CDATA[oOl5Ts4PREdJhxH2Vi0yKZxsJA2g]]></openid><out_trade_no><![CDATA[66101677427112186905604160751126]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[D62FC70616F42FAB96E496132A35DCE8]]></sign><time_end><![CDATA[20160304175541]]></time_end><total_fee>100</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1000300242201603043722290108]]></transaction_id></xml>");
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[300]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[qddqy1f237b97rydkalh0oghowkq7418]]></nonce_str><openid><![CDATA[oOl5TsztUv--xQtf8DJCXEsWPv6k]]></openid><out_trade_no><![CDATA[02011910430633105510672594277477]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[D8B76849E1BB295B684ADDCF52DDF427]]></sign><time_end><![CDATA[20160304180138]]></time_end><total_fee>300</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1006880242201603043722655553]]></transaction_id></xml>");
			list.add("<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[PSBC_DEBIT]]></bank_type><cash_fee><![CDATA[300]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[tog9k5lsu9rtine7ytr06a3526izvnuk]]></nonce_str><openid><![CDATA[oOl5TsxULNu-Mzbobx3ishiYmwtY]]></openid><out_trade_no><![CDATA[75060352536246385368735050935630]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[084D0933D52ADA4D4CAB1086F327D25B]]></sign><time_end><![CDATA[20160304180359]]></time_end><total_fee>300</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1009770242201603043724407246]]></transaction_id></xml></xml>");
			for(String body:list){
				ReturnData returnData = (ReturnData) Util.getObjectFromXML(
						body, ReturnData.class);
				
				String orderNo =  returnData.getOut_trade_no();
				String code = "";
				if (returnData.getResult_code().equals("SUCCESS")) {
					code = "000000";
				} else{
					code = "100000";
				}
				Order order = orderService.callBack(orderNo, code);
				
				if (StringUtil.isNotEmpty(order) && returnData.getResult_code().equals("SUCCESS")) {
					try {
//						PushUtil.pullOneMessage("67", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("68", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("69", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("71", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("411", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
//						PushUtil.pullOneMessage("640", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
					
						orderService.executeFuc(order);
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e.getMessage());
					}
				} 
			}
				
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		try {
			response.getWriter().write(data);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String body = "<xml><appid><![CDATA[wx08b1f747c2330a96]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[50]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1248129801]]></mch_id><nonce_str><![CDATA[z3g5239otlphntdev96y5p0bgd0kp089]]></nonce_str><openid><![CDATA[oOl5TswT31NVUweT6rwrzwT63Kyc]]></openid><out_trade_no><![CDATA[87358812066406782101024712598605]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[197AA4978340321B6EFDF2E68BDBC460]]></sign><time_end><![CDATA[20160112122520]]></time_end><total_fee>50</total_fee><trade_type><![CDATA[APP]]></trade_type><transaction_id><![CDATA[1002660242201601122653876859]]></transaction_id></xml>";
		ReturnData returnData = (ReturnData) Util.getObjectFromXML(
				body, ReturnData.class);
		
		String orderNo =  returnData.getOut_trade_no();
		String code = "";
		if (returnData.getResult_code().equals("SUCCESS")) {
			code = "000000";
		} else{
			code = "100000";
		}
		System.out.println(code);
	}
}


