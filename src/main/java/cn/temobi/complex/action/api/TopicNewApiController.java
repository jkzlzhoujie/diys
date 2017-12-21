package cn.temobi.complex.action.api;

import java.util.ArrayList;
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
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dto.OrderDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodity;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysFlowNo;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.complex.service.PmCommodityService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmTopicInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.SysFlowNoService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/topicNew")
public class TopicNewApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="pmTopicInfoService")
	private PmTopicInfoService pmTopicInfoService;
	
	@Resource(name="pmCommodityInfoService")
	private PmCommodityInfoService pmCommodityInfoService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="sysFlowNoService")
	private SysFlowNoService sysFlowNoService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmCommodityService")
	private PmCommodityService pmCommodityService;
	
	/**
	 * 图片上传
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadImage", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject uploadImage(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
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
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}

	    	Map<String,String> map = FileUtil.uploadImage(request, response);
			if(StringUtil.isEmpty(map))
			{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("上传失败");
				return object;
			}
			
			object.setResponse(map);
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
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("userId", userId);
	    	map.put("statusTo", userId);
	    	List<Order> list = orderService.findByMap(map);
	    	List<OrderDto> relist = new ArrayList<OrderDto>();
	    	if(list != null && list.size() > 0)
	    	{
	    		OrderDto orderDto;
	    		for(Order order:list)
	    		{
	    			orderDto = new OrderDto();
	    			orderDto.setNum(1);
	    			orderDto.setFreight("0");
	    			orderDto.setType(order.getType().replaceAll("1", "X秀专属私人订制"));
	    			OrderDto commodityDto =pmCommodityInfoService.getDtoById(order.getCommodityInfoId());
	    			orderDto.setCommodityName(commodityDto.getCommodityName());
	    			orderDto.setCommodityType(commodityDto.getCommodityType());
	    			orderDto.setCommodityThumbnail(host+commodityDto.getCommodityThumbnail());
	    			orderDto.setOrderNo(order.getOrderNo());
	    			orderDto.setStatus(order.getStatus());
	    			orderDto.setCommodityId(order.getCommodityId());
	    			orderDto.setCommodityInfoId(order.getCommodityInfoId());
	    			orderDto.setAmount((int)(Double.parseDouble(order.getAmount())*100)+"");
	    			relist.add(orderDto);
	    		}
	    	}
	    	object.setResponse(relist);
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
	 * 新版私人订制提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/customNew", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject customNew(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
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
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String description = request.getParameter("description");
	    	String depict = request.getParameter("depict");
	    	if(StringUtil.isEmpty(description) && StringUtil.isEmpty(depict))
	    	{
	    		return object;
	    	}
	    	String contact = request.getParameter("contact");
	    	if(StringUtil.isEmpty(contact))
	    	{
	    		return object;
	    	}
	    	
	    	String qqContact = request.getParameter("qqContact");
	    	PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(type));
			if (StringUtil.isEmpty(pmCommodityInfo)) {
				return object;
			}

			String thumbnail = request.getParameter("thumbnail");
	    	if(StringUtil.isEmpty(thumbnail))
	    	{
	    		return object;
	    	}
	    	String url = request.getParameter("url");
	    	if(StringUtil.isEmpty(url))
	    	{
	    		return object;
	    	}
			PmProductInfo resource = new PmProductInfo();
			resource.setClientId(Long.parseLong(clientId));
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUrl(url);
			resource.setThumbnail(thumbnail);
			resource.setIsPublic(3);
			resource.setAudit(0);
			resource.setCreateFrom("1");
			resource.setUserId(Long.parseLong(userId));
			pmProductInfoService.save(resource);
			
			Map<String,Object> flowMap = new HashMap<String, Object>();
			flowMap.put("flowType", 1);
			String time = DateUtils.getCurrDateStr();
			flowMap.put("flowDate", time);
			List<SysFlowNo> list = sysFlowNoService.findByMap(flowMap);
			int a = 0;
			SysFlowNo sysFlowNo = null;
			if(list != null && list.size() > 0)
			{
				sysFlowNo = list.get(0);
				a = sysFlowNo.getFlowSeq()+1;
				sysFlowNo.setFlowSeq(a);
				sysFlowNo.setUpdateTime(DateUtils.getCurrDateTimeStr());
				sysFlowNoService.update(sysFlowNo);
			}else{
				a += 1;
				sysFlowNo = new SysFlowNo();
				sysFlowNo.setFlowDate(time);
				sysFlowNo.setFlowSeq(a);
				sysFlowNo.setFlowType(1);
				sysFlowNo.setUpdateTime(DateUtils.getCurrDateTimeStr());
				sysFlowNoService.save(sysFlowNo);
			}
			
			PmTopicJoinProducts pmTopicJoinProducts = new PmTopicJoinProducts();
			pmTopicJoinProducts.setContact(HtmlUtils.htmlEscape(contact));
			pmTopicJoinProducts.setQqContact(qqContact);
			if(StringUtil.isNotEmpty(description))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(description));
			}
			if(StringUtil.isNotEmpty(depict))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
			}

			Map<String,Object> map = new HashMap<String, Object>();
			map.put("topicType", "1");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicJoinProducts.setTopicId(topicList.get(0).getId());
			}
			pmTopicJoinProducts.setProductId(resource.getId());
			pmTopicJoinProducts.setUserId(Long.parseLong(userId));
			pmTopicJoinProducts.setJoinType(type);
			pmTopicJoinProducts.setDistributeNo(DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD)+String.format("%03d", a));
			pmTopicJoinProducts.setStatus("0");
			String orderNo = RandomUtils.getRandomStr(1, 32, 1)[0];
			pmTopicJoinProducts.setOrderNo(orderNo);
			pmTopicJoinProductsService.save(pmTopicJoinProducts);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			OrderDto orderDto = new OrderDto();
			orderDto.setNum(1);
			orderDto.setFreight("0");
			orderDto.setType("X秀专属私人订制");
			OrderDto commodityDto =pmCommodityInfoService.getDtoById(pmCommodityInfo.getId());
			orderDto.setCommodityName(commodityDto.getCommodityName());
			orderDto.setCommodityType(commodityDto.getCommodityType());
			orderDto.setCommodityThumbnail(host+commodityDto.getCommodityThumbnail());
			orderDto.setOrderNo(orderNo);
			orderDto.setStatus("0");
			orderDto.setCommodityId(pmCommodityInfo.getCommodityId());
			orderDto.setCommodityInfoId(pmCommodityInfo.getId());
			orderDto.setAmount(pmCommodityInfo.getPrice());
			
			Order order = new Order();
			order.setAmount(Double.parseDouble(pmCommodityInfo.getPrice()) / 100+"");
			order.setClientId(Long.parseLong(clientId));
			order.setOrderNo(orderNo);
			order.setUserId(Long.parseLong(userId));
			order.setType("1");
			order.setStatus("0");
			order.setCommodityId(pmCommodityInfo.getCommodityId());
			order.setCommodityInfoId(pmCommodityInfo.getId());
			order.setProductId(pmTopicJoinProducts.getProductId());
			orderService.save(order);
			object.setResponse(orderDto);
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("你输入的内容含有敏感词");
		}
		return object;
	}
	
	
	/**
	 * 私人订制商品详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commodityDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject commodityDetail(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String pmCommodityId = request.getParameter("pmCommodityId");
	    	if(StringUtil.isEmpty(pmCommodityId))
	    	{
	    		return object;
	    	}
	    	PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(pmCommodityId));
	    	if(StringUtil.isEmpty(pmCommodity))
	    	{
	    		return object;
	    	}
	    	pmCommodity.setThumbnail(host+pmCommodity.getThumbnail());
			if(StringUtil.isNotEmpty(pmCommodity.getUrl()))
			{
				String arr[] = pmCommodity.getUrl().split(",");
				String temp = "";
				for(String a:arr)
				{
					temp+= host+a+",";
				}
				pmCommodity.setUrl(temp.substring(0, temp.length()-1));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("commodityId", pmCommodity.getId());
			List<PmCommodityInfo> infoList = pmCommodityInfoService.findByMap(map);
			pmCommodity.setList(infoList);
	    
	    	object.setResponse(pmCommodity);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("你输入的内容含有敏感词");
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
	    	String orderNo = request.getParameter("orderNo");
	    	if(StringUtil.isEmpty(orderNo))
	    	{
	    		return object;
	    	}
	    	Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			List<Order> list = orderService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				Order order = list.get(0);
				if(!userId.equals(order.getUserId()+""))
				{
					return object;
				}
				List<PmTopicJoinProducts> jplist = pmTopicJoinProductsService.findByMap(map);
				if(jplist != null && jplist.size() > 0)
				{
					PmTopicJoinProducts pmTopicJoinProducts = jplist.get(0);
					pmTopicJoinProducts.setStatus("3");
					pmTopicJoinProductsService.update(pmTopicJoinProducts);
				}else{
					return object;
				}
				order.setStatus("3");
				orderService.update(order);
				
			}else{
				return object;
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
	    	String orderNo = request.getParameter("orderNo");
	    	if(StringUtil.isEmpty(orderNo))
	    	{
	    		return object;
	    	}
	    	Map<String, String> map = new HashMap<String, String>();
			map.put("orderNo", orderNo);
			List<Order> list = orderService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				Order order = list.get(0);
				if(!userId.equals(order.getUserId()+""))
				{
					return object;
				}
				List<PmTopicJoinProducts> jplist = pmTopicJoinProductsService.findByMap(map);
				if(jplist != null && jplist.size() > 0)
				{
					PmTopicJoinProducts pmTopicJoinProducts = jplist.get(0);
					pmTopicJoinProducts.setStatus("4");
					pmTopicJoinProductsService.update(pmTopicJoinProducts);
				}else{
					return object;
				}
				order.setStatus("4");
				orderService.update(order);
				
			}else{
				return object;
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
}
