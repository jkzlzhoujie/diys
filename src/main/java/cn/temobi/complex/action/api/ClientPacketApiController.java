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

import cn.temobi.complex.dto.MaterialResourceDto;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.MaterialDownload;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.complex.entity.MaterialUseLog;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.ResourceShare;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.MaterialDownloadService;
import cn.temobi.complex.service.MaterialResourceService;
import cn.temobi.complex.service.MaterialService;
import cn.temobi.complex.service.MaterialUseLogService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.ResourceShareService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

/**
 * 表情管理接口
 * @author hjf
 * 2014 五月 7 15:04:25
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/pk")
public class ClientPacketApiController extends ClientApiBaseController {
	
	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="materialService")
	private MaterialService materialService;
	
	@Resource(name="materialDownloadService")
	private MaterialDownloadService materialDownloadService;
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	@Resource(name="resourceShareService")
	private ResourceShareService resourceShareService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="materialResourceService")
	private MaterialResourceService materialResourceService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="materialUseLogService")
	private MaterialUseLogService materialUseLogService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
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
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type))
	    	{
	    		return object;
	    	}
	    	String sort = request.getParameter("sort");
	    	Map<String, String> map = new HashMap<String, String>();
	    	map.put("status", "1");
	    	map.put("type", type);
	    	if("1".equals(type) || "2".equals(type))
	    	{
	    		map.put("sort", "sort");
	    	}else{
	    		if("1".equals(sort))
		    	{
		    		map.put("sort", "created_when");
		    	}else if("2".equals(sort)){
		    		map.put("sort", "download_num");
		    	}
		    	else{
		    		map.put("sort", "sort");
		    	}
	    	}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "300";
			Page page =  new Page<Material>(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			Page<Material> pageResult = new Page<Material>();
			pageResult = materialService.findByPage(page, map);
			List<Material> list = pageResult.getResult();
			if(list!= null && list.size() > 0) {
				for(Material material:list)
				{
					material.setThumbnailUrl(host+material.getThumbnailUrl());
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
	 * 素材详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/detail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject detailForAndroid(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
	    	String materialId = request.getParameter("materialId");
	    	String clientId = request.getParameter("clientId");
	    	if(StringUtil.isNotEmpty(materialId)) {
	    		Material material = materialService.getById(Long.valueOf(materialId));
	    		material.setThumbnailUrl(host+material.getThumbnailUrl());
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("成功");
	    		object.setResponse(material);
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
	    	String imei = request.getParameter("imei");
	    	String version = request.getParameter("version");
	    	String clientId = request.getParameter("clientId");
			if(StringUtil.isNotEmpty(materialId) && StringUtil.isNotEmpty(clientId)) {
				Material material = materialService.getById(Long.valueOf(materialId));
				if(StringUtil.isNotEmpty(material)) {
					MaterialDownload materialDownload = new MaterialDownload();
					materialDownload.setClientId(Long.parseLong(clientId));
					materialDownload.setImei(imei);
					materialDownload.setMaterialId(material.getId());
					materialDownload.setMaterialType(material.getType()+"");
					materialDownload.setVersion(version);
					
					material.setDownloadNum(material.getDownloadNum()+1);
					
					materialDownloadService.saveUpdate(materialDownload, material);
					if(material.getType() == 3 && "4".equals(version))
					{
						object.setResponse(host+material.getDownUrl());
					}else{
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
				    			materialResourceDto.setType(mr.getType());
				    			materialResourceDto.setUrl(mr.getUrl());
				    			materialResourceDto.setContent(html+mr.getContent()+html2);
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
				}else {
	    			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);//
	    			object.setDesc("没有数据");
	    		}
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		return object;
	}
	
	
	/**
	 * 广告图列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/bannerlist", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject bannerList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try {
			List<Banner> list = bannerService.findAll();
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
	 * 图片分享
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/share", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject share(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String resourceId = request.getParameter("resourceId");
			String userId = request.getParameter("userId");
			String productId = request.getParameter("productId");
			String themeId = request.getParameter("themeId");
	    	String imei = request.getParameter("imei");
	    	String version = request.getParameter("osVersion");
	    	String type = request.getParameter("type");
	    	String clientId = request.getParameter("clientId");
	    	String shareType = request.getParameter("shareType");
	    	String shareStyle = request.getParameter("shareStyle");
	    	String expressIds = request.getParameter("expressIds");
	    	String chartletIds = request.getParameter("chartletIds");
	    	String yanWritings = request.getParameter("yanWritings");
	    	String writings = request.getParameter("writings");
	    	String useType = request.getParameter("useType");
			Map<String,String> map = new HashMap<String, String>();
			map.put("resourceId", resourceId);
			map.put("userId", userId);
			map.put("productId", productId);
			map.put("themeId", themeId);
			map.put("imei", imei);
			map.put("version", version);
			map.put("type", type);
			map.put("clientId", clientId);
			map.put("shareType", shareType);
			map.put("shareStyle", shareStyle);
			map.put("expressIds", expressIds);
			map.put("chartletIds", chartletIds);
			map.put("yanWritings", yanWritings);
			map.put("writings", writings);
			map.put("useType", useType);
			
			return resourceShareService.saveAll(map, object);
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
		return object;
	}
	
	
	/**
	 * 图片上传
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/v1/upload", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject upload(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String resourceId = request.getParameter("resourceId");
	    	String clientId = request.getParameter("clientId");
	    	String description = request.getParameter("description");
	    	String laber = request.getParameter("laber");
	    	String depict = request.getParameter("depict");
	    	String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(resourceId) && StringUtil.isNotEmpty(clientId)) {
				Map<String,String> map = FileUtil.uploadImage(request, response);
				if(StringUtil.isEmpty(map))
				{
					object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					object.setDesc("上传失败");
					return object;
				}
		    	map.put("resourceId", resourceId);
		    	List<PmProductInfo> list = pmProductInfoService.findByMap(map);
		    	if(list != null && list.size() > 0)
		    	{
		    		object.setCode(Constant.RESPONSE_ERROR_10003);
					object.setDesc("作品已上传");
					return object;
		    	}
				PmProductInfo resource = new PmProductInfo();
				resource.setClientId(Long.parseLong(clientId));
				resource.setResourceId(resourceId);
				resource.setUrl(map.get("url"));
				resource.setThumbnail(map.get("thumbnail"));
//				resource.setIsPublic(1);
				resource.setAudit(1);
				if(StringUtil.isNotEmpty(description))
				{
					resource.setDescription(HtmlUtils.htmlEscape(description));
				}
				if(StringUtil.isNotEmpty(depict))
				{
					resource.setDescription(HtmlUtils.htmlEscape(depict));
				}
				resource.setProductLabel(laber);
				resource.setCreateFrom("1");
				pmProductInfoService.save(resource);
				
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				return object;
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("你输入的内容含有敏感词");
		}
		return object;
	}
}
