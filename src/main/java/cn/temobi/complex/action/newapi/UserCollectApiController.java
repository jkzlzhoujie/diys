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

import com.salim.cache.CacheHelper;


import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.PIndexDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.CmUserCollect;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.service.CmUserCollectService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 用户收藏接口
 * @author zhouj
 * 
 * 2016年4月25日10
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/collect")
public class UserCollectApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="cmUserCollectService")
	private CmUserCollectService cmUserCollectService;
	
	/**
	 * 作品收藏 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/productCollectList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject productCollectList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "9";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
	    	Page page =  new Page(pageNoNum,pageSizeNum);
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    	Map<String,Object> param = new HashMap<String, Object>();
	    	param.put("type", 1);
	    	param.put("userId", cmUserInfo.getId());
			Page<PmProductInfoDto> pageResult = cmUserCollectService.findProductDtoByPage(page, param);
		
			List<PmProductInfoDto> list = pageResult.getResult();
			for(PmProductInfoDto pmProductInfoDto:list)
			{
				pmProductInfoDto.setThumbnail(host+pmProductInfoDto.getThumbnail());
				pmProductInfoDto.setUrl(host+pmProductInfoDto.getUrl());
				pmProductInfoDto.setDepict(pmProductInfoDto.getDescription());
			}
    		object.setResponse(list);
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
	 * 作者收藏 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/authorCollectList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject authorCollectList(HttpServletRequest request) {
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
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    	Map<String,Object> param = new HashMap<String, Object>();
	    	param.put("type", 2);
	    	param.put("userId", cmUserInfo.getId());
	    	
			Page<CmUserInfoListDto> pageResult = cmUserCollectService.findAuthorDto(page,param);
	    	List<CmUserInfoListDto> list = pageResult.getResult();
	    	
    		object.setResponse(list);
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
	 * 主题悬赏求P 收藏 列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/topicCollectList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject topicCollectList(HttpServletRequest request) {
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
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    	Map<String,Object> param = new HashMap<String, Object>();
	    	param.put("type", 3);
	    	param.put("userId", cmUserInfo.getId());
	    	
	    	Page<PmTopicJoinProducts> pageResult =  cmUserCollectService.findPmTopicJoinByPage(page, param);
	    	
	    	List<PmTopicJoinProducts> pmTopicJoinProducts = new ArrayList<PmTopicJoinProducts>();
	    	pmTopicJoinProducts = pageResult.getResult();
	    	List<PIndexDto> pIndexDtoList = cmUserCollectService.findPtuProuctDto(pmTopicJoinProducts);
	    	
    		object.setResponse(pIndexDtoList);
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
	 * 添加 收藏 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/addCollect", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject addCollect(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	CmUserCollect cmUserCollect = new CmUserCollect();
	    	cmUserCollect.setType(Integer.valueOf(type));
	    	Map<String,Object> param = new HashMap<String, Object>();
	    	param.put("type", type);
	    	if(type.trim().equals("1")){
	    		String productId = request.getParameter("productId");
		    	if(StringUtil.isEmpty(productId)){
		    		return object;
		    	}
		    	cmUserCollect.setProductId(Long.valueOf(productId) );
		    	param.put("productId", productId);
	    	}else if(type.trim().equals("2")){
	    		String authorId = request.getParameter("authorId");
		    	if(StringUtil.isEmpty(authorId)){
		    		return object;
		    	}
		    	cmUserCollect.setAuthorId(Long.valueOf(authorId));
		    	param.put("authorId", authorId);
	    	}else if(type.trim().equals("3")){
	    		String topicId = request.getParameter("topicId");
		    	if(StringUtil.isEmpty(topicId)){
		    		return object;
		    	}
		    	cmUserCollect.setTopicId(Long.valueOf(topicId) );
		    	param.put("topicId", topicId);
	    	}
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    	cmUserCollect.setUserId(cmUserInfo.getId());
	    	
	    	param.put("userId", cmUserInfo.getId());
	    	List<CmUserCollect> cmUserCollectList = cmUserCollectService.findByMap(param); 
	    	if(cmUserCollectList == null || cmUserCollectList.size()==0){
	    		cmUserCollectService.save(cmUserCollect);
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("收藏成功");
	    	}else{
	    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    		object.setDesc("已收藏");

	    	}
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 删除 收藏 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/delCollect", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject delCollect(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String collectIds = request.getParameter("collectIds");
	    	if(StringUtil.isEmpty(collectIds)){
	    		return object;
	    	}
	    	
	    	cmUserCollectService.batchDelCollect(collectIds);
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
	 * 取消 收藏 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/cancelCollect", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject cancelCollect(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	CmUserCollect cmUserCollect = new CmUserCollect();
	    	cmUserCollect.setType(Integer.valueOf(type));
	    	Map<String,Object> param = new HashMap<String, Object>();
	    	param.put("type", type);
	    	if(type.trim().equals("1")){
	    		String productId = request.getParameter("productId");
	        	if(StringUtil.isEmpty(productId)){
	        		return object;
	        	}
	        	cmUserCollect.setProductId(Long.valueOf(productId) );
	        	param.put("productId", productId);
	    	}else if(type.trim().equals("2")){
	    		String authorId = request.getParameter("authorId");
	        	if(StringUtil.isEmpty(authorId)){
	        		return object;
	        	}
	        	cmUserCollect.setAuthorId(Long.valueOf(authorId));
	        	param.put("authorId", authorId);
	    	}else if(type.trim().equals("3")){
	    		String topicId = request.getParameter("topicId");
	        	if(StringUtil.isEmpty(topicId)){
	        		return object;
	        	}
	        	cmUserCollect.setTopicId(Long.valueOf(topicId) );
	        	param.put("topicId", topicId);
	    	}
	    	
	    	Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	CmUserInfo cmUserInfo = (CmUserInfo) passMap.get("cmUserInfo");
	    	cmUserCollect.setUserId(cmUserInfo.getId());
	    	param.put("userId", cmUserInfo.getId());
	    	
	    	cmUserCollectService.cancelCollect(param);
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
	
	
	
}
