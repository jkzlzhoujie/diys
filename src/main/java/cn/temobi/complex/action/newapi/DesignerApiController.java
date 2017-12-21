package cn.temobi.complex.action.newapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.dao.CmDesignerProductFormatDao;
import cn.temobi.complex.entity.CmDesignerHonor;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.complex.service.CmDesignerHonorService;
import cn.temobi.complex.service.CmDesignerInfoPicService;
import cn.temobi.complex.service.CmDesignerProductFormatService;
import cn.temobi.complex.service.CmDesignerProductInfoService;
import cn.temobi.complex.service.CmDesignerService;
import cn.temobi.complex.service.DesignerService;
import cn.temobi.complex.service.SysProductTypeInfoService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 设计师  接口
 * @author zhouj
 * 
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/designer")
public class DesignerApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysProductTypeInfoService")
	private SysProductTypeInfoService sysProductTypeInfoService;
	
	@Resource(name="cmDesignerService")
	private CmDesignerService cmDesignerService;
	
	@Resource(name="designerService")
	private DesignerService designerService;
	
	@Resource(name="cmDesignerInfoPicService")
	private CmDesignerInfoPicService cmDesignerInfoPicService;
	
	@Resource(name="cmDesignerProductInfoService")
	private CmDesignerProductInfoService cmDesignerProductInfoService;
	
	@Resource(name="cmDesignerHonorService")
	private CmDesignerHonorService cmDesignerHonorService;
	
	@Resource(name = "cmDesignerProductFormatService")
	private CmDesignerProductFormatService cmDesignerProductFormatService;
	
	/**
	 * 技能标签列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerlabelList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerlabelList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String key = "product@productTypeInfoList";
	    	List<SysProductTypeInfo>  productTypeList = new ArrayList<SysProductTypeInfo>();
			List<SysProductTypeInfo> templist = (List<SysProductTypeInfo>) CacheHelper.getInstance().get(key);
			if(templist!=null && templist.size() > 0){
				productTypeList =  templist;
			}else{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("level", 1);
				productTypeList =  sysProductTypeInfoService.findByMap(map);
				CacheHelper.getInstance().set(3600*24, key, productTypeList);
			}
			object.setResponse(productTypeList);
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
	 * 设计师 详细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerDetail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			if(StringUtil.isEmpty(designerId)){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			if(cmDesignerInfo.getFinishSchoolPhotoThumbnail() != null){
				cmDesignerInfo.setFinishSchoolPhotoThumbnail(host + cmDesignerInfo.getFinishSchoolPhotoThumbnail() );
				cmDesignerInfo.setFinishSchoolPhoto(host + cmDesignerInfo.getFinishSchoolPhoto() );
			}
			if(cmDesignerInfo.getLifeImageUrlThumbnail() != null){
				cmDesignerInfo.setLifeImageUrlThumbnail(host + cmDesignerInfo.getLifeImageUrlThumbnail() );
				cmDesignerInfo.setLifeImageUrl(host + cmDesignerInfo.getLifeImageUrl() );
			}
			if(cmDesignerInfo.getIdCardUrl1Thumbnail() != null){
				cmDesignerInfo.setIdCardUrl1Thumbnail(host + cmDesignerInfo.getIdCardUrl1Thumbnail() );
				cmDesignerInfo.setIdCardUrl1(host + cmDesignerInfo.getIdCardUrl1() );
			}
			if(cmDesignerInfo.getIdCardUrl2Thumbnail() != null){
				cmDesignerInfo.setIdCardUrl2Thumbnail(host + cmDesignerInfo.getIdCardUrl2Thumbnail() );
				cmDesignerInfo.setIdCardUrl2(host + cmDesignerInfo.getIdCardUrl2() );
			}
			map.clear();
			map.put("designerId", designerId.trim());
			List<CmDesignerInfoPic> cmDesignerInfoPicList = cmDesignerInfoPicService.findByMap(map); 
			for (CmDesignerInfoPic cmDesignerInfoPic : cmDesignerInfoPicList) {
				cmDesignerInfoPic.setImageUrl(host + cmDesignerInfoPic.getImageUrl() );
				cmDesignerInfoPic.setImageUrlThumbnail(host + cmDesignerInfoPic.getImageUrlThumbnail() );
			}
			map.put("designerId", designerId.trim());
			List<CmDesignerHonor> cmDesignerHonorList = cmDesignerHonorService.findByMap(map); 
			for (CmDesignerHonor cmDesignerHonor : cmDesignerHonorList) {
				cmDesignerHonor.setImageUrlThumbnail(host + cmDesignerHonor.getImageUrlThumbnail() );
				cmDesignerHonor.setImageUrl(host + cmDesignerHonor.getImageUrl() );
			}
			cmDesignerInfo.setCmDesignerInfoPicList(cmDesignerInfoPicList);
			object.setResponse(cmDesignerInfo);
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
	 * 添加 设计师
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDesigner", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addDesigner(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			CmDesignerInfo cmDesignerInfo = new  CmDesignerInfo();
			cmDesignerInfo.setUserId(cmUserInfo.getId());
			
			String realName = request.getParameter("realName");
			String labels = request.getParameter("labels");
			String idCardNum = request.getParameter("idCardNum");
			String picFlag = request.getParameter("picFlag");
			
			if(StringUtil.isEmpty(realName) || StringUtil.isEmpty(labels) || StringUtil.isEmpty(idCardNum) || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			cmDesignerInfo.setRealName(realName.trim());
			cmDesignerInfo.setLabels(labels.trim());
			cmDesignerInfo.setIdCardNum(idCardNum.trim());
			
			if(picFlag.trim().equals("yes")){
				
			}
			List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
			uploadImage(request, returnMapList, object);
	    	if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
	    		return object;
	    	}
			if( returnMapList != null && returnMapList.size() > 0){
				cmDesignerInfo.setIdCardUrl1(returnMapList.get(0).get("url"));
				cmDesignerInfo.setIdCardUrl1Thumbnail(returnMapList.get(0).get("thumbnail"));
				cmDesignerInfo.setIdCardUrl2(returnMapList.get(1).get("url"));
				cmDesignerInfo.setIdCardUrl2Thumbnail(returnMapList.get(1).get("thumbnail"));
			}
			cmDesignerInfo.setStatus(1);
			cmDesignerService.save(cmDesignerInfo);
			object.setResponse(cmDesignerInfo);
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
	 * 修改 设计师
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDesigner", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateDesigner(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(designerId)  || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			String realName = request.getParameter("realName");
			if(StringUtil.isNotEmpty(realName)){
				map.put("realName", realName.trim());
				cmDesignerInfo.setRealName(realName.trim());
			}
			
			String labels = request.getParameter("labels");
			if(StringUtil.isNotEmpty(labels)){
				map.put("labels", labels.trim());
				cmDesignerInfo.setLabels(labels.trim());
			}
			
			String idCardNum = request.getParameter("idCardNum");
			if(StringUtil.isNotEmpty(idCardNum)){
				map.put("idCardNum", idCardNum.trim());
				cmDesignerInfo.setIdCardNum(idCardNum.trim());
			}
				
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					cmDesignerInfo.setIdCardUrl1(returnMapList.get(0).get("url"));
					cmDesignerInfo.setIdCardUrl1Thumbnail(returnMapList.get(0).get("thumbnail"));
					cmDesignerInfo.setIdCardUrl2(returnMapList.get(1).get("url"));
					cmDesignerInfo.setIdCardUrl2Thumbnail(returnMapList.get(1).get("thumbnail"));
				}
			}
			
			cmDesignerService.update(cmDesignerInfo);
			object.setResponse(cmDesignerInfo);
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
	 * 设计师  -- 设置毕业院校
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDesignerSchool", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject setDesignerSchool(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(designerId)  || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			
			String school = request.getParameter("school");
			if(StringUtil.isNotEmpty(school)){
				cmDesignerInfo.setSchool(school.trim());
			}
			String finishSchoolTime = request.getParameter("finishSchoolTime");
			if(StringUtil.isNotEmpty(finishSchoolTime)){
				cmDesignerInfo.setFinishSchoolTime(finishSchoolTime.trim());
			}
	    	
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					cmDesignerInfo.setFinishSchoolPhoto(returnMapList.get(0).get("url"));
					cmDesignerInfo.setFinishSchoolPhotoThumbnail(returnMapList.get(0).get("thumbnail"));
				}
			}
			cmDesignerService.update(cmDesignerInfo);
			object.setResponse(cmDesignerInfo);
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
	 * 设计师  -- 作品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerProductList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerProductList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			if(StringUtil.isEmpty(designerId)){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			map.clear();
			map.put("designerId", cmDesignerInfo.getId());
			List<CmDesignerInfoPic> cmDesignerInfoPicList = cmDesignerInfoPicService.findByMap(map);
			for (CmDesignerInfoPic cmDesignerInfoPic : cmDesignerInfoPicList) {
				cmDesignerInfoPic.setImageUrl(host + cmDesignerInfoPic.getImageUrl());
				cmDesignerInfoPic.setImageUrlThumbnail(host + cmDesignerInfoPic.getImageUrlThumbnail());
			}
			object.setResponse(cmDesignerInfoPicList);
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
	 * 设计师  -- 添加 作品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDesignerProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addDesignerProduct(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(designerId)  || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			List<CmDesignerInfoPic> cmDesignerInfoPicList = new ArrayList<CmDesignerInfoPic>();
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					for (Map<String, String> map2 : returnMapList) {
						CmDesignerInfoPic cmDesignerInfoPic = new CmDesignerInfoPic();
						cmDesignerInfoPic.setDesignerId(cmDesignerInfo.getId());
						cmDesignerInfoPic.setImageUrl(map2.get("url"));
						cmDesignerInfoPic.setImageUrlThumbnail(map2.get("thumbnail"));
					}
				}
			}
			cmDesignerInfoPicService.saveBatch(cmDesignerInfoPicList);
			object.setResponse("");
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
	 * 设计师  -- 删除作品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delDesignerProduct", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delDesignerProduct(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String id = request.getParameter("id");
			if(StringUtil.isEmpty(id)){
				 return object;
			}
			cmDesignerInfoPicService.delete(Long.valueOf(id));
			object.setResponse("");
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
	 * 设计师 --  荣誉证书列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerHonorList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerHonorList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			if(StringUtil.isEmpty(designerId)){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			map.clear();
			map.put("designerId", cmDesignerInfo.getId());
			List<CmDesignerHonor> cmDesignerHonorList = cmDesignerHonorService.findByMap(map);
			object.setResponse(cmDesignerHonorList);
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
	 * 设计师 --  添加荣誉证书
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDesignerHonor", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addDesignerHonor(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			String honorTime = request.getParameter("honorTime");
			String honorText = request.getParameter("honorText");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(designerId)  || StringUtil.isEmpty(picFlag) || StringUtil.isEmpty(honorTime) || StringUtil.isEmpty(honorText) ){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			
			CmDesignerHonor cmDesignerHonor = new CmDesignerHonor();
			cmDesignerHonor.setDesignerId(cmDesignerInfo.getId());
			cmDesignerHonor.setHonorTime(honorTime);
			cmDesignerHonor.setHonorText(honorText.toString());
			
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					cmDesignerHonor.setImageUrl(returnMapList.get(0).get("url"));
					cmDesignerHonor.setImageUrlThumbnail(returnMapList.get(0).get("thumbnail"));
				}
			}
			cmDesignerHonorService.save(cmDesignerHonor);
			object.setResponse(cmDesignerHonor);
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
	 * 设计师 --  修改荣誉证书
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDesignerHonor", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateDesignerHonor(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String id = request.getParameter("id");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(id)  || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			CmDesignerHonor cmDesignerHonor = cmDesignerHonorService.getById(Long.valueOf(id));
			if(cmDesignerHonor==null){
				return object;
			}
			String honorTime = request.getParameter("honorTime");
			if(StringUtil.isNotEmpty(honorTime)){
				cmDesignerHonor.setHonorTime(honorTime);
			}
			String honorText = request.getParameter("honorText");
			if(StringUtil.isNotEmpty(honorText)){
				cmDesignerHonor.setHonorText(honorText.toString());
			}
			
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					cmDesignerHonor.setImageUrl(returnMapList.get(0).get("url"));
					cmDesignerHonor.setImageUrlThumbnail(returnMapList.get(0).get("thumbnail"));
				}
			}
			
			cmDesignerHonorService.update(cmDesignerHonor);
			object.setResponse(cmDesignerHonor);
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
	 * 设计师 --  删除荣誉证书
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delDesignerHonor", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delDesignerHonor(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String id = request.getParameter("id");
			if(StringUtil.isEmpty(id)){
				 return object;
			}
			cmDesignerHonorService.delete(Long.valueOf(id));
			object.setResponse("");
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
	 * 设计师 --  设置生活照
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDesignerLifePic", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject setDesignerLifePic(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			String picFlag = request.getParameter("picFlag");
			if(StringUtil.isEmpty(designerId)  || StringUtil.isEmpty(picFlag) ){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			if(picFlag.trim().equals("yes")){
				List<Map<String,String>>  returnMapList = new ArrayList<Map<String,String>>();
				uploadImage(request, returnMapList, object);
				if(object.getCode() != Constant.RESPONSE_PARAMS_ERROR){
					return object;
				}
				if( returnMapList != null && returnMapList.size() > 0){
					cmDesignerInfo.setLifeImageUrl(returnMapList.get(0).get("url"));
					cmDesignerInfo.setLifeImageUrlThumbnail(returnMapList.get(0).get("thumbnail"));
				}
			}
			cmDesignerService.update(cmDesignerInfo);
			object.setResponse(cmDesignerInfo);
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
	 * 设计师 --  商品列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerGoodsList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerGoodsList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			String designerId = request.getParameter("designerId");
			if(StringUtil.isEmpty(designerId)){
				 return object;
			}
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.valueOf(designerId));
			if(cmDesignerInfo==null){
				return object;
			}
			map.clear();
			map.put("designerId", cmDesignerInfo.getId());
			List<CmDesignerProductInfo> cmDesignerProductInfoList = cmDesignerProductInfoService.findByMap(map);
			for (CmDesignerProductInfo cmDesignerProductInfo : cmDesignerProductInfoList) {
				cmDesignerProductInfo.setProductImgUrl(host + cmDesignerProductInfo.getProductImgUrl());
				cmDesignerProductInfo.setProductImgUrlThumbnail(host + cmDesignerProductInfo.getProductImgUrlThumbnail());
			}
			object.setResponse(cmDesignerProductInfoList);
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
	 * 设计师 --  添加商品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addDesignerGoods", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addDesignerGoods(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			return designerService.addDesignerGoods(object,request);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 设计师 --  修改商品
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/updateDesignerGoods", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject updateDesignerGoods(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	return designerService.updateDesignerGoods(object,request);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 设计师 
	 * type 1 商品上架 2 下架 3 删除
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/setDesignerGoodsStatus", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject setDesignerGoodsStatus(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String id = request.getParameter("id");
	    	String type = request.getParameter("type");
			if(StringUtil.isEmpty(type) || StringUtil.isEmpty(id)){
				 return object;
			}
			CmDesignerProductInfo cmDesignerProductInfo = cmDesignerProductInfoService.getById(Long.valueOf(id.trim()));
			int status = 1;
			if(type.trim().equals("1")){
				status = 1;
			}else if (type.trim().equals("2")){
				status = 2;
			}
			if (type.trim().equals("3")){
				cmDesignerProductInfoService.delete(Long.valueOf(id));
			}else{
				cmDesignerProductInfo.setStatus(status);
			}
			object.setResponse("");
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
	 * 设计师 --  商品详细
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/designerGoodsDetail", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject designerGoodsDetail(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String id = request.getParameter("id");
			if(StringUtil.isEmpty(id)){
				 return object;
			}
			CmDesignerProductInfo cmDesignerProductInfo = cmDesignerProductInfoService.getById(Long.valueOf(id.trim()));
			if(cmDesignerProductInfo.getProductImgUrlThumbnail()!=null){
				cmDesignerProductInfo.setProductImgUrl(host + cmDesignerProductInfo.getProductImgUrl());
				cmDesignerProductInfo.setProductImgUrlThumbnail(host + cmDesignerProductInfo.getProductImgUrlThumbnail());
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("productId", id);
			List<CmDesignerProductFormat> cmDesignerProductFormatList = cmDesignerProductFormatService.findByMap(map);
			cmDesignerProductInfo.setCmDesignerProductFormatList(cmDesignerProductFormatList);
			object.setResponse(cmDesignerProductInfo);
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
	
	
	static void uploadImage(HttpServletRequest request ,List<Map<String,String>>  returnMapList ,ResponseObject object){
		try {
			//上传图片
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap =multipartRequest.getFileMap();
	        if(fileMap != null && fileMap.size()>0){
	        	FileOperationController fileOperationController = new FileOperationController();
	        	returnMapList = fileOperationController.uploadDesignerImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				object.setCode(Constant.RESPONSE_ERROR_10012);
	        				object.setDesc("图片上传失败");
	        			}
	        		}
	        	}else{
	        		object.setCode(Constant.RESPONSE_ERROR_10012);
	        		object.setDesc("图片上传失败");
	        	}
	        }else{
	        	object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
        		object.setDesc("请选择图片");
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	    public static void main(String[] args) throws Exception {

	        String cid = "2016001";
	        String uid = "15321";
	        String mobile = "18206060967";

			 System.out.println("");//3ea01fd8e2470b264d03a6112454d86b
			

	    }
	
	
}
