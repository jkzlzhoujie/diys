package cn.temobi.complex.action.api;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.MaterialResourceDto;
import cn.temobi.complex.entity.BackgroundPic;
import cn.temobi.complex.entity.BusinessCount;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserRanking;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysHotLabels;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.service.BackgroundPicService;
import cn.temobi.complex.service.BusinessCountService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserRankingService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.MaterialResourceService;
import cn.temobi.complex.service.MaterialService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysHotLabelsService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.MD5;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/v42")
public class V42ApiController extends ClientApiBaseController{
	
	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="materialService")
	private MaterialService materialService;
	
	@Resource(name="businessCountService")
	private BusinessCountService businessCountService;
	
	@Resource(name="laberService")
	public LaberService laberService;
	
	@Resource(name="sysHotLabelsService")
	public SysHotLabelsService sysHotLabelsService;
	
	@Resource(name="cmUserInfoService")
	public CmUserInfoService cmUserInfoService;
	
	@Resource(name="cmUserRankingService")
	private CmUserRankingService cmUserRankingService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="backgroundPicService")
	private BackgroundPicService backgroundPicService;
	
	/**
	 * 素材列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject listForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
			List<Material> list = materialService.findUseByMap(null);
			if(list!= null && list.size() > 0) {
				for(Material material:list)
				{
					material.setThumbnailUrl(host+material.getThumbnailUrl());
					material.setZipPath(host+material.getZipPath());
					material.setDownUrl(null);
					material.setBusinessContent(html+material.getBusinessContent()+html2);
				}
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    	object.setDesc("成功");
		    	object.setResponse(list);
			}else {
    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
    			object.setDesc("没有数据");
    		}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 素材下载
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/download", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject downloadForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String materialId = request.getParameter("materialId");
	    	String clientId = request.getParameter("clientId");
			if(StringUtil.isNotEmpty(materialId) && StringUtil.isNotEmpty(clientId)) {
				Material material = materialService.getById(Long.valueOf(materialId));
				if(StringUtil.isNotEmpty(material)) {
						Map<String,Object> map = new HashMap<String, Object>();
				    	map.put("materialId", materialId);
				    	List<MaterialResource> list = materialResourceService.findByMap(map);
				    	List<MaterialResourceDto> dtoList = new ArrayList<MaterialResourceDto>();
				    	if(list!= null && list.size() > 0) {
				    		MaterialResourceDto materialResourceDto;
				    		for(MaterialResource mr:list)
				    		{
				    			materialResourceDto = new MaterialResourceDto();
				    			materialResourceDto.setId(mr.getId());
				    			materialResourceDto.setName(mr.getName());
				    			materialResourceDto.setMaterialId(mr.getMaterialId());
				    			materialResourceDto.setImageUrl(host+mr.getImageUrl());
				    			dtoList.add(materialResourceDto);
				    		}
					    	object.setResponse(dtoList);
						}else {
			    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
			    			object.setDesc("没有数据");
			    			return object;
			    		}
					}
					object.setDesc("成功");
					object.setCode(Constant.RESPONSE_SUCCESS_CODE);
					return object;
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		return object;
	}
	
	/**
	 * 应用点击
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/business/click", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject click(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String materialId = request.getParameter("materialId");
	    	String materialResId = request.getParameter("materialResId");
	    	Material material = null;
	    	if(StringUtil.isNotEmpty(materialId))
	    	{
	    		material = materialService.getById(Long.parseLong(materialId));
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
	    	String location = request.getParameter("location");
	    	if(StringUtil.isEmpty(location))
	    	{
	    		return object;
	    	}
	    	BusinessCount businessCount = new BusinessCount();
	    	businessCount.setType(type);
	    	businessCount.setClientId(Long.parseLong(clientId));
	    	if(StringUtil.isNotEmpty(material))
	    	{
	    		businessCount.setMaterialId(material.getId());
		    	businessCount.setBusiness(material.getBusiness());
	    	}
	    	if(StringUtil.isNotEmpty(materialResId))
	    	{
	    		businessCount.setMaterialResId(Long.parseLong(materialResId));
	    	}
	    	businessCount.setLocation(location);
	    	businessCountService.save(businessCount);
	    	object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 标签列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laberList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject laberList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	List<String> laberList = new ArrayList<String>();
	    	if("0".equals(type))
	    	{
	    		laberList = new ArrayList<String>();
	    		List<SysHotLabels> list = sysHotLabelsService.findAll();
	    		if(list != null && list.size() > 0)
	    		{
	    			for(SysHotLabels sysHotLabels:list){
	    				laberList.add(sysHotLabels.getLabelName());
	    			}
	    		}
	    	}else if("1".equals(type))
	    	{
	    		Map<String,Object> laberMap = new HashMap<String, Object>();
	    		laberMap.put("limit", 12);
	    		laberList = laberService.findRand(laberMap);
	    	}
	    	object.setResponse(laberList);
	    	object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	@Resource(name="materialResourceService")
	private MaterialResourceService materialResourceService;


	/**
	 * 标签添加
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laberAdd", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject laberAdd(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String name = request.getParameter("name");
	    	if(StringUtil.isEmpty(name))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("laberName", name);
	    	List<Laber> list = laberService.findByMap(map);
	    	if(list != null && list.size() > 0)
    		{
	    		object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
	    		object.setDesc("标签已存在");
	    		return object;
    		}
	    	Laber laber = new Laber();
			laber.setName(name);
			laber.setType("4");
			laber.setStatus("2");
			laberService.save(laber);
	    	object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	@RequestMapping(value="/hdIndex",method={RequestMethod.GET,RequestMethod.POST})
	public String hdIndex(HttpServletRequest request,Model model)
	{
		String name = request.getParameter("name");
		return "bo/hd/"+name;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getGameurl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getGameurl(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
//	    	String url = "http://m.5miao.com/partners/10000164/?appid=10000164";
//	    	String userId = request.getParameter("userId");
//	    	if(StringUtil.isNotEmpty(userId))
//	    	{
//	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
//	    		String appid = URLEncoder.encode("10000164","UTF-8");
//	    		String avator = URLEncoder.encode(cmUserInfo.getHeadImageUrl(),"UTF-8");
//	    		String channel = URLEncoder.encode("test","UTF-8");
//	    		String container = URLEncoder.encode("app_webview","UTF-8");
//	    		String name = URLEncoder.encode(cmUserInfo.getNickName(),"UTF-8");
//	    		String uid = URLEncoder.encode(cmUserInfo.getId()+"","UTF-8");
//	    		String appkey = "36cd125bcff7a690d4c6667ef2bc08d1";
//	    		String signString = "appid="+appid+",avator="+avator+",channel="+channel+",container="+container+",name="+name+",uid="+uid+",appkey="+appkey;
//	    		String sign = MD5.getMD5(signString).toLowerCase();
//	    		url = "http://m.5miao.com/partners/"+appid+"?appid="+appid+"&uid="+uid+"&name="+name+"&avator="+avator+"&channel="+channel+"&container=app_webview&sign="+sign;
//	    	}
	    	String url = "";
	    	//http://h5.xtoneapp.com?re=60012BC9
	    	Map<String,String> param = new HashMap<String, String>();
	    	param.put("code",SysParamConstant.GAME_URL);
			List<SysParameter> sysParameterList = sysParameterService.findByMap(param);
			if(sysParameterList != null && sysParameterList.size()==1){
				url = sysParameterList.get(0).getValue();
			}
	    	object.setResponse(url);
	    	object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 获取 背景图
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getBackgroundPic", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getBackgroundPic(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)){
				return object;
			}
			Map<String,String> param = new HashMap<String, String>();
			param.put("ttpe", type);
			param.put("status", "1");
			List<BackgroundPic> baList = backgroundPicService.findByMap(param);
			Map<String,String> returnMap = new HashMap<String, String>();
			if(baList != null){
				returnMap.put("imageUrl",host+baList.get(0).getImageUrl());
			}else{
				returnMap.put("imageUrl", "");
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
	
	
	//排行榜分享页面
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/rankingShare", method = {RequestMethod.GET,RequestMethod.POST})
	public String rankingShare(HttpServletRequest request, ModelMap model) {
	    try {
	    	String userId = request.getParameter("key");
	    	String a = com.salim.encrypt.TaobaoKey.decode(userId);
	    	if(StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(a))
	    	{
	    		Map<String,Object> configmap = new HashMap<String, Object>();
	    		configmap.put("enParamName", "rankingShareNum");
				List<SysConfigParam> configlist = sysConfigParamService.findByMap(configmap);
				SysConfigParam sysConfigParam = null;
				if(configlist.size() > 0)
				{
					sysConfigParam =  configlist.get(0);
					sysConfigParam.setParamValue((Integer.parseInt(sysConfigParam.getParamValue())+1)+"");
					sysConfigParamService.update(sysConfigParam);
				}else{
					sysConfigParam = new SysConfigParam();
					sysConfigParam.setType("0");
					sysConfigParam.setEnParamName("rankingShareNum");
					sysConfigParam.setParamValue("1");
					sysConfigParamService.save(sysConfigParam);
				}
	    		
	    		String time = DateUtils.getNextDate(new Date(), -1);
		    	Map<String,Object> map = new HashMap<String, Object>();
		    	map.put("userId",a);
		    	map.put("time", time);
		    	List<CmUserRanking> list = cmUserRankingService.findByMap(map);
		    	CmUserRanking cmUserRanking = list.get(0);
		    	String url = cmUserRanking.getHeadImageUrl();
		    	if(StringUtil.isEmpty(url))
		    	{
		    		url = "http://pic.weiju360.cn/diys/rs/otherImg/kf1.jpg";
		    	}
		    	CmUserInfo cmUserInfo = cmUserInfoService.getById(cmUserRanking.getUserId());
		    	
		    	model.addAttribute("url", url);
		    	model.addAttribute("productCount", cmUserInfo.getProductCount());
		    	model.addAttribute("fansCount", cmUserInfo.getFansCount());
		    	model.addAttribute("ranking", cmUserRanking.getTotalScoreNum());
		    	double temp = (1-cmUserRanking.getTotalScoreNum()/2000000.0)*100;
		    	model.addAttribute("num",(new Double(temp)).intValue());
	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
	    return "wx/pm";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cx", method = {RequestMethod.GET,RequestMethod.POST})
	public String cx(HttpServletRequest request, ModelMap model) {
	    return "wx/cx";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cx1", method = {RequestMethod.GET,RequestMethod.POST})
	public String cx1(HttpServletRequest request, ModelMap model) {
	    return "wx/cx1";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cx2", method = {RequestMethod.GET,RequestMethod.POST})
	public String cx2(HttpServletRequest request, ModelMap model) {
	    return "wx/cx2";
	}
	
	
	
	/**
	 * 获取标签列表 5 悬赏求P
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getlaberList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getlaberList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	if("5".equals(type))
	    	{
	    		Map<String, Object> map = new HashMap<String, Object>();
	    		map.put("type", type);
	    		List<Laber> list = laberService.findByMap(map);
	    		object.setResponse(list);
	    	}
	    	object.setDesc("成功");
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
	
}
