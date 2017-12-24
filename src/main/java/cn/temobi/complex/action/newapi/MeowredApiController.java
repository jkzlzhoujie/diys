package cn.temobi.complex.action.newapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.complex.service.WeixinVoteRecordService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 喵网红  接口
 * @author zhouj
 * 
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/meowred")
public class MeowredApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	@Resource(name="weixinVoteRecordService")
	private WeixinVoteRecordService weixinVoteRecordService;
	
	
	/**
	 * 支持我的  总打call数，和总得票数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/supportMeCallAndVoteCount", method = {RequestMethod.GET,RequestMethod.POST})
	public String supportMeCallAndVoteCount(HttpServletRequest request,Model model) {
		String indexPage = "redirect:/client/health/healthIndex";
		
    	String netRedUserId = request.getParameter("netRedUserId");
    	if(StringUtil.isEmpty(netRedUserId)){
    		return indexPage;
    	}
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("netRedUserId", netRedUserId);
    	List<VoteRecord>  voteRecords = weixinVoteRecordService.getSumCountByType(param);
    	model.addAttribute("voteRecords",voteRecords);
		return "weichat/index/wallet";
	}
	
	/**
	 * 支持我的  总打call数人数，和投票人数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/supportMePersonCount", method = {RequestMethod.GET,RequestMethod.POST})
	public String supportMePersonCount(HttpServletRequest request,Model model) {
		String indexPage = "redirect:/client/health/healthIndex";
		
    	String netRedUserId = request.getParameter("netRedUserId");
    	if(StringUtil.isEmpty(netRedUserId)){
    		return indexPage;
    	}
    	Map<String,Object> param = new HashMap<String, Object>();
    	param.put("netRedUserId", netRedUserId);
    	param.put("type", 1);
    	int count = weixinVoteRecordService.getCount(param).intValue();
    	
    	param.put("netRedUserId", netRedUserId);
    	param.put("type", 2);
    	int callCount = weixinVoteRecordService.getCount(param).intValue();
		
		model.addAttribute("count",count);
		model.addAttribute("callCount",callCount);
		
		return "weichat/index/wallet";
	}
	
	/**
	 * 支持我的用户列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/supportMeUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public String supportMeUser(HttpServletRequest request,Model model) {
		String indexPage = "redirect:/client/health/healthIndex";
		
    	String netRedUserId = request.getParameter("netRedUserId");
    	if(StringUtil.isEmpty(netRedUserId)){
    		return indexPage;
    	}
    	Map<String,Object> param = new HashMap<String, Object>();
    	String pageSize = request.getParameter("pageSize");
    	String pageNo = request.getParameter("pageNo");
    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		param.put("limit", page.getPageSize());
		param.put("offset", page.getOffset());
    	param.put("netRedUserId", netRedUserId);
    	
    	Page<VoteRecord> pageResult = new Page<VoteRecord>();
    	pageResult = weixinVoteRecordService.getSupportMeVoteRecordPage(page,param);
    	List<VoteRecord> voteRecordList = pageResult.getResult();
		model.addAttribute("voteRecordList",voteRecordList);
		model.addAttribute("pageNo",pageResult.getPageNo());
		return "weichat/index/wallet";
	}
	
	
	/**
	 * 我支持的网红列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/iSupportNetRedUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public String iSupportNetRedUserList(HttpServletRequest request,Model model) {
		String indexPage = "redirect:/client/health/healthIndex";
		
    	String voteUserId = request.getParameter("voteUserId");
    	if(StringUtil.isEmpty(voteUserId)){
    		return indexPage;
    	}
    	Map<String,Object> param = new HashMap<String, Object>();
    	String pageSize = request.getParameter("pageSize");
    	String pageNo = request.getParameter("pageNo");
    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
    	if(StringUtil.isEmpty(pageSize)) pageSize = "5";
		Page page =  new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
		param.put("limit", page.getPageSize());
		param.put("offset", page.getOffset());
    	param.put("voteUserId", voteUserId);
    	
    	Page<VoteRecord> pageResult = new Page<VoteRecord>();
    	pageResult = weixinVoteRecordService.getISupportNetRedVoteRecordPage(page,param);
    	List<VoteRecord> voteRecordList = pageResult.getResult();
		model.addAttribute("voteRecordList",voteRecordList);
		model.addAttribute("pageNo",pageResult.getPageNo());
		return "weichat/index/wallet";
	}
	
	
	/**
	 * 用户投票
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userVote", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject userVote(HttpServletRequest request,Model model) {
		
    	ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
			String voteUserId = request.getParameter("voteUserId");
			String netRedUserId = request.getParameter("netRedUserId");
			String count = request.getParameter("count");
			String type = request.getParameter("type");
	    	if(StringUtil.isEmpty(voteUserId) || StringUtil.isEmpty(netRedUserId) || StringUtil.isEmpty(count) || StringUtil.isEmpty(type) ){
	    		return object;
	    	}
	    	return weixinVoteRecordService.saveVote(object ,voteUserId,netRedUserId,count,type);
	    	
			
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	
	
	
}
