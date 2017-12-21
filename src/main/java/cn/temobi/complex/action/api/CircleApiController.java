package cn.temobi.complex.action.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.DESPlus;

import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CircleType;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.complex.entity.CmCircleUser;
import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.complex.entity.CmCircleUserLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.complex.entity.SysAdvert;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.service.CircleTypeService;
import cn.temobi.complex.service.CmCircleProductService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmCircleUserFollowService;
import cn.temobi.complex.service.CmCircleUserLogService;
import cn.temobi.complex.service.CmCircleUserService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserRankingService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.SysAdvertService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/circle")
public class CircleApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	String host1 = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url1"); 
	
	@Resource(name="cmUserRankingService")
	private CmUserRankingService cmUserRankingService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="cmCircleServive")
	private CmCircleServive cmCircleServive;
	
	@Resource(name="cmCircleUserService")
	private CmCircleUserService cmCircleUserService;
	
	@Resource(name="cmCircleUserLogService")
	private CmCircleUserLogService cmCircleUserLogService;
	
	@Resource(name="cmCircleProductService")
	private CmCircleProductService cmCircleProductService;
	
	@Resource(name="cmCircleUserFollowService")
	private CmCircleUserFollowService cmCircleUserFollowService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="sysAdvertService")
	private SysAdvertService sysAdvertService;

	@Resource(name="circleTypeService")
	private CircleTypeService circleTypeService;
	
	
	/**
	 * 排行榜
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ranking", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject ranking(HttpServletRequest request,HttpServletResponse response) {
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
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	String time = DateUtils.getNextDate(new Date(), -1);
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	String key = "ranking@"+type;
	    	List<CmUserRanking> userList = (List<CmUserRanking>) CacheHelper.getInstance().get(key);
	    	if(userList == null || userList.size() <= 0)
	    	{
		    	searchMap.put("limit",8);
		    	searchMap.put("offset", 0);
		    	searchMap.put("time", time);
		    	if("1".equals(type))
		    	{
		    		searchMap.put("orderField", "totalScore");
		    	}else if("2".equals(type))
		    	{
		    		searchMap.put("orderField", "charm");
		    	}else if("3".equals(type))
		    	{
		    		searchMap.put("orderField", "originality");
		    	}
		    	userList = cmUserRankingService.findByMap(searchMap);
		    	if(userList == null || userList.size() <= 0)
		    	{
		    		time= DateUtils.getNextDate(new Date(), -2);
		    	 	searchMap.put("time", time);
		    	 	userList = cmUserRankingService.findByMap(searchMap);
		    	}
		    	CacheHelper.getInstance().set(60*60*24, key, userList);
	    	}
	    	
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("userId",userId);
	    	map.put("time", time);
	    	List<CmUserRanking> list = cmUserRankingService.findByMap(map);
	    	CmUserRanking cmUserRanking = null;
	    	if(list != null && list.size() > 0)
	    	{
	    		cmUserRanking = list.get(0);
	    		map.put("time", DateUtils.getNextDate(time, -1));
		    	List<CmUserRanking> yestList = cmUserRankingService.findByMap(map);
		    	if(yestList != null && yestList.size() > 0)
		    	{
		    		CmUserRanking yestCmUserRanking = yestList.get(0);
		    		cmUserRanking.setYesCharmNum(yestCmUserRanking.getCharmNum());
		    		cmUserRanking.setYesOriginalityNum(yestCmUserRanking.getOriginalityNum());
		    		cmUserRanking.setYesTotalScoreNum(yestCmUserRanking.getTotalScoreNum());
		    	}
	    	}
	    	Map<String,Object> returnMap = new HashMap<String, Object>();
	    	searchMap.put("enParamName", "promoteStr");
	    	List<SysConfigParam> configlist = sysConfigParamService.findByMap(searchMap);
	    	String promoteStr = "";
	    	if(configlist != null && configlist.size() > 0)
	    	{
	    		promoteStr = configlist.get(0).getParamValue();
	    	}
	    	returnMap.put("userList", userList);
	    	returnMap.put("userObject", cmUserRanking);
	    	returnMap.put("rankingTime", time);
	    	returnMap.put("promoteStr", promoteStr);
	    	returnMap.put("rankingShareUrl", host1+"/diys/client/v42/rankingShare?key="+com.salim.encrypt.TaobaoKey.encode(userId));
			object.setResponse(returnMap);
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
	 * 广告列表
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
	    	List<SysAdvert> list = sysAdvertService.findAll();
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
	 * 圈子首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject index(HttpServletRequest request,HttpServletResponse response) {
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
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", userId);
	    	searchMap.put("flag","0");
	    	List<CmCircle> list = cmCircleServive.findByMap(searchMap);
	    	List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    	searchMap.put("limit", 4);
	    	List<CmCircleProduct> productList = cmCircleProductService.findByMap(searchMap);
	    	
	    	Map<String,Object> objMap = new HashMap<String, Object>();
	    	objMap.put("userId", userId);
	    	objMap.put("nickName", cmUserInfo.getNickName());
	    	objMap.put("headImageUrl", cmUserInfo.getHeadImageUrl());
	    	objMap.put("circleNum",list.size());
	    	int totalImageNum = 0;
	    	int totalUserNum = 0;
	    	if(list != null && list.size() > 0)
	    	{
	    		for(CmCircle cmCircle:list)
	    		{
	    			totalImageNum += cmCircle.getImageNum();
	    			totalUserNum += cmCircle.getUserNum();
	    			
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    		}
	    	}
	    	if(followList != null && followList.size() > 0)
	    	{
	    		for(CmCircle cmCircle:followList)
	    		{
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    			cmCircle.setIsFollow("1");
	    		}
	    	}
	    	if(productList != null && productList.size() > 0)
	    	{
	    		for(CmCircleProduct cmCircleProduct:productList)
	    		{
	    			cmCircleProduct.setUrl(host+cmCircleProduct.getUrl());
	    		}
	    	}
	    	objMap.put("totalImageNum",totalImageNum);
	    	objMap.put("totalUserNum",totalUserNum);
	    	Map<String,Object> returnMap = new HashMap<String, Object>();
	    	returnMap.put("list", list);
	    	returnMap.put("followList", followList);
	    	returnMap.put("productList", productList);
	    	returnMap.put("objMap", objMap);
			object.setResponse(returnMap);
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
	 * 圈子搜索
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/search", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject search(HttpServletRequest request,HttpServletResponse response) {
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
	    	String keyword = request.getParameter("keyword");
	    	if(StringUtil.isEmpty(keyword))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("keyword", keyword);
	    	searchMap.put("flag", "0");
	    	List<CmCircle> alllist = cmCircleServive.findByMap(searchMap);
	    	searchMap.remove("keyword");
	    	searchMap.put("userId", userId);
	    	List<CmCircle> list = cmCircleServive.findByMap(searchMap);
	    	List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    	alllist.removeAll(list);
	    	alllist.removeAll(followList);
	    	
	    	Map<String,Object> returnMap = new HashMap<String, Object>();
	    	returnMap.put("list", list);
	    	returnMap.put("followList", followList);
	    	returnMap.put("alllist", alllist);
	    	if(list != null && list.size() > 0)
	    	{
	    		for(CmCircle cmCircle:list)
	    		{
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    		}
	    	}
	    	if(followList != null && followList.size() > 0)
	    	{
	    		for(CmCircle cmCircle:followList)
	    		{
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    		}
	    	}
	    	if(alllist != null && alllist.size() > 0)
	    	{
	    		for(CmCircle cmCircle:alllist)
	    		{
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    		}
	    	}
			object.setResponse(returnMap);
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
	 * 社区圈子搜索 2
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/circleSearch", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject circleSearch(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	String keyword = request.getParameter("keyword");
	    	if(StringUtil.isEmpty(keyword)){
	    		return object;
	    	}
	    	String userId = request.getParameter("userId");
			
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("keyword", keyword);
	    	searchMap.put("flag", "0");
	    	
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			searchMap.put("limit", page.getPageSize());
			searchMap.put("offset", page.getOffset());
			List<CmCircle> alllist = new ArrayList<CmCircle>();
			Page<CmCircle> result = cmCircleServive.findByPage(page, searchMap);
			alllist = result.getResult();
	    	if(StringUtil.isNotEmpty(userId)){
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    		searchMap.clear();
	    		searchMap.put("userId", userId);
	    		List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    		
		    	if(alllist != null && alllist.size() > 0){
		    		for(CmCircle cmCircle:alllist){
		    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
		    			cmCircle.setLogo(host+cmCircle.getLogo());
		    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
		    			for(CmCircle followCircle:followList){
			    			if(cmCircle.getId() - followCircle.getId()== 0 ){
				    			cmCircle.setIsFollow("1");
					    	}else{
					    		cmCircle.setIsFollow("0");
				    		}
		    		    }
	    	       }
		    	}
	    	}else{
	    		if(alllist != null && alllist.size() > 0){
		    		for(CmCircle cmCircle:alllist){
		    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
		    			cmCircle.setLogo(host+cmCircle.getLogo());
		    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
					    cmCircle.setIsFollow("0");
	    	       }
		    	}
	    	}
	    	
			object.setResponse(alllist);
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
	 * 圈子详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject detail(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String userId = request.getParameter("userId");
			
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId)){
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null){
	    		return object;
	    	}
	    	if(!"0".equals(cmCircle.getFlag())){
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("圈子已解散");
				return object;
	    	}
	    	cmCircle.setLatestImage(host+cmCircle.getLatestImage());
			cmCircle.setLogo(host+cmCircle.getLogo());
			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
			
			CmUserInfo circleUser = cmUserInfoService.getById(cmCircle.getUserId());
			cmCircle.setHeadImageUrl(circleUser.getHeadImageUrl());
			
			Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("circleId", circleId);
	    	int userCount = cmCircleUserService.getCount(searchMap).intValue();
			int imgCount = cmCircleProductService.getCount(searchMap).intValue();
			cmCircle.setUserNum(userCount);
			cmCircle.setImageNum(imgCount);
	    	
	    	
	    	if(StringUtil.isNotEmpty(userId)){
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    		CmCircleUserLog cmCircleUserLog = new CmCircleUserLog();
	    		cmCircleUserLog.setCircleId(Long.parseLong(circleId));
	    		cmCircleUserLog.setClientId(Long.parseLong(clientId));
	    		cmCircleUserLog.setUserId(Long.parseLong(userId));
	    		cmCircleUserLogService.save(cmCircleUserLog);
	    		searchMap.clear();
	    		searchMap.put("userId", userId);
	    		searchMap.put("circleId", circleId);
	    		List<CmCircleUserFollow> list = cmCircleUserFollowService.findByMap(searchMap);
	    		if(list != null && list.size() > 0){
		    		cmCircle.setIsFollow("1");
		    	}else{
		    		cmCircle.setIsFollow("0");
		    	}
	    	}else{
	    		cmCircle.setIsFollow("0");
	    	}
	    	
			object.setResponse(cmCircle);
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
	 * 所有圈子作品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allProductList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject allProductList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("flag", "0");
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			map.put("searchId", "desc");
			
			
			List<PmProductInfo> list = new ArrayList<PmProductInfo>();
			String key = "circle@productList"+pageNo;
			List<PmProductInfo> templist = (List<PmProductInfo>) CacheHelper.getInstance().get(key);
    		if(templist == null || templist.size() <= 0 || Integer.valueOf(pageNo)> 5 )
			{
    			List<Long> productList = new ArrayList<Long>();
    			productList = cmCircleProductService.findProductId(map);
    			if(productList != null && productList.size() > 0)
    			{
    				map.put("productList", productList);
    				list = pmProductInfoService.findByMap(map);
    				for(PmProductInfo pmProductInfo:list)
    				{
    					pmProductInfo.setThumbnail(host+pmProductInfo.getThumbnail());
    					pmProductInfo.setUrl(host+pmProductInfo.getUrl());
    				}
    			}
    			if(list!= null && list.size() > 0 && Integer.valueOf(pageNo)<= 5 ){
					CacheHelper.getInstance().set(60*60*24,key, list);
				}
			}else{
				list = templist;
			}
    		for (PmProductInfo pmProductInfo : list) {
    			pmProductInfo.setDepict(pmProductInfo.getDescription());
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
	 * 单个圈子作品
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId)){
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null){
	    		return object;
	    	}
	    	if(!"0".equals(cmCircle.getFlag()))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("圈子已解散");
				return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("circleId", circleId);
			map.put("flag", "0");
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			map.put("searchId", "desc");
			
			List<Long> productList = new ArrayList<Long>();
			List<PmProductInfo> list = new ArrayList<PmProductInfo>();
			productList = cmCircleProductService.findProductId(map);
			
			if(productList != null && productList.size() > 0)
			{
				map.put("productList", productList);
				list = pmProductInfoService.findByMap(map);
				for(PmProductInfo pmProductInfo:list)
				{
					pmProductInfo.setThumbnail(host+pmProductInfo.getThumbnail());
					pmProductInfo.setUrl(host+pmProductInfo.getUrl());
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
	 * 所有圈子关联的作者
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/allUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject allUserList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("flag", "0");
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			
			String key = "circle@userList"+pageNo;
			List<CmUserInfoListDto> list = new ArrayList<CmUserInfoListDto>();
			List<CmUserInfoListDto> templist = (List<CmUserInfoListDto>) CacheHelper.getInstance().get(key);
    		if(templist == null || templist.size() <= 0 || Integer.valueOf(pageNo)> 5 )
			{
    			List<Long> userList = new ArrayList<Long>();
    			userList = cmCircleUserService.findUserId(map);
    			if(userList != null && userList.size() > 0){
    				map.put("list", userList);
    				list = cmUserInfoService.findByList(map);
    				if(list != null && list.size() > 0){
    					for(CmUserInfoListDto cmUserInfoListDto:list){
    		    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth())){
    		    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
    		    			}
    		    		}
    				}
    			}
    			if(list!= null && list.size() > 0 && Integer.valueOf(pageNo)<= 5 ){
					CacheHelper.getInstance().set(60*60*24,key, list);
				}
			}else{
				list = templist;
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
	 * 单个圈子关联的作者
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject userList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId)){
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null){
	    		return object;
	    	}
	    	if(!"0".equals(cmCircle.getFlag())){
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("圈子已解散");
				return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("circleId", circleId);
			map.put("flag", "0");
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			
			List<Long> userList = new ArrayList<Long>();
			List<CmUserInfoListDto> list = new ArrayList<CmUserInfoListDto>();
			userList = cmCircleUserService.findUserId(map);
			
			if(userList != null && userList.size() > 0){
				map.put("list", userList);
				list = cmUserInfoService.findByList(map);
				if(list != null && list.size() > 0){
					for(CmUserInfoListDto cmUserInfoListDto:list){
		    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth())){
		    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
		    			}
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
	 * 单个圈子关注的用户
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/followUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject followUserList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId)){
	    		return object;
	    	}
	    	
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null){
	    		return object;
	    	}
	    	if(!"0".equals(cmCircle.getFlag())){
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("圈子已解散");
				return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("circleId", circleId);
			
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			long count = cmCircleUserFollowService.getCount(map).longValue();
			
			List<Long> userList = new ArrayList<Long>();
			userList = cmCircleUserFollowService.findUserId(map);
			
			List<CmUserInfoListDto> list = new ArrayList<CmUserInfoListDto>();
			if(userList != null && userList.size() > 0)
			{
				map.clear();
				map.put("list", userList);
				list = cmUserInfoService.findByList(map);
				if(list != null && list.size() > 0){
					for(CmUserInfoListDto cmUserInfoListDto:list){
		    			if(StringUtil.isNotEmpty(cmUserInfoListDto.getBirth())){
		    				cmUserInfoListDto.setAge(DateUtils.getAge(cmUserInfoListDto.getBirth())+"");
		    			}
		    		}
				}
			}
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("totalCount", count);
			returnMap.put("cmUserInfoList", list);
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
	 * 关注
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/follow", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject follow(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", userId);
	    	searchMap.put("circleId", circleId);
	    	List<CmCircleUserFollow> list = cmCircleUserFollowService.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("已关注");
				return object;
	    	}
	    	cmCircle.setFollowNum(cmCircle.getFollowNum()+1);
	    	
	    	CmCircleUserFollow cmCircleUserFollow = new CmCircleUserFollow();
	    	cmCircleUserFollow.setCircleId(Long.parseLong(circleId));
	    	cmCircleUserFollow.setClientId(Long.parseLong(clientId));
	    	cmCircleUserFollow.setUserId(Long.parseLong(userId));
	    	cmCircleServive.follow(cmCircle,cmCircleUserFollow);
	    	
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
	 * 取消关注
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelFollow", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject cancelFollow(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", userId);
	    	searchMap.put("circleId", circleId);
	    	List<CmCircleUserFollow> list = cmCircleUserFollowService.findByMap(searchMap);
	    	if(list == null || list.size() < 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("未关注");
				return object;
	    	}else{
	    		cmCircleServive.cancelFollow(cmCircle, list.get(0));
	    	}
	    	
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
	 * 不看这张图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject cancelProduct(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	String productIdS = request.getParameter("productIdS");
	    	if(StringUtil.isEmpty(productIdS))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	if(!userId.equals(cmCircle.getUserId()+""))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限");
				return object;
	    	}
	    	
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("productIdS", productIdS);
	    	cmCircleServive.cancelProduct(cmCircle, map);
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
	 * 不看这个人
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject cancelUser(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	String circleUserId = request.getParameter("circleUserId");
	    	if(StringUtil.isEmpty(circleUserId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	if(!userId.equals(cmCircle.getUserId()+""))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限");
				return object;
	    	}
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", circleUserId);
	    	searchMap.put("circleId", circleId);
	    	List<CmCircleUser> list = cmCircleUserService.findByMap(searchMap);
	    	if(list == null || list.size() < 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("系统错误");
				return object;
	    	}else{
	    		cmCircle.setUserNum(cmCircle.getUserNum()-1);
	    		cmCircleServive.update(cmCircle);
	    		CmCircleUser cmCircleUser = list.get(0);
	    		cmCircleUser.setFlag("1");
	    		cmCircleUserService.update(cmCircleUser);
	    	}
	    	
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
	 * 置顶
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/topUser", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject topUser(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	String circleUserId = request.getParameter("circleUserId");
	    	if(StringUtil.isEmpty(circleUserId))
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	if(!userId.equals(cmCircle.getUserId()+""))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限");
				return object;
	    	}
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("userId", circleUserId);
	    	searchMap.put("circleId", circleId);
	    	List<CmCircleUser> list = cmCircleUserService.findByMap(searchMap);
	    	if(list == null || list.size() < 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("系统错误");
				return object;
	    	}else{
	    		Map<String,Object> searchMap2 = new HashMap<String, Object>();
	    		searchMap2.put("max", "max");
	    		List<CmCircleUser> list2 = cmCircleUserService.findByMap(searchMap2);
	    		if(list2 != null && list2.size() > 0)
	    		{
	    			CmCircleUser maxUser = list2.get(0);
	    			
	    			CmCircleUser cmCircleUser = list.get(0);
		    		cmCircleUser.setSort(maxUser.getSort()+1);
		    		cmCircleUserService.update(cmCircleUser);
	    		}
	    		
	    	}
	    	
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
	 * 解散
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delete", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delete(HttpServletRequest request,HttpServletResponse response) {
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
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	if(!userId.equals(cmCircle.getUserId()+""))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你没有权限");
				return object;
	    	}
	    	
	    	if(!"0".equals(cmCircle.getFlag()))
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("圈子已解散");
				return object;
	    	}
	    	cmCircle.setFlag("1");
	    	cmCircleServive.update(cmCircle);
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
	 * 创建
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/create", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject create(HttpServletRequest request,HttpServletResponse response) {
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
	    	String thumbnail = request.getParameter("thumbnail");
	    	if(StringUtil.isEmpty(thumbnail))
	    	{
	    		return object;
	    	}
	    	String logo = request.getParameter("logo");
	    	if(StringUtil.isEmpty(logo))
	    	{
	    		return object;
	    	}
	    	String depict = request.getParameter("depict");
	    	if(StringUtil.isEmpty(depict))
	    	{
	    		return object;
	    	}
	    	String name = request.getParameter("name");
	    	if(StringUtil.isEmpty(name))
	    	{
	    		return object;
	    	}
	    	
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("nameLimit", name);
	    	searchMap.put("flag", "0");
	    	List<CmCircle> list = cmCircleServive.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("亲，你刚输入的名称已经存在，换个更有才的吧");
				return object;
	    	}
	    	searchMap.remove("nameLimit");
	    	searchMap.put("userId", userId);
	    	list = cmCircleServive.findByMap(searchMap);
	    	if(list != null && list.size() >= 3)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("亲，最多只能创建三个圈子哦！");
				return object;
	    	}
	    	searchMap.put("endDate", DateUtils.getCurrDateStr());
	    	searchMap.put("startDate", DateUtils.getCurrDateStr());
	    	searchMap.put("userId", userId);
	    	list = cmCircleServive.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("你今天只能创建一个圈子");
				return object;
	    	}
	    	CmCircle cmCircle = new CmCircle();
	    	cmCircle.setDepict(depict);
	    	cmCircle.setName(name);
	    	cmCircle.setLogo(logo);
	    	cmCircle.setThumbnail(thumbnail);
	    	cmCircle.setUserId(Long.parseLong(userId));
	    	cmCircle.setClientId(Long.parseLong(clientId));
	    	cmCircle.setFlag("0");
	    	
	    	Map<String,String> map = new HashMap<String, String>();
	    	map.put("clientId", clientId);
	    	cmCircleServive.saveAll(cmCircle, cmUserInfo, map);
	    	
	    	
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
	 * 修改
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject update(HttpServletRequest request,HttpServletResponse response) {
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
	    	String thumbnail = request.getParameter("thumbnail");
	    	String logo = request.getParameter("logo");
	    	String depict = request.getParameter("depict");
	    	
	    	String circleId = request.getParameter("circleId");
	    	if(StringUtil.isEmpty(circleId))
	    	{
	    		return object;
	    	}
	    	
	    	String type = request.getParameter("type");
	    	
	    	CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
	    	if(cmCircle == null)
	    	{
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    	if(cmUserInfo == null)
	    	{
	    		return object;
	    	}
	    	
	    	if(StringUtil.isNotEmpty(depict))
	    	{
	    		cmCircle.setDepict(depict);
	    	}
	    	if(StringUtil.isNotEmpty(logo))
	    	{
	    		cmCircle.setLogo(logo);
	    	}
	    	if(StringUtil.isNotEmpty(thumbnail))
	    	{
	    		cmCircle.setThumbnail(thumbnail);
	    	}
	    	if(StringUtil.isNotEmpty(type))
	    	{
	    		cmCircle.setType(Integer.parseInt(type) );
	    	}
	    	cmCircleServive.updateCm(cmCircle);
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
	 * 获取圈子类型
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCircleType", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getCircleType(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,String> param = new HashMap<String, String>();
			param.put("status", "1");
			List<CircleType>  circleTypeList = new ArrayList<CircleType>();
			circleTypeList = circleTypeService.findByMap(param);
			object.setResponse(circleTypeList);
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
	 * 圈子推荐列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCircleRecommend", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getCircleRecommend(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> searchMap = new HashMap<String, Object>();
			Map<Long,Object> followMap = new HashMap<Long, Object>();
			List<CmCircle>  circleList = new ArrayList<CmCircle>();
			String userId = request.getParameter("userId");
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "3";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			
			String key = "circle@circleRecommendList"+pageNo;
			if(StringUtil.isNotEmpty(userId)){
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    		searchMap.put("userId", userId);
	    		searchMap.put("flag","0");
	    		List<CmCircle> createList = cmCircleServive.findByMap(searchMap);
	    		if(createList != null && createList.size() > 0){
		    		for(CmCircle cmCircle:createList){
		    			followMap.put(cmCircle.getId(), cmCircle.getId());
		    		}
		    	}
	    		List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    		if(followList != null && followList.size() > 0){
		    		for(CmCircle cmCircle:followList){
		    			followMap.put(cmCircle.getId(), cmCircle.getId());
		    		}
		    	}
	    	}else{
	    		// 采用 缓存
				List<CmCircle> templist = (List<CmCircle>) CacheHelper.getInstance().get(key);
				//用户未登录缓存
				if(templist!=null && templist.size()>0){
					circleList = templist;
				}
	    	}
	    
			if(circleList == null || circleList.size() < 1 ){
				searchMap.clear();
				searchMap.put("limit", page.getPageSize());
				searchMap.put("offset", page.getOffset());
				searchMap.put("flag","0");
				searchMap.put("isRecommend","1");
				searchMap.put("placeRecommend","desc");
				
				Page<CmCircle> Page = cmCircleServive.findByPage(page, searchMap);
				circleList = Page.getResult();
				//推荐的圈子 少于指定数量的时候，查找最新的补充
				if(circleList.size() < Integer.valueOf(pageSize)){
					String ids = "";
					searchMap.clear();
					for (CmCircle cmCircle : circleList) {
						ids = ids+cmCircle.getId().toString()+",";
					}
					if(circleList!=null && circleList.size()>0 ){
						searchMap.put("notInIds",ids.substring(0, ids.length()-1));
					}
					searchMap.put("limit", page.getPageSize());
					searchMap.put("offset", page.getOffset());
					searchMap.put("flag","0");
					Page<CmCircle> Page2 = cmCircleServive.findByPage(page, searchMap);
					List<CmCircle>  circleList2 = new ArrayList<CmCircle>();
					circleList2 = Page2.getResult();
					circleList.clear();
					for (CmCircle cmCircle : circleList2) {
						circleList.add(cmCircle);
					}
				}
				if(circleList != null && circleList.size() > 0){
		    		for(CmCircle cmCircle:circleList){
		    			searchMap.clear();
		    			searchMap.put("circleId",cmCircle.getId());
		    			int userCount = cmCircleUserService.getCount(searchMap).intValue();
		    			int imgCount = cmCircleProductService.getCount(searchMap).intValue();
		    			cmCircle.setUserNum(userCount);
		    			cmCircle.setImageNum(imgCount);
		    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
		    			cmCircle.setLogo(host+cmCircle.getLogo());
		    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
		    		}
		    	}
		    	if(circleList!= null && circleList.size() > 0 && Integer.valueOf(pageNo) < 10){
					CacheHelper.getInstance().set(60*60*24,key, circleList);
				}
			}
			
			if(circleList != null && circleList.size() > 0 && followMap!=null && followMap.size() > 0){
	    		for(CmCircle cmCircle:circleList){
	    			if(followMap.containsKey(cmCircle.getId())){
	    				cmCircle.setIsFollow("1");
	    			}else{
	    				cmCircle.setIsFollow("0");
	    			}
	    		}
	    	}
	    	
			object.setResponse(circleList);
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
	 * 获取更多圈子列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMoreCircleList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getMoreCircleList(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> searchMap = new HashMap<String, Object>();
			Map<Long,Object> followMap = new HashMap<Long, Object>();
			List<CmCircle>  circleList = new ArrayList<CmCircle>();
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			long t1 = System.currentTimeMillis();
			
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId))
	    	{
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null)
	    		{
	    			return object;
	    		}
	    		searchMap.put("userId", userId);
	    		searchMap.put("flag","0");
	    		List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    		if(followList != null && followList.size() > 0){
		    		for(CmCircle cmCircle:followList){
		    			followMap.put(cmCircle.getId(), cmCircle.getId());
		    		}
		    	}
	    	}
			
    		// 采用 缓存
			String key = "circle@moreCircleList"+pageNo;
			List<CmCircle> templist = (List<CmCircle>) CacheHelper.getInstance().get(key);	
			if(templist!=null && templist.size()>0){
				circleList = templist;
			}			
			if(circleList == null || circleList.size() < 1 ){
				searchMap.clear();
				searchMap.put("limit", page.getPageSize());
				searchMap.put("offset", page.getOffset());
				searchMap.put("flag","0");
				searchMap.put("placeRecommend","desc");
				
				Page<CmCircle> Page = cmCircleServive.findByPage(page, searchMap);
				circleList = Page.getResult();
		    	if(circleList!= null && circleList.size() > 0 && Integer.valueOf(pageNo)<=3  ){
					CacheHelper.getInstance().set(60*60*24,key, circleList);
				}
			}
			
			if(circleList != null && circleList.size() > 0){
	    		for(CmCircle cmCircle:circleList){
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    			if(followMap.containsKey(cmCircle.getId())){
	    				cmCircle.setIsFollow("1");
	    			}else{
	    				cmCircle.setIsFollow("0");
	    			}
	    		}
	    	}
			long t2 = System.currentTimeMillis();
//			log.error("circle more => t2-t1 " + (t2-t1) );
			object.setResponse(circleList);
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
	 * 我创建和我关注的圈子列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyCircleList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getMyCircleList(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> searchMap = new HashMap<String, Object>();
			Map<Long,Object> followMap = new HashMap<Long, Object>();
			List<CmCircle>  circleList = new ArrayList<CmCircle>();
			String userId = request.getParameter("userId");
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
	    	
			int startPage =  (Integer.parseInt(pageNo) - 1) * Integer.parseInt(pageSize);
			int endPage =  (Integer.parseInt(pageNo)) * Integer.parseInt(pageSize);
			
			
			if(StringUtil.isNotEmpty(userId)){
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    		searchMap.put("userId", userId);
	    		searchMap.put("flag","0");
	    		List<CmCircle> createList = cmCircleServive.findByMap(searchMap);
	    		if(createList != null && createList.size() > 0){
		    		for(CmCircle cmCircle:createList){
		    			followMap.put(cmCircle.getId(), cmCircle.getId());
		    			circleList.add(cmCircle);
		    		}
		    	}
	    		List<CmCircle> followList = cmCircleServive.findMyFollow(searchMap);
	    		if(followList != null && followList.size() > 0){
		    		for(CmCircle cmCircle:followList){
		    			followMap.put(cmCircle.getId(), cmCircle.getId());
		    			circleList.add(cmCircle);
		    		}
		    	}
	    	}else{
	    		return object;
	    	}
			List<CmCircle>  newCircleList = new ArrayList<CmCircle>();
			if(circleList!=null && circleList.size() > 0 ){
				for (int i = 0; i < circleList.size(); i++) {
					if(i>=endPage){
						break;
					}
					if(i>=startPage){
						newCircleList.add(circleList.get(i));
					}
				}
			}
			
			if(newCircleList != null && newCircleList.size() > 0){
	    		for(CmCircle cmCircle:newCircleList){
	    			cmCircle.setLatestImage(host+cmCircle.getLatestImage());
	    			cmCircle.setLogo(host+cmCircle.getLogo());
	    			cmCircle.setThumbnail(host+cmCircle.getThumbnail());
	    			if(followMap.containsKey(cmCircle.getId())){
	    				cmCircle.setIsFollow("1");
	    			}else{
	    				cmCircle.setIsFollow("0");
	    			}
	    		}
	    	}
			
			object.setResponse(newCircleList);
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
