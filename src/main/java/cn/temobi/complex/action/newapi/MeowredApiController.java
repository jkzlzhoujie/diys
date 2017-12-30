package cn.temobi.complex.action.newapi;

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

import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.complex.service.UserOptionService;
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
	private final String indexPage = "redNet/index";
	
	@Resource(name="weixinVoteRecordService")
	private WeixinVoteRecordService weixinVoteRecordService;
	
	@Resource(name = "userOptionService")
    private UserOptionService       userOptionService;
	
	
	
	/**
	 * 支持我的用户数量和投票数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
	public String test (HttpServletRequest request,Model model) {
//		String indexPage = "/redNet/index";
//		String indexPage = "/redNet/signUpinfo";
//		String indexPage = "/redNet/meSupport";
		String indexPage = "/redNet/supportMe";
		return indexPage;
	}
	
	/**
	 * 支持我的
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/supportMe", method = {RequestMethod.GET,RequestMethod.POST})
	public String supportMe (HttpServletRequest request,Model model) {
		String netRedUserId = request.getParameter("netRedUserId");
    	if(StringUtil.isEmpty(netRedUserId)){
    		return indexPage;
    	}
		return "/redNet/supportMe";
	}
	
	
	
	/**
	 * 支持我的用户数量和投票数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/supportMeUserCount ", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject supportMeUserCount (HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String netRedUserId = request.getParameter("netRedUserId");
	    	if(StringUtil.isEmpty(netRedUserId)){
	    		object.setDesc("网红用户不能为空");
	    	}
	    	Map<String,Object> param = new HashMap<String, Object>();
			param.clear();
	    	param.put("netRedUserId", netRedUserId);
	    	param.put("type", 1);
	    	int count = weixinVoteRecordService.getCount(param).intValue();
	    	param.clear();
	    	param.put("netRedUserId", netRedUserId);
	    	param.put("type", 2);
	    	int callCount = weixinVoteRecordService.getCount(param).intValue();
	    	VoteRecord voteRecord = new VoteRecord();
	    	voteRecord.setCallCountPer(callCount);
	    	voteRecord.setCountPer(count);
			param.clear();
	    	param.put("netRedUserId", netRedUserId);
	    	List<VoteRecord>  voteRecords = weixinVoteRecordService.getSumCountByType(param);
	    	voteRecord.setCallCount( voteRecords.get(0)!=null? voteRecords.get(0).getCallCount() :0 );
	    	voteRecord.setCount(voteRecords.get(0)!=null? voteRecords.get(0).getCount() :0 );
	    	object.setResponse(voteRecord);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc(Constant.RESPONSE_SUCCESS_DESC);
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 支持我的用户列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/supportMeUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject supportMeUser(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String netRedUserId = request.getParameter("netRedUserId");
	    	if(StringUtil.isEmpty(netRedUserId)){
	    		object.setDesc("网红用户不能为空");
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
	    	object.setResponse(voteRecordList);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc(Constant.RESPONSE_SUCCESS_DESC);
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
	}
	
	/**
	 * 我支持的网红
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/meSupport", method = {RequestMethod.GET,RequestMethod.POST})
	public String meSupport (HttpServletRequest request,Model model) {
		String voteUserId = request.getParameter("voteUserId");
    	if(StringUtil.isEmpty(voteUserId)){
    		return indexPage;
    	}
		return "/redNet/supportMe";
	}
	
	
	/**
	 * 我支持的网红列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/iSupportNetRedUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject iSupportNetRedUserList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			String voteUserId = request.getParameter("voteUserId");
	    	if(StringUtil.isEmpty(voteUserId)){
	    		object.setDesc("微信用户不能为空");
	    		return object;
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
	    	object.setResponse(voteRecordList);
	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
	    	object.setDesc(Constant.RESPONSE_SUCCESS_DESC);
		}catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙");
		}
		return object;
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
