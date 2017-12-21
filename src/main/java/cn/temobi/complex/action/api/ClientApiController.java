package cn.temobi.complex.action.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.DESPlus;
import com.salim.encrypt.EncryptDecrypt;
import com.salim.encrypt.TaobaoKey;

import cn.temobi.complex.dto.MaterialResourceDto;
import cn.temobi.complex.dto.ThemeDto;
import cn.temobi.complex.entity.Application;
import cn.temobi.complex.entity.ApplicationCount;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.LaberConfigure;
import cn.temobi.complex.entity.LaberSearch;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.entity.ThemeLove;
import cn.temobi.complex.entity.WxDiy;
import cn.temobi.complex.service.ApplicationCountService;
import cn.temobi.complex.service.ApplicationService;
import cn.temobi.complex.service.ClassifyService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.LaberConfigureService;
import cn.temobi.complex.service.LaberSearchService;
import cn.temobi.complex.service.MaterialResourceService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.complex.service.ThemeLoveService;
import cn.temobi.complex.service.ThemeService;
import cn.temobi.complex.service.WxDiyService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/new")
public class ClientApiController extends ClientApiBaseController {
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="themeService")
	private ThemeService themeService;
	
	@Resource(name="themeLoveService")
	private ThemeLoveService themeLoveService;
	
	@Resource(name="classifyService")
	private ClassifyService classifyService;
	
	@Resource(name="laberConfigureService")
	private LaberConfigureService laberConfigureService;
	
	@Resource(name="laberSearchService")
	private LaberSearchService laberSearchService;
	
	@Resource(name="materialResourceService")
	private MaterialResourceService materialResourceService;
	
	@Resource(name="applicationService")
	public ApplicationService applicationService;
	
	@Resource(name="applicationCountService")
	public ApplicationCountService applicationCountService;
	
	@Resource(name="systemSetingService")
	public SystemSetingService systemSetingService;
	
	@Resource(name="pmProductInfoService")
	public PmProductInfoService pmProductInfoService;
	
	@Resource(name="wxDiyService")
	public WxDiyService wxDiyService;
	
	@Resource(name="cmUserInfoService")
	public CmUserInfoService cmUserInfoService;
	
	/**
	 * 分类列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/classify/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject listForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("status", "1");
	    	map.put("type", "1");
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "300";
	    	
	    	List<Classify> list = new ArrayList<Classify>();
	    	List<Classify> tempList = (List<Classify>) CacheHelper.getInstance().get("classify@pageNo"+pageNo);
	    	if(tempList==null || tempList.size()< 1){
	    		Page page =  new Page<Material>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
				Page<Classify> pageResult = new Page<Classify>();
				pageResult = classifyService.findByPage(page, map);
				list = pageResult.getResult();
				for(Classify classify:list){
					classify.setImage(host+classify.getImage());
				}
				CacheHelper.getInstance().set(60*60*24, "classify@pageNo"+pageNo, list);
	    	}else{
	    		list = tempList;
	    	}
	    	
			if(list!= null && list.size() > 0) {
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
	 * 主题列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/theme/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject themeList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String classifyId = request.getParameter("classifyId");
	    	if(StringUtil.isEmpty(classifyId)){
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId)){
	    		return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "300";
			Page page =  new Page<Material>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
	    	
			List<ThemeDto> dtolist = new ArrayList<ThemeDto>();
			
	    	List<ThemeDto> tempList = (List<ThemeDto>) CacheHelper.getInstance().get("theme@themeDto"+classifyId+pageNo);
	    	if(tempList==null || tempList.size()< 1){
	    		Classify classify = classifyService.getById(Long.parseLong(classifyId));
		    	Map<String, String> map = new HashMap<String, String>();
		    	map.put("classifyId", classifyId);
		    	map.put("status", "1");
		    	
		    	
				Page<Theme> pageResult = new Page<Theme>();
				pageResult = themeService.findByPage(page, map);
				List<Theme> list = pageResult.getResult();
				if(list!= null && list.size() > 0) {
					Theme theme;
					ThemeDto themeDto;
					for(int i=0;i<list.size();i++){
						theme = list.get(i);
						themeDto = new ThemeDto();
						themeDto.setId(theme.getId());
						themeDto.setClassifyId(theme.getClassifyId());
						themeDto.setImageUrl(host+theme.getImageUrl());
						themeDto.setLoveNum(theme.getLoveNum());
						themeDto.setThemeUnlock(theme.getThumbnailUrl());
						themeDto.setThemeName(theme.getThemeName());
						themeDto.setThemeNum(classify.getThemeNum());
						map.put("clientId", clientId);
						map.put("themeId", theme.getId()+"");
						List<ThemeLove> tlList = themeLoveService.findByMap(map);
						if(tlList != null && tlList.size() > 0){
							themeDto.setIsLove("1");
						}else{
							themeDto.setIsLove("2");
						}
						dtolist.add(themeDto);
					}
					CacheHelper.getInstance().set(60*60*24, "theme@themeDto"+classifyId+pageNo, dtolist);
				}
	    	}else{
	    		dtolist = tempList;
	    	}
	    	
	    	
			if(dtolist!= null && dtolist.size() > 0) {
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    	object.setDesc("成功");
		    	object.setResponse(dtolist);
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
	 * 喜欢主题
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/theme/love", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject themeLove(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String themeId = request.getParameter("themeId");
	    	if(StringUtil.isEmpty(themeId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	ThemeLove themeLove = new ThemeLove();
	    	themeLove.setClientId(Long.parseLong(clientId));
	    	themeLove.setThemeId(Long.parseLong(themeId));
	    	themeLoveService.saveAll(themeLove);
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
	 * 标签列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laber/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject laberList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "300";
			Page page =  new Page<LaberConfigure>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			
			Map<String,Object> tempMap = (Map<String,Object>) CacheHelper.getInstance().get("laber@list"+type+pageNo);
	    	if(tempMap==null || tempMap.size()< 1){
	    		Page<LaberConfigure> pageResult = new Page<LaberConfigure>();
				Map<String,String> searchMap = new HashMap<String, String>();
				searchMap.put("type", type);
				pageResult = laberConfigureService.findByPage(page, searchMap);
				List<LaberConfigure> list = pageResult.getResult();
				
				SystemSeting systemSeting = systemSetingService.getById(null);
				returnMap.put("hotLaber", systemSeting.getHotLaber());
				returnMap.put("laberList", list);
				
	    		CacheHelper.getInstance().set(60*60*24, "laber@list"+type+pageNo, returnMap);
	    	}else{
	    		returnMap = tempMap;
	    	}
			
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc("成功");
			object.setResponse(returnMap);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 根据标签查找表情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/laber/search", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject search(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String laberName = request.getParameter("laberName");
	    	if(StringUtil.isEmpty(laberName))
	    	{
	    		return object;
	    	}
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
	    	LaberSearch laberSearch = new LaberSearch();
	    	laberSearch.setLaberName(laberName);
	    	laberSearch.setClientId(Long.parseLong(clientId));
	    	laberSearchService.save(laberSearch);
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("laberName", laberName);
	    	map.put("type", type);
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
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    	object.setDesc("成功");
		    	object.setResponse(dtoList);
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
	 * 应用列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/application/list", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject applicationList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("status", "1");
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "300";
			Page page =  new Page<Material>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page<Application> pageResult = new Page<Application>();
			pageResult = applicationService.findByPage(page, map);
			List<Application> list = pageResult.getResult();
			if(list!= null && list.size() > 0) {
				for(Application application:list)
				{
					application.setLogo(host+application.getLogo());
					if(StringUtil.isNotEmpty(application.getImage()))
					{
						String image="";
						String arr[] = application.getImage().split(",");
						for(int i=0;i<arr.length;i++)
						{
							image += host+arr[i];
							if(i!=arr.length-1)
							{
								image += ",";
							}
							application.setImage(image);
						}
					}
					application.setUrl("");
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
	 * 应用点击
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/application/click", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject click(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String applicationId = request.getParameter("applicationId");
	    	if(StringUtil.isEmpty(applicationId))
	    	{
	    		return object;
	    	}
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isEmpty(clientId))
	    	{
	    		return object;
	    	}
	    	Application application = applicationService.getById(Long.parseLong(applicationId));
	    	ApplicationCount applicationCount = new ApplicationCount();
	    	applicationCount.setClientId(Long.parseLong(clientId));
	    	applicationCount.setApplicationId(Long.parseLong(applicationId));
	    	
	    	application.setCountNum(application.getCountNum()+1);

	    	applicationCountService.saveAll(applicationCount, application);
	    	object.setResponse(host+application.getUrl());
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
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/h5Theme", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void h5Theme(HttpServletRequest request,HttpServletResponse response) {
	    try{
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("h5Push", "1");
	    	List<Theme> list = themeService.findByMap(map);
	    	
	    	if(list != null && list.size() > 0)
	    	{
	    		List<String> returnList = new ArrayList<String>();
	    		for(Theme theme:list)
	    		{
	    			returnList.add(host+theme.getImageUrl());
	    		}
	    		response.setContentType("text/plain");  
	            String jsonpcallback =request.getParameter("jsonpcallback");//得到js函数名称  
	            try {  
	            	response.getWriter().write(jsonpcallback + "("+JSONArray.fromObject(returnList).toString()+")"); //返回jsonp数据  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	    	}
	    }catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/productDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void productDetail(HttpServletRequest request,HttpServletResponse response) {
	    try{
	    	String url = "";
	    	String productId = request.getParameter("productId");
	    	String arr[] = new String[3];
	    	if(StringUtil.isNotEmpty(productId))
	    	{
	    		if(productId.indexOf("*wx*") != -1)
	    		{
	    			Map<String,Object> map = new HashMap<String, Object>();
			    	map.put("resourceId", productId);
			    	List<WxDiy> list = wxDiyService.findByMap(map);
			    	if(list != null && list.size() > 0)
			    	{
			    		WxDiy wxDiy = list.get(0);
			    		arr[0] = wxDiy.getBgSrc();
			    		arr[1] = wxDiy.getySrc();
			    		arr[2] = wxDiy.getImgVal();
			    	}
	    		}else{
	    			Map<String,Object> map = new HashMap<String, Object>();
			    	map.put("resourceId", productId);
			    	List<PmProductInfo> list = pmProductInfoService.findByMap(map);
			    	if(list != null || list.size() > 0)
			    	{
			    		url = host+list.get(0).getUrl();
			    		arr[0] = url;
			    	}
	    		}
	    	}
	    	
	    	response.setContentType("text/plain");  
            String jsonpcallback =request.getParameter("jsonpcallback");//得到js函数名称  
            try {  
            	response.getWriter().write(jsonpcallback + "("+JSONArray.fromObject(arr)+")"); //返回jsonp数据  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
	    }catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/zProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody void zProduct(HttpServletRequest request,HttpServletResponse response) {
	    try{
	    	String productId = request.getParameter("productId");
	    	if(StringUtil.isNotEmpty(productId))
	    	{
	    		if(productId.indexOf("*wx*") != -1)
	    		{
	    		}else{
	    			Map<String,Object> map = new HashMap<String, Object>();
			    	map.put("resourceId", productId);
			    	List<PmProductInfo> list = pmProductInfoService.findByMap(map);
			    	if(list != null || list.size() > 0)
			    	{
			    		PmProductInfo pmProductInfo =list.get(0);
			    		pmProductInfoService.zProduct(pmProductInfo);
			    	}
	    		}
	    	}
	    	String jsonpcallback =request.getParameter("jsonpcallback");//得到js函数名称  
	  	    response.getWriter().println(jsonpcallback + "(\""+200+"\")");
	  		response.getWriter().flush();
	  		response.getWriter().close();
	    }catch(Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(TaobaoKey.encode("520520150628231026"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
