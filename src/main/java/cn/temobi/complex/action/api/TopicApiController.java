package cn.temobi.complex.action.api;

import java.util.ArrayList;
import java.util.Collections;
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

import cn.temobi.complex.dao.PmProductLabelDao;
import cn.temobi.complex.dto.CmUserInfoOtherDto;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.PmTopicInfoDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.entity.CmBusiScope;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserCollect;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserImageIntroduce;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodity;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductLabel;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicCase;
import cn.temobi.complex.entity.PmTopicInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.QqList;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysFlowNo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.complex.service.CmBusiScopeService;
import cn.temobi.complex.service.CmUserCollectService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserImageIntroduceService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.complex.service.PmCommodityService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.PmTopicCaseService;
import cn.temobi.complex.service.PmTopicInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.PmTopicPsProductsService;
import cn.temobi.complex.service.QqListService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysFlowNoService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.complex.service.SysProductTypeInfoService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

import com.salim.cache.CacheHelper;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/topic")
public class TopicApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="pmTopicInfoService")
	private PmTopicInfoService pmTopicInfoService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmTopicCaseService")
	private PmTopicCaseService pmTopicCaseService;
	
	@Resource(name="sysProductTypeInfoService")
	public SysProductTypeInfoService sysProductTypeInfoService;
	
	@Resource(name="cmUserExtendInfoService")
	public CmUserExtendInfoService cmUserExtendInfoService;
	
	@Resource(name="cmUserImageIntroduceService")
	public CmUserImageIntroduceService cmUserImageIntroduceService;
	
	@Resource(name="cmBusiScopeService")
	public CmBusiScopeService cmBusiScopeService;
	
	@Resource(name="pmTopicPsProductsService")
	public PmTopicPsProductsService pmTopicPsProductsService;
	
	@Resource(name="pmProductPraisesService")
	public PmProductPraisesService pmProductPraisesService;
	
	@Resource(name="sysConfigParamService")
	public SysConfigParamService sysConfigParamService;
	
	@Resource(name="sysFlowNoService")
	public SysFlowNoService sysFlowNoService;
	
	@Resource(name="pmCommodityService")
	public PmCommodityService pmCommodityService;
	
	@Resource(name="pmCommodityInfoService")
	public PmCommodityInfoService pmCommodityInfoService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="pmProductLabelDao")
	private PmProductLabelDao pmProductLabelDao;
	
	@Resource(name="cmUserCollectService")
	private CmUserCollectService cmUserCollectService;
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="qqListService")
	private QqListService qqListService;
	
	
	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/topicIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject index(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String clientId = request.getParameter("clientId");
	    	List<PmTopicInfo> list = pmTopicInfoService.findAll();
	    	List<PmTopicInfoDto> returnlist = new ArrayList<PmTopicInfoDto>();
	    	if(list != null && list.size() > 0)
	    	{
	    		PmTopicInfoDto pmTopicInfoDto;
	    		for(PmTopicInfo pmTopicInfo:list)
	    		{
	    			pmTopicInfoDto = new PmTopicInfoDto();
	    			pmTopicInfoDto.setTopicName(pmTopicInfo.getTopicName());
	    			pmTopicInfoDto.setBannerUrl(host+pmTopicInfo.getBannerUrl());
	    			pmTopicInfoDto.setOperatorUsers(pmTopicInfo.getOperatorUsers());
	    			pmTopicInfoDto.setTopicType(pmTopicInfo.getTopicType());
	    			pmTopicInfoDto.setId(pmTopicInfo.getId());
	    			pmTopicInfoDto.setRemark(pmTopicInfo.getRemark());
	    			returnlist.add(pmTopicInfoDto);
	    		}
	    	}
	    	object.setResponse(returnlist);
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
	 * 私人订制提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/custom", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject custom(HttpServletRequest request,HttpServletResponse response) {
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
	    	String topicId = request.getParameter("topicId");
	    	if(StringUtil.isEmpty(topicId))
	    	{
	    		return object;
	    	}
	    	PmTopicInfo pmTopicInfo = pmTopicInfoService.getById(Long.parseLong(topicId));
	    	if(pmTopicInfo == null)
	    	{
	    		return object;
	    	}
	    	PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(type));
			if (StringUtil.isEmpty(pmCommodity)) {
				return object;
			}
			String orderNo = request.getParameter("orderNo");
	    	if(StringUtil.isEmpty(orderNo))
	    	{
	    		return object;
	    	}
	    	Map<String, String> ordermap = new HashMap<String, String>();
	    	ordermap.put("orderNo", orderNo);
			List<Order> orderlist = orderService.findByMap(ordermap);
			Order order = null;
			if(orderlist != null && orderlist.size() > 0)
			{
				order = orderlist.get(0);
				if(!"1".equals(order.getStatus()))
				{
					return object;
				}
				if(order.getCommodityId() - pmCommodity.getId() != 0)
				{
					return object;
				}
			}else{
				return object;
			}
			
			List<PmTopicJoinProducts> jplist = pmTopicJoinProductsService.findByMap(ordermap);
			if(jplist != null && jplist.size() > 0)
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
			
			
			PmProductInfo resource = new PmProductInfo();
			resource.setClientId(Long.parseLong(clientId));
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUrl(map.get("url"));
			resource.setThumbnail(map.get("thumbnail"));
			resource.setIsPublic(3);
			resource.setAudit(0);
			resource.setCreateFrom("1");
			resource.setUserId(Long.parseLong(userId));
			pmProductInfoService.save(resource);
			
			if(StringUtil.isNotEmpty(order))
			{
				order.setProductId(resource.getId());
				orderService.update(order);
			}
			
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
			if(StringUtil.isNotEmpty(description))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(description));
			}
			if(StringUtil.isNotEmpty(depict))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
			}
			pmTopicJoinProducts.setTopicId(Long.parseLong(topicId));
			pmTopicJoinProducts.setProductId(resource.getId());
			pmTopicJoinProducts.setUserId(Long.parseLong(userId));
			pmTopicJoinProducts.setJoinType(type);
			pmTopicJoinProducts.setDistributeNo(DateUtils.getCurrentDateTime(DateUtils.YYYYMMDD)+String.format("%03d", a));
			pmTopicJoinProducts.setOrderNo(orderNo);
			pmTopicJoinProducts.setStatus("1");
			pmTopicJoinProductsService.save(pmTopicJoinProducts);
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
	 * 案例列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/caseList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject caseList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("caseType", type);
	    	List<PmTopicCase> list = pmTopicCaseService.findByMap(map);
	    	if(list != null && list.size() > 0)
	    	{
	    		for(PmTopicCase pmTopicCase:list)
	    		{
	    			pmTopicCase.setPsProductUrl(host+pmTopicCase.getPsProductUrl());
	    			pmTopicCase.setSrcProductUrl(host+pmTopicCase.getSrcProductUrl());
	    		}
	    	}
	    	object.setResponse(list);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/drawType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject drawType(HttpServletRequest request) { 
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String clientId = request.getParameter("clientId");
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("parentId", "0");
	    	List<SysProductTypeInfo> list = sysProductTypeInfoService.findByMap(map);
			object.setResponse(list);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/drawIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject drawIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String clientId = request.getParameter("clientId");
	    	String type = request.getParameter("type");
	     	Map<String, Object> map = new HashMap<String, Object>();
	     	if(StringUtil.isEmpty(type))
	     	{
	     		type = "time";
	     	}
	     	if("hot".equals(type))
	    	{
	    		map.put("orderFried", "hot_score");
	    	}else if("time".equals(type)){
	    		map.put("orderFried", "createdwhen");
	    	}else{
		    	map.put("parentId", type);
		    	List<SysProductTypeInfo> list = sysProductTypeInfoService.findByMap(map);
		    	map.put("typeList", list);
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
			Page page =  new Page<PmProductInfo>(pageNoNum,pageSizeNum);
			map.put("isPublic", "4");
			Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
			List<PmProductInfoDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				Map<String, Object> typeMap = new HashMap<String, Object>();
				List<SysProductTypeInfo> typeList = sysProductTypeInfoService.findAll();
				if(typeList != null && typeList.size() > 0)
				{
					for(SysProductTypeInfo sysProductTypeInfo:typeList)
					{
						typeMap.put(sysProductTypeInfo.getId()+"", sysProductTypeInfo);
					}
				}
				for(PmProductInfoDto pmProductInfoDto:list)
				{
					pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
					pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
					pmProductInfoDto.setDepict(pmProductInfoDto.getDescription());
					SysProductTypeInfo sysProductTypeInfo = sysProductTypeInfoService.getById(pmProductInfoDto.getTypeId());
					String path = sysProductTypeInfo.getPath();
					String typeName = "";
					if(StringUtil.isNotEmpty(path))
					{
						String arr[] = path.split(",");
						for(String a:arr)
						{
							typeName += ((SysProductTypeInfo) typeMap.get(a)).getName() +"-";
						}
						pmProductInfoDto.setTypeName(typeName.substring(0,typeName.length()-1)+","+ ((SysProductTypeInfo) typeMap.get(arr[0])).getTypeColor());
					}
				}
			}
			object.setResponse(list);
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
	 * 画家
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/drawUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject drawUser(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
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
	    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(userId));
	    	if(cmUserExtendInfo == null){
	    		return object;
	    	}
	    	CmUserInfoOtherDto dto = new CmUserInfoOtherDto();
	    	dto.setAttentionLabel(cmUserExtendInfo.getAttentionLabel());
	    	dto.setAttentionsCount(cmUserInfo.getAttentionsCount());
	    	dto.setCity(cmUserInfo.getCity());
	    	dto.setCoverPraises(cmUserExtendInfo.getCoverPraises());
	    	dto.setCoverThumbnail(cmUserExtendInfo.getCoverThumbnail());
	    	dto.setFansCount(cmUserInfo.getFansCount());
	    	dto.setNickName(cmUserInfo.getNickName());
	    	dto.setSex(cmUserInfo.getSex());
	    	dto.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
	    	dto.setUserId(cmUserInfo.getId());
	    	dto.setDiscussCount(cmUserInfo.getDiscussCount());
	    	dto.setProductCount(cmUserInfo.getProductCount());
	    	dto.setSignature(HtmlUtils.htmlUnescape(cmUserExtendInfo.getSignature()));
	    	dto.setBirth(cmUserInfo.getBirth());
	    	dto.setCareer(cmUserExtendInfo.getCareer());
	    	dto.setPraisesCount(cmUserInfo.getPraisesCount());
	    	dto.setIntroduce(cmUserExtendInfo.getIntroduce());
	    	dto.setIdentityInfo(cmUserExtendInfo.getIdentityInfo());
	    	if(StringUtil.isNotEmpty(cmUserInfo.getBirth()))
	    	{
	    		dto.setAge(DateUtils.getAge(cmUserInfo.getBirth())+"");
	    	}
	    	Object zObject = CacheHelper.getInstance().get(clientId+"Cover"+userId);
	    	if(StringUtil.isNotEmpty(zObject))
	    	{
	    		dto.setIsZ(1);
	    	}
	    	map.put("userId", userId);
	    	List<CmUserImageIntroduce> imagelist = cmUserImageIntroduceService.findByMap(map);
	    	List<String> strlist = new ArrayList<String>();
	    	if(imagelist != null && imagelist.size() > 0)
	    	{
	    		for(CmUserImageIntroduce cmUserImageIntroduce:imagelist)
	    		{
	    			strlist.add(host+cmUserImageIntroduce.getImageUrl());
	    		}
	    	}
	    	Map<String, Object> typeMap = new HashMap<String, Object>();
			List<SysProductTypeInfo> typeList = sysProductTypeInfoService.findAll();
			if(typeList != null && typeList.size() > 0)
			{
				for(SysProductTypeInfo sysProductTypeInfo:typeList)
				{
					typeMap.put(sysProductTypeInfo.getId()+"", sysProductTypeInfo.getName());
				}
			}
	    	List<CmBusiScope> bsList = cmBusiScopeService.findByMap(map);
	    	if(bsList != null && bsList.size() > 0)
	    	{
	    		String productType = "";
	    		List<String> templist = new ArrayList<String>();
	    		Map<String,String> tempMap = new HashMap<String, String>();
	    		for(CmBusiScope cmBusiScope:bsList)
	    		{
	    			String temp = tempMap.get(cmBusiScope.getFirstTypeId()+"");
	    			if(StringUtil.isNotEmpty(temp))
	    			{
	    				tempMap.put(cmBusiScope.getFirstTypeId()+"", temp+typeMap.get(cmBusiScope.getTypeId()+"")+"、");
	    			}else{
	    				templist.add(cmBusiScope.getFirstTypeId()+"");
	    				tempMap.put(cmBusiScope.getFirstTypeId()+"", typeMap.get(cmBusiScope.getFirstTypeId()+"")+"：");
	    			}
	    		}
	    		if(templist != null && templist.size() > 0)
	    		{
	    			for(String id:templist)
		    		{
	    				String temp = tempMap.get(id);
	    				if(StringUtil.isNotEmpty(temp)){
	    					productType += temp.substring(0, temp.length()-1);
	    				}
		    		}
	    			dto.setProductType(productType.substring(0, productType.length()-1));
	    		}
	    	}
	    	dto.setList(strlist);
	    	Map<String,Object> rmap = new HashMap<String, Object>();
	    	rmap.put("userDetail", dto);
	    	
	    	map.put("userId", userId);
	    	List<SysProductTypeInfo> list = sysProductTypeInfoService.findByUserId(map);
	    	
	    	rmap.put("typeList", list);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(rmap);
	    }catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getType(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String parentType = request.getParameter("parentType");
	    	if(StringUtil.isEmpty(parentType))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	
	    	map.put("parentId", parentType);
			List<SysProductTypeInfo> typeList = sysProductTypeInfoService.findByMap(map);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(typeList);
	    }catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitDraw", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject submitDraw(HttpServletRequest request) {
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
	    	String typeId = request.getParameter("typeId");
	    	if(StringUtil.isEmpty(typeId))
	    	{
	    		return object;
	    	}
	    	String drawId = request.getParameter("drawId");
	    	if(StringUtil.isEmpty(drawId))
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
			
			PmTopicJoinProducts pmTopicJoinProducts = new PmTopicJoinProducts();
			pmTopicJoinProducts.setContact(HtmlUtils.htmlEscape(contact));
			if(StringUtil.isNotEmpty(description))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(description));
			}
			if(StringUtil.isNotEmpty(depict))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("topicType", "2");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicJoinProducts.setTopicId(topicList.get(0).getId());
			}
			pmTopicJoinProducts.setUserId(Long.parseLong(userId));
			pmTopicJoinProducts.setJoinType(typeId);
			pmTopicJoinProducts.setAcceptUserId(Long.parseLong(drawId));
			pmTopicJoinProductsService.save(pmTopicJoinProducts);
			object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pIndex", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject pIndex(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	     	Map<String,Object> map = new HashMap<String, Object>();
	     	map.put("user", "pt");
	     	if(StringUtil.isNotEmpty(userId))
	     	{
	     		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
		    	if(cmUserInfo == null)
		    	{
		    		return object;
		    	}
		    	if(cmUserInfo.getUserType() == 1)
		    	{
		    		map.put("user", "sjs");
		    	}
	     	}
	    	String pageNo = request.getParameter("pageNo");
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("enParamName", "pNum");
	    	List<SysConfigParam> configlist = sysConfigParamService.findByMap(searchMap);
	    	String pageSize = "";
	    	if(configlist != null && configlist.size() > 0)
	    	{
	    		pageSize = configlist.get(0).getParamValue();
	    	}else{
	    		pageSize = "12";
	    	}
	    	int pageNoNum = 0;
	    	int pageSizeNum = Integer.parseInt(pageSize);
			map.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			List<PmTopicJoinProducts> list = new ArrayList<PmTopicJoinProducts>();
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			if(topicList != null && topicList.size() > 0)
			{
				map.put("topicId",topicList.get(0).getId());
				if(StringUtil.isEmpty(pageNo))
				{
					Number total = pmTopicJoinProductsService.getCount(map);
					long totalItems = total.longValue();
				    if (totalItems <= 0) {
				    	pageNoNum = 1;
			        }else{
			        	long count = totalItems / pageSizeNum;
				        if (totalItems % pageSizeNum > 0) {
				            count++;
				        }
				        pageNoNum = (int) count;
			        }
				}else{
					pageNoNum = Integer.parseInt(pageNo);
				}
				map.put("orderFried", "join_time");
				map.put("sequence", "ASC");
				Page page =  new Page<PmTopicJoinProducts>(pageNoNum,pageSizeNum);
				pageResult = pmTopicJoinProductsService.findByPage(page, map);
				list = pageResult.getResult();
				if(list != null && list.size() > 0)
				{
					for(PmTopicJoinProducts pmTopicJoinProducts:list)
					{
						CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
						pmTopicJoinProducts.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
						pmTopicJoinProducts.setNickName(cmUserInfo.getNickName());
						pmTopicJoinProducts.setUrl(host+pmTopicJoinProducts.getUrl());
						pmTopicJoinProducts.setDepict(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
						pmTopicJoinProducts.setDescription(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
					}
				}
			}
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("totalPages", pageResult.getTotalPages());
			Collections.reverse(list);
			returnMap.put("list", list);
			
			object.setResponse(returnMap);
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
	 * -- 新的没有用到
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pIndexNew", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject pIndexNew(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	     	Map<String,Object> map = new HashMap<String, Object>();
	     	map.put("user", "pt");
	     	if(StringUtil.isNotEmpty(userId))
	     	{
	     		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
		    	if(cmUserInfo == null)
		    	{
		    		return object;
		    	}
		    	if(cmUserInfo.getUserType() == 1)
		    	{
		    		map.put("user", "sjs");
		    	}
	     	}
	    	String pageNo = request.getParameter("pageNo");
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("enParamName", "pNum");
	    	List<SysConfigParam> configlist = sysConfigParamService.findByMap(searchMap);
	    	searchMap.put("enParamName", "godPUrl");
	    	List<SysConfigParam> godList = sysConfigParamService.findByMap(searchMap);
	    	String pageSize = "";
	    	if(configlist != null && configlist.size() > 0)
	    	{
	    		pageSize = configlist.get(0).getParamValue();
	    	}else{
	    		pageSize = "12";
	    	}
	    	int pageNoNum =  Integer.parseInt(pageNo);;
	    	int pageSizeNum = Integer.parseInt(pageSize);
			map.put("topicType", "0");
			map.put("orderFried", "join_time");
			map.put("sequence", "ASC");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			List<PmTopicJoinProducts> totalList = new ArrayList<PmTopicJoinProducts>();
			if(topicList != null && topicList.size() > 0)
			{
				map.put("topicId",topicList.get(0).getId());
				
				//总的原图数
				Number totalNum = pmTopicJoinProductsService.getCount(map);
				//总期数
				int totalPeriods  = 1;
				if(StringUtil.isNotEmpty(totalNum) && totalNum.intValue() > 0)
				{
					 totalPeriods = totalNum.intValue() / pageSizeNum;
			         if (totalNum.intValue() % pageSizeNum > 0) {
			        	totalPeriods++;
			         }
				}
		        if(totalPeriods >= (pageNoNum-1)*10)
		        {
		        	Map<String,Object> psMap = new HashMap<String, Object>();
		        	int temp = totalPeriods-(pageNoNum-1)*10;
		        	int temp2 = totalPeriods-pageNoNum*10;
		        	if(temp2 < 0)
		        	{
		        		temp2 = 0;
		        	}
		        	for(int i=temp;i>temp2;i--)
		        	{
		        		int offset = 0;
		        		if(i==totalPeriods)
		        		{
		        			offset = totalNum.intValue();
		        		}else{
		        			offset = i*pageSizeNum;
		        		}
		        		 map.put("limit", 1);
		        		 map.put("offset",offset-1);
		        		 List<PmTopicJoinProducts> list = pmTopicJoinProductsService.findByMap(map);
		        		 PmTopicJoinProducts pmTopicJoinProducts =  list.get(0);
		        		 psMap.put("fristObj", "fristObj");
		        		 psMap.put("joinId", pmTopicJoinProducts.getId());
		        		 psMap.put("audit", "1");
	        			 pmTopicJoinProducts.setUrl(host+pmTopicJoinProducts.getUrl());
	        			 pmTopicJoinProducts.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
	        			 List<PmTopicPsProducts> fristObj = pmTopicPsProductsService.findByMap(psMap);
	        			 if(fristObj != null && fristObj.size() > 0)
	        			 {
	        				 pmTopicJoinProducts.setSpUrl(host+fristObj.get(0).getUrl());
		        			 pmTopicJoinProducts.setSpThumbnail(host+fristObj.get(0).getThumbnail());
	        			 }else{
	        				 if(godList != null && godList.size() > 0)
	        				 {
	        					 pmTopicJoinProducts.setSpUrl(host+godList.get(0).getParamValue());
			        			 pmTopicJoinProducts.setSpThumbnail(host+godList.get(0).getParamValue());
	        				 }
	        			 }
	        			 
	        			 
	        			 pmTopicJoinProducts.setJournalSeq(i);
	        			 pmTopicJoinProducts.setDescription(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
		        		 totalList.add(pmTopicJoinProducts);
		        	}
		        }
			}
			object.setResponse(totalList);
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
	 * -- 新的没有用到
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/periodsDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject periodsDetail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	String periods = request.getParameter("periods");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	if(StringUtil.isEmpty(periods))
	    	{
	    		return object;
	    	}
	     	Map<String,Object> map = new HashMap<String, Object>();
	     	map.put("user", "pt");
	     	if(StringUtil.isNotEmpty(userId))
	     	{
	     		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
		    	if(cmUserInfo == null)
		    	{
		    		return object;
		    	}
		    	if(cmUserInfo.getUserType() == 1)
		    	{
		    		map.put("user", "sjs");
		    	}
	     	}
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("enParamName", "pNum");
	    	List<SysConfigParam> configlist = sysConfigParamService.findByMap(searchMap);
	    	searchMap.put("enParamName", "godPUrl");
	    	List<SysConfigParam> godList = sysConfigParamService.findByMap(searchMap);
	    	String pageSize = "";
	    	if(configlist != null && configlist.size() > 0)
	    	{
	    		pageSize = configlist.get(0).getParamValue();
	    	}else{
	    		pageSize = "12";
	    	}
	    	int pageNoNum = Integer.parseInt(periods);
	    	int pageSizeNum = Integer.parseInt(pageSize);
			map.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			List<PmTopicJoinProducts> list = new ArrayList<PmTopicJoinProducts>();
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			if(topicList != null && topicList.size() > 0)
			{
				map.put("topicId",topicList.get(0).getId());
				map.put("orderFried", "join_time");
				map.put("sequence", "ASC");
				Page page =  new Page<PmTopicJoinProducts>(pageNoNum,pageSizeNum);
				pageResult = pmTopicJoinProductsService.findByPage(page, map);
				list = pageResult.getResult();
				if(list != null && list.size() > 0)
				{
					Map<String,Object> psMap = new HashMap<String, Object>();
					for(PmTopicJoinProducts pmTopicJoinProducts:list)
					{
						pmTopicJoinProducts.setUrl(host+pmTopicJoinProducts.getUrl());
						pmTopicJoinProducts.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
						pmTopicJoinProducts.setDepict(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
						pmTopicJoinProducts.setDescription(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
						psMap.put("fristObj", "fristObj");
		        		psMap.put("joinId", pmTopicJoinProducts.getId());
		        		psMap.put("audit", "1");
		        		List<PmTopicPsProducts> fristObj = pmTopicPsProductsService.findByMap(psMap);
	        			if(fristObj != null && fristObj.size() > 0)
	        			{
	        				pmTopicJoinProducts.setSpUrl(host+fristObj.get(0).getUrl());
		        			pmTopicJoinProducts.setSpThumbnail(host+fristObj.get(0).getThumbnail());
	        			}else{
	        				if(godList != null && godList.size() > 0)
	        				{
	        					pmTopicJoinProducts.setSpUrl(host+godList.get(0).getParamValue());
			        			pmTopicJoinProducts.setSpThumbnail(host+godList.get(0).getParamValue());
	        				}
	        			}
	        			pmTopicJoinProducts.setJournalSeq(pageNoNum);
					}
				}
			}
			Collections.reverse(list);
			object.setResponse(list);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	     	Map<String,Object> map = new HashMap<String, Object>();
			map.put("topicType", "0");
			map.put("user", "sjs");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			List<PmTopicJoinProducts> list = new ArrayList<PmTopicJoinProducts>();
			if(topicList != null && topicList.size() > 0)
			{
				map.put("topicId",topicList.get(0).getId());
				map.put("userId",userId);
				map.put("noDelStatus",4);
				list = pmTopicJoinProductsService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					for(PmTopicJoinProducts pmTopicJoinProducts:list)
					{
						pmTopicJoinProducts.setUrl(host+pmTopicJoinProducts.getUrl());
						pmTopicJoinProducts.setThumbnail(host+pmTopicJoinProducts.getThumbnail());
						pmTopicJoinProducts.setDepict(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
						pmTopicJoinProducts.setDescription(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
					}
				}
			}
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("list", list);
			
			object.setResponse(returnMap);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myHelpP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myHelpP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	     	Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("audit", "1");
			List<PmTopicPsProducts> list = pmTopicPsProductsService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				for(PmTopicPsProducts pmTopicPsProducts:list)
				{
					pmTopicPsProducts.setUrl(host+pmTopicPsProducts.getUrl());
					pmTopicPsProducts.setThumbnail(host+pmTopicPsProducts.getThumbnail());
				}
			}
			object.setResponse(list);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject pDetail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		String joinId = request.getParameter("joinId");
		String userId = request.getParameter("userId");
    	String clientId = request.getParameter("clientId");
	    try{
	     	Map<String,Object> map = new HashMap<String, Object>();
	     	if(StringUtil.isEmpty(joinId)){
	    		return object;
	    	}
	     	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
			Page page =  new Page<PmTopicPsProductsDto>(pageNoNum,pageSizeNum);
			map.put("joinId", joinId);
			map.put("audit", "1");
			
			String key = "pmTopicJoinProducts@psProducts"+joinId+pageNo ;
			List<PmTopicPsProducts> list = new ArrayList<PmTopicPsProducts>();
	     	List<PmTopicPsProducts> tempList = (List<PmTopicPsProducts>) CacheHelper.getInstance().get(key);
	     	
	     	if(tempList== null || tempList.size()==0  ){
	     		map.put("limit", pageSizeNum);
	     		map.put("offset", (pageNoNum-1)*pageSizeNum);
				list = pmTopicPsProductsService.findByMap(map);
				//ps作品 缓存5分钟
				CacheHelper.getInstance().set(60*5, key, list);
	     	}else{
	     		list = tempList;
	     	}
	     	
	     	//ps 的作品有没有被赞过
			if(list != null && list.size() > 0){
				for(PmTopicPsProducts pmTopicPsProducts:list){
					pmTopicPsProducts.setUrl(host+pmTopicPsProducts.getUrl());
					pmTopicPsProducts.setThumbnail(host+pmTopicPsProducts.getThumbnail());
			    	pmTopicPsProducts.setPsDescription(HtmlUtils.htmlUnescape(pmTopicPsProducts.getPsDescription()));
			    	
			    	Map<String, Object> searchMap = new HashMap<String, Object>();
			    	searchMap.put("productId", pmTopicPsProducts.getProductId());
			    	if(StringUtil.isNotEmpty(userId)){
			    		searchMap.put("userId", userId);
			    	}else{
			    		searchMap.put("clientId", clientId);
			    	}
			    	int count = pmProductPraisesService.getCount(searchMap).intValue();
			    	if(count > 0){
			    		pmTopicPsProducts.setIsZ(1);
			    	}else{
			    		pmTopicPsProducts.setIsZ(0);
			    	}
				}
			}
	     	
	    	String key2 = "pmTopicPsProducts@firstPs"+joinId;
	     	PmTopicPsProducts pmTopicPsProducts = null;
	     	PmTopicPsProducts tempPmTopicPsProduct = (PmTopicPsProducts) CacheHelper.getInstance().get(key2);
			if(tempPmTopicPsProduct == null && "1".equals(pageNo.trim()) ){
				map.clear();
				map.put("joinId", joinId);
				map.put("audit", "1");
				map.put("fristObj", "fristObj");
				List<PmTopicPsProducts> fristList = pmTopicPsProductsService.findByMap(map);
				
				if(fristList != null && fristList.size() > 0 ){
					pmTopicPsProducts = fristList.get(0);
					pmTopicPsProducts.setUrl(host+pmTopicPsProducts.getUrl());
					pmTopicPsProducts.setThumbnail(host+pmTopicPsProducts.getThumbnail());
			    	pmTopicPsProducts.setPsDescription(HtmlUtils.htmlUnescape(pmTopicPsProducts.getPsDescription()));
			    	
					Map<String, Object> searchMap = new HashMap<String, Object>();
			    	searchMap.put("productId", pmTopicPsProducts.getProductId());
			    	if(StringUtil.isNotEmpty(userId)){
			    		searchMap.put("userId", userId);
			    	}else{
			    		searchMap.put("clientId", clientId);
			    	}
			    	int count = pmProductPraisesService.getCount(searchMap).intValue();
			    	if(count > 0){
			    		pmTopicPsProducts.setIsZ(1);
			    	}else{
			    		pmTopicPsProducts.setIsZ(0);
			    	}
				}
				CacheHelper.getInstance().set(60*60*2, key2, pmTopicPsProducts);
			}else{
				pmTopicPsProducts = tempPmTopicPsProduct;
	     	}
	     	
			PmTopicJoinProducts pmTopicJoinProducts = null;
			String key3 = "pmTopicJoinProducts@firstTopic"+joinId;
			pmTopicJoinProducts = (PmTopicJoinProducts) CacheHelper.getInstance().get(key3);
			if("1".equals(pageNo.trim()) && pmTopicJoinProducts==null ){//对应 joinId 的 属性未找到，再查询
				pmTopicJoinProducts = pmTopicJoinProductsService.getById(Long.parseLong(joinId));
				if(StringUtil.isEmpty(pmTopicJoinProducts)){
					return object;
				}
				CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
				pmTopicJoinProducts.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
				pmTopicJoinProducts.setNickName(cmUserInfo.getNickName());
				pmTopicJoinProducts.setUrl(host+pmTopicJoinProducts.getUrl());
				pmTopicJoinProducts.setDepict(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlUnescape(pmTopicJoinProducts.getDescription()));
				
				
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("productId", pmTopicJoinProducts.getProductId());
				if(StringUtil.isNotEmpty(userId)){
					searchMap.put("userId", userId);
				}else{
					searchMap.put("clientId", clientId);
				}
		    	if(pmTopicPsProducts!=null){
		    		int count = pmProductPraisesService.getCount(searchMap).intValue();
		    		if(count > 0){
		    			pmTopicPsProducts.setIsZ(1);
		    		}else{
		    			pmTopicPsProducts.setIsZ(0);
		    		}
		    	}
				
				List<PmProductLabel> labelList = new ArrayList<PmProductLabel>();
				String key4 = "pmProductLabel@productId"+pmTopicJoinProducts.getProductId();
				List<PmProductLabel> tempLabelList = (List<PmProductLabel>) CacheHelper.getInstance().get(key4);
				if(tempLabelList != null && tempLabelList.size() > 0){
					labelList = tempLabelList;
				}else{
					Map<String,Object> labelMap = new HashMap<String, Object>();
					labelMap.put("productId",pmTopicJoinProducts.getProductId());
					labelList = pmProductLabelDao.findByMap(labelMap);
					CacheHelper.getInstance().set(60*60*24, key4, labelList);
				}
				if(labelList != null && labelList.size()>0){
					pmTopicJoinProducts.setPmproductLabelList(labelList);
				}
				
				PIndexDto pIndexDto= new PIndexDto();
				String key5 = "pIndexDto@countPs"+joinId;
				PIndexDto pIndexDtoTemp = (PIndexDto) CacheHelper.getInstance().get(key3);
				if(pIndexDtoTemp != null ){
					pIndexDto = pIndexDtoTemp;
				}else{
					Map<String,Object> allMap = new HashMap<String, Object>();
					allMap.put("joinId",pmTopicJoinProducts.getId());
					allMap.put("audit", "1");
					pIndexDto = pmTopicPsProductsService.countPs(allMap);
					CacheHelper.getInstance().set(60*60*1, key5, pIndexDto);
				}
				if(StringUtil.isNotEmpty(pIndexDto)){
					pmTopicJoinProducts.setJoinUser(pIndexDto.getTotalUser());
				}
				
				
				CacheHelper.getInstance().set(60*5, key3, pmTopicJoinProducts);
			}
			
	     	//是否收藏该作品
	    	int iscollect = 0;
	    	if(StringUtil.isNotEmpty(userId)){
	    		Map<String, Object> collectmap = new HashMap<String, Object>();
	    		collectmap.put("userId", userId);
	    		collectmap.put("topicId", joinId);
	    		collectmap.put("type", 3);
		    	List<CmUserCollect>  cmUserCollectList = cmUserCollectService.findByMap(collectmap);
		    	if(cmUserCollectList != null && cmUserCollectList.size() >0 ){
		    		iscollect = 1;
		    	}
	    	}
	    	if(pmTopicJoinProducts!=null){
	    		pmTopicJoinProducts.setIsCollect(iscollect);
	    	}
	     	
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("fristObj", pmTopicPsProducts);
			returnMap.put("topObj", pmTopicJoinProducts);
			returnMap.put("totalPages", 0);
			returnMap.put("list", list);	
	    	object.setResponse(returnMap);
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
	 * 删除 我的悬赏求P 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delMyP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delMyP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String joinIds = request.getParameter("joinIds");
	    	if(StringUtil.isEmpty(joinIds))
	    	{
	    		return object;
	    	}
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	pmTopicJoinProductsService.batchDelMyp(joinIds);
			
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
	 * 求p提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/helpP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject helpP(HttpServletRequest request,HttpServletResponse response) {
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
	    	if(cmUserInfo.getIsAccusation() == 1)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("被举报不能使用");
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
	    	Map<String,String> map = FileUtil.uploadImage(request, response);
			if(StringUtil.isEmpty(map))
			{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("上传失败");
				return object;
			}
			PmProductInfo resource = new PmProductInfo();
			resource.setClientId(Long.parseLong(clientId));
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUrl(map.get("url"));
			resource.setThumbnail(map.get("thumbnail"));
			resource.setIsPublic(2);
			resource.setAudit(1);
			resource.setCreateType(0);
			resource.setCreateFrom("1");
			resource.setUserId(Long.parseLong(userId));
			pmProductInfoService.save(resource);
			
			PmTopicJoinProducts pmTopicJoinProducts = new PmTopicJoinProducts();
			if(StringUtil.isNotEmpty(description))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(description));
			}
			if(StringUtil.isNotEmpty(depict))
			{
				pmTopicJoinProducts.setDescription(HtmlUtils.htmlEscape(depict));
			}
			Map<String,Object> topicmap = new HashMap<String, Object>();
			topicmap.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(topicmap);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicJoinProducts.setTopicId(topicList.get(0).getId());
			}
			pmTopicJoinProducts.setProductId(resource.getId());
			pmTopicJoinProducts.setUserId(Long.parseLong(userId));
			pmTopicJoinProducts.setJoinType(type);
			pmTopicJoinProducts.setStatus("1");
			pmTopicJoinProductsService.save(pmTopicJoinProducts);
			
			pmScoreLogService.addScore("8", cmUserInfo, clientId, resource.getId()+"","");
	    	pmScoreLogService.addScore("1019", cmUserInfo, clientId, resource.getId()+"","");
	    	cmUserInfoService.update(cmUserInfo);
		
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
	 * p图提交
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/submitP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject submitP(HttpServletRequest request,HttpServletResponse response) {
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
	    	if(cmUserInfo.getIsAccusation() == 1)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("被举报不能使用");
				return object;
	    	}
	    	String joinId = request.getParameter("joinId");
	    	if(StringUtil.isEmpty(joinId))
	    	{
	    		return object;
	    	}
	    	PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(Long.parseLong(joinId));
	    	if(pmTopicJoinProducts == null)
	    	{
	    		return object;
	    	}
	    	String description = request.getParameter("description");
	    	String depict = request.getParameter("depict");
	    	if(StringUtil.isEmpty(description) && StringUtil.isEmpty(depict))
	    	{
	    		return object;
	    	}
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(pmTopicJoinProducts.getProductId());
	    	Map<String,String> map = FileUtil.uploadImage(request, response);
			if(StringUtil.isEmpty(map))
			{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("上传失败");
				return object;
			}
			
			PmProductInfo resource = new PmProductInfo();
			resource.setClientId(Long.parseLong(clientId));
			resource.setUserId(Long.parseLong(userId));
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUrl(map.get("url"));
			resource.setThumbnail(map.get("thumbnail"));
			resource.setIsPublic(2);
			resource.setAudit(1);
			resource.setCreateType(1);
			resource.setCreateFrom("1");
			pmProductInfoService.save(resource);
			
			pmTopicJoinProducts.setJoinProducts(pmTopicJoinProducts.getJoinProducts()+1);
			pmTopicJoinProductsService.update(pmTopicJoinProducts);
			
			PmTopicPsProducts pmTopicPsProducts = new PmTopicPsProducts();
			pmTopicPsProducts.setJoinId(pmTopicJoinProducts.getId());
			pmTopicPsProducts.setSrcProductId(pmTopicJoinProducts.getProductId());
			pmTopicPsProducts.setProductId(resource.getId());
			if(StringUtil.isNotEmpty(description))
			{
				pmTopicPsProducts.setPsDescription(HtmlUtils.htmlEscape(description));
			}
			if(StringUtil.isNotEmpty(depict))
			{
				pmTopicPsProducts.setPsDescription(HtmlUtils.htmlEscape(depict));
			}
			pmTopicPsProducts.setPsUserId(Long.parseLong(userId));
			pmTopicPsProducts.setPsUserType(cmUserInfo.getUserType());
			Map<String,Object> topicmap = new HashMap<String, Object>();
			topicmap.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(topicmap);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicPsProducts.setTopicId(topicList.get(0).getId());
			}
			PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", "主人，"+cmUserInfo.getNickName()+"P了你的图，快来看看", "16", joinId, "");
			pmTopicPsProductsService.save(pmTopicPsProducts);
			
			if("1".equals(cmUserInfo.getUserType()))
    		{
    			pmScoreLogService.addScore("6", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}else{
    			pmScoreLogService.addScore("9", cmUserInfo, clientId, pmProductInfo.getId()+"","");
    		}
	    	pmScoreLogService.addScore("1020", cmUserInfo, clientId, pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfo);
	    	
	    	
	    	//更新悬赏详细里面的缓存
	    	Map<String,Object> searchmap = new HashMap<String, Object>();
	    	searchmap.put("joinId", joinId);
	    	searchmap.put("audit", "1");
	    	int count = pmTopicPsProductsService.getCount(searchmap).intValue();
	    	int pageNo = count/18+1;
	    	String key = "pmTopicJoinProducts@psProducts"+joinId+pageNo ;
			List<PmTopicPsProducts> list = new ArrayList<PmTopicPsProducts>();
	     	List<PmTopicPsProducts> tempList = (List<PmTopicPsProducts>) CacheHelper.getInstance().get(key);
	     	if(tempList!=null && tempList.size()> 0 ){
	     		list = tempList;
	     	}
	     	pmTopicPsProducts.setUrl(map.get("url"));
	     	pmTopicPsProducts.setThumbnail(map.get("thumbnail"));
	     	list.add(pmTopicPsProducts);
	     	CacheHelper.getInstance().set(60*5, key, list);
	     	
	     	
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
	 * 私人订制商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/commodityList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject commodityList(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> reMap = new HashMap<String, Object>();
	    	Map<String,Object> tempMap = (Map<String,Object>) CacheHelper.getInstance().get("commodityList");	
			if(tempMap!=null && tempMap.size()>0){
				reMap = tempMap;
			}else{
				Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("status", "0");
		    	List<PmCommodity> list = pmCommodityService.findByMap(map);
		    	if(list != null && list.size() > 0)
		    	{
		    		for(PmCommodity pmCommodity:list)
		    		{
		    			if(StringUtil.isNotEmpty(pmCommodity.getContent()))
		    			{
		    				pmCommodity.setContent(html+pmCommodity.getContent()+html2);
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
		    			
		    			map.put("commodityId", pmCommodity.getId());
		    			List<PmCommodityInfo> infoList = pmCommodityInfoService.findByMap(map);
		    			if(infoList!=null && infoList.size() > 0 ){
		    				pmCommodity.setList(infoList);
		    			}
		    		}
		    	}
		    	reMap.put("list", list);
		    	Map<String,Object> searchMap = new HashMap<String, Object>();
		    	searchMap.put("enParamName", "privateUrl");
		    	List<SysConfigParam> configlist = sysConfigParamService.findByMap(searchMap);
		    	String indexImage = "";
		    	if(configlist != null && configlist.size() > 0)
		    	{
		    		indexImage = host+configlist.get(0).getParamValue();
		    	}
		    	reMap.put("indexImage",indexImage);
		    	Map<String,String> param = new HashMap<String, String>();
		    	param.put("code",SysParamConstant.XXIU_400);
		    	List<SysParameter> sysParameter400 = sysParameterService.findByMap(param);
		    	if(sysParameter400 != null && sysParameter400.size()==1){
		    		reMap.put("xxiu400Number",sysParameter400.get(0).getValue());
		    	}
		    	param.clear();
		    	param.put("code",SysParamConstant.XXIU_QQ);
		    	List<SysParameter> sysParameterQQ = sysParameterService.findByMap(param);
		    	if(sysParameterQQ != null && sysParameterQQ.size()==1){
		    		reMap.put("xxiuBusinessQQ",sysParameterQQ.get(0).getValue());
		    	}
		    	param.clear();
		    	param.put("status", "1");
		    	List<QqList> qqList = new ArrayList<QqList>();
		    	qqList = qqListService.findByMap(param);
		    	if(qqList!=null && qqList.size()>0){
		    		reMap.put("xxiuShouHuiQQ",sysParameterQQ.get(0).getValue());
		    	}
		    	CacheHelper.getInstance().set(60*60*24,"commodityList", reMap);
			}		
	    	object.setResponse(reMap);
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
	
	
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>();
		list.add(9);
		list.add(9);
		list.add(9);
		
		for(int a :list)
		{
			System.out.println(a);
		}
	}
}
