package cn.temobi.complex.action.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.temobi.complex.action.def.FileOperationController;
import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CirclePostAccusation;
import cn.temobi.complex.entity.CirclePostDisc;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CircleType;
import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.SysType;
import cn.temobi.complex.service.CirclePostAccusationService;
import cn.temobi.complex.service.CirclePostDiscService;
import cn.temobi.complex.service.CirclePostPicService;
import cn.temobi.complex.service.CirclePostPraisesService;
import cn.temobi.complex.service.CirclePostService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;



@Controller
@RequestMapping("/boCirclePost")
public class CirclePostController extends BoBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 

	@Resource(name="circlePostService")
	private CirclePostService circlePostService;
	
	@Resource(name="cmCircleServive")
	private CmCircleServive cmCircleServive;
	
	@Resource(name="circlePostPicService")
	private CirclePostPicService circlePostPicService;
	
	@Resource(name="circlePostDiscService")
	private CirclePostDiscService circlePostDiscService;
	
	@Resource(name="circlePostPraisesService")
	private CirclePostPraisesService circlePostPraisesService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="circlePostAccusationService")
	private CirclePostAccusationService circlePostAccusationService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;

	
	
	
	/**
	 * 兴趣圈帖子列表
	 */
	@RequestMapping(value="/circlePostList")
	public String circlePostList(HttpServletRequest request,ModelMap model){
		try {
			String id = request.getParameter("id");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String text = request.getParameter("text");
			String userId = request.getParameter("userId");
			String circleId = request.getParameter("circleId");
			
			Map<String,String> map = new HashMap<String,String>();
			
			if(StringUtil.isNotEmpty(id)){
				map.put("id", id);
				model.addAttribute("id",id);
			}
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(text)){
				map.put("text", text);
				model.addAttribute("text",text);
			}
			if(StringUtil.isNotEmpty(userId)){
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			if(StringUtil.isNotEmpty(circleId)){
				map.put("circleId", circleId);
				model.addAttribute("circleId",circleId);
			}
			String sequence =request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)){
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}
			map.put("addTime", "desc");
			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("sequenceShort", orderFried);
				map.put("addTime", "");
				model.addAttribute("orderFried",orderFried);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = circlePostService.findByPageTwo(page,map);
			List<CirclePost> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/circle/circlePost_list";
	}
	
	/**
	 * 查看帖子图片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/circlePostEdit")
	public String circlePostEdit(HttpServletRequest request, ModelMap model) {
		try {
			String circlePostId = request.getParameter("id");
			if(StringUtil.isNotEmpty(circlePostId)){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("circlePostId", circlePostId);
				List<CirclePostPic> circlePostPicList = circlePostPicService.findByMap(param);
				if(circlePostPicList!=null && circlePostPicList.size() > 0 ){
					model.addAttribute("list",circlePostPicList);
				}else{
					model.addAttribute("list",null);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circlePostPic_edit";
	}
	
	/**
	 * 编辑贴子查数据功能
	 * @author Zhangzk
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/circlePostEditText")
	public String circlePostEditText(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				CirclePost circlePost = circlePostService.getById(Long.parseLong(id));
				model.addAttribute("entity", circlePost);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circlePost_edit";
	}
	
	/**
	 * 编辑贴子保存数据功能
	 * @author Zhangzk
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/circlePostEditSave",method={RequestMethod.GET,RequestMethod.POST})
	public String circlePostEditSave(HttpServletRequest request,ModelMap model,CirclePost circlePost){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			circlePostService.update(circlePost);
		}else{
			circlePostService.save(circlePost);
		}
		return "redirect:/Bo/boCirclePost/circlePostList";
	}
	
	/**
	 * 移动贴子到圈子
	 * @author Zhangzk
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/circlePostMoveEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String circlePostMove(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				CirclePost circlePost = circlePostService.getById(Long.parseLong(id));
				model.addAttribute("entity", circlePost);
			}
			
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("isRecommend", "1");
			List<CmCircle> typeList = cmCircleServive.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circlePostMove_edit";
	}
	
	/**
	 * 移动贴子修改后保存功能
	 * @author Zhangzk
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/circlePostMoveSave",method={RequestMethod.GET,RequestMethod.POST})
	public String circlePostMoveSave(HttpServletRequest request,Model model,CirclePost circlePost){
		String circleId = request.getParameter("circleId");  	//获取传上来的圈子ID号
		if(StringUtil.isNotEmpty(circleId)){
			CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(circleId));
			if(cmCircle == null){		//如果圈子的ID不存在则得到的对像为空值，返回错误信息。
				model.addAttribute("message", "该圈子不存在！");
				return "bo/error";
			}else{
				String id = request.getParameter("id");
				circlePost.setCircleId(Long.parseLong(circleId));
				if(StringUtil.isNotEmpty(id)){
					circlePostService.update(circlePost);
				}else{
					circlePostService.save(circlePost);
				}
			}
		}
		return "redirect:/Bo/boCirclePost/circlePostList";
	}
	
	/**
	 *  推荐帖子
	 */
	@RequestMapping(value="/circlePostRecommend")
	public @ResponseBody String circlePostRecommend(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CirclePost circlePost = circlePostService.getById(Long.valueOf(id));
			
			//设置推荐   未实现
			
			
			circlePostService.update(circlePost);
		}
		return "";
	}
	
	
	
	
	/**
	 * 删除帖子
	 */
	@RequestMapping(value="/circlePostDelete")
	public @ResponseBody String circlePostDelete(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CirclePost circlePost = circlePostService.getById(Long.valueOf(id));
			circlePost.setStatus(0);
			circlePostService.update(circlePost);
		}
		return "";
	}
	
	
	/**
	 * 兴趣圈帖子评论列表
	 */
	@RequestMapping(value="/circlePostDiscList")
	public String circlePostDiscList(HttpServletRequest request,ModelMap model){
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String text = request.getParameter("text");
			String discUserId = request.getParameter("discUserId");
			String circlePostId = request.getParameter("circlePostId");
			String isAccusation = request.getParameter("isAccusation");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(text)){
				map.put("text", text);
				model.addAttribute("text",text);
			}
			if(StringUtil.isNotEmpty(discUserId)){
				map.put("discUserId", discUserId);
				model.addAttribute("discUserId",discUserId);
			}
			if(StringUtil.isNotEmpty(circlePostId)){
				map.put("circlePostId", circlePostId);
				model.addAttribute("circlePostId",circlePostId);
			}
			if(StringUtil.isNotEmpty(isAccusation)){
				map.put("isAccusation", isAccusation);
				model.addAttribute("isAccusation",isAccusation);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = circlePostDiscService.findByPageTwo(page,map);
			List<CirclePostDisc> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/circle/circlePostDisc_list";
	}
	
	/**
	 * 删除帖子
	 */
	@RequestMapping(value="/circlePostDiscDelete")
	public @ResponseBody String circlePostDiscDelete(HttpServletRequest request,ModelMap model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CirclePostDisc circlePostDisc = circlePostDiscService.getById(Long.valueOf(id));
			circlePostDisc.setStatus(0);
			circlePostDiscService.update(circlePostDisc);
		}
		return "";
	}
	
	
	/**
	 * 兴趣圈帖子评论举报列表
	 */
	@RequestMapping(value="/circlePostAccusationList")
	public String circlePostAccusationList(HttpServletRequest request,ModelMap model){
		try {
			String circlePostId = request.getParameter("circlePostId");
			String circlePostDiscId = request.getParameter("circlePostDiscId");
			String userId = request.getParameter("userId");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(circlePostId)){
				map.put("circlePostId", circlePostId);
				model.addAttribute("circlePostId",circlePostId);
			}
			if(StringUtil.isNotEmpty(circlePostDiscId)){
				map.put("circlePostDiscId", circlePostDiscId);
				model.addAttribute("circlePostDiscId",circlePostDiscId);
			}
			if(StringUtil.isNotEmpty(userId)){
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = circlePostAccusationService.findByPage(page,map);
			List<CirclePostAccusation> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/circle/circlePostAccu_list";
	}
	
	
	/**
	 * 你确定要解除举报
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/accusationRelease ", method = {RequestMethod.GET, RequestMethod.POST })
	public String accusationupdate(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CirclePostAccusation circlePostAccusation = circlePostAccusationService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(circlePostAccusation)) {
					circlePostAccusation.setIsDistort(1);//不属实
					circlePostAccusationService.update(circlePostAccusation);
					CmUserMessage cmUserMessage = new CmUserMessage();
					//帖子
					if(circlePostAccusation.getCirclePostId() != null){
						CirclePost circlePost = circlePostService.getById(circlePostAccusation.getCirclePostId());
						circlePost.setIsAccusation(0);
						circlePostService.update(circlePost);
						cmUserMessage.setType(6);
						cmUserMessage.setProductId(circlePost.getId());
				    	String content = "亲，你发布的帖子" + circlePost.getTitle() + "被举报，已经重新上线，谢谢支持。";
				    	cmUserMessage.setContent(content);
				    	PushUtil.pullOneMessage(circlePost.getUserId()+"", content, Constant.PUSH_TYPE_10, circlePost.getId()+"", "");
					}
					//评论
					if(circlePostAccusation.getCirclePostDiscId() != null){
						CirclePostDisc circlePostDisc = circlePostDiscService.getById(circlePostAccusation.getCirclePostDiscId());
						circlePostDisc.setIsAccusation(0);
						circlePostDiscService.update(circlePostDisc);
						cmUserMessage.setType(6);
						cmUserMessage.setProductId(circlePostDisc.getId());
				    	cmUserMessage.setProductUrl(circlePostDisc.getThumbnail());
				    	String content = "亲，你发表的评论" + circlePostDisc.getText() + "被举报，已经重新上线，谢谢支持。";
				    	cmUserMessage.setContent(content);
				    	PushUtil.pullOneMessage(circlePostDisc.getDiscUserId()+"", content, Constant.PUSH_TYPE_10, circlePostDisc.getId()+"", "");
					}
					
			    	cmUserMessage.setUserId(circlePostAccusation.getAccusationUserId());
			    	cmUserMessage.setSendUserId(circlePostAccusation.getAccusationUserId());
			    	cmUserMessageService.save(cmUserMessage);
			    	
					CmUserInfo accusationCmUserInfo = cmUserInfoService.getById(circlePostAccusation.getAccusationUserId());
					accusationCmUserInfo.setAccuErrCount(accusationCmUserInfo.getAccuErrCount()+1);
					cmUserInfoService.update(accusationCmUserInfo);
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("isDistort", "0");
					map.put("userId", circlePostAccusation.getUserId());
					List<CirclePostAccusation> list = circlePostAccusationService.findByMap(map);
					if(list == null || list.size() <= 0){
						CmUserInfo cmUserInfo = cmUserInfoService.getById(circlePostAccusation.getUserId());
						cmUserInfo.setIsAccusation(0);
						cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
						cmUserInfoService.update(cmUserInfo);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/circle/circlePostAccu_list";
	}
	
	
	/**
	 * 你确认属实
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/accusationIsTrue", method = {RequestMethod.GET, RequestMethod.POST })
	public String accusationStatus(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CirclePostAccusation circlePostAccusation = circlePostAccusationService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(circlePostAccusation)) {
					circlePostAccusation.setIsDistort(2);
					circlePostAccusationService.update(circlePostAccusation);
			
					CmUserInfo cmUserInfo = cmUserInfoService.getById(circlePostAccusation.getUserId());
					cmUserInfo.setIsAccusation(1);
					cmUserInfoService.update(cmUserInfo);
			    	
			    	CmUserInfo cmUserInfoTo = cmUserInfoService.getById(circlePostAccusation.getAccusationUserId());
			    	cmUserInfoTo.setAccuCount(cmUserInfoTo.getAccuCount()-1);
			    	cmUserInfoService.update(cmUserInfoTo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/circle/circlePostAccu_list";
	}
	
	/**
	 * 发布帖子 跳转
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/circlePostReleaseEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String circlePostReleaseEdit(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(cmCircle)) {
					model.addAttribute("cmCircle", cmCircle);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circlePostRelease_edit";
	}
	
	
	/**
	 * 发布帖子 保存
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/releasePostSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String releasePostSave(HttpServletRequest request, Model model,CirclePost circlePost) {
		try {
			CmUserInfo cmUserInfo = cmUserInfoService.getById(circlePost.getUserId());
			if(StringUtil.isEmpty(cmUserInfo)){
				model.addAttribute("message", "用户不存在！");
				return "bo/error";
			}
			
			circlePost.setDiscussNum(0);
			circlePost.setShareNum(0);
			circlePost.setZanNum(0);
			circlePost.setStatus(1);
			circlePost.setIsAccusation(0);
			circlePost.setCreatedWhen(DateUtils.getCurrDateTimeStr());
			circlePostService.save(circlePost);		
			

			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        Map fileMap =multipartRequest.getFileMap();
	        if(fileMap != null && fileMap.size()>0){
	        	List<CirclePostPic> circlePostPicList = new ArrayList<CirclePostPic>();
	        	FileOperationController fileOperationController = new FileOperationController();
	        	List<Map<String,String>>  returnMapList = fileOperationController.uploadAppImage(request);
	        	if(returnMapList!= null && returnMapList.size()>0){
	        		for (Map<String, String> returnMap : returnMapList) {
	        			if(returnMap.get("error")!=null){
	        				return "bo/error";
	        			}else{
	        				CirclePostPic circlePostPic = new CirclePostPic();
	        				circlePostPic.setCirclePostId(circlePost.getId());
	        				circlePostPic.setPicUrl(returnMap.get("picUrl"));
	        				circlePostPic.setThumbnail(returnMap.get("thumbnail"));
	        				circlePostPicList.add(circlePostPic);
	        			}
	        		}
	        		circlePostPicService.saveBatch(circlePostPicList);
	        	}
	        }
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/boCirclePost/circlePostList";
	}
	
	
}
