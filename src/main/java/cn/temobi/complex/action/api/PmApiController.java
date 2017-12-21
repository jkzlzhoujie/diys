package cn.temobi.complex.action.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.list.SetUniqueList;
import org.apache.commons.lang.StringUtils;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.CMPVersion;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.CmUserInfoOtherDto;
import cn.temobi.complex.dto.CmUserMessageDto;
import cn.temobi.complex.dto.MyDiscussDto;
import cn.temobi.complex.dto.PmProductDiscussDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.dto.TwoDiscussDto;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserCollect;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.MaterialUseLog;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.complex.entity.SysBackupLabels;
import cn.temobi.complex.entity.SysHotLabels;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.BlackListService;
import cn.temobi.complex.service.BlackListUserEquipmentService;
import cn.temobi.complex.service.CirclePostPicService;
import cn.temobi.complex.service.CirclePostService;
import cn.temobi.complex.service.CmTalenInfoService;
import cn.temobi.complex.service.CmUserCollectService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserFansService;
import cn.temobi.complex.service.CmUserGroupService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.CmUserPrivilegeService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.MaterialUseLogService;
import cn.temobi.complex.service.PmProductAccusationService;
import cn.temobi.complex.service.PmProductCollectPicService;
import cn.temobi.complex.service.PmProductDiscussService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.PmTopicPsProductsService;
import cn.temobi.complex.service.ProductRecommendService;
import cn.temobi.complex.service.SysBackupLabelsService;
import cn.temobi.complex.service.SysHotLabelsService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PageModel;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

