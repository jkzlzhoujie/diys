package cn.temobi.complex.action.newapi;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.util.AlipayNotify;
import com.salim.cache.CacheHelper;
import com.tencent.common.Util;
import com.tencent.protocol.pay_protocol.ReturnData;

import cn.temobi.complex.dao.PmTopicJoinProductsDao;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.complex.entity.SysPrivilege;
import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserPrivilegeService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.SysPackageService;
import cn.temobi.complex.service.SysPrivilegeContentService;
import cn.temobi.complex.service.SysPrivilegeContentTypeService;
import cn.temobi.complex.service.SysPrivilegePackageService;
import cn.temobi.complex.service.SysPrivilegeService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 客户端用户特权套餐接口
 * @author zhouj
 * 
 * 2016年4月25日10
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/privilege")
public class ClientUserPrivilegeApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysPrivilegeService")
	private SysPrivilegeService sysPrivilegeService;
	
	@Resource(name="sysPackageService")
	private SysPackageService sysPackageService;
	
	@Resource(name="sysPrivilegePackageService")
	private SysPrivilegePackageService sysPrivilegePackageService;
	
	@Resource(name="sysPrivilegeContentService")
	private SysPrivilegeContentService sysPrivilegeContentService;
	
	@Resource(name="sysPrivilegeContentTypeService")
	private SysPrivilegeContentTypeService sysPrivilegeContentTypeService;
	
	@Resource(name="cmUserPrivilegeService")
	private CmUserPrivilegeService cmUserPrivilegeService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name = "pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	
	/**
	 * 系统 特权列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject priList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "3";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	Page page =  new Page(pageNoNum,pageSizeNum);
	    	
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("status", "1");
	    	Page<SysPrivilege> pageResult =  sysPrivilegeService.findByPage(page, param);
	    	List<SysPrivilege> sysPrivilegeList = new ArrayList<SysPrivilege>();
	    	sysPrivilegeList = pageResult.getResult();
	    	
    		object.setResponse(sysPrivilegeList);
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
	 * 系统所有 套餐列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priSysPackageList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject priSysPackageList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "3";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	Page page =  new Page(pageNoNum,pageSizeNum);
	    	
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("status", "1");
	    	Page<SysPackage> pageResult =  sysPackageService.findByPage(page, param);
	    	List<SysPackage> sysPackageList = new ArrayList<SysPackage>();
	    	sysPackageList = pageResult.getResult();
	    	
    		object.setResponse(sysPackageList);
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
	 * 单个特权的  套餐列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priPackageList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject priPackageList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String privilegeId = request.getParameter("privilegeId");
			if(StringUtil.isEmpty(privilegeId)){
				log.error("privilegeId 有误！");
	    		return object;
	    	}
	    	
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("privilegeId", privilegeId);
	    	List<SysPrivilegePackage> sysPrivilegePackageList  =  sysPrivilegePackageService.findByMap(param);
    		object.setResponse(sysPrivilegePackageList);
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
	 * 系统所有 特权内容类型列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priContentTypeList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject sysPriContentTypeList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "6";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	Page page =  new Page(pageNoNum,pageSizeNum);
	    	
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("status", "1");
	    	Page<SysPrivilegeContentType> pageResult = sysPrivilegeContentTypeService.findByPage(page, param);
	    	List<SysPrivilegeContentType> sysPrivilegeContentTypeList = new ArrayList<SysPrivilegeContentType>();
	    	sysPrivilegeContentTypeList = pageResult.getResult();
	    	
    		object.setResponse(sysPrivilegeContentTypeList);
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
	 * 单个特权 内容列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priContentList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject priContentList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String privilegeId = request.getParameter("privilegeId");
			if(StringUtil.isEmpty(privilegeId)){
				log.error("privilegeId 有误！");
	    		return object;
	    	}
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("privilegeId", privilegeId);
	      	List<SysPrivilegeContent> sysPrivilegeContentList = sysPrivilegeContentService.findByMap(param);
    		object.setResponse(sysPrivilegeContentList);
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
	 * 用户的特权 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getUserPri", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getUserPri(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
			if(StringUtil.isEmpty(userId)){
				log.error("usrID 有误！");
	    		return object;
	    	}
	    	Map<String, Object> param = new HashMap<String, Object>();
	    	param.put("userId", userId);
	    	param.put("valid", 1);
	    	List<CmUserPrivilege> cmUserPrivilegeList  = cmUserPrivilegeService.findByMap(param);
    		object.setResponse(cmUserPrivilegeList);
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
	 * 我的特权 首页
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myPrivilege", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myPrivilege(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		
	    try{
	    	List<SysPrivilege> sysPrivilegeList = new ArrayList<SysPrivilege>();
	    	List<SysPackage> sysPackageList  = new ArrayList<SysPackage>();
	    	List<SysPrivilegeContentType> sysPrivilegeContentTypeList = new ArrayList<SysPrivilegeContentType>();
	    	List<CmUserPrivilege> cmUserPrivilegeList = new ArrayList<CmUserPrivilege>();
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
				log.error("usrID 有误！");
	    		return object;
	    	}
	    	CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
			if(cmUserInfo == null){
				return object;
			}
			
			if( cmUserInfo.getPrivilegeLevel()!=null && !cmUserInfo.getPrivilegeLevel().equals(SysParamConstant.PRI_LEVEL_GENERAL) ){ //已开通特权
				Map<String, Object> param = new HashMap<String, Object>();
		    	param.put("userId", userId);
		    	//用户未开通的特权
		    	List<SysPrivilege> notUserPrivilegeList =  sysPrivilegeService.findByMap(param);
		    	param.put("valid", 1);
		    	//用户开通的特权
		    	cmUserPrivilegeList = cmUserPrivilegeService.findByMap(param);
		    	
		    	for (SysPrivilege sysPrivilege : notUserPrivilegeList) {
		    		String privilegeId = sysPrivilege.getId().toString();
		    		
		    		sysPackageList = getSysPackList(privilegeId);
		    		sysPrivilegeContentTypeList = getContentTypeList(privilegeId);
	    			
		    		sysPrivilege.setSysPrivilegeContentTypeList(sysPrivilegeContentTypeList);
	    			sysPrivilege.setSysPackageList(sysPackageList);
	    			sysPrivilege.setImageUrl(host+sysPrivilege.getGrayImageUrl());
		    		sysPrivilege.setFailureTime(null);
	    			sysPrivilege.setEffectTime(null);
	    			sysPrivilege.setIsNew(0);
	    			sysPrivilegeList.add(sysPrivilege);
		    	}
		    	
		    	if(cmUserPrivilegeList!= null && cmUserPrivilegeList.size()>0 ){
		    		for (CmUserPrivilege cmUserPrivilege : cmUserPrivilegeList) {
		    			String privilegeId = cmUserPrivilege.getPrivilegeId().toString() ;
		    			param.clear();
		    			param.put("privilegeId", privilegeId );
		    			SysPrivilege sysPrivilege = sysPrivilegeService.getById(Long.valueOf(cmUserPrivilege.getPrivilegeId()));
		    			
		    			sysPackageList = getSysPackList(privilegeId);
			    		sysPrivilegeContentTypeList = getContentTypeList(privilegeId);

		    			sysPrivilege.setSysPrivilegeContentTypeList(sysPrivilegeContentTypeList);
		    			sysPrivilege.setSysPackageList(sysPackageList);
		    			sysPrivilege.setImageUrl(host+sysPrivilege.getImageUrl());
		    			sysPrivilege.setFailureTime(cmUserPrivilege.getFailureTime());
		    			sysPrivilege.setEffectTime(cmUserPrivilege.getEffectTime());
		    			sysPrivilege.setIsNew(cmUserPrivilege.getIsNew());
		    			
		    			sysPrivilegeList.add(sysPrivilege);
					}
		    	}
		    	
	    		
	    	}else{//未开通
	    		
				List<SysPrivilege> templist = (List<SysPrivilege>) CacheHelper.getInstance().get("sysPrivilege@sysPrivilegeList");
				if(templist!=null && templist.size()>0){
					sysPrivilegeList = templist;
				}else{
					Map<String, Object> param = new HashMap<String, Object>();
			    	param.put("status", "1");
			    	sysPrivilegeList =  sysPrivilegeService.findByMap(param);
			    	if(sysPrivilegeList!= null && sysPrivilegeList.size()>0 ){
			    		for (SysPrivilege sysPrivilege : sysPrivilegeList) {
			    			sysPrivilege.setImageUrl(host+sysPrivilege.getGrayImageUrl());
			    			sysPrivilege.setFailureTime(null);
			    			sysPrivilege.setEffectTime(null);
			    			sysPrivilege.setIsNew(0);
			    			param.put("privilegeId", sysPrivilege.getId());
			    			sysPackageList =  sysPackageService.findByMap(param);
			    			sysPrivilegeContentTypeList =  sysPrivilegeContentTypeService.findByMap(param);
			    			sysPrivilege.setSysPrivilegeContentTypeList(sysPrivilegeContentTypeList);
			    			for (SysPrivilegeContentType sysPrivilegeContentType : sysPrivilegeContentTypeList) {
			    				sysPrivilegeContentType.setImageUrl(host+sysPrivilegeContentType.getImageUrl());
			    				
			    			}
			    			sysPrivilege.setSysPackageList(sysPackageList);
						}
			    		CacheHelper.getInstance().set(60*60*24,"sysPrivilege@sysPrivilegeList", sysPrivilegeList);
			    	}
				}
		    	
	    	}
    		object.setResponse(sysPrivilegeList);
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
	
	
	
	public List<SysPackage> getSysPackList(String privilegeId){
		Map<String, Object> param = new HashMap<String, Object>();
		List<SysPackage> sysPackageList  = new ArrayList<SysPackage>();
		
		List<SysPackage> tempSysPackageList = (List<SysPackage>) CacheHelper.getInstance().get("sysPrivilege@sysPackageList"+privilegeId);
		if(tempSysPackageList!=null && tempSysPackageList.size()>0){
			sysPackageList = tempSysPackageList;
		}else{
			param.clear();
			param.put("privilegeId", privilegeId);
			sysPackageList =  sysPackageService.findByMap(param);
			CacheHelper.getInstance().set(60*60*24,"sysPrivilege@sysPackageList"+privilegeId, sysPackageList);
		}
		return sysPackageList;
	}
	
	public List<SysPrivilegeContentType> getContentTypeList(String privilegeId){
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<SysPrivilegeContentType> sysPrivilegeContentTypeList  = new ArrayList<SysPrivilegeContentType>();
		List<SysPrivilegeContentType> tempSysPriList = (List<SysPrivilegeContentType>) CacheHelper.getInstance().get("sysPrivilege@ContentType"+privilegeId);
		if(tempSysPriList!=null && tempSysPriList.size()>0){
			sysPrivilegeContentTypeList = tempSysPriList;
		}else{
			param.clear();
			param.put("privilegeId", privilegeId);
			sysPrivilegeContentTypeList =  sysPrivilegeContentTypeService.findByMap(param);
			for (SysPrivilegeContentType sysPrivilegeContentType : sysPrivilegeContentTypeList) {
				sysPrivilegeContentType.setImageUrl(host+sysPrivilegeContentType.getImageUrl());
			}
			CacheHelper.getInstance().set(60*60*24,"sysPrivilege@ContentType"+privilegeId, sysPrivilegeContentTypeList);
		}
		return sysPrivilegeContentTypeList;
	}
	
	
	/**
	 * 开通 特权
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/openPrivilege", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject openPrivilege(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			String privilegeId = request.getParameter("privilegeId");
			if(StringUtil.isEmpty(privilegeId)){
				log.error("privilegeId 有误！");
	    		return object;
	    	}
			String packaegeId = request.getParameter("packaegeId");
			if(StringUtil.isEmpty(packaegeId)){
				log.error("packaegeId 有误！");
	    		return object;
	    	}
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("privilegeId", privilegeId);
			passMap.put("packaegeId", packaegeId);
			getDefaultPara(request, passMap, null);
			return sysPrivilegeService.openPrivilegeSubmit(passMap,object);
			
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 *  特权开通 支付时  获取支付参数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priGetPayParameter", method = {RequestMethod.GET,RequestMethod.POST})
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
	    	return sysPrivilegeService.getPayParameter(passMap, object);
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 *  判断是否有权限  专属悬赏单 , 举报
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/priCheckExclusiveP", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject priCheckExclusiveP(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId))
	    	{
	    		return object;
	    	}
	    	String join_id = request.getParameter("joinId");
	    	if(StringUtil.isEmpty(join_id))
	    	{
	    		return object;
	    	}
	    	
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("userId", userId);
			
			CmUserInfo cmUserInfo = cmUserInfoService.getById( Long.valueOf(userId) );
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById( Long.valueOf(join_id) );
			
			
			List<SysPrivilegeContent> privilegeContentList = new ArrayList<SysPrivilegeContent>();
			
			List<SysPrivilegeContent> templist = (List<SysPrivilegeContent>) CacheHelper.getInstance().get("sysPrivilege@sysPriContent");
			String privilegeId = (String) CacheHelper.getInstance().get("privilege@privilegeId");
			if(templist!=null && templist.size()>0 ){
				privilegeContentList = templist;
			}
			
			if(privilegeContentList == null  || privilegeContentList.size() <= 0 || privilegeId ==null ){
				passMap.put("typeCode", SysParamConstant.XUAN_ORDER );
				List<SysPrivilegeContent>  sysPrivilegeContentList = sysPrivilegeContentService.findByMap(passMap);
				if(sysPrivilegeContentList != null && sysPrivilegeContentList.size() > 0){
					passMap.clear();
					passMap.put("privilegeId", sysPrivilegeContentList.get(0).getPrivilegeId() );
					privilegeId = sysPrivilegeContentList.get(0).getPrivilegeId();
					CacheHelper.getInstance().set(60*60*24, "privilege@privilegeId", privilegeId);
					privilegeContentList = sysPrivilegeContentService.findByMap(passMap);
					for (SysPrivilegeContent sysPrivilegeContent : privilegeContentList) {
						sysPrivilegeContent.setImageUrl(host+sysPrivilegeContent.getImageUrl());
					}
					CacheHelper.getInstance().set(60*60*24, "sysPrivilege@sysPriContent", privilegeContentList);
				}else{
					log.error("套餐内容未配！");
				}
			}
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			if(pmTopicJoinProducts.getIsPri()==1){//是专属悬赏
					SysPrivilege sysPrivilege = sysPrivilegeService.getById(Long.valueOf(privilegeId) );
					if( !cmUserInfo.getPrivilegeLevel().equals(sysPrivilege.getLevel() )){
						returnMap.put("sysPrivilegeContent", privilegeContentList);
						returnMap.put("note", "亲，该悬赏需要" + sysPrivilege.getName() + "方可参与！");
						returnMap.put("isPri", 0);//无权 参与
					}else{
						returnMap.put("note", "");
						returnMap.put("isPri", 1);//有权 参与
					}
					returnMap.put("isXuanShang", "1");
					object.setResponse(returnMap);
		    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
		    		object.setDesc("成功");
				
			}else{
				returnMap.put("sysPrivilegeContent", privilegeContentList);
				returnMap.put("isXuanShang", "0");//不是专属悬赏单
				returnMap.put("note", "亲，只有会员才能玩哦！");
				if(cmUserInfo.getPrivilegeLevel()==null || cmUserInfo.getPrivilegeLevel().equals("general")){
					returnMap.put("isPri", 0);//无权 参与
				}else{
					returnMap.put("isPri", 1);//有权 参与
				}
				object.setResponse(returnMap);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("成功");
			}
	    	return object;
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
}
