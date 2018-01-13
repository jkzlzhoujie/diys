package cn.temobi.complex.action.api;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.formula.ptg.IntPtg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodity;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.complex.service.PmCommodityService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.IpUtil;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

import com.alipay.config.AlipayConfig;
import com.alipay.sign.RSA;
import com.alipay.util.AlipayCore;
import com.alipay.util.AlipayNotify;
import com.tencent.WXPay;
import com.tencent.common.Configure;
import com.tencent.common.RandomStringGenerator;
import com.tencent.common.Signature;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ReqData;
import com.tencent.protocol.pay_protocol.ResData;
import com.tencent.protocol.pay_protocol.ReturnData;

/**
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/order")
public class OrderApiController extends ClientApiBaseController {
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="pmCommodityService")
	private PmCommodityService pmCommodityService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmCommodityInfoService")
	private PmCommodityInfoService pmCommodityInfoService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getorderinfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ResponseObject getorderinfo(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("请升级新版本在购买");
		try {
			return object;
			
//			String userId = request.getParameter("userId");
//			if (StringUtil.isEmpty(userId)) {
//				return object;
//			}
//			String clientId = request.getParameter("clientId");
//			if (StringUtil.isEmpty(clientId)) {
//				return object;
//			}
//			String amount = request.getParameter("amount");
//			if (StringUtil.isEmpty(amount)) {
//				return object;
//			}
//			String type = request.getParameter("type");
//			if (StringUtil.isEmpty(type)) {
//				return object;
//			}
//			String commodityId = request.getParameter("commodityId");
//			if (StringUtil.isEmpty(type)) {
//				return object;
//			}
//			PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(commodityId));
//			if (StringUtil.isEmpty(pmCommodity)) {
//				return object;
//			}
//			String price = Double.parseDouble(amount) / 100+"";
//			if(!amount.equals(pmCommodity.getPrice()))
//			{
//				return object;
//			}
//				
//			String productDes = pmCommodity.getName();
//			String productDetail = pmCommodity.getDetail();
//			String productExt = request.getParameter("productExt");
//			String orderNo = RandomUtils.getRandomStr(1, 32, 1)[0];
//			ReqData reqData = new ReqData(productDes, productExt, orderNo,
//					Integer.parseInt(amount),
//					Constant.HOST_URL+"/diys/client/order/notify",
//					IpUtil.getIp(request), "APP", productDetail);
//			String responseString = WXPay.requestUnifiedorderService(reqData);
//			log.error(responseString);
//			if(StringUtil.isNotEmpty(responseString))
//			{
//				ResData resData = (ResData) Util.getObjectFromXML(responseString,
//						ResData.class);
//				//订单保存
//				Order order = new Order();
//				order.setAmount(price);
//				order.setClientId(Long.parseLong(clientId));
//				order.setOrderNo(orderNo);
//				order.setUserId(Long.parseLong(userId));
//				order.setType(type);
//				order.setPayType("1");
//				order.setStatus("0");
//				order.setCommodityId(Long.parseLong(commodityId));
//				orderService.save(order);
//				
//				Map<String, Object> returnMap = new HashMap<String, Object>();
//				returnMap.put("prepayid", resData.getPrepay_id());
//				returnMap.put("appid", Configure.getAppid());
//				returnMap.put("partnerid", Configure.getMchid());
//				returnMap.put("noncestr",
//						RandomStringGenerator.getRandomStringByLength(32));
//				returnMap.put(
//						"timestamp",
//						DateUtils.parse(DateUtils.getCurrDateTimeStr(),
//								DateUtils.YYYY_MM_DD_HH_MM_SS).getTime() / 1000);
//				returnMap.put("package", "Sign=WXPay");
//				returnMap.put("sign", Signature.getSign(returnMap));
//				returnMap.put("packageStr", "Sign=WXPay");
//				returnMap.put("orderNo", orderNo);
//				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
//		    	object.setDesc("成功");
//		    	object.setResponse(returnMap);
//			}else{
//				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
//    			object.setDesc("获取失败");
//			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		 return object;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/weixinNew", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ResponseObject weixinNew(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
//		try {
//			String userId = request.getParameter("userId");
//			if (StringUtil.isEmpty(userId)) {
//				return object;
//			}
//			String clientId = request.getParameter("clientId");
//			if (StringUtil.isEmpty(clientId)) {
//				return object;
//			}
//			String amount = request.getParameter("amount");
//			if (StringUtil.isEmpty(amount)) {
//				return object;
//			}
//			String type = request.getParameter("type");
//			if (StringUtil.isEmpty(type)) {
//				return object;
//			}
//			String commodityInfoId = request.getParameter("commodityInfoId");
//			if (StringUtil.isEmpty(commodityInfoId)) {
//				return object;
//			}
//			String orderNo = request.getParameter("orderNo");
//			if (StringUtil.isEmpty(orderNo)) {
//				return object;
//			}
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("orderNo", orderNo);
//			List<PmTopicJoinProducts> list = pmTopicJoinProductsService.findByMap(map);
//			PmTopicJoinProducts pmTopicJoinProducts;
//			if(list != null && list.size() > 0)
//			{
//				pmTopicJoinProducts = list.get(0);
//				if(!pmTopicJoinProducts.getJoinType().equals(commodityInfoId))
//				{
//					return object;
//				}
//				
//				if(!userId.equals(pmTopicJoinProducts.getUserId()+""))
//				{
//					return object;
//				}
//			}else{
//				return object;
//			}
//			List<Order> orderlist = orderService.findByMap(map);
//			Order order;
//			if(orderlist != null && orderlist.size() > 0)
//			{
//				order = orderlist.get(0);
//				if(!userId.equals(order.getUserId()+""))
//				{
//					return object;
//				}
//			}else{
//				return object;
//			}
//			
//			PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(commodityInfoId));
//			if (StringUtil.isEmpty(pmCommodityInfo)) {
//				return object;
//			}
//			String price = Double.parseDouble(amount) / 100+"";
//			if(!amount.equals(pmCommodityInfo.getPrice()))
//			{
//				return object;
//			}
//			PmCommodity pmCommodity	= pmCommodityService.getById(pmCommodityInfo.getCommodityId());
//			String productDes = pmCommodityInfo.getName();
//			String productDetail = pmCommodity.getDetail();
//			String productExt = request.getParameter("productExt");
////			ReqData reqData = new ReqData(productDes, productExt, orderNo,
////					Integer.parseInt(amount),
////					Constant.HOST_URL+"/diys/client/order/notify",
////					IpUtil.getIp(request), "APP", productDetail);
////			String responseString = WXPay.requestUnifiedorderService(reqData);
////			log.error(responseString);
////			
////			if(StringUtil.isNotEmpty(responseString))
////			{
////				ResData resData = (ResData) Util.getObjectFromXML(responseString,
////						ResData.class);
////				//订单保存
////				Order order = new Order();
////				order.setAmount(price);
////				order.setClientId(Long.parseLong(clientId));
////				order.setOrderNo(orderNo);
////				order.setUserId(Long.parseLong(userId));
////				order.setType(type);
////				order.setPayType("1");
////				order.setStatus("0");
////				order.setCommodityId(pmCommodity.getId());
////				order.setCommodityInfoId(pmCommodityInfo.getId());
////				order.setProductId(pmTopicJoinProducts.getProductId());
////				orderService.save(order);
//				order.setPayType("1");
//				orderService.update(order);
//				
//				Map<String, Object> returnMap = new HashMap<String, Object>();
//				returnMap.put("prepayid", resData.getPrepay_id());
//				returnMap.put("appid", Configure.getAppid());
//				returnMap.put("partnerid", Configure.getMchid());
//				returnMap.put("noncestr",
//						RandomStringGenerator.getRandomStringByLength(32));
//				returnMap.put(
//						"timestamp",
//						DateUtils.parse(DateUtils.getCurrDateTimeStr(),
//								DateUtils.YYYY_MM_DD_HH_MM_SS).getTime() / 1000);
//				returnMap.put("package", "Sign=WXPay");
//				returnMap.put("sign", Signature.getSign(returnMap));
//				returnMap.put("packageStr", "Sign=WXPay");
//				returnMap.put("orderNo", orderNo);
//				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
//		    	object.setDesc("成功");
//		    	object.setResponse(returnMap);
//			}else{
//				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
//    			object.setDesc("获取失败");
//			}
//			
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			object.setCode(Constant.RESPONSE_ERROR_CODE);
//			object.setDesc("服务器有点忙，请稍后再试");
//		}
		 return object;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/notify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void notify(HttpServletRequest request, HttpServletResponse response) {
		try {
			InputStream inputStream = request.getInputStream(); // 模拟接收请求xml
			String body = IOUtils.toString(inputStream, "UTF-8");
			log.error("微信支付回调接口" + body);
			if (StringUtil.isNotBlank(body)) {
				ReturnData returnData = (ReturnData) Util.getObjectFromXML(
						body, ReturnData.class);
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", returnData.getOut_trade_no());
				List<Order> list = orderService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					Order order = list.get(0);
					if (returnData.getResult_code().equals("SUCCESS")) {
						try {
							PushUtil.pullOneMessage("67", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("68", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("69", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("71", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("411", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("640", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						order.setStatus("1");
						
						List<PmTopicJoinProducts> jplist = pmTopicJoinProductsService.findByMap(map);
						if(jplist != null && jplist.size() > 0)
						{
							PmTopicJoinProducts pmTopicJoinProducts = jplist.get(0);
							pmTopicJoinProducts.setStatus("1");
							pmTopicJoinProductsService.update(pmTopicJoinProducts);
							
							CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
							pmScoreLogService.addScore("10", cmUserInfo, cmUserInfo.getClientId()+"", pmTopicJoinProducts.getProductId()+"",order.getAmount());
					    	cmUserInfoService.update(cmUserInfo);
						}
					} else if (returnData.getResult_code().equals("FAIL")) {
						order.setStatus("2");
					}
					orderService.update(order);
					
					
				}
			}
			
			String data = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
			response.getWriter().write(data);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alipayinfo", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody ResponseObject alipayinfo(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String userId = request.getParameter("userId");
			if (StringUtil.isEmpty(userId)) {
				return object;
			}
			String clientId = request.getParameter("clientId");
			if (StringUtil.isEmpty(clientId)) {
				return object;
			}
			String amount = request.getParameter("amount");
			if (StringUtil.isEmpty(amount)) {
				return object;
			}
			String type = request.getParameter("type");
			if (StringUtil.isEmpty(type)) {
				return object;
			}
			String commodityInfoId = request.getParameter("commodityInfoId");
			if (StringUtil.isEmpty(commodityInfoId)) {
				return object;
			}
			String orderNo = request.getParameter("orderNo");
			if (StringUtil.isEmpty(orderNo)) {
				return object;
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			List<PmTopicJoinProducts> list = pmTopicJoinProductsService.findByMap(map);
			PmTopicJoinProducts pmTopicJoinProducts;
			if(list != null && list.size() > 0)
			{
				pmTopicJoinProducts = list.get(0);
				if(!pmTopicJoinProducts.getJoinType().equals(commodityInfoId))
				{
					return object;
				}
				
				if(!userId.equals(pmTopicJoinProducts.getUserId()+""))
				{
					return object;
				}
			}else{
				return object;
			}
			
			List<Order> orderlist = orderService.findByMap(map);
			Order order;
			if(orderlist != null && orderlist.size() > 0)
			{
				order = orderlist.get(0);
				if(!userId.equals(order.getUserId()+""))
				{
					return object;
				}
			}else{
				return object;
			}
			
			PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(commodityInfoId));
			if (StringUtil.isEmpty(pmCommodityInfo)) {
				return object;
			}
			String price = Double.parseDouble(amount) / 100+"";
			if(!amount.equals(pmCommodityInfo.getPrice()))
			{
				return object;
			}
			PmCommodity pmCommodity	= pmCommodityService.getById(pmCommodityInfo.getCommodityId());
			String productDes = pmCommodityInfo.getName();
			String productDetail = pmCommodity.getDetail();
		
			
//			//订单保存
//			Order order = new Order();
//			order.setAmount(price);
//			order.setClientId(Long.parseLong(clientId));
//			order.setOrderNo(orderNo);
//			order.setUserId(Long.parseLong(userId));
//			order.setType(type);
//			order.setPayType("2");
//			order.setStatus("0");
//			order.setCommodityId(pmCommodity.getId());
//			order.setCommodityInfoId(pmCommodityInfo.getId());
//			order.setProductId(pmTopicJoinProducts.getProductId());
//			orderService.save(order);
			order.setPayType("2");
			orderService.update(order);
			
			Map<String, String> returnMap = new HashMap<String, String>();
			returnMap.put("service", AlipayConfig.service);
			returnMap.put("partner", AlipayConfig.partner);
			returnMap.put("_input_charset", AlipayConfig.input_charset);
			returnMap.put("notify_url", AlipayConfig.notify_url);
			returnMap.put("out_trade_no",orderNo);
			returnMap.put("subject",productDes);
			returnMap.put("payment_type","1");
			returnMap.put("seller_id",AlipayConfig.seller_id);
			returnMap.put("total_fee",price);
			returnMap.put("body",productDetail);
			returnMap.put("it_b_pay","30m");
			returnMap.put("return_url","m.alipay.com");
			Map<String, String> sParaNew = AlipayCore.paraFilter(returnMap);
	        String preSignStr = AlipayCore.getOrderInfo(sParaNew);
	        String sign = RSA.sign(preSignStr, AlipayConfig.private_key, AlipayConfig.input_charset);
			try {
				// 仅需对sign 做URL编码
				sign = URLEncoder.encode(sign, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
	        returnMap.put("sign", sign);
			returnMap.put("sign_type", AlipayConfig.sign_type);
			returnMap.put("orderNo", orderNo);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
	    	object.setResponse(returnMap);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		 return object;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/alipaynotify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody
	void alipaynotify(HttpServletRequest request, HttpServletResponse response) {
		try {
			// 获取请求参数中所有的信息
			Map<String, String> reqParam = getAllRequestParam(request);
			log.error("支付宝支付回调接口" + reqParam.toString());
			boolean tempB = AlipayNotify.verify(reqParam);
			if(tempB)
			{
				Map<String, String> map = new HashMap<String, String>();
				map.put("orderNo", reqParam.get("out_trade_no"));
				List<Order> list = orderService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					Order order = list.get(0);
					if (reqParam.get("trade_status").equals("TRADE_SUCCESS")) {
						try {
							PushUtil.pullOneMessage("67", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("68", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("69", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("71", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("411", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
							PushUtil.pullOneMessage("640", "亲，有一个新的订单支付成功，请及时处理！", "14", "", "");
						} catch (Exception e) {
							e.printStackTrace();
						}
						
						order.setStatus("1");
						orderService.update(order);
						
						List<PmTopicJoinProducts> jplist = pmTopicJoinProductsService.findByMap(map);
						if(jplist != null && jplist.size() > 0)
						{
							PmTopicJoinProducts pmTopicJoinProducts = jplist.get(0);
							pmTopicJoinProducts.setStatus("1");
							pmTopicJoinProductsService.update(pmTopicJoinProducts);
						
							CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
							pmScoreLogService.addScore("10", cmUserInfo, cmUserInfo.getClientId()+"", pmTopicJoinProducts.getProductId()+"",order.getAmount());
					    	cmUserInfoService.update(cmUserInfo);
						}
					}
				}
			}
			String data = "success";
			response.getWriter().write(data);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
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

	public static void main(String[] args) {
		System.out.println((int)Double.parseDouble("60.00"));
	}
}
