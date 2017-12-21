package cn.temobi.complex.action.newapi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.dto.CirclePostDiscDto;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.complex.entity.CirclePostDiscPraises;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CirclePostPraises;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.service.BlackListService;
import cn.temobi.complex.service.CirclePostAccusationService;
import cn.temobi.complex.service.CirclePostDiscPraisesService;
import cn.temobi.complex.service.CirclePostDiscService;
import cn.temobi.complex.service.CirclePostPicService;
import cn.temobi.complex.service.CirclePostPraisesService;
import cn.temobi.complex.service.CirclePostService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmCircleUserFollowService;
import cn.temobi.complex.service.CmUserGroupService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/circlePost")
public class CirclePostApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 

	@Resource(name="circlePostService")
	private CirclePostService circlePostService;
	
	@Resource(name="circlePostPicService")
	private CirclePostPicService circlePostPicService;
	
	@Resource(name="circlePostDiscService")
	private CirclePostDiscService circlePostDiscService;
	
	@Resource(name="circlePostDiscPraisesService")
	private CirclePostDiscPraisesService circlePostDiscPraisesService;
	
	@Resource(name="circlePostPraisesService")
	private CirclePostPraisesService circlePostPraisesService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="cmUserGroupService")
	private CmUserGroupService cmUserGroupService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="circlePostAccusationService")
	private CirclePostAccusationService circlePostAccusationService;
	
	@Resource(name="blackListService")
	private BlackListService blackListService;
	
	@Resource(name="cmCircleServive")
	private CmCircleServive cmCircleServive;
	
	@Resource(name="cmCircleUserFollowService")
	private CmCircleUserFollowService cmCircleUserFollowService;
	
	
	
	
	//除了浏览其他都要登录
	
	/**
	 * 获取帖子列表(最新 ,最热广场)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCirclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getCirclePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)){
				type = "new";
				map.put("addTime", "desc");
			}else{
				if(type.trim().equals("new")){//最新
					map.put("addTime", "desc");
				}else if(type.trim().equals("hot")){//热门
					map.put("zanNum", "desc");
				}
			}
			String userId = request.getParameter("userId");
			
			String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			
			map.put("isAccusation", 0);//未举报
			map.put("status", 1);
			
			String key = "circle@circlePostList"+type+pageNo;
			// 采用 缓存 ，缓存最新 和 最热
			List<CirclePost> templist = (List<CirclePost>) CacheHelper.getInstance().get(key);
			List<CirclePost>  circlePostList = new ArrayList<CirclePost>();
			
			//最热 采用 缓存
			if( Integer.valueOf(pageNo)<= 3 && type.toString().equals("hot") && templist!=null && templist.size()>0){
				circlePostList = templist;
			}else{
				Page<CirclePost> Page = circlePostService.findByPage(page, map);
				circlePostList = Page.getResult();
				if(circlePostList!=null && circlePostList.size() > 0){
					for (CirclePost circlePost : circlePostList) {
						Map<String,Object> param = new HashMap<String, Object>();
						//帖子图片
						param.clear();
						param.put("circlePostId", circlePost.getId());
						List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
						List<String> picStrList = new ArrayList<String>();
						for (CirclePostPic circlePostPic : circlePostPicList) {
							picStrList.add(host + circlePostPic.getPicUrl() );
						}
						circlePost.setCirclePostPicList(picStrList);
						//未登录用户-- 查看是否点过赞
						if(StringUtil.isNotEmpty(userId)){
				    		param.clear();
				    		param.put("userId", userId);
				    		param.put("circlePostId", circlePost.getId());
				    		List<CirclePostPraises> circlePostPraisesList = circlePostPraisesService.findByMap(param);
				    		for (CirclePostPraises circlePostPraises : circlePostPraisesList) {
								if(circlePostPraises.getType() - 0 == 0){
									circlePost.setIsZan(1);
								}else if(circlePostPraises.getType()-1 == 0){
									circlePost.setIsCai(1);
								}else if(circlePostPraises.getType()-2 == 0){
									circlePost.setIsFen(1);
								}
							}
				    	}
					}
				}
			}
			//用户未登录  缓存设置
			if(StringUtil.isEmpty(userId) && Integer.valueOf(pageNo)<= 3 && (templist==null || templist.size()<= 0 )){
				if(circlePostList!= null && circlePostList.size() > 0 && type.toString().equals("hot")){
					CacheHelper.getInstance().set(60*60*2,key, circlePostList);
				}
				if(circlePostList!= null && circlePostList.size() > 0 && type.toString().equals("new")){
					CacheHelper.getInstance().set(60*3,key, circlePostList);
				}
			}
			
			//已登录用户-- 查看是否点过赞
			if(StringUtil.isNotEmpty(userId)){
				for (CirclePost circlePost : circlePostList) {
					Map<String,Object> param = new HashMap<String, Object>();
		    		param.clear();
		    		param.put("userId", userId);
		    		param.put("circlePostId", circlePost.getId());
		    		List<CirclePostPraises> circlePostPraisesList = circlePostPraisesService.findByMap(param);
		    		for (CirclePostPraises circlePostPraises : circlePostPraisesList) {
						if(circlePostPraises.getType() - 0 == 0){
							circlePost.setIsZan(1);
						}else if(circlePostPraises.getType()-1 == 0){
							circlePost.setIsCai(1);
						}else if(circlePostPraises.getType()-2 == 0){
							circlePost.setIsFen(1);
						}
					}
				}
			}
			
			object.setResponse(circlePostList);
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
	 * 获取帖子列表(动态 我关注的圈子和我自己创建的圈子)
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getMyCirclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getMyCirclePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			
			List<CirclePost>  circlePostList = new ArrayList<CirclePost>();
			
			Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			map.put("userId", cmUserInfo.getId());
			List<CmCircle> circleIdList= cmCircleServive.getByCircleIdAboutMe(map);
			String circleIds = "";
			for (CmCircle cmCircle : circleIdList) {
				circleIds = circleIds + "," + cmCircle.getId() ;
			}
			if(StringUtil.isEmpty(circleIds)){
				object.setResponse(circlePostList);
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				return object;
			}
			map.clear();	
			map.put("circleIds", circleIds.substring(1));
			map.put("addTime", "desc");
			String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			
			map.put("isAccusation", 0);//未举报
			map.put("status", 1);
			
			
			Page<CirclePost> Page = circlePostService.findByPage(page, map);
			circlePostList = Page.getResult();
			
			if(circlePostList!=null && circlePostList.size() > 0){
				for (CirclePost circlePost : circlePostList) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.clear();
					param.put("circlePostId", circlePost.getId());
					List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
					List<String> picStrList = new ArrayList<String>();
					for (CirclePostPic circlePostPic : circlePostPicList) {
						picStrList.add(host + circlePostPic.getPicUrl() );
					}
					circlePost.setCirclePostPicList(picStrList);
					
					//查看是否点过赞
					if(StringUtil.isNotEmpty(cmUserInfo.getId())){
			    		param.clear();
			    		param.put("userId", cmUserInfo.getId());
			    		param.put("circlePostId", circlePost.getId());
			    		List<CirclePostDiscPraises> circlePostDiscPraisesList = circlePostDiscPraisesService.findByMap(param);
			    		for (CirclePostDiscPraises circlePostDiscPraises : circlePostDiscPraisesList) {
							if(circlePostDiscPraises.getType()- 0 == 0){
								circlePost.setIsZan(1);
							}else if(circlePostDiscPraises.getType()-1 == 0){
								circlePost.setIsCai(1);
							}else if(circlePostDiscPraises.getType()-2 == 0){
								circlePost.setIsFen(1);
							}
						}
			    	}else{
			    		circlePost.setIsZan(0);
			    		circlePost.setIsCai(0);
						circlePost.setIsFen(0);
					}
					
				}
				
			}
			object.setResponse(circlePostList);
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
	 * 获取单个圈子里帖子列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSingleCirclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getSingleCirclePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String circleId = request.getParameter("circleId");
			if(StringUtil.isEmpty(circleId)){
				log.error("circleid 为空！");
				return object;
			}
			map.put("circleId", circleId);
			
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)){
				map.put("addTime", "desc");
			}else{
				if(type.trim().equals("new")){//最新
					map.put("addTime", "desc");
				}else if(type.trim().equals("hot")){//热门
					map.put("zanNum", "desc");
				}
			}
			
			String userId = request.getParameter("userId");
		
			
			String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			
			map.put("isAccusation", 0);//未举报
			map.put("status", 1);
			
			List<CirclePost>  circlePostList = new ArrayList<CirclePost>();
			Page<CirclePost> Page = circlePostService.findByPage(page, map);
			circlePostList = Page.getResult();
			
			if(circlePostList!=null && circlePostList.size() > 0){
				for (CirclePost circlePost : circlePostList) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.clear();
					param.put("circlePostId", circlePost.getId());
					List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
					List<String> picStrList = new ArrayList<String>();
					for (CirclePostPic circlePostPic : circlePostPicList) {
						picStrList.add(host + circlePostPic.getPicUrl() );
					}
					circlePost.setCirclePostPicList(picStrList);
					//登录用户-- 查看是否点过赞
					if(StringUtil.isNotEmpty(userId)){
			    		param.clear();
			    		param.put("userId", userId);
			    		param.put("circlePostId", circlePost.getId());
			    		List<CirclePostPraises> circlePostPraisesList = circlePostPraisesService.findByMap(param);
			    		for (CirclePostPraises circlePostPraises : circlePostPraisesList) {
							if(circlePostPraises.getType() - 0 == 0){
								circlePost.setIsZan(1);
							}else if(circlePostPraises.getType()-1 == 0){
								circlePost.setIsCai(1);
							}else if(circlePostPraises.getType()-2 == 0){
								circlePost.setIsFen(1);
							}
						}
			    	}
				}
				
			}
			object.setResponse(circlePostList);
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
	 * 发布帖子
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/releasePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject releasePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String noPic = request.getParameter("noPic");
			if(StringUtil.isEmpty(noPic)){
				log.error("noPic 不能为空！");
				return object;
			}
			map.put("noPic", noPic.trim());
			
			String title = request.getParameter("title");
			if(StringUtil.isEmpty(title)){
				log.error("title 不能为空！");
				return object;
			}
			map.put("title", title.trim());
			
			String text = request.getParameter("text");
			if(StringUtil.isEmpty(text)){
				log.error("text 不能为空！");
				return object;
			}
			map.put("text", text.trim());
			
			String circleId = request.getParameter("circleId");
			if(StringUtil.isEmpty(circleId)){
				log.error("circleid 为空！");
				return object;
			}
			map.put("circleId", circleId.trim());
			getDefaultPara(request, map, null);
	    	return circlePostService.releaseSave(request, map,object);
			
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	
	/**
	 * 帖子详情
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/detailCiclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject detailCiclePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)){
				log.error("circlePostId 为空！");
				return object;
			}
			
			String userId = request.getParameter("userId");
			
			CirclePost circlePost= circlePostService.getById(Long.valueOf(circlePostId));
			if(circlePost != null){
				Map<String,Object> param = new HashMap<String, Object>();
				param.put("circlePostId", circlePostId);
				List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
				List<String> picStrList = new ArrayList<String>();
				for (CirclePostPic circlePostPic : circlePostPicList) {
					picStrList.add(host + circlePostPic.getPicUrl() );
				}
				
				//登录用户-- 查看是否点过赞
				if(StringUtil.isNotEmpty(userId)){
		    		param.clear();
		    		param.put("userId", userId);
		    		param.put("circlePostId", circlePostId);
		    		List<CirclePostPraises> circlePostPraisesList = circlePostPraisesService.findByMap(param);
		    		for (CirclePostPraises circlePostPraises : circlePostPraisesList) {
						if(circlePostPraises.getType() - 0 == 0){
							circlePost.setIsZan(1);
						}else if(circlePostPraises.getType()-1 == 0){
							circlePost.setIsCai(1);
						}else if(circlePostPraises.getType()-2 == 0){
							circlePost.setIsFen(1);
						}
					}
		    	}
				
				
				
				circlePost.setCirclePostPicList(picStrList);
				object.setResponse(circlePost);
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				object.setDesc("成功");
			}else{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("帖子已删除！");
			}
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	
	/**
	 * 删除帖子
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject deletePost(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)){
				log.error("circlePostId 为空！");
				return object;
			}
			CirclePost circlePost= circlePostService.getById(Long.valueOf(circlePostId));
			if(circlePost != null ){
				circlePost.setStatus(2);
				circlePostService.update(circlePost);
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
	 * 帖子-点赞，踩，分享
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/postZan", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject postZan(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)){
				log.error("circlePostId 不能为空！");
				return object;
			}
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)){
				log.error("type 不能为空！");
				return object;
			}
			CirclePost circlePost = circlePostService.getById(Long.valueOf( circlePostId ));
			
			Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			
			map.put("circlePostId", circlePost.getId());
			map.put("userId", cmUserInfo.getId() );
			map.put("type", type );
			int num = circlePostPraisesService.getCount(map).intValue();
			String mesCont = "赞了";
			if(num > 0 ){
				if(type.trim().equals("0")){
					object.setDesc("您已赞！");
					mesCont = "赞了";
				}else if(type.trim().equals("1")){
					object.setDesc("您已踩！");
					mesCont = "踩了";
				}else if(type.trim().equals("2")){
					object.setDesc("您已分享！");
					mesCont = "分享了";
				}
				object.setCode(Constant.RESPONSE_ERROR_10011);
				return object;
			}
			if(type.trim().equals("0")){
				circlePost.setZanNum(circlePost.getZanNum()+1);
			}else if(type.trim().equals("1")){
				circlePost.setCaiNum(circlePost.getCaiNum()+1);
			}else if(type.trim().equals("2")){
				circlePost.setShareNum(circlePost.getShareNum()+1);
			}
			
			int saveNum = circlePostPraisesService.circlePostZ(circlePost,cmUserInfo,mesCont,type);
			if(saveNum > 0 ){
				object.setResponse("");
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			}
			
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 举报帖子
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reportCirclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject reportCirclePost(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo reportCmUserInfo = (CmUserInfo) map.get("cmUserInfo");//举报人
			if(reportCmUserInfo == null){
	    		return object;
	    	}
	    	String type = request.getParameter("type");//举报类型
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	
	    	String circlePostId = request.getParameter("circlePostId");
	    	if(StringUtil.isEmpty(circlePostId)){
	    		return object;
	    	}
	    	CirclePost circlePost = circlePostService.getById(Long.parseLong(circlePostId));
	    	if(circlePost== null){
	    		return object;
	    	}
	    	if(reportCmUserInfo.getIsAccusation() == 1){
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("被举报不能使用");
				return object;
	    	}
	    	if(reportCmUserInfo.getAccuErrCount() > 3) {
    			object.setCode(Constant.RESPONSE_ERROR_10003);
    			object.setDesc("暂时无法使用举报功能，请联系X秀确认");
    			return object;
	    	}
	    	
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("circlePostId", circlePostId);
	    	searchMap.put("accusationUserId", reportCmUserInfo.getId());
	    	List<CirclePostAccusation> list = circlePostAccusationService.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10011);
				object.setDesc("已举报");
				return object;
	    	}
	    	
	    	circlePostService.reportCirclePost(circlePost,reportCmUserInfo,type);
	    	PushUtil.pullOneMessage(circlePost.getUserId().toString(), Constant.JB_DISC, Constant.PUSH_TYPE_31, circlePost.getId().toString(), "");
	    	
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
			
			PushUtil.pushAccountListMultiple(userlist,"亲，有帖子被举报，请及时处理！", Constant.PUSH_TYPE_18, "", "");
	    	
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
	 * 获取单个帖子评论列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getCirclePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getCirclePostDisc(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)){
				log.error("circlePostId 不能为空！");
				return object;
			}
			CirclePost crclePost = circlePostService.getById(Long.valueOf(circlePostId));
			if(crclePost == null ){
				return object;
			}else{
				if(crclePost.getStatus()!=1 ){
					object.setDesc("帖子已删除！");
					object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
					return object;
				}				
			}
			
			String userId = request.getParameter("userId");
			
			String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			map.put("isAccusation", 0);//未举报
			map.put("status", 1);
			map.put("circlePostId", circlePostId);
			map.put("limit", page.getPageSize());
			map.put("offset", page.getOffset());
			long count = circlePostDiscService.getCount(map).longValue();
			
			List<CirclePostDiscDto>  circlePostDiscDtoList = new ArrayList<CirclePostDiscDto>();
			List<CirclePostDisc>  circlePostDiscList = new ArrayList<CirclePostDisc>();
			Page<CirclePostDisc> Page = circlePostDiscService.findByPage(page, map);
			circlePostDiscList = Page.getResult();
		
			
			for (CirclePostDisc circlePostDisc : circlePostDiscList) {
				CirclePostDiscDto circlePostDiscDto = new CirclePostDiscDto();
				circlePostDiscDto.setCirclePostId(circlePostDisc.getCirclePostId());
				circlePostDiscDto.setDiscHeadImageUrl(circlePostDisc.getDiscHeadImageUrl());
				circlePostDiscDto.setDiscNickName(circlePostDisc.getDiscNickName());
				circlePostDiscDto.setDiscTime(circlePostDisc.getDiscTime());
				circlePostDiscDto.setDiscUserId(circlePostDisc.getDiscUserId());
				circlePostDiscDto.setId(circlePostDisc.getId());
				if( circlePostDisc.getPicUrl() != null ){
					circlePostDiscDto.setPicUrl(host + circlePostDisc.getPicUrl());
				}
				circlePostDiscDto.setZanNum(circlePostDisc.getZanNum());
				circlePostDiscDto.setText(circlePostDisc.getText());
				circlePostDiscDto.setEmoji(circlePostDisc.getEmoji());
				
				//登录用户-- 查看是否点过赞
				if(StringUtil.isNotEmpty(userId)){
					Map<String,Object> param = new HashMap<String,Object>();
		    		param.clear();
		    		param.put("userId", userId);
		    		param.put("circlePostDiscId", circlePostDisc.getId());
		    		List<CirclePostDiscPraises> circlePostDiscPraisesList = circlePostDiscPraisesService.findByMap(param);
		    		for (CirclePostDiscPraises circlePostDiscPraises : circlePostDiscPraisesList) {
						if(circlePostDiscPraises.getType()- 0 == 0){
							circlePostDiscDto.setIsZan(1);
						}else if(circlePostDiscPraises.getType()-1 == 0){
							circlePostDiscDto.setIsCai(1);
						}else if(circlePostDiscPraises.getType()-2 == 0){
							circlePostDiscDto.setIsFen(1);
						}
					}
		    	}
				
				if(circlePostDisc.getType() == 2 && circlePostDisc.getParentId()!= null){//回复的评论 查找出父级评论
					CirclePostDisc parentCirclePostDisc = circlePostDiscService.getById(circlePostDisc.getParentId());
					if(parentCirclePostDisc != null && parentCirclePostDisc.getStatus()==1 ){
						if( parentCirclePostDisc.getPicUrl() != null ){
							parentCirclePostDisc.setPicUrl(host + parentCirclePostDisc.getPicUrl());
						}
						circlePostDiscDto.setParentCirclePostDisc(parentCirclePostDisc);
						circlePostDiscDtoList.add(circlePostDiscDto);
					}
					//备注：是回复评论，找不到父级的 此回复也不展示
				}else{
					circlePostDiscDtoList.add(circlePostDiscDto);
				}
			}
			
			Map<String,Object> returnMap = new HashMap<String, Object>();
			returnMap.put("circlePostDiscDtoList", circlePostDiscDtoList);
			returnMap.put("count", count);
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
	 * 对单个帖子评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/circlePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject circlePostDisc(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String noPic = request.getParameter("noPic");
			if(StringUtil.isEmpty(noPic)){
				log.error("noPic 不能为空！");
				return object;
			}
			map.put("noPic", noPic.trim());
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)  ){
				log.error("circlePostId 为空！");
				return object;
			}
			map.put("circlePostId", circlePostId.trim());
			
			String text = request.getParameter("text");
			if(StringUtil.isNotEmpty(text)  ){
				map.put("text", text.trim());
			}
			String emoji = request.getParameter("emoji");
			if(StringUtil.isNotEmpty(emoji)  ){
				map.put("emoji", emoji.trim());
			}
			
			getDefaultPara(request, map, null);
			
			return circlePostDiscService.circlePostDiscSave(request, map,object);
			
			
			
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	
	/**
	 * 删除帖子评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/deletePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject deletePostDisc(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String circlePostDiscId = request.getParameter("circlePostDiscId");
			if(StringUtil.isEmpty(circlePostDiscId)){
				log.error("circlePostDiscId 为空！");
				return object;
			}
			CirclePostDisc circlePostDisc = circlePostDiscService.getById(Long.valueOf( circlePostDiscId ));
			if(circlePostDisc == null ){
				return object;
			}
			CirclePost circlePost = circlePostService.getById(circlePostDisc.getCirclePostId());
			circlePost.setDiscussNum(circlePost.getDiscussNum()-1);
			circlePostService.update(circlePost);
			
			int num = circlePostDiscService.delete(circlePostDiscId);
			if(num > 0){
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			}
			return object;
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 回复单个帖子评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/circlePostDiscApply", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject circlePostDiscApply(HttpServletRequest request,HttpServletResponse response) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String noPic = request.getParameter("noPic");
			if(StringUtil.isEmpty(noPic)){
				log.error("noPic 不能为空！");
				return object;
			}
			map.put("noPic", noPic.trim());
			
			String circlePostId = request.getParameter("circlePostId");
			if(StringUtil.isEmpty(circlePostId)){
				log.error("circlePostId 为空！");
				return object;
			}
			map.put("circlePostId", circlePostId.trim());
			
			String circlePostDiscId = request.getParameter("circlePostDiscId");
			if(StringUtil.isEmpty(circlePostDiscId)){
				log.error("circlePostDiscId 为空！");
				return object;
			}
			map.put("circlePostDiscId", circlePostDiscId.trim());

			String text = request.getParameter("text");
			if(StringUtil.isNotEmpty(text)  ){
				map.put("text", text.trim());
			}
			String emoji = request.getParameter("emoji");
			if(StringUtil.isNotEmpty(emoji)  ){
				map.put("emoji", emoji.trim());
			}
			
			getDefaultPara(request, map, null);
			return circlePostDiscService.circlePostRepplySave(request, map,object);
			
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 帖子评论点赞
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/circlePostDiscZ", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject circlePostDiscZ(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String circlePostDiscId = request.getParameter("circlePostDiscId");
	    	if(StringUtil.isEmpty(circlePostDiscId)){
	    		log.error("circlepostdiscId 不能为空！");
				return object;
	    	}
	    	
	    	CirclePostDisc circlePostDisc = circlePostDiscService.getById(Long.parseLong(circlePostDiscId));
	    	if(circlePostDisc == null){
	    		return object;
	    	}
	    	
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
	    	
			map.put("circlePostDiscId", circlePostDisc.getId());
			map.put("userId", cmUserInfo.getId() );
			map.put("type", 0 );
			int num = circlePostDiscPraisesService.getCount(map).intValue();
			if(num > 0 ){
				object.setDesc("您已赞！");
				object.setCode(Constant.RESPONSE_ERROR_10011);
				return object;
			}
			circlePostDisc.setZanNum(circlePostDisc.getZanNum()+1);		
			
			int saveNum = circlePostDiscPraisesService.circlePostDiscZ(circlePostDisc,cmUserInfo);
			
			if(saveNum > 0 ){
				object.setResponse("");
				object.setDesc("成功");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			}
			return object;
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	/**
	 * 举报帖子评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/reportCirclePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject reportCirclePostDisc(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo reportCmUserInfo = (CmUserInfo) map.get("cmUserInfo");//举报人
			if(reportCmUserInfo == null){
	    		return object;
	    	}
	    	String type = request.getParameter("type");//举报类型
	    	if(StringUtil.isEmpty(type)){
	    		return object;
	    	}
	    	String circlePostDiscId = request.getParameter("circlePostDiscId");
	    	if(StringUtil.isEmpty(circlePostDiscId)){
	    		return object;
	    	}
	    	CirclePostDisc circlePostDisc = circlePostDiscService.getById(Long.parseLong(circlePostDiscId));
	    	if(circlePostDisc == null){
	    		return object;
	    	}
	    	if(reportCmUserInfo.getIsAccusation() == 1){
	    		object.setCode(Constant.RESPONSE_ERROR_10006);
				object.setDesc("被举报不能使用");
				return object;
	    	}
	    	if(reportCmUserInfo.getAccuErrCount() > 3) {
    			object.setCode(Constant.RESPONSE_ERROR_10003);
    			object.setDesc("暂时无法使用举报功能，请联系X秀确认");
    			return object;
	    	}
	    	
	    	Map<String,Object> searchMap = new HashMap<String, Object>();
	    	searchMap.put("circlePostDiscId", circlePostDiscId);
	    	searchMap.put("accusationUserId", reportCmUserInfo.getId());
	    	List<CirclePostAccusation> list = circlePostAccusationService.findByMap(searchMap);
	    	if(list != null && list.size() > 0)
	    	{
	    		object.setCode(Constant.RESPONSE_ERROR_10011);
				object.setDesc("已举报");
				return object;
	    	}
	    	
	    	circlePostDiscService.reportCirclePostDisc(circlePostDisc,reportCmUserInfo,type);
	    	PushUtil.pullOneMessage(circlePostDisc.getDiscUserId().toString(), Constant.JB_DISC, Constant.PUSH_TYPE_32, circlePostDisc.getId().toString(), "");
	    	
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
			if(reportCmUserInfo.getAccuErrCount() > 3) 
	    	{
    			object.setCode(Constant.RESPONSE_ERROR_10003);
    			object.setDesc("暂时无法使用举报功能，请联系X秀确认");
    			return object;
	    	}
			
			PushUtil.pushAccountListMultiple(userlist,"亲，有帖子评论被举报，请及时处理！", Constant.PUSH_TYPE_18, "", "");
	    	
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
	 * 我发布的帖子
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myCirclePost", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myCirclePost(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	String userId = request.getParameter("userId");
	    	if(StringUtil.isEmpty(userId)){
	    		log.error("userId 不能为空！");
				return object;
	    	}else{
	    		CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.valueOf(userId));
	    		if(cmUserInfo == null){
	    			return object;
	    		}
	    	}
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("isAccusation", 0);//未举报
			searchMap.put("status", 1);
			searchMap.put("userId", userId);
			searchMap.put("addTime", "desc");
			
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			searchMap.put("limit", page.getPageSize());
			searchMap.put("offset", page.getOffset());
			
			List<CirclePost> circlePostList = new ArrayList<CirclePost>();
			Page<CirclePost> Page = circlePostService.findByPage(page, searchMap);
			circlePostList = Page.getResult();
			
			if(circlePostList!=null && circlePostList.size() > 0){
				for (CirclePost circlePost : circlePostList) {
					Map<String,Object> param = new HashMap<String, Object>();
					param.put("circlePostId", circlePost.getId());
					List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
					List<String> picStrList = new ArrayList<String>();
					for (CirclePostPic circlePostPic : circlePostPicList) {
						picStrList.add(host + circlePostPic.getPicUrl() );
					}
					circlePost.setCirclePostPicList(picStrList);
					
					//查看是否点过赞
					if(StringUtil.isNotEmpty(userId)){
			    		param.clear();
			    		param.put("userId", userId);
			    		param.put("circlePostId", circlePost.getId());
			    		List<CirclePostDiscPraises> circlePostDiscPraisesList = circlePostDiscPraisesService.findByMap(param);
			    		for (CirclePostDiscPraises circlePostDiscPraises : circlePostDiscPraisesList) {
							if(circlePostDiscPraises.getType()- 0 == 0){
								circlePost.setIsZan(1);
							}else if(circlePostDiscPraises.getType()-1 == 0){
								circlePost.setIsCai(1);
							}else if(circlePostDiscPraises.getType()-2 == 0){
								circlePost.setIsFen(1);
							}
						}
			    	}
					
				}
			}
			
			object.setResponse(circlePostList);
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
	 * 我发表的评论
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myCirclePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject myCirclePostDisc(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("isAccusation", 0);//未举报
			searchMap.put("status", 1);
			searchMap.put("discUserId", cmUserInfo.getId());
			
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			searchMap.put("limit", page.getPageSize());
			searchMap.put("offset", page.getOffset());

			List<CirclePostDisc> circlePostDiscList = new ArrayList<CirclePostDisc>();
			Page<CirclePostDisc> Page = circlePostDiscService.findByPage(page, searchMap);
			circlePostDiscList = Page.getResult();
			
			List<CirclePostDiscDto>  circlePostDiscDtoList = new ArrayList<CirclePostDiscDto>();
			for (CirclePostDisc circlePostDisc : circlePostDiscList) {
				CirclePostDiscDto circlePostDiscDto = new CirclePostDiscDto();
				CirclePost circlePost = circlePostService.getById(circlePostDisc.getCirclePostId());
				if(circlePost != null && circlePost.getStatus()==1 ){
					circlePostDiscDto.setCirclePost(circlePost);
				}
				circlePostDiscDto.setCirclePostId(circlePostDisc.getCirclePostId());
				circlePostDiscDto.setDiscHeadImageUrl(circlePostDisc.getDiscHeadImageUrl());
				circlePostDiscDto.setDiscNickName(circlePostDisc.getDiscNickName());
				circlePostDiscDto.setDiscTime(circlePostDisc.getDiscTime());
				circlePostDiscDto.setDiscUserId(circlePostDisc.getDiscUserId());
				circlePostDiscDto.setId(circlePostDisc.getId());
				circlePostDiscDto.setZanNum(circlePostDisc.getZanNum());
				circlePostDiscDto.setText(circlePostDisc.getText());
				circlePostDiscDto.setEmoji(circlePostDisc.getEmoji());
				if( circlePostDisc.getPicUrl() != null ){
					circlePostDiscDto.setPicUrl(host + circlePostDisc.getPicUrl());
				}
				if(circlePostDisc.getType() == 2 && circlePostDisc.getParentId()!= null){//回复的评论 查找出父级评论
					CirclePostDisc parentCirclePostDisc = circlePostDiscService.getById(circlePostDisc.getParentId());
					if(parentCirclePostDisc != null && parentCirclePostDisc.getStatus()==1 ){
						if( parentCirclePostDisc.getPicUrl() != null ){
							parentCirclePostDisc.setPicUrl(host + parentCirclePostDisc.getPicUrl());
						}
						circlePostDiscDto.setParentCirclePostDisc(parentCirclePostDisc);
						circlePostDiscDtoList.add(circlePostDiscDto);
					}else{	//备注：是恢复评论，找不到父级的 此回复也不展示
						circlePostDiscDto.setCirclePost(null);
					}
				}else{
					circlePostDiscDtoList.add(circlePostDiscDto);
				}
			}
			
			object.setResponse(circlePostDiscDtoList);
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
	 * 回复我的评论列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/replyMyCirclePostDisc", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject replyMyCirclePostDisc(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			
			Map<String, Object> searchMap = new HashMap<String, Object>();
			searchMap.put("isAccusation", 0);//未举报
			searchMap.put("status", 1);
			searchMap.put("discUserId", cmUserInfo.getId());
			searchMap.put("type",2);//回复
			
			String pageNo = request.getParameter("pageNo");
	    	String pageSize = request.getParameter("pageSize");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "10";
			Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
			searchMap.put("limit", page.getPageSize());
			searchMap.put("offset", page.getOffset());
			
			List<CirclePostDisc> circlePostDiscList = new ArrayList<CirclePostDisc>();
			Page<CirclePostDisc> Page = circlePostDiscService.findByPage(page, searchMap);
			circlePostDiscList = Page.getResult();
			
			List<CirclePostDiscDto>  circlePostDiscDtoList = new ArrayList<CirclePostDiscDto>();
			for (CirclePostDisc circlePostDisc : circlePostDiscList) {
				CirclePostDiscDto circlePostDiscDto = new CirclePostDiscDto();
				CirclePost circlePost = circlePostService.getById(circlePostDisc.getCirclePostId());
				if(circlePost != null && circlePost.getStatus()==1 ){
					circlePostDiscDto.setCirclePost(circlePost);
				}else{
					circlePostDiscDto.setCirclePost(null);
				}
				circlePostDiscDto.setCirclePostId(circlePostDisc.getCirclePostId());
				circlePostDiscDto.setDiscHeadImageUrl(circlePostDisc.getDiscHeadImageUrl());
				circlePostDiscDto.setDiscNickName(circlePostDisc.getDiscNickName());
				circlePostDiscDto.setDiscTime(circlePostDisc.getDiscTime());
				circlePostDiscDto.setDiscUserId(circlePostDisc.getDiscUserId());
				circlePostDiscDto.setId(circlePostDisc.getId());
				circlePostDiscDto.setZanNum(circlePostDisc.getZanNum());
				circlePostDiscDto.setText(circlePostDisc.getText());
				circlePostDiscDto.setEmoji(circlePostDisc.getEmoji());
				if( circlePostDisc.getPicUrl() != null ){
					circlePostDiscDto.setPicUrl(host + circlePostDisc.getPicUrl());
				}
				if(circlePostDisc.getType() == 2 && circlePostDisc.getParentId()!= null){//回复的评论 查找出父级评论
					CirclePostDisc parentCirclePostDisc = circlePostDiscService.getById(circlePostDisc.getParentId());
					if(parentCirclePostDisc != null && parentCirclePostDisc.getStatus()==1 ){
						circlePostDiscDto.setParentCirclePostDisc(parentCirclePostDisc);
						if( parentCirclePostDisc.getPicUrl() != null ){
							parentCirclePostDisc.setPicUrl(host + parentCirclePostDisc.getPicUrl());
						}
						circlePostDiscDtoList.add(circlePostDiscDto);
					}else{	//备注：是恢复评论，找不到父级的 此回复也不展示
						circlePostDiscDto.setCirclePost(null);
					}
				}else{
					circlePostDiscDtoList.add(circlePostDiscDto);
				}
			}
			
			object.setResponse(circlePostDiscDtoList);
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
	 * 上传文件 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/uploadFile", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject uploadFile(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
	    	Map<String, Object> map = new HashMap<String, Object>();
	    	getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			
			request.setCharacterEncoding("utf-8");  //设置编码  
	        //获得磁盘文件条目工厂  
	        DiskFileItemFactory factory = new DiskFileItemFactory();  
	        //获取文件需要上传到的路径  
	        String path = request.getRealPath("/upload");  
	          
	        //如果没以下两行设置的话，上传大的 文件 会占用 很多内存，  
	        //设置暂时存放的 存储室 , 这个存储室，可以和 最终存储文件 的目录不同  
	        /** 
	         * 原理 它是先存到 暂时存储室，然后在真正写到 对应目录的硬盘上，  
	         * 按理来说 当上传一个文件时，其实是上传了两份，第一个是以 .tem 格式的  
	         * 然后再将其真正写到 对应目录的硬盘上 
	         */  
	        factory.setRepository(new File(path));  
	        //设置 缓存的大小，当上传文件的容量超过该缓存时，直接放到 暂时存储室  
	        factory.setSizeThreshold(1024*1024) ;  
	        //高水平的API文件上传处理  
	        ServletFileUpload upload = new ServletFileUpload(factory);  
	        try {  
	            //可以上传多个文件  
	            List<FileItem> list = (List<FileItem>)upload.parseRequest(request);  
	              
	            for(FileItem item : list)  
	            {  
	                //获取表单的属性名字  
	                String name = item.getFieldName();  
	                  
	                //如果获取的 表单信息是普通的 文本 信息  
	                if(item.isFormField())  
	                {                     
	                    //获取用户具体输入的字符串 ，名字起得挺好，因为表单提交过来的是 字符串类型的  
	                    String value = item.getString() ;  
	                      
	                    request.setAttribute(name, value);  
	                }  
	                //对传入的非 简单的字符串进行处理 ，比如说二进制的 图片，电影这些  
	                else  
	                {  
	                    /** 
	                     * 以下三步，主要获取 上传文件的名字 
	                     */  
	                    //获取路径名  
	                    String value = item.getName() ;  
	                    //索引到最后一个反斜杠  
	                    int start = value.lastIndexOf("\\");  
	                    //截取 上传文件的 字符串名字，加1是 去掉反斜杠，  
	                    String filename = value.substring(start+1);  
	                      
	                    request.setAttribute(name, filename);  
	                      
	                    //真正写到磁盘上  
	                    //它抛出的异常 用exception 捕捉  
	                      
	                    //item.write( new File(path,filename) );//第三方提供的  
	                      
	                    //手动写的  
	                    OutputStream out = new FileOutputStream(new File(path,filename));  
	                      
	                    InputStream in = item.getInputStream() ;  
	                      
	                    int length = 0 ;  
	                    byte [] buf = new byte[1024] ;  
	                      
	                    System.out.println("获取上传文件的总共的容量："+item.getSize());  
	  
	                    // in.read(buf) 每次读到的数据存放在   buf 数组中  
	                    while( (length = in.read(buf) ) != -1)  
	                    {  
	                        //在   buf 数组中 取出数据 写到 （输出流）磁盘上  
	                        out.write(buf, 0, length);  
	                          
	                    }  
	                      
	                    in.close();  
	                    out.close();  
	                }  
	            }  
	            object.setResponse("");
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				object.setDesc("成功");
	              
	              
	        } catch (FileUploadException e) {  
	            e.printStackTrace();  
	        }  
	        catch (Exception e) {  
	            e.printStackTrace();  
	        }  
			
			
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
}