import com.salim.cache.CacheHelper;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/pm")
public class PmApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmTopicPsProductsService")
	private PmTopicPsProductsService pmTopicPsProductsService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	@Resource(name="cmTalenInfoService")
	private CmTalenInfoService cmTalenInfoService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="cmUserExtendInfoService")
	private CmUserExtendInfoService cmUserExtendInfoService;
	
	@Resource(name="pmProductPraisesService")
	private PmProductPraisesService pmProductPraisesService;
	
	@Resource(name="pmProductDiscussService")
	private PmProductDiscussService pmProductDiscussService;
	
	@Resource(name="cmUserFansService")
	private CmUserFansService cmUserFansService;
	
	@Resource(name="pmProductAccusationService")
	private PmProductAccusationService pmProductAccusationService;
	
	@Resource(name="sysHotLabelsService")
	private SysHotLabelsService sysHotLabelsService;
	
	@Resource(name="laberService")
	private LaberService laberService;
	
	@Resource(name="cmUserGroupService")
	private CmUserGroupService cmUserGroupService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="sysBackupLabelsService")
	private SysBackupLabelsService sysBackupLabelsService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="productRecommendService")
	private ProductRecommendService productRecommendService;
	
	@Resource(name="blackListService")
	private BlackListService blackListService;
	
	@Resource(name="blackListUserEquipmentService")
	private BlackListUserEquipmentService blackListUserEquipmentService;
	
	@Resource(name="materialUseLogService")
	private MaterialUseLogService materialUseLogService;
	
	@Resource(name="cmUserPrivilegeService")
	private CmUserPrivilegeService cmUserPrivilegeService;
	
	@Resource(name="cmUserCollectService")
	private CmUserCollectService cmUserCollectService;
	
	@Resource(name="circlePostService")
	private CirclePostService circlePostService;
	
	@Resource(name="circlePostPicService")
	private CirclePostPicService circlePostPicService;
	
	@Resource(name="pmProductCollectPicService")
	private PmProductCollectPicService pmProductCollectPicService;
	
	
	/**
	 * 首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject index(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String type = request.getParameter("type");
	    	String clientId = request.getParameter("clientId");
	    	String userId = request.getParameter("userId");
	    	String requestType = request.getParameter("requestType");
	    	List<Long> sysProductIdList = SetUniqueList.decorate(new ArrayList<String>());
	    	
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	List<PmProductInfoDto> list = new ArrayList<PmProductInfoDto>();
	    	List<PmTopicPsProducts> pslist = new ArrayList<PmTopicPsProducts>();
	    	// 1 最热 3 最新 4 主题 5 P图秀 
	    	if("3".equals(type))
	    	{
	    		map.put("audit", "1");
				map.put("isPublic", "1");
				map.put("limit", 18);
    			List<Long> newList = pmProductInfoService.findProductIdList(map);
	    		Object objectList = CacheHelper.getInstance().get(clientId+"indexNew");
    			String str1 = "";
    			String str2 = "";
    			if(StringUtil.isNotEmpty(objectList))
    			{
    				String indexNew = (String) objectList;
    				str1 = indexNew.split("\\|")[0];
    				str2 = indexNew.split("\\|")[1];
    			}
    			if(StringUtil.isNotEmpty(requestType) && (!"0".equals(requestType)) && ("1".equals(str2) || (StringUtil.isNotEmpty(objectList) && newList.get(0) - Long.parseLong(str1) == 0)))
    			{
    				Page page =  new Page(pageNoNum,pageSizeNum);
		    		map.put("audit", "1");
					map.put("isPublic", "1");
		    		map.put("limit", page.getPageSize());
					map.put("offset", page.getOffset());
					map.put("endDate",DateUtils.getCurrDateStr());
					map.put("startDate", DateUtils.getNextDate(new Date(), -1));
					List<Long> productList = pmProductInfoService.findNewProductId(map);
					if(productList != null && productList.size() > 0)
					{
						Map<String,Object> index3Map = new HashMap<String, Object>();
						index3Map.put("productList", productList);
						index3Map.put("ids",StringUtils.join(productList.toArray(), ","));
						list = pmProductInfoService.findDtoMap(index3Map);
					}
					if(StringUtil.isNotEmpty(clientId) && !"2".equals(requestType) && list.size() > 0)
		    		{
		    			CacheHelper.getInstance().set(60*60*24,clientId+"indexNew", list.get(0).getProductId()+"|1");
		    		}
    			}else{
    				if(pageNo.equals("1"))
		    		{
		    			Page page =  new Page<PmProductInfoDto>(pageNoNum-1,pageSizeNum);
						map.put("audit", "1");
						map.put("isPublic", "1");
						map.put("productList", newList);
						map.put("ids",StringUtils.join(newList.toArray(), ","));
						Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
						list = pageResult.getResult();
		    		}else if(!pageNo.equals("1")){
		    			Page page =  new Page<PmProductInfoDto>(pageNoNum-1,pageSizeNum);
						map.put("audit", "1");
						map.put("isPublic", "1");
						map.put("orderFried", "createdwhen");
						map.put("newList", newList);
						Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
						list = pageResult.getResult();
		    		}
		    		if(StringUtil.isNotEmpty(clientId) && !"2".equals(requestType))
		    		{
		    			CacheHelper.getInstance().set(60*60*24,clientId+"indexNew", list.get(0).getProductId()+"|0");
		    		}
    			}
    			seachList(type,null,list,"");
	    	}else if("4".equals(type))  // 4 主题
	    	{
	    		List<Long> templist = (List<Long>) CacheHelper.getInstance().get("index@reList");
	    		if(templist == null || templist.size() <= 0)
    			{
	    			Map<String,Object> index4Map = new HashMap<String, Object>();
		    		index4Map.put("type", type);
		    		index4Map.put("limit", 18*10);
					index4Map.put("offset", 0);
		    		List<ProductRecommend> reList = productRecommendService.findByMap(index4Map);
		    		templist = new ArrayList<Long>();
	    			if(reList != null && reList.size() > 0)
	    			{
	    				for(ProductRecommend productRecommend:reList)
	    				{
	    					templist.add(productRecommend.getProductId());
	    				}
	    			}
	    			CacheHelper.getInstance().set(60*60*24,"index@reList", templist);
    			}
	    		PageModel onePageModel = new PageModel(templist, pageSizeNum);
    			List<Long> onePageList = onePageModel.getObjects(1);
	    		
	    		if(pageNoNum > 1 && pageNoNum <= 10)
	    		{
	    			List<Long> totalList = SetUniqueList.decorate(new ArrayList<String>());
	    			totalList.addAll(onePageList);
	    			
		    		Map<String, Object> searchMap = new HashMap<String, Object>();
	    			searchMap.put("audit", "1");
	    			searchMap.put("isPublic", "1");
		    		Object objectList = CacheHelper.getInstance().get("sysType4List");
		    		if(StringUtil.isEmpty(objectList))
		    		{
		    			searchMap.remove("laber");
			    		searchMap.put("orderFried", "magic_score");
			    		searchMap.put("limit", pageSizeNum);
			    		sysProductIdList.addAll(pmProductInfoService.findProductList(searchMap));
			    		
			    		searchMap.put("limit", 5);
			    		List<SysBackupLabels> bLList = sysBackupLabelsService.findByMap(searchMap);
			    		if(bLList != null && bLList.size() > 0)
			    		{
			    			searchMap.put("orderFried", "hot_score");
			    			for(SysBackupLabels sysBackupLabels:bLList)
			    			{
			    				searchMap.put("laber", sysBackupLabels.getName());
			    				sysProductIdList.addAll(pmProductInfoService.findProductList(searchMap));
			    			}
			    		}
			    		
			    		searchMap.put("orderFried", "hot_score");
			    		searchMap.put("discussCount", 1);
			    		searchMap.remove("laber");
			    		searchMap.remove("limit");
			    		sysProductIdList.addAll(pmProductInfoService.findProductList(searchMap));
			    		
			    		CacheHelper.getInstance().set(60*60*24, "sysType4List", sysProductIdList);
		    		}else{
		    			sysProductIdList = (List<Long>) objectList;
		    		}
	    			totalList.addAll(sysProductIdList);
		    		totalList.removeAll(templist);
		    		
		    		List<PmProductInfoDto> tempList = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type+"@otherPage");
		    		if(tempList == null || tempList.size() <= 0)
	    			{
		    			List<Long> seachList = totalList.subList(0, 9*pageSizeNum);
			    		map.put("productList", seachList);
		    			map.put("ids", StringUtils.join(seachList.toArray(), ","));
		    			tempList = pmProductInfoService.findDtoMap(map);
		    			
		    			CacheHelper.getInstance().set(60*60*24, "index@"+type+"@otherPage", tempList);
	    			}
		    		seachList(type,null,tempList,"index@"+type+"@otherPage");
		    		PageModel pageModel = new PageModel(tempList, pageSizeNum);
	    			list = pageModel.getObjects(pageNoNum-1);
	    		}else if(pageNoNum == 1)
	    		{
    				list = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type+"@page");
    				if(list == null || list.size() <= 0)
	    			{
		    			map.put("productList", onePageList);
		    			map.put("ids", StringUtils.join(templist.toArray(), ","));
		    			list = pmProductInfoService.findDtoMap(map);
						CacheHelper.getInstance().set(60*60*24, "index@"+type+"@page", list);
	    			}
    				seachList(type,null,list,"index@"+type+"@page");
	    		}else{
	    			list = new ArrayList<PmProductInfoDto>();
	    		}
	    	}else if("5".equals(type))// 5 P图
	    	{
	    		if(pageNoNum <= 30)
	    		{
	    			List<PmTopicPsProducts> tempPsList = (List<PmTopicPsProducts>) CacheHelper.getInstance().get("index@"+type);
	    			if(tempPsList == null || tempPsList.size() <= 0)
	    			{
	    				map.put("audit", "1");
						map.put("limit", pageSizeNum*10);
						map.put("offset", 0);
						tempPsList = pmTopicPsProductsService.findMapIndex(map);
						
						CacheHelper.getInstance().set(60*60*24, "index@"+type, tempPsList);
	    			}
	    			seachList(type,tempPsList,null,"index@"+type);
	    			
	    			PageModel pageModel = new PageModel(tempPsList, pageSizeNum);
	    			pslist = pageModel.getObjects(pageNoNum);
	    			
	    		}else{
	    			Page page =  new Page<PmTopicPsProductsDto>(pageNoNum,pageSizeNum);
					map.put("audit", "1");
					Page<PmTopicPsProducts> pageResult = pmTopicPsProductsService.findIndex(page, map);
					pslist = pageResult.getResult();
					seachList(type,pslist,null,"");
	    		}
	    	}else if("1".equals(type)){// 1 最热
	    		if(pageNoNum <= 10)
	    		{
	    			List<PmProductInfoDto> tempList = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type);
	    			if(tempList == null || tempList.size() <= 0)
	    			{
	    				map.put("orderFried", "hot_score");
			    		map.put("audit", "1");
						map.put("isPublic", "1");
						map.put("limit", pageSizeNum*10);
						map.put("offset", 0);
						tempList = pmProductInfoService.findDtoMap(map);
						
						CacheHelper.getInstance().set(60*60*24, "index@"+type, tempList);
	    			}
	    			seachList(type,null,tempList,"index@"+type);
	    			
	    			PageModel pageModel = new PageModel(tempList, pageSizeNum);
	    			list = pageModel.getObjects(pageNoNum);
	    			
	    		}else{
	    			Page page =  new Page<PmProductInfoDto>(pageNoNum,pageSizeNum);
		    		map.put("orderFried", "hot_score");
		    		map.put("audit", "1");
					map.put("isPublic", "1");
					Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
					list = pageResult.getResult();
					seachList(type,null,list,"");
	    		}
	    	}else if("2".equals(type)){
	    		if(pageNoNum <= 10)
	    		{
	    			List<PmProductInfoDto> tempList = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type);
	    			if(tempList == null || tempList.size() <= 0)
	    			{
	    				map.put("orderFried", "magic_score");
			    		map.put("audit", "1");
						map.put("isPublic", "1");
						map.put("limit", pageSizeNum*10);
						map.put("offset", 0);
						tempList = pmProductInfoService.findDtoMap(map);
						
						CacheHelper.getInstance().set(60*10, "index@"+type, tempList);
	    			}
	    			seachList(type,null,tempList,"index@"+type);
	    			
	    			PageModel pageModel = new PageModel(tempList, pageSizeNum);
	    			list = pageModel.getObjects(pageNoNum);
	    			
	    		}else{
	    			Page page =  new Page<PmProductInfoDto>(pageNoNum,pageSizeNum);
					map.put("audit", "1");
					map.put("orderFried", "magic_score");
					map.put("isPublic", "1");
					Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
					list = pageResult.getResult();
					seachList(type,null,list,"");
	    		}
	    	}else
	    	{
	    		List<PmProductInfoDto> tempList = (List<PmProductInfoDto>) CacheHelper.getInstance().get("index@"+type);
	    		if(tempList == null || tempList.size() <= 0)
    			{
		    		Map<String,Object> index4Map = new HashMap<String, Object>();
		    		index4Map.put("type",type);
		    		index4Map.put("limit", 18*10);
					index4Map.put("offset", 0);
		    		List<ProductRecommend> reList = productRecommendService.findByMap(index4Map);
		    		List<Long> templist = new ArrayList<Long>();
	    			if(reList != null && reList.size() > 0)
	    			{
	    				for(ProductRecommend productRecommend:reList)
	    				{
	    					templist.add(productRecommend.getProductId());
	    				}
	    			}
	    			if(templist != null && templist.size() > 0)
	    			{
		    			map.put("productList", templist);
		    			map.put("ids",StringUtils.join(templist.toArray(), ","));
		    			
	    				tempList = pmProductInfoService.findDtoMap(map);
	    				
	    				CacheHelper.getInstance().set(60*60*1, "index@"+type, tempList);
	    			}
    			}
	    		seachList(type,null,tempList,"index@"+type);
	    		if(tempList!=null && tempList.size() >0){
	    			PageModel pageModel = new PageModel(tempList, pageSizeNum);
	    			list = pageModel.getObjects(pageNoNum);
	    		}
	    	}
	    	map.put("status", "1");
			map.put("type", "show_banner6&"+type);
			map.put("currentDate", DateUtils.getCurrDateStr());
			int num = bannerService.getCount(map).intValue();
			Banner banObject = null;
			List<CmUserInfoListDto> drObject = new ArrayList<CmUserInfoListDto>();;
			try {
				if(list.size() == pageSizeNum && num > 0)
				{
					if(pageNoNum%2==0)
		    		{
						int drPageNo = ((pageNoNum-1)/2)%num+1;
						Page banPage =  new Page<Banner>(drPageNo, 1);
						Page<Banner> banPageResult = bannerService.findByPage(banPage, map);
						banObject = banPageResult.getResult().get(0);
						banObject.setPicUrl(host+banObject.getPicUrl());
		    		}
		    		else
		    		{
		    			List<CmUserInfoListDto> templist = (List<CmUserInfoListDto>) CacheHelper.getInstance().get("index@drList");
		    			int drPageNo = (pageNoNum/2)%num+1;
		    			if(templist == null || templist.size() <= 0)
		    			{
			    			Map<String,Object> drmap = new HashMap<String, Object>();
			    			drmap.put("type", "1");
			    			List<CmTalenInfo> cmTalenInfoList = cmTalenInfoService.findByMap(drmap);
			    			map.put("limit", num*5+cmTalenInfoList.size());
			    			map.put("offset", 0);
			    			map.put("orderFried", "hot_score");
			    			List<Long> idList = pmProductInfoService.findIdList(map);
			    			if(idList.size() > 0)
			    			{
			    				if(cmTalenInfoList !=null && cmTalenInfoList.size() > 0)
				    			{
				    				for(CmTalenInfo cmTalenInfo:cmTalenInfoList)
				    				{
				    					idList.remove(cmTalenInfo.getUserId());
				    				}
				    			}
				    			if(cmTalenInfoList !=null && cmTalenInfoList.size() > 0)
				    			{
				    				for(CmTalenInfo cmTalenInfo:cmTalenInfoList)
				    				{
				    					if(cmTalenInfo.getTalenSeq()-1>=idList.size())
				    					{
				    						idList.add(cmTalenInfo.getUserId());
				    					}else{
				    						if(cmTalenInfo.getTalenSeq()>1){
				    							idList.add(cmTalenInfo.getTalenSeq()-1, cmTalenInfo.getUserId());
				    						}
				    					}
				    				}
				    			}
				    			map.clear();
				    			map.put("list", idList);
				    			map.put("ids", StringUtils.join(idList.toArray(), ","));
				    			templist = cmUserInfoService.findByList(map);
				    			CacheHelper.getInstance().set(60*60*24, "index@drList", templist);
			    			}
		    			}
		    			PageModel pageModel = new PageModel(templist, 5);
		    			drObject = pageModel.getObjects(drPageNo);
		    		}
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			
			Map<String,Object> reMap = new HashMap<String, Object>();
			reMap.put("banObject", banObject);
			reMap.put("drObject", drObject);
		
			if("5".equals(type))
			{
				if(StringUtil.isNotEmpty(userId)){
					for(PmTopicPsProducts pmTopicPsProducts : pslist){
						map.clear();
						map.put("userId", userId);
						map.put("productId", pmTopicPsProducts.getProductId());
						int praCount = pmProductPraisesService.getCount(map).intValue();
						if(praCount > 0){
							pmTopicPsProducts.setIsZ(1);
						}
					}
				}
				reMap.put("productList", pslist);
			}else{
				if(StringUtil.isNotEmpty(userId)){
					for(PmProductInfoDto pmProductInfoDto : list){
						map.clear();
						map.put("userId", userId);
						map.put("productId", pmProductInfoDto.getProductId());
						int praCount = pmProductPraisesService.getCount(map).intValue();
						if(praCount > 0){
							pmProductInfoDto.setIsZ(1);
						}
					}
				}
				reMap.put("productList", list);
			}
		
			List<Map<String,Object>> rList = new ArrayList<Map<String,Object>>();
			if(list.size() > 0 || pslist.size() > 0)
			{
				rList.add(reMap);
			}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(rList);
	    }catch(Exception e) {
			log.error(e.getMessage());
			log.error(e.getStackTrace().toString());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 作品详细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject detail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		String productId = request.getParameter("productId");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(productId)){
	    		return object;
	    	}
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null){
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
	    	if(cmUserInfo == null){
	    		return object;
	    	}
	    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+1);
	    	pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+1);
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	List<PmProductCollectPic> pmProductCollectPicList = new ArrayList<PmProductCollectPic>();
	    	if(pmProductInfo.getPicCollectFlag()==1){
	    		Map<String, Object> param = new HashMap<String, Object>();
	    		param.put("productId", pmProductInfo.getId());
	    		pmProductCollectPicList = pmProductCollectPicService.findByMap(param);
	    	}
	    	
	    	if(StringUtil.isNotEmpty(userId)){
	    		if(pmProductInfo.getUserId() - Long.parseLong(userId) != 0){
	    			CmUserFans cmUserFans = new CmUserFans();
			    	cmUserFans.setUserId(pmProductInfo.getUserId());
			    	cmUserFans.setFansUserId(Long.parseLong(userId));
			    	
			    	List<CmUserFans> list = cmUserFansService.findAll(cmUserFans);
			    	if(list == null || list.size() <= 0){
			    		cmUserFansService.save(cmUserFans);
				    	
				    	CmUserInfo otherUser = cmUserInfoService.getById(Long.parseLong(userId));
				    	otherUser.setAttentionsCount(otherUser.getAttentionsCount()+1);
				    	cmUserInfoService.update(otherUser);
				    	
				    	cmUserInfo.setFansCount(cmUserInfo.getFansCount()+1);
				    	cmUserInfoService.update(cmUserInfo);
			    	}
	    		}
	    	}
	    	
	    	pmScoreLogService.addScore("14", cmUserInfo, cmUserInfo.getClientId()+"",productId,"");
//	    	cmUserInfoService.update(cmUserInfo);
	    	
	    	PmProductInfoDto pmProductInfoDto = new PmProductInfoDto();
	    	pmProductInfoDto.setCity(cmUserInfo.getCity());
	    	pmProductInfoDto.setUserId(cmUserInfo.getId());
	    	pmProductInfoDto.setClientId(pmProductInfo.getClientId());
	    	pmProductInfoDto.setCreatedWhen(pmProductInfo.getCreatedWhen());
	    	pmProductInfoDto.setDiscussCount(pmProductInfo.getDiscussCount());
	    	pmProductInfoDto.setDownloadCount(pmProductInfo.getDownloadCount());
	    	pmProductInfoDto.setEditCount(pmProductInfo.getEditCount());
	    	pmProductInfoDto.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
	    	pmProductInfoDto.setNickName(cmUserInfo.getNickName());
	    	pmProductInfoDto.setPraiseCount(pmProductInfo.getPraiseCount());
	    	pmProductInfoDto.setProductId(pmProductInfo.getId());
	    	pmProductInfoDto.setProductLabel(pmProductInfo.getProductLabel());
	    	pmProductInfoDto.setSex(cmUserInfo.getSex());
	    	pmProductInfoDto.setShareCount(pmProductInfo.getShareCount());
	    	pmProductInfoDto.setStampCount(pmProductInfo.getStampCount());
	    	pmProductInfoDto.setUrl(host+pmProductInfo.getUrl());
	    	pmProductInfoDto.setThumbnail(host+pmProductInfo.getThumbnail());
	    	pmProductInfoDto.setMachine(cmUserInfo.getMachine());
	    	pmProductInfoDto.setIsC(0);
	    	pmProductInfoDto.setIsZ(0);
	    	pmProductInfoDto.setIsPublic(pmProductInfo.getIsPublic());
	    	pmProductInfoDto.setDescription(HtmlUtils.htmlUnescape(pmProductInfo.getDescription()));
	    	pmProductInfoDto.setDepict(HtmlUtils.htmlUnescape(pmProductInfo.getDescription()));
	    	pmProductInfoDto.setPicCollectCount(pmProductInfo.getPicCollectCount());
	    	
	    	if(pmProductCollectPicList!=null && pmProductCollectPicList.size() > 0){
	    		for (PmProductCollectPic pmProductCollectPic : pmProductCollectPicList) {
	    			pmProductCollectPic.setUrl(host+pmProductCollectPic.getUrl());
	    			pmProductCollectPic.setThumbnail(host+pmProductCollectPic.getThumbnail());
				}
	    	}else{
	    			PmProductCollectPic pmProductCollectPic = new  PmProductCollectPic();
	    			pmProductCollectPic.setUrl(host+pmProductInfo.getUrl());
	    			pmProductCollectPic.setThumbnail(host+pmProductInfo.getThumbnail());
	    			pmProductCollectPicList.add(pmProductCollectPic);
	    	}
	    	
	    	pmProductInfoDto.setPmProductCollectPicList(pmProductCollectPicList);
	    	Map<String, Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("productId", pmProductInfo.getId());
	    	if(StringUtil.isNotEmpty(userId)){
	    		searchMap.put("userId", userId);
	    	}else{
	    		searchMap.put("clientId", clientId);
	    	}
	    	
	    	int count = pmProductPraisesService.getCount(searchMap).intValue();
	    	if(count > 0){
	    		pmProductInfoDto.setIsZ(1);
	    	}else{
	    		pmProductInfoDto.setIsZ(0);
	    	}
	    	
	    	//点赞的人
	    	map.put("type", 0);
	    	map.put("limit", 5);
	    	map.put("productId", pmProductInfo.getId());
			List<Long> seaid = pmProductPraisesService.findIdList(map);
			List<CmUserInfoListDto> list = new ArrayList<CmUserInfoListDto>();
			if(seaid != null && seaid.size() > 0){
				map.clear();
				map.put("list", seaid);
				map.put("ids",StringUtils.join(seaid.toArray(), ","));
				list = cmUserInfoService.findByList(map);
			}
			
			//是否收藏该作品
	    	int iscollect = 0;
	    	if(StringUtil.isNotEmpty(userId)){
	    		Map<String, Object> collectmap = new HashMap<String, Object>();
	    		collectmap.put("userId", userId);
	    		collectmap.put("productId", productId);
	    		collectmap.put("type", 1);
	    		int collCount = cmUserCollectService.getCount(collectmap).intValue();
		    	if(collCount >0 ){
		    		iscollect = 1;
		    	}
	    	}
	    	pmProductInfoDto.setIsCollect(iscollect);
	    	
	    	Map<String,Object> reMap = new HashMap<String, Object>();
	    	reMap.put("pmProductInfoDto", pmProductInfoDto);
	    	reMap.put("userList", list);
	    	object.setResponse(reMap);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage()+"|"+productId);
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 更多赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/moreZ", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject moreZ(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	map.put("limit", pageSizeNum);
	    	map.put("offset", (pageNoNum-1)*10);
	    	
	    	map.put("productId", productId);
	    	//点赞的人
	    	map.put("type", 0);
			List<Long> seaid = pmProductPraisesService.findIdList(map);
			List<CmUserInfoListDto> list = null;
			if(seaid != null && seaid.size() > 0){
				map.put("list", seaid);
				map.put("ids", StringUtils.join(seaid.toArray(), ","));
				list = cmUserInfoService.findByList(map);
				if(list != null && list.size() > 0)
		    	{
		    		for(CmUserInfoListDto cmUserInfoListDto:list)
		    		{
		    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth()))
		    			{
		    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
		    			}
		    		}
		    	}
			}
			Map<String,Object> reMap = new HashMap<String, Object>();
	    	reMap.put("userList", list);
	    	object.setResponse(reMap);
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
	 * 获取评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDiscuss", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getDiscuss(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> map2 = new HashMap<String, Object>();
	    	map2.put("parentDiscussIdTo", "0");
	    	map2.put("productId",productId);
	    	map2.put("limit", 2);
	    	map2.put("sort", "hot_score");
	    	map2.put("offset", 0);
	    	map2.put("hotDiscuss", "hotDiscuss");
	    	List<PmProductDiscussDto> hotList = pmProductDiscussService.findDto(map2);
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("parentDiscussIdTo", "0");
	    	map.put("productId",productId);
	    	map.put("limit", 8);
	    	map.put("sort", "a.reply_time");
	    	map.put("offset", 0);
	    	List<PmProductDiscussDto> newList = pmProductDiscussService.findDto(map);
	    	Map<String,Object> rmMap = new HashMap<String, Object>();
	    	if(hotList != null && hotList.size() > 0)
	    	{
	    		for(PmProductDiscussDto pmProductDiscussDto:hotList)
	    		{
	    			Object zObject = CacheHelper.getInstance().get(clientId+"discussZ"+pmProductDiscussDto.getDiscussId());
	    	    	if(StringUtil.isNotEmpty(zObject))
	    	    	{
	    	    		pmProductDiscussDto.setIsZ(1);
	    	    	}
	    	    	if(StringUtil.isNotEmpty(pmProductDiscussDto.getImageUrl()))
	    	    	{
	    	    		pmProductDiscussDto.setImageUrl(host+pmProductDiscussDto.getImageUrl());
	    	    		pmProductDiscussDto.setThumbnail(pmProductDiscussDto.getImageUrl().replaceAll("userImage", "thumbnailImage"));
	    	    		pmProductDiscussDto.setContent(HtmlUtils.htmlUnescape(pmProductDiscussDto.getContent()));
	    	    	}
	    		}
	    	}
	    	if(newList != null && newList.size() > 0)
	    	{
	    		for(PmProductDiscussDto pmProductDiscussDto:newList)
	    		{
	    			Object zObject = CacheHelper.getInstance().get(clientId+"discussZ"+pmProductDiscussDto.getDiscussId());
	    	    	if(StringUtil.isNotEmpty(zObject))
	    	    	{
	    	    		pmProductDiscussDto.setIsZ(1);
	    	    	}
	    	    	if(StringUtil.isNotEmpty(pmProductDiscussDto.getImageUrl()))
	    	    	{
	    	    		pmProductDiscussDto.setImageUrl(host+pmProductDiscussDto.getImageUrl());
	    	    		pmProductDiscussDto.setThumbnail(pmProductDiscussDto.getImageUrl().replaceAll("userImage", "thumbnailImage"));
	    	    		pmProductDiscussDto.setContent(HtmlUtils.htmlUnescape(pmProductDiscussDto.getContent()));
	    	    	}
	    		}
	    	}
	    	rmMap.put("hotList", hotList);
	    	rmMap.put("newList", newList);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(rmMap);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 获取更多二级评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllTwoDiscuss", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getAllTwoDiscuss(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String discussId = request.getParameter("discussId");
	    	if(StringUtil.isEmpty(discussId))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("parentDiscussId", discussId);
	    	map.put("limit",300);
	    	map.put("offset", 0);
	    	List<TwoDiscussDto> list =pmProductDiscussService.findDtoTo(map);
	    	if(list != null && list.size() > 0)
	    	{
	    		for(TwoDiscussDto twoDiscussDto:list){
	    			if(StringUtil.isNotEmpty(twoDiscussDto.getImageUrl()))
	    	    	{
	    				twoDiscussDto.setImageUrl(host+twoDiscussDto.getImageUrl());
	    				twoDiscussDto.setThumbnail(twoDiscussDto.getImageUrl().replaceAll("userImage", "thumbnailImage"));
	    	    	}
	    		}
	    	}
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 获取更多评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getAllDiscuss", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getAllDiscuss(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "8";
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("parentDiscussIdTo", "0");
	    	map.put("productId",productId);
	    	map.put("sort", "a.reply_time");
	    	Page page =  new Page<PmProductDiscussDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page<PmProductDiscussDto> pageResult = pmProductDiscussService.findByPageTo(page, map);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			List<PmProductDiscussDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
	    	{
	    		for(PmProductDiscussDto pmProductDiscussDto:list)
	    		{
	    			Object zObject = CacheHelper.getInstance().get(clientId+"discussZ"+pmProductDiscussDto.getDiscussId());
	    	    	if(StringUtil.isNotEmpty(zObject))
	    	    	{
	    	    		pmProductDiscussDto.setIsZ(1);
	    	    	}
	    	    	if(StringUtil.isNotEmpty(pmProductDiscussDto.getImageUrl()))
	    	    	{
	    	    		pmProductDiscussDto.setImageUrl(host+pmProductDiscussDto.getImageUrl());
	    	    		pmProductDiscussDto.setThumbnail(pmProductDiscussDto.getImageUrl().replaceAll("userImage", "thumbnailImage"));
	    	    		pmProductDiscussDto.setContent(HtmlUtils.htmlUnescape(pmProductDiscussDto.getContent()));
	    	    	}
	    		}
	    	}
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * banner
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/banner", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject banner(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("status", "1");
			map.put("type", "1");
			List<Banner> list = bannerService.findByMap(map);
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 看别人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/seeOthers", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject seeOthers(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		String userId = request.getParameter("userId");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
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
	    	dto.setLevel(cmUserInfo.getLevel());
	    	
	    	dto.setPriLevel(cmUserInfo.getPrivilegeLevel());
	    	map.put("userId", userId);
	    	map.put("valid", 1);
	    	List<CmUserPrivilege> cmUserPrivilegeList =  cmUserPrivilegeService.findByMap(map);
	    	if(cmUserPrivilegeList != null && cmUserPrivilegeList.size()>0 ){
	    		for (CmUserPrivilege cmUserPrivilege : cmUserPrivilegeList) {
	    			cmUserPrivilege.setImageUrl(host+cmUserPrivilege.getImageUrl());
    			}
	    		dto.setCmUserPrivilegeList(cmUserPrivilegeList);
	    	}
	    	
	    	//是否收藏该作者
	    	int iscollect = 0;
	    	String myUserId = request.getParameter("myUserId");
	    	if(StringUtil.isNotEmpty(myUserId))
	    	{
	    		Map<String, Object> collectmap = new HashMap<String, Object>();
	    		collectmap.put("userId", myUserId);
	    		collectmap.put("authorId", userId);
	    		collectmap.put("type", 2);
		    	List<CmUserCollect>  cmUserCollectList = cmUserCollectService.findByMap(collectmap);
		    	if(cmUserCollectList != null && cmUserCollectList.size() >0 ){
		    		iscollect = 1;
		    	}
	    	}
	    	dto.setIsCollect(iscollect);
	    	
	    	
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
	    	map.put("isRead", "0");
	    	List<CmUserMessage> meList = cmUserMessageService.findByMap(map);
	    	if(meList != null && meList.size() > 0)
	    	{
	    		dto.setIsRead(1);
	    	}
	    	map.clear();
	    	map.put("userId", userId);
	    	map.put("isAccusation", 0);//未举报
			map.put("status", 1);
			int circlePostCount = circlePostService.getCount(map).intValue();
			dto.setCirclePostCount(circlePostCount);//帖子数量
			
			map.clear();
	    	map.put("userId", userId);
			int collectionNum = cmUserCollectService.getCount(map).intValue();
			dto.setCollectionNum(collectionNum);//收藏数量
	    	
	    	Map<String,Object> rmap = new HashMap<String, Object>();
	    	rmap.put("userDetail", dto);
	    	map.put("limit", 5);
	    	map.put("offset", 0);
	    	List<CmUserInfoListDto> dtolist = cmUserFansService.findDto(map);
	    	rmap.put("fansList", dtolist);
	    	
	    	
	    	
	    	pmScoreLogService.addScore("12", cmUserInfo, cmUserInfo.getClientId()+"","","");
	    	cmUserInfoService.update(cmUserInfo);
	    	
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(rmap);
	    }catch(Exception e) {
			log.error(e.getMessage()+"userId="+userId);
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 更多粉丝
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allFans", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject allFans(HttpServletRequest request) {
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
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	map.put("userId", userId);
			Page page =  new Page<CmUserInfoListDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page<CmUserInfoListDto> pageResult = cmUserFansService.findDtoPage(page,map);
	    	List<CmUserInfoListDto> list = pageResult.getResult();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(CmUserInfoListDto cmUserInfoListDto:list)
	    		{
	    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth()))
	    			{
	    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
	    			}
	    		}
	    	}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 我的作品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/productList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject productList(HttpServletRequest request) {
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
	    	String operateUserId = request.getParameter("operateUserId");
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page page =  new Page<PmProductInfoDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			if(cmUserInfo.getUserType() == 2)
			{
				map.put("drawaudit", "1");
			}else{
				if(StringUtil.isNotEmpty(operateUserId))
				{
					if(Long.parseLong(operateUserId) - Long.parseLong(userId) == 0)
					{
						map.put("myaudit", "1");
					}else{
						map.put("audit", "1");
						map.put("isPublic", "1");
					}
				}else{
					if(StringUtil.isNotEmpty(cmUserInfo.getClientId()) && cmUserInfo.getClientId() - Long.parseLong(clientId) == 0)
					{
						map.put("myaudit", "1");
					}else{
						map.put("audit", "1");
						map.put("isPublic", "1");
					}
				}
			}
			
				
			map.put("userId", userId);
			map.put("orderFried", "a.createdWhen");
			Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
		
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			List<PmProductInfoDto> list = pageResult.getResult();
			for(PmProductInfoDto pmProductInfoDto:list)
			{
				pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
				pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
				pmProductInfoDto.setDepict(pmProductInfoDto.getDescription());
			}
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 我的评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myDiscuss", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myDiscuss(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", userId);
	    	searchMap.put("audit", "1");
	    	searchMap.put("isPublic", "1");
	    	searchMap.put("parentDiscussIdTo", "1");
	    	searchMap.put("limit", page.getPageSize());
	    	searchMap.put("offset",page.getOffset());
	    	List<MyDiscussDto> list = pmProductDiscussService.findMyDiscuss(searchMap);
	    	if(list != null && list.size() > 0)
			{
				for(MyDiscussDto myDiscussDto:list)
				{
					myDiscussDto.setThumbnail(host+myDiscussDto.getThumbnail());
					for(PmProductDiscuss pmProductDiscuss:myDiscussDto.getList())
					{
						pmProductDiscuss.setContent(HtmlUtils.htmlUnescape(pmProductDiscuss.getContent()));
					}
				}
			}
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 举报图片
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reportImage", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject reportImage(HttpServletRequest request) {
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
	    	CmUserInfo reportCmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(reportCmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	if(reportCmUserInfo.getIsAccusation() == 1)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("被举报不能使用");
				return object;
	    	}
	    	if(reportCmUserInfo.getAccuErrCount() > 3) 
	    	{
	    		if( !(reportCmUserInfo.getId().toString().equals("655090") || reportCmUserInfo.getId().toString().equals("269215") || reportCmUserInfo.getId().toString().equals("485031") )  ){
	    			object.setCode(Constant.RESPONSE_ERROR_10003);
	    			object.setDesc("暂时无法使用举报功能，请联系X秀确认");
	    			return object;
	    		}
	    	}
	    	
	    	
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> grmap = new HashMap<String, Object>();
			grmap.put("groupId", "6");
			List<CmUserGroup> grouplist = cmUserGroupService.findByMap(grmap);
			List<String> userlist  = new ArrayList<String>();
			boolean yyUser = false;
			if(grouplist != null && grouplist.size() > 0)
			{
				for(CmUserGroup cmUserGroup:grouplist)
				{
					if(reportCmUserInfo.getId() - cmUserGroup.getUserId() == 0){
						yyUser = true;
					}
					userlist.add(cmUserGroup.getUserId() + "");
				}
			}
	    	
			if(!yyUser){
				if(reportCmUserInfo.getAccuCount() >= 3){
					object.setCode(Constant.RESPONSE_ERROR_10003);
					object.setDesc("亲，连续举报3次将暂停举报功能一小会，我们紧急确认后将恢复。");
					return object;
				}
			}
		
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("productId", productId);
	    	searchMap.put("accusationUserId", userId);
	    	searchMap.put("isDistortTo", "0");
	    	List<PmProductAccusation> list = pmProductAccusationService.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("已举报");
				return object;
	    	}
	    	PmProductAccusation pmProductAccusation = new PmProductAccusation();
	    	pmProductAccusation.setAccusationUserId(Long.parseLong(userId));
	    	pmProductAccusation.setClientId(Long.parseLong(clientId));
	    	pmProductAccusation.setProductId(Long.parseLong(productId));
	    	pmProductAccusation.setType(type);
	    	pmProductAccusation.setUserId(pmProductInfo.getUserId());
	    	pmProductAccusation.setProductUrl(pmProductInfo.getUrl());
	    	pmProductAccusationService.save(pmProductAccusation);
	    	

	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
	    	
	    	// //2016年5月26日    普通会员直接下线  会员改为审核后下线  -- 原始将作品直接设置为举报，即下线。
	    	if(cmUserInfo.getPrivilegeLevel()==null || cmUserInfo.getPrivilegeLevel().trim().equals(SysParamConstant.PRI_LEVEL_GENERAL)  ){
	    		cmUserInfo.setIsAccusation(1);
	    		cmUserInfo.setProductCount(cmUserInfo.getProductCount()-1);
	    		cmUserInfoService.update(cmUserInfo);
		    	pmProductInfo.setAudit(2);
		    	pmProductInfoService.update(pmProductInfo);
	    	}
	    	
	    	
	    	if(pmProductInfo.getIsPublic() == 2){
    			Map<String,Object> joinMap = new HashMap<String, Object>();
    			if(pmProductInfo.getCreateType() == 1){
    				
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicPsProducts> psList = pmTopicPsProductsService.findByMap(joinMap);
    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
    				
    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", reportCmUserInfo.getNickName()+Constant.J_STRING, "30",pmTopicPsProducts.getJoinId()+"", "");
    			}else if(pmProductInfo.getCreateType() == 0){
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsService.findByMap(joinMap);
    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
    				
    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", reportCmUserInfo.getNickName()+Constant.J_STRING, "30", pmTopicJoinProducts.getId()+"", "");
    			}
    		}else{
    			if(cmUserInfo.getPrivilegeLevel().trim().equals(SysParamConstant.PRI_LEVEL_GENERAL) ){
    				CmUserMessage cmUserMessage = new CmUserMessage();
    				String content = "你与"+DateUtils.getYear()+"年"+DateUtils.getMonth()+"月"+DateUtils.getDay()+"日发送的图片被举报，暂时下线，等待审核通知";
    				cmUserMessage.setContent(content);
    				cmUserMessage.setType(6);
    				cmUserMessage.setProductId(pmProductInfo.getId());
    				cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
    				cmUserMessage.setUserId(pmProductInfo.getUserId());
    				cmUserMessage.setSendUserId(Long.parseLong(userId));
    				cmUserMessageService.save(cmUserMessage);
    				
    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", reportCmUserInfo.getNickName()+Constant.J_STRING, "9", pmProductInfo.getId()+"", "");
    			}
    		}
	    	
	    	reportCmUserInfo.setAccuCount(reportCmUserInfo.getAccuCount()+1);
    		cmUserInfoService.update(reportCmUserInfo);
    		
			PushUtil.pushAccountListMultiple(userlist,"亲，有作品被举报，请及时处理！", "18", "", "");
	    	
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
	 * 作品点赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/zProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject zProduct(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String userId = request.getParameter("userId");

	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("productId", productId);
	    	searchMap.put("type", type);
	    	if(StringUtil.isNotEmpty(userId))
	    	{
	    		searchMap.put("userId", userId);
	    	}else{
	    		searchMap.put("clientId", clientId);
	    	}
	    	List<PmProductPraises> list = pmProductPraisesService.findByMap(searchMap);
	    	PmProductPraises pmProductPraises = null;
	    	if(list != null && list.size() > 0)
	    	{
	    		if("0".equals(type))
		    	{
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("你已经赞过 ^_^");
					return object;
		    	}else if("1".equals(type))
		    	{
		    		pmProductPraises = list.get(0);
		    		pmProductPraisesService.delete(pmProductPraises);
		    		
		    		pmProductInfo.setStampCount(pmProductInfo.getStampCount()-1);
			    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2);
			    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()-2);
			    	pmProductInfoService.update(pmProductInfo);
		    	}
	    	}else{
	    		pmProductPraises = new PmProductPraises();
		    	if(StringUtil.isNotEmpty(userId))
		    	{
		    		pmProductPraises.setPraiseUserId(Long.parseLong(userId));
		    	}
		    	pmProductPraises.setClientId(Long.parseLong(clientId));
		    	pmProductPraises.setProductId(Long.parseLong(productId));
		    	pmProductPraises.setType(Integer.parseInt(type));
		    	pmProductPraisesService.save(pmProductPraises);

		    	CmUserInfo oldCmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
		    	if("0".equals(type))
		    	{
		    		pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+1);
			    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2);
			    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2);
			    	
			    	
			    	pmScoreLogService.addScore("15", oldCmUserInfo, oldCmUserInfo.getClientId()+"", pmProductInfo.getId()+"","");
			    	
			    	oldCmUserInfo.setPraisesCount(oldCmUserInfo.getPraisesCount()+1);
			    	cmUserInfoService.update(oldCmUserInfo);
			    	
		    	}else if("1".equals(type))
		    	{
		    		pmProductInfo.setStampCount(pmProductInfo.getStampCount()+1);
			    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()-2);
			    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2);
		    	}
		    	pmProductInfoService.update(pmProductInfo);
		    	
		    	if(StringUtil.isNotEmpty(userId))
		    	{
		    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
			    	if(cmUserInfo == null)
			    	{
			    		return object;
			    	}
			    	
			    	if(pmProductInfo.getIsPublic() == 2){
			    		if("0".equals(type))
				    	{
			    			Map<String,Object> joinMap = new HashMap<String, Object>();
			    			if(pmProductInfo.getCreateType() == 1){
			    				
			    				joinMap.put("productId", pmProductInfo.getId());
			    				List<PmTopicPsProducts> psList = pmTopicPsProductsService.findByMap(joinMap);
			    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
			    				
//			    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.Z_STRING, "26", pmTopicPsProducts.getJoinId()+"", "");
			    			}else if(pmProductInfo.getCreateType() == 0){
			    				joinMap.put("productId", pmProductInfo.getId());
			    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsService.findByMap(joinMap);
			    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
			    				
//			    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.Z_STRING, "26", pmTopicJoinProducts.getId()+"", "");
			    			}
				    	}
		    		}else{
		    			
		    			//添加消息
				    	CmUserMessage cmUserMessage = new CmUserMessage();
				    	if("0".equals(type))
				    	{
				    		cmUserMessage.setContent("赞了你的作品");
				    		cmUserMessage.setType(1);
				    		
				    		pmScoreLogService.addScore("19", cmUserInfo, clientId, pmProductInfo.getId()+"","");
				    		
//				    		PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.Z_STRING, "11", pmProductInfo.getId()+"", "");
				    	}else if("1".equals(type))
				    	{
				    		cmUserMessage.setContent("踩了你的作品");
				    		cmUserMessage.setType(2);
				    		
				    		pmScoreLogService.addScore("20", cmUserInfo, clientId, pmProductInfo.getId()+"","");
				    		
//				    		PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.C_STRING, "6", pmProductInfo.getId()+"", "");
				    	}
				    	cmUserInfoService.update(cmUserInfo);
				    	
				    	cmUserMessage.setProductId(pmProductInfo.getId());
				    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
				    	cmUserMessage.setUserId(pmProductInfo.getUserId());
				    	cmUserMessage.setSendUserId(Long.parseLong(userId));
				    	cmUserMessageService.save(cmUserMessage);
		    		}
		    	}
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
	 * 作品评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/discussProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject discussProduct(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	
	    	String content = request.getParameter("content");
	    	if(StringUtil.isEmpty(content))
	    	{
	    		content="";
	    	}
	    	
	    	String type = request.getParameter("type");
	    	String imageUrl = request.getParameter("imageUrl");
	    	String mrIds = request.getParameter("mrIds");
	    	
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
	    	{
	    		return object;
	    	}else{
	    		if(pmProductInfo.getUserId().toString().equals("484950")){//x秀官方
	    			object.setCode(Constant.RESPONSE_ERROR_10006);
					object.setDesc("禁止评论");
					return object;
	    		}
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
	    	if(cmUserInfo.getIsBan() == 1)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("禁止评论");
				return object;
	    	}
	    	//设备号 在黑名单中
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	if(StringUtil.isNotEmpty(cmUserInfo.getImei()))
	    	{
	    		map.put("content", cmUserInfo.getImei());
	    		int count = blackListUserEquipmentService.checkContent(map).intValue();
		    	if(count>0)
		    	{
		    		object.setCode(Constant.RESPONSE_ERROR_10006);
		    		object.setDesc("禁止评论");
		    		return object;
		    	}
	    	}
	    	//三分钟内 只能评论三条
	    	map.clear();
	    	map.put("discussUserIdOfThree", userId);
	    	int num3 = pmProductDiscussService.findDiscussCount(map).intValue();
	    	if(num3 >= 3){
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
	    		object.setDesc("评论过快，歇歇看看美图吧");
	    		return object;
	    	}
	    	
	    	//一天内发表了相同的评论
	    	map.clear();
	    	map.put("discussUserIdOfOneDay", userId);
	    	map.put("content", content);
	    	int numOneDay = pmProductDiscussService.findDiscussCount(map).intValue();
	    	if(numOneDay >= 1){
	    		object.setCode(Constant.RESPONSE_ERROR_10007);
	    		object.setDesc("一天内不允许发表多条相同的评论");
	    		return object;
	    	}
	    	
	    	List<BlackListWord> list = blackListService.findAll();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(BlackListWord blackListWord:list)
	    		{
	    			if(content.indexOf(blackListWord.getContent()) != -1){
	    				object.setCode(Constant.RESPONSE_ERROR_10006);
	    				object.setDesc("你输入的内容含有敏感词");
	    				return object;
	    			}
	    		}
	    	}
	    	
	    	PmProductDiscuss pmProductDiscuss = new PmProductDiscuss();
	    	pmProductDiscuss.setProductId(Long.parseLong(productId));
	    	pmProductDiscuss.setUserId(pmProductInfo.getUserId());
	    	pmProductDiscuss.setDiscussUserId(Long.parseLong(userId));
	    	pmProductDiscuss.setContent(HtmlUtils.htmlEscape(content));
	    	pmProductDiscuss.setParentDiscussId(0L);
	    	if(StringUtil.isNotEmpty(type))
	    	{
	    		pmProductDiscuss.setType(Integer.parseInt(type));
	    		pmProductDiscuss.setImageUrl(imageUrl);
	    	}
	    	pmProductDiscussService.save(pmProductDiscuss);
	    	if(StringUtil.isNotEmpty(type) && StringUtil.isNotEmpty(mrIds))
	    	{
	    		List<MaterialUseLog> mul = new ArrayList<MaterialUseLog>();
	    		String materialResIds[] = mrIds.split(",");
    			for(String materialResId:materialResIds)
    			{
    				MaterialUseLog materialUseLog = new MaterialUseLog();
    				materialUseLog.setMaterialResId(Long.parseLong(materialResId));
    				materialUseLog.setType("1");
    				materialUseLog.setMaterialType("3");
    				materialUseLog.setOtherId(pmProductDiscuss.getId()+"");
    				mul.add(materialUseLog);
    			}
    			
    			if(mul.size() > 0)
			  	{
			  		materialUseLogService.insertList(mul);
			  	}
	    	}
	    	pmProductInfo.setDiscussCount(pmProductInfo.getDiscussCount()+1);
	    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2);
	    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+10);
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	cmUserInfo.setDiscussCount(cmUserInfo.getDiscussCount()+1);
	    	
	    	pmScoreLogService.addScore("22", cmUserInfo, clientId, pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfo);
	    	
	    	CmUserInfo cmUserInfoTo = cmUserInfoService.getById(pmProductInfo.getUserId());
	    	pmScoreLogService.addScore("17", cmUserInfoTo, cmUserInfoTo.getClientId()+"", pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfoTo);
	    	
	    	if(pmProductInfo.getIsPublic() == 2){
    			Map<String,Object> joinMap = new HashMap<String, Object>();
    			if(pmProductInfo.getCreateType() == 1){
    				
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicPsProducts> psList = pmTopicPsProductsService.findByMap(joinMap);
    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
    				
//    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.P_STRING, "28", pmTopicPsProducts.getJoinId()+"", "");
    			}else if(pmProductInfo.getCreateType() == 0){
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsService.findByMap(joinMap);
    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
    				
//    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.P_STRING, "28", pmTopicJoinProducts.getId()+"", "");
    			}
    		}else{
	    	
		    	CmUserMessage cmUserMessage = new CmUserMessage();
		    	cmUserMessage.setContent(content);
	    		cmUserMessage.setType(4);
	    		cmUserMessage.setRelId(pmProductDiscuss.getId());
		    	cmUserMessage.setProductId(pmProductInfo.getId());
		    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
		    	cmUserMessage.setUserId(pmProductInfo.getUserId());
		    	cmUserMessage.setSendUserId(Long.parseLong(userId));
		    	cmUserMessageService.save(cmUserMessage);
		    	
//		    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.P_STRING, "12", pmProductInfo.getId()+"", "");
    		}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("你输入的内容含有敏感词");
		}
	    return object;
	}
	
	/**
	 * 二次编辑
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/twoEdit", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject twoEdit(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String productId = request.getParameter("productId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
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
	    	pmProductInfo.setEditCount(pmProductInfo.getEditCount()+1);
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	pmScoreLogService.addScore("4", cmUserInfo, clientId, pmProductInfo.getId()+"","");
	    	pmScoreLogService.addScore("1017", cmUserInfo, clientId, pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfo);
	    	
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
	 * 下载数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/downProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject downProduct(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
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
	    	pmProductInfo.setDownloadCount(pmProductInfo.getDownloadCount()+1);
	    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+3);
	    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+3);
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	
	    	pmScoreLogService.addScore("23", cmUserInfo, clientId, pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfo);
	    	
	    	CmUserInfo cmUserInfoTo = cmUserInfoService.getById(pmProductInfo.getUserId());
	    	pmScoreLogService.addScore("18", cmUserInfoTo, cmUserInfoTo.getClientId()+"", pmProductInfo.getId()+"","");
	    	cmUserInfoService.update(cmUserInfoTo);
	    	
	    	
	    	if(pmProductInfo.getIsPublic() == 2){
    			Map<String,Object> joinMap = new HashMap<String, Object>();
    			if(pmProductInfo.getCreateType() == 1){
    				
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicPsProducts> psList = pmTopicPsProductsService.findByMap(joinMap);
    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
    				
    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.X_STRING, "29",pmTopicPsProducts.getJoinId()+"", "");
    			}else if(pmProductInfo.getCreateType() == 0){
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsService.findByMap(joinMap);
    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
    				
    				PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.X_STRING, "29",pmTopicJoinProducts.getId()+"", "");
    			}
    		}else{
		    	CmUserMessage cmUserMessage = new CmUserMessage();
		    	cmUserMessage.setContent("下载了你的作品");
	    		cmUserMessage.setType(5);
		    	cmUserMessage.setProductId(pmProductInfo.getId());
		    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
		    	cmUserMessage.setUserId(pmProductInfo.getUserId());
		    	cmUserMessage.setSendUserId(Long.parseLong(userId));
		    	cmUserMessageService.save(cmUserMessage);
		    	
		    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.X_STRING, "8", pmProductInfo.getId()+"", "");
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
	 * 评论回复
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/discussReply", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject discussReply(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String receiveUserId = request.getParameter("receiveUserId");
	    	if(StringUtil.isEmpty(receiveUserId))
	    	{
	    		return object;
	    	}
	    	String discussId = request.getParameter("discussId");
	    	if(StringUtil.isEmpty(discussId))
	    	{
	    		return object;
	    	}
	    	
	    	String content = request.getParameter("content");
	    	if(StringUtil.isEmpty(content))
	    	{
	    		return object;
	    	}
	    	
	    	PmProductDiscuss pmProductDiscuss = pmProductDiscussService.getById(Long.parseLong(discussId));
	    	if(pmProductDiscuss == null)
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
	    	if(cmUserInfo.getIsBan() == 1)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("禁止评论");
				return object;
	    	}
	    	List<BlackListWord> list = blackListService.findAll();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(BlackListWord blackListWord:list)
	    		{
	    			if(content.indexOf(blackListWord.getContent()) != -1){
	    				object.setCode(Constant.RESPONSE_ERROR_10006);
	    				object.setDesc("你输入的内容含有敏感词");
	    				return object;
	    			}
	    		}
	    	}
	    	PmProductDiscuss newPmProductDiscuss = new PmProductDiscuss();
	    	newPmProductDiscuss.setProductId(pmProductDiscuss.getProductId());
	    	newPmProductDiscuss.setUserId(pmProductDiscuss.getUserId());
	    	newPmProductDiscuss.setDiscussUserId(Long.parseLong(userId));
	    	newPmProductDiscuss.setContent(HtmlUtils.htmlEscape(content));
	    	newPmProductDiscuss.setLevel(2);
	    	newPmProductDiscuss.setReplyUserId(Long.parseLong(receiveUserId));
	    	newPmProductDiscuss.setParentDiscussId(pmProductDiscuss.getId());
	    	pmProductDiscussService.save(newPmProductDiscuss);
	    	
	    	pmProductDiscuss.setReplys(pmProductDiscuss.getReplys()+1);
	    	pmProductDiscuss.setHotScore(pmProductDiscuss.getHotScore()+2);
	    	pmProductDiscuss.setReplyTime(DateUtils.getCurrDateTimeStr());
	    	pmProductDiscussService.update(pmProductDiscuss);
	    	
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(pmProductDiscuss.getProductId());
	    
	    	if(pmProductInfo.getIsPublic() == 2){
    			Map<String,Object> joinMap = new HashMap<String, Object>();
    			if(pmProductInfo.getCreateType() == 1){
    				
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicPsProducts> psList = pmTopicPsProductsService.findByMap(joinMap);
    				PmTopicPsProducts pmTopicPsProducts = psList.get(0);
    				
    				PushUtil.pullOneMessage(receiveUserId, cmUserInfo.getNickName()+Constant.H_STRING, "25", pmTopicPsProducts.getJoinId()+"", "");
    			}else if(pmProductInfo.getCreateType() == 0){
    				joinMap.put("productId", pmProductInfo.getId());
    				List<PmTopicJoinProducts> joinList = pmTopicJoinProductsService.findByMap(joinMap);
    				PmTopicJoinProducts pmTopicJoinProducts = joinList.get(0);
    				
    				PushUtil.pullOneMessage(receiveUserId, cmUserInfo.getNickName()+Constant.H_STRING, "25", pmTopicJoinProducts.getId()+"", "");
    			}
    		}else{
    			PushUtil.pullOneMessage(receiveUserId, cmUserInfo.getNickName()+Constant.H_STRING, "17", pmProductDiscuss.getProductId()+"", "");
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
	 * 评论点赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/discussZ", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject discussZ(HttpServletRequest request) {
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
	    	String discussId = request.getParameter("discussId");
	    	if(StringUtil.isEmpty(discussId))
	    	{
	    		return object;
	    	}
	    	
	    	PmProductDiscuss pmProductDiscuss = pmProductDiscussService.getById(Long.parseLong(discussId));
	    	if(pmProductDiscuss == null)
	    	{
	    		return object;
	    	}
	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	
	    	Object zObject = CacheHelper.getInstance().get(clientId+"discussZ"+discussId);
	    	if(StringUtil.isNotEmpty(zObject))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你已经赞过 ^_^");
				return object;
	    	}
	    	
	    	pmProductDiscuss.setPraises(pmProductDiscuss.getPraises()+1);
	    	pmProductDiscuss.setHotScore(pmProductDiscuss.getHotScore()+1);
	    	pmProductDiscussService.update(pmProductDiscuss);
	    	
	    	CachedValueAndTime(clientId+"discussZ"+discussId, "1", 48);
	    	
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
	 * 发布到大厅
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/release", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject release(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,String> map = new HashMap<String, String>();
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		return object;
	    	}
	    	map.put("userId", userId);
	    	String resourceId = request.getParameter("resourceId");
	    	if(StringUtil.isEmpty(resourceId)){
	    		return object;
	    	}
	    	if(StringUtil.isEmpty(resourceId)){
	    		return object;
	    	}
	    	map.put("resourceId", resourceId);
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	if(StringUtil.isNotEmpty(clientId)){
	    		map.put("clientId", clientId);
	    	}
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	if(StringUtil.isNotEmpty(type)){
	    		map.put("type", type);
	    	}
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isNotEmpty(circleId)){
	    		map.put("circleId", circleId);
	    	}
	    	
	    	return pmProductInfoService.releaseProduct(object,map);
	    	
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 发布作品集到大厅
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/releaseProductCollect", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject releaseProductCollect(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	log.error("发布图集");
	    	Map<String,String> map = new HashMap<String, String>();
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		return object;
	    	}
	    	map.put("userId", userId);
	    	String resourceId = request.getParameter("resourceId");
	    	if(StringUtil.isEmpty(resourceId)){
	    		return object;
	    	}
	    	map.put("resourceId", resourceId);
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	map.put("clientId", clientId);
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	map.put("type", type);
	    	
	    	String description = request.getParameter("description");
	    	if(StringUtil.isEmpty(description)){
	    		return object;
	    	}
	    	map.put("description", description);
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber)){
	    		return object;
	    	}
	    	map.put("laber", laber);
	    	
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isNotEmpty(circleId)){
	    		map.put("circleId", circleId);
	    	}
	    	
			object = pmProductInfoService.uploadResource(map, request, response, object);
			if( object != null ){
				return object;
			}
			log.error("保存图片");
			object = initResponseObject();
	    	object = pmProductInfoService.releaseProductList(object,map);
	    	return object;
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 搜索作品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchPru", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject searchPru(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page page =  new Page<PmProductInfoDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("audit", "1");
			map.put("isPublic", "1");
			map.put("laber", laber);
			map.put("orderFried", "createdwhen");
			Page<PmProductInfoDto> pageResult = pmProductInfoService.findByPageDto(page, map);
		
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			List<PmProductInfoDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmProductInfoDto pmProductInfoDto:list)
				{
					pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
					pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
					pmProductInfoDto.setDepict(pmProductInfoDto.getDescription());
				}
			}
			
			SysBackupLabels sysBackupLabels = new SysBackupLabels();
			sysBackupLabels.setClientId(Long.parseLong(clientId));
			sysBackupLabels.setName(laber);
			List<SysBackupLabels> laberlist = sysBackupLabelsService.findAll(sysBackupLabels);
			if(laberlist == null || laberlist.size() <= 0)
			{
				sysBackupLabelsService.save(sysBackupLabels);
			}else{
				SysBackupLabels newSysBackupLabels = laberlist.get(0);
				newSysBackupLabels.setNum(newSysBackupLabels.getNum()+1);
				sysBackupLabelsService.update(newSysBackupLabels);
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
	 * 搜索作者
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject searchUser(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page page =  new Page<CmUserInfoListDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("attentionLabel", laber);
			Page<CmUserInfoListDto> pageResult = cmUserInfoService.findDtoByMap(page, map);
		
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			List<CmUserInfoListDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(CmUserInfoListDto cmUserInfoListDto:list)
	    		{
	    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth()))
	    			{
	    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
	    			}
	    		}
			}
			
			SysBackupLabels sysBackupLabels = new SysBackupLabels();
			sysBackupLabels.setClientId(Long.parseLong(clientId));
			sysBackupLabels.setName(laber);
			List<SysBackupLabels> laberlist = sysBackupLabelsService.findAll(sysBackupLabels);
			if(laberlist == null || laberlist.size() <= 0)
			{
				sysBackupLabelsService.save(sysBackupLabels);
			}else{
				SysBackupLabels newSysBackupLabels = laberlist.get(0);
				newSysBackupLabels.setNum(newSysBackupLabels.getNum()+1);
				sysBackupLabelsService.update(newSysBackupLabels);
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
	 * 热门标签
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/hotLaber", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject hotLaber(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	List<SysHotLabels> list = sysHotLabelsService.findAll();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(SysHotLabels sysHotLabels:list)
	    		{
	    			sysHotLabels.setCover(host+sysHotLabels.getCover());
	    		}
	    	}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 减少标签
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lessenLaber", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject lessenLaber(HttpServletRequest request) {
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
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(userId));
	    	if(StringUtil.isNotEmpty(cmUserExtendInfo.getAttentionLabel()))
	    	{
	    		List<String> test = new ArrayList<String>();  
	    		for(String t : cmUserExtendInfo.getAttentionLabel().split(",")){  
	    		        test.add(t);  
	    		}  
	    		test.remove(laber);
	    		cmUserExtendInfo.setAttentionLabel(StringUtils.join(test.toArray(), ","));
	    		cmUserExtendInfoService.update(cmUserExtendInfo);
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
	    	if(StringUtil.isNotEmpty(cmUserInfo.getBirth()))
	    	{
	    		dto.setAge(DateUtils.getAge(cmUserInfo.getBirth())+"");
	    	}
	    	Object zObject = CacheHelper.getInstance().get(clientId+"Cover"+userId);
	    	if(StringUtil.isNotEmpty(zObject))
	    	{
	    		dto.setIsZ(1);
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("userId", userId);
	    	map.put("isRead", "0");
	    	List<CmUserMessage> meList = cmUserMessageService.findByMap(map);
	    	if(meList != null && meList.size() > 0)
	    	{
	    		dto.setIsRead(1);
	    	}
	    	object.setResponse(dto);
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
	 * 增加标签
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addLaber", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addLaber(HttpServletRequest request) {
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
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String attentionLabel ="";
	    	CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(userId));
	    	if(StringUtil.isNotEmpty(cmUserExtendInfo.getAttentionLabel()))
	    	{
	    		attentionLabel = cmUserExtendInfo.getAttentionLabel()+","+laber;
	    	}else{
	    		attentionLabel = laber;
	    	}
	    	cmUserExtendInfo.setAttentionLabel(attentionLabel);
    		cmUserExtendInfoService.update(cmUserExtendInfo);
    		
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
	    	if(StringUtil.isNotEmpty(cmUserInfo.getBirth()))
	    	{
	    		dto.setAge(DateUtils.getAge(cmUserInfo.getBirth())+"");
	    	}
	    	Object zObject = CacheHelper.getInstance().get(clientId+"Cover"+userId);
	    	if(StringUtil.isNotEmpty(zObject))
	    	{
	    		dto.setIsZ(1);
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("userId", userId);
	    	map.put("isRead", "0");
	    	List<CmUserMessage> meList = cmUserMessageService.findByMap(map);
	    	if(meList != null && meList.size() > 0)
	    	{
	    		dto.setIsRead(1);
	    	}
	    	object.setResponse(dto);
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
	 * 查找标签
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/searchLaber", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject searchLaber(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String laber = request.getParameter("laber");
	    	if(StringUtil.isEmpty(laber))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("name", laber);
	    	List<Laber> list = laberService.findByMap(map);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	/**
	 * 消息列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/messagelist", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject messagelist(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page<CmUserMessageDto> page =  new Page<CmUserMessageDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	    	if("1".equals(type))
	    	{
	    		map.put("type1", type);
	    	}else if("2".equals(type))
	    	{
	    		map.put("type", "4");
	    	}else if("3".equals(type))
	    	{
	    		map.put("type", "1");
	    	}
	    	map.put("userId", userId);
	    	Page<CmUserMessageDto> pageResult = cmUserMessageService.findDtoByMap(page, map);
	    	List<CmUserMessageDto> list = pageResult.getResult();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(CmUserMessageDto cmUserMessageDto:list){
	    			cmUserMessageDto.setProductUrl(host+cmUserMessageDto.getProductUrl());
	    			if("2".equals(type))
	    			{
	    				cmUserMessageDto.setContent(HtmlUtils.htmlUnescape(cmUserMessageDto.getContent()));
	    				PmProductDiscuss pmProductDiscuss = pmProductDiscussService.getById(cmUserMessageDto.getRelId());
	    				if(StringUtil.isNotEmpty(pmProductDiscuss))
	    				{
	    					cmUserMessageDto.setDiscussType(pmProductDiscuss.getType());
	    					cmUserMessageDto.setImageUrl(pmProductDiscuss.getImageUrl());
	    				}
	    			}
	    		}
	    	}
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
			
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 消息统计
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/messageCount", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject messageCount(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("type1", "type1");
	    	map.put("userId", userId);
	    	map.put("isRead", "0");
	    	Number sysCount = cmUserMessageService.getCount(map);
	    	Map<String,String> map1 = new HashMap<String, String>();
	    	map1.put("type", "1");
	    	map1.put("userId", userId);
	    	map1.put("isRead", "0");
	    	Number zCount = cmUserMessageService.getCount(map1);
	    	map1.put("type", "4");
	    	Number dCount = cmUserMessageService.getCount(map1);
	    	Map<String,Object> reMap = new HashMap<String, Object>();
	    	reMap.put("sysCount", sysCount);
	    	reMap.put("zCount", zCount);
	    	reMap.put("dCount", dCount);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(reMap);
			
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 消息删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/messagedel", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject messagedel(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String messageId = request.getParameter("messageId");
	    	if(StringUtil.isEmpty(messageId))
	    	{
	    		return object;
	    	}
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
	    	CmUserMessage cmUserMessage = cmUserMessageService.getById(Long.parseLong(messageId));
	    	if(StringUtil.isEmpty(cmUserMessage))
	    	{
	    		return object;
	    	}
	    	cmUserMessageService.delete(cmUserMessage);
	    	
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
	 * 消息删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/messageread", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject messageread(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String messageId = request.getParameter("messageId");
	    	String type = request.getParameter("type");
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
	    	if(StringUtil.isNotEmpty(messageId))
	    	{
	    		CmUserMessage cmUserMessage = cmUserMessageService.getById(Long.parseLong(messageId));
		    	if(StringUtil.isEmpty(cmUserMessage))
		    	{
		    		return object;
		    	}
		    	cmUserMessage.setIsRead(1);
		    	cmUserMessageService.update(cmUserMessage);
	    	}else{
	    		if(StringUtil.isNotEmpty(type))
	    		{
	    			Map<String,String> map = new HashMap<String, String>();
	    			if("1".equals(type))
	    	    	{
	    	    		map.put("type1", type);
	    	    	}else if("2".equals(type))
	    	    	{
	    	    		map.put("type", "4");
	    	    	}else if("3".equals(type))
	    	    	{
	    	    		map.put("type", "1");
	    	    	}
	    	    	map.put("userId", userId);
	    	    	List<CmUserMessage> list = cmUserMessageService.findByMap(map);
	    	    	if(list != null && list.size() > 0)
	    	    	{
	    	    		for(CmUserMessage cmUserMessage:list)
	    	    		{
	    	    			cmUserMessage.setIsRead(1);
	    			    	cmUserMessageService.update(cmUserMessage);
	    	    		}
	    	    	}
	    		}
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
	 * 访问我的人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myFollow", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myFollow(HttpServletRequest request) {
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
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	map.put("fansUserId", userId);
			Page page =  new Page<CmUserInfoListDto>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page<CmUserInfoListDto> pageResult = cmUserFansService.findFollowPage(page,map);
	    	List<CmUserInfoListDto> list = pageResult.getResult();
	    	if(list != null && list.size() > 0)
	    	{
	    		for(CmUserInfoListDto cmUserInfoListDto:list)
	    		{
	    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth()))
	    			{
	    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
	    			}
	    		}
	    	}
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(list);
	    }catch(Exception e) {
			log.error(e.getMessage());
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
	@RequestMapping(value = "/updateStatus", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateStatus(HttpServletRequest request) {
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
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isEmpty(productId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	String status = request.getParameter("status");
	    	if(StringUtil.isEmpty(status))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(productId));
	    	if(pmProductInfo == null)
	    	{
	    		return object;
	    	}
	    	if(pmProductInfo.getUserId() - cmUserInfo.getId() != 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限操作");
				return object;
	    	}
	    	if(pmProductInfo.getIsPublic() == 99)
    		{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限操作");
				return object;
    		}
	    	if("1".equals(status))
	    	{
	    		pmProductInfo.setIsPublic(98);
	    	}else if("2".equals(status))
	    	{
	    		pmProductInfo.setIsPublic(1);
	    	}else if("3".equals(status))
	    	{
	    		pmProductInfo.setIsPublic(99);
	    		cmUserInfo.setProductCount(cmUserInfo.getProductCount()-1);
	    		cmUserInfoService.update(cmUserInfo);
	    	}
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	
	    	PmProductInfoDto pmProductInfoDto = new PmProductInfoDto();
	    	pmProductInfoDto.setCity(cmUserInfo.getCity());
	    	pmProductInfoDto.setUserId(cmUserInfo.getId());
	    	pmProductInfoDto.setClientId(pmProductInfo.getClientId());
	    	pmProductInfoDto.setCreatedWhen(pmProductInfo.getCreatedWhen());
	    	pmProductInfoDto.setDiscussCount(pmProductInfo.getDiscussCount());
	    	pmProductInfoDto.setDownloadCount(pmProductInfo.getDownloadCount());
	    	pmProductInfoDto.setEditCount(pmProductInfo.getEditCount());
	    	pmProductInfoDto.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
	    	pmProductInfoDto.setNickName(cmUserInfo.getNickName());
	    	pmProductInfoDto.setPraiseCount(pmProductInfo.getPraiseCount());
	    	pmProductInfoDto.setProductId(pmProductInfo.getId());
	    	pmProductInfoDto.setProductLabel(pmProductInfo.getProductLabel());
	    	pmProductInfoDto.setSex(cmUserInfo.getSex());
	    	pmProductInfoDto.setShareCount(pmProductInfo.getShareCount());
	    	pmProductInfoDto.setStampCount(pmProductInfo.getStampCount());
	    	pmProductInfoDto.setUrl(host+pmProductInfo.getUrl());
	    	pmProductInfoDto.setThumbnail(host+pmProductInfo.getThumbnail());
	    	pmProductInfoDto.setMachine(cmUserInfo.getMachine());
	    	pmProductInfoDto.setIsC(0);
	    	pmProductInfoDto.setIsZ(0);
	    	pmProductInfoDto.setIsPublic(pmProductInfo.getIsPublic());
	    	pmProductInfoDto.setDescription(pmProductInfo.getDescription());
	    	pmProductInfoDto.setDepict(pmProductInfo.getDescription());
	    	Map<String, Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("productId", pmProductInfo.getId());
	    	if(StringUtil.isNotEmpty(userId))
	    	{
	    		searchMap.put("userId", userId);
	    	}else{
	    		searchMap.put("clientId", clientId);
	    	}
	    	List<PmProductPraises> praList = pmProductPraisesService.findByMap(searchMap);
	    	if(praList != null && praList.size() > 0)
	    	{
	    		for(PmProductPraises pmProductPraises:praList)
	    		{
	    			if(pmProductPraises.getType() == 0)
	    			{
	    				pmProductInfoDto.setIsZ(1);
	    			}else if(pmProductPraises.getType() == 1){
	    				pmProductInfoDto.setIsC(1);
	    			}
	    		}
	    	}
	    	
	    	Map<String,Object> reMap = new HashMap<String, Object>();
	    	reMap.put("pmProductInfoDto", pmProductInfoDto);
	    	object.setResponse(reMap);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
	    }catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
//	public static String listToString(List<Long> stringList){
//        if (stringList==null) {
//            return null;
//        }
//        StringBuilder result=new StringBuilder();
//        boolean flag=false;
//        for (Long string : stringList) {
//            if (flag) {
//                result.append(",");
//            }else {
//                flag=true;
//            }
//            result.append(string);
//        }
//        return result.toString();
//    }
//	
//	public static String listToStringTo(List<String> stringList){
//        if (stringList==null) {
//            return null;
//        }
//        StringBuilder result=new StringBuilder();
//        boolean flag=false;
//        for (String string : stringList) {
//            if (flag) {
//                result.append(",");
//            }else {
//                flag=true;
//            }
//            result.append(string);
//        }
//        return result.toString();
//    }
	
	public static String getCharacterPosition(String string){
	    //这里是获取"/"符号的位置
	    Matcher slashMatcher = Pattern.compile(",").matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       //当"/"符号第三次出现的位置
	       if(mIdx == 3){
	          break;
	       }
	    }
	    if(StringUtil.isNotEmpty(string))
	    {
	    	String arr[] = string.split(",");
	    	if(arr.length <= 3)
	    	{
	    		 return string;
	    	}
	    }
	    return string.substring(0, slashMatcher.start());
	 } 
	
	
	private void seachList(String type,List<PmTopicPsProducts> pslist,List<PmProductInfoDto> list,String key){
		Map<Long,Integer> updateMap = (Map<Long, Integer>) CacheHelper.getInstance().get("updateMap");
		if(StringUtil.isEmpty(updateMap))
		{
			updateMap = new ConcurrentHashMap<Long, Integer>();
		}
		if("5".equals(type))
		{
			if(pslist != null && pslist.size() > 0)
			{
				for(PmTopicPsProducts pmTopicPsProducts:pslist)
				{
					if(pmTopicPsProducts.getThumbnail().indexOf(host) == -1)
					{
						pmTopicPsProducts.setThumbnail(host+pmTopicPsProducts.getThumbnail());
						pmTopicPsProducts.setUrl(host+pmTopicPsProducts.getUrl());
						pmTopicPsProducts.setPsDescription(HtmlUtils.htmlUnescape(pmTopicPsProducts.getPsDescription()));
					}
					
					Integer tempNum = updateMap.get(pmTopicPsProducts.getProductId());
					if(StringUtil.isEmpty(tempNum))
					{
						tempNum = 0;
					}
					updateMap.put(pmTopicPsProducts.getProductId(),tempNum+1);
					pmTopicPsProducts.setSearchCount(pmTopicPsProducts.getSearchCount()+1);
				}
				
				if(StringUtil.isNotEmpty(key))
				{
					CacheHelper.getInstance().set(60*60*24, key, pslist);
				}
			}
			
		}else{
			if(list != null && list.size() > 0)
			{
				for(PmProductInfoDto pmProductInfoDto:list)
				{
					if(pmProductInfoDto.getThumbnail().indexOf(host) == -1)
					{
						pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
						pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
						pmProductInfoDto.setDescription(HtmlUtils.htmlUnescape(pmProductInfoDto.getDescription()));
					}
					
					pmProductInfoDto.setDepict(HtmlUtils.htmlUnescape(pmProductInfoDto.getDescription()));
					
					Integer tempNum = updateMap.get(pmProductInfoDto.getProductId());
					if(StringUtil.isEmpty(tempNum))
					{
						tempNum = 0;
					}
					updateMap.put(pmProductInfoDto.getProductId(),tempNum+1);
					pmProductInfoDto.setSearchCount(pmProductInfoDto.getSearchCount()+1);
				}
				
				if(StringUtil.isNotEmpty(key))
				{
					CacheHelper.getInstance().set(60*60*24, key, list);
				}
			}
		}
		
		CacheHelper.getInstance().set(60*60*24, "updateMap", updateMap);
	}
	
	 public static void main(String[] args) {
		 
	 }
}
