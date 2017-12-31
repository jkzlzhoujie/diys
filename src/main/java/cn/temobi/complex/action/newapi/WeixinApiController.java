package cn.temobi.complex.action.newapi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.salim.cache.CacheHelper;
import com.sms.SmsMessageUtil;
import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.WeixinConfigure;
import com.weichat.common.CommonUtil;
import com.weichat.common.Token;
import com.weichat.common.WeixinClientUtil;
import com.weichat.common.WeixinUser;


import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.AccessRecord;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.complex.entity.WxSign;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.complex.service.UserOptionService;
import cn.temobi.complex.service.WeixinAccessRecordService;
import cn.temobi.complex.service.WeixinClientApiService;
import cn.temobi.complex.service.WeixinUserInfoService;
import cn.temobi.complex.service.WeixinVoteRecordService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 公众号  三个主界面
 * @author zhouj
 * 
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/weixin")
public class WeixinApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	private final String indexPage = "redNet/index";
	
	@Resource(name="weixinUserInfoService")
	private WeixinUserInfoService weixinUserInfoService;
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="weixinClientApiService")
	private WeixinClientApiService weixinClientApiService;
	
	@Resource(name="weixinAccessRecordService")
	private WeixinAccessRecordService accessRecordService;
	
	@Resource(name="userOptionService")
	private UserOptionService userOptionService;
	
	@Resource(name = "weixinVoteRecordService")
	private WeixinVoteRecordService recordService;
	
	@Resource(name="weixinVoteRecordService")
	private WeixinVoteRecordService weixinVoteRecordService;
	
	
	/**
	 * 配置好  从预约界面开始，授权 同意完回调到预约界面
	 * 第一步，起点
	 * 微信前面三个菜单链接都要配
	 * 跳转到 落地报名  -- 微信入口
	 * @param request
	 * @param model
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7a7bc6c16b83c47d&redirect_uri=https%3A%2F%2Fwww.hehuanginfo.com%2Fdiys%2FBo%2Fmeowred%2FsupportMeCallAndVoteCount&response_type=code&scope=snsapi_base&state=123#wechat_redirect
	 * @return
	 */
	@RequestMapping(value="/landRegistration", method = {RequestMethod.GET, RequestMethod.POST })
	public String landRegistration(HttpServletRequest request) {
		
		String weiChatCode = request.getParameter("code");
		String userId =  request.getParameter("userId");
		log.error("code = "+ weiChatCode);
		WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
		if(StringUtil.isNotEmpty(weiChatCode) && StringUtil.isEmpty(userId)){//不为空时 已经获取，为空时未获取 获取用户信息
			weixinUserInfo = getWeixinUserInfo(weiChatCode,weixinUserInfo);
			userId = weixinUserInfo.getId().toString(); 
		}
		//每进入这个页面 增加一次访问记录
		AccessRecord record = new AccessRecord();
		record.setCreateTime(new Date());
		record.setAttentionUserId(Long.valueOf(userId));
		accessRecordService.save(record);
		
    	if(StringUtil.isNotEmpty(userId)){
    		log.error("userId=" + userId);
    	}else{
    		log.error("微信授权用户信息错误！");
    		return indexPage;
    	}
    	return "redirect:/clientNew/redNet/registrationRedio?voteUserId="+userId;
	}
	
	/**
	 * 获取微信用户信息
	 * @param code
	 * @param cmUserInfo
	 * @return
	 */
	private WeixinUserInfo getWeixinUserInfo(String code,WeixinUserInfo cmUserInfo ){
		WeixinUserInfo weixinUserInfo =  new WeixinUserInfo();
		CommonUtil commonUtil = new CommonUtil();
		Token token = new Token();
		token = commonUtil.getOauthSecond(code,token);
		if(token != null && token.getOpenid() != null){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("openId", token.getOpenid());
			List<WeixinUserInfo>  weixinUserInfoList =  weixinUserInfoService.findByMap(map);
			if(weixinUserInfoList !=null && weixinUserInfoList.size() > 0){
				weixinUserInfo = weixinUserInfoList.get(0);
			}else{
				log.error("token: accessToken = " + token.getAccessToken() + ", openId =" + token.getOpenid() );
				updateTokenToDB(token.getAccessToken());
				WeixinUser userInfo = new WeixinUser();
				userInfo =  commonUtil.getUserInfo(token.getAccessToken(), token.getOpenid(),userInfo);
				weixinUserInfo.setCity(userInfo.getCity());
				weixinUserInfo.setOpenId(token.getOpenid());
				weixinUserInfo.setHeadImgUrl(userInfo.getHeadImgUrl());
				weixinUserInfo.setNickname(userInfo.getNickname());
				weixinUserInfo.setSex(userInfo.getSex());
				weixinUserInfo.setOpenId(userInfo.getOpenId());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				weixinUserInfo.setSubscribeTime(sdf.format(new Date() ));
				weixinUserInfoService.save(weixinUserInfo);
			}
		}else{
			log.error("token is null");
		}
		return weixinUserInfo;
	}
	
	/**
	 * 报名视频介绍页
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registrationRedio", method = {RequestMethod.GET,RequestMethod.POST})
	public String registrationRedio (String voteUserId) {
    	return "/redNet/registrationRedio";
	}
	
	/**
	 * 报名介绍页
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/registrationIntroduce", method = {RequestMethod.GET,RequestMethod.POST})
	public String registrationIntroduce (String voteUserId) {
    	return "/redNet/registrationIntroduce";
	}
	
	
	
	
	/**
	 * 报名页
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/signUpinfo", method = {RequestMethod.GET,RequestMethod.POST})
	public String signUpinfo () {
    	return "/redNet/signUpinfo";
	}
	
	 /**
     * 获取验证码
     * 
     * @param request
     * @return
     */
	@ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getCode", method = { RequestMethod.GET, RequestMethod.POST })
    public ResponseObject getCode(String phone) {
        ResponseObject object = initResponseObject();
        object.setCode(Constant.RESPONSE_PARAMS_ERROR);
        object.setDesc("参数错误");
        try {
            int num = (int)((Math.random()*9+1)*100000);
            long time = System.currentTimeMillis();
            SmsMessageUtil util = new SmsMessageUtil();
            if(util.sendMessage(phone, String.valueOf(num), "")){
                object.setCode(Constant.RESPONSE_SUCCESS_CODE);
                CachedValueAndTime(phone, num+"|"+time, 5*60);
                object.setDesc("发送成功");
            }else{
                object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
                object.setDesc("发送失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }

    /**
     * 校验验证码
     * 
     * @param request
     * @return
     */
    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/checkMsgCode", method = { RequestMethod.GET,RequestMethod.POST })
    public ResponseObject checkMsgCode(HttpServletRequest request) {
        ResponseObject object = initResponseObject();
        object.setCode(Constant.RESPONSE_PARAMS_ERROR);
        object.setDesc("参数错误");
        try {
            String msgCode = request.getParameter("msgCode");
            if (StringUtil.isEmpty(msgCode)) {
                return object;
            }
            String mobile = request.getParameter("mobile");
            if (StringUtil.isEmpty(mobile)) {
                return object;
            }

            String oldCode = (String) CacheHelper.getInstance().get(mobile);
            if (StringUtil.isEmpty(oldCode)) {
                object.setCode(Constant.RESPONSE_ERROR_10004);
                object.setDesc("验证码过期");
                return object;
            }

            if (oldCode.split("\\|")[0].equals(msgCode)) {
                object.setDesc("成功");
                object.setCode(Constant.RESPONSE_SUCCESS_CODE);
                return object;
            } else {
                object.setCode(Constant.RESPONSE_ERROR_10003);
                object.setDesc("验证码错误");
                return object;
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }
	
	
	 /**
     * 报名 保存
     * @param 
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/saveNetRedUser", method = {RequestMethod.GET, RequestMethod.POST })
    public ResponseObject saveNetRedUser(String userStr,String code,String weichatUserId ) {
        ResponseObject object = initResponseObject();
        try {
        	Gson  gson =  new  Gson() ;
        	NetRedUser user = gson.fromJson(userStr, NetRedUser.class);
            if (user.getId() != 0) {
            	userOptionService.update(user);
            } else {
                String oldCode = (String) CacheHelper.getInstance().get(user.getPhone());
                if(StringUtils.equals(code, oldCode)){
                	userOptionService.save(user);
	                object.setDesc("成功");
	                object.setResponse(user);
	                object.setCode(Constant.RESPONSE_SUCCESS_CODE);
                }else{
                    object.setCode(Constant.RESPONSE_ERROR_CODE);
                    object.setDesc("验证码错误,请重新输入");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }
    
    /**
     * 完善个人中心
     * @param netRedUsers
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateNetRedUser", method = {RequestMethod.GET, RequestMethod.POST })
    public ResponseObject updateNetRedUser(HttpServletRequest request,String userStr) {
        ResponseObject object = initResponseObject();
        try {
        	System.out.println(userStr);
        	Gson  gson =  new  Gson() ;
        	NetRedUser user = gson.fromJson(userStr, NetRedUser.class);
            userOptionService.update(user);
            object.setResponse(user);
            object.setDesc("成功");
            object.setCode(Constant.RESPONSE_SUCCESS_CODE);
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
    }
    
    
    /**
     * 个人信息完善页
     * @param request
     * @return
     */
    @RequestMapping(value = "/userForm", method = {RequestMethod.GET, RequestMethod.POST })
    public String signUpInfo(String netRedUserId) {
    	if(StringUtil.isEmpty(netRedUserId)){
    		return indexPage;
    	}
        return "redNet/userForm";
    }
	
	/**
	 * 查询个人信息详情
	 */
    @ResponseBody
    @RequestMapping(value = "/getNetRedUser", method = { RequestMethod.GET, RequestMethod.POST })
    public NetRedUser getNetRedUser(String netRedUserId) {
        NetRedUser netRedUser = null;
        if (StringUtil.isNotEmpty(netRedUserId)) {
             netRedUser = userOptionService.getById(Long.parseLong(netRedUserId));
        }
        return netRedUser;
    }

    /**
	 * 支持我的
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/supportMe", method = {RequestMethod.GET,RequestMethod.POST})
	public String supportMe (String netRedUserId) {
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
	public ResponseObject supportMeUserCount (String netRedUserId) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
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
	 * 支持我的用户列表 及 给我的投票和打call数
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/supportMeUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject supportMeUser(String netRedUserId,int pageSize,int pageNo) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	if(StringUtil.isEmpty(netRedUserId)){
	    		object.setDesc("网红用户不能为空");
	    	}
	    	Map<String,Object> param = new HashMap<String, Object>();
			Page page =  new Page(pageNo, pageSize);
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
	 * 支持单个网红 的  微信用户列表 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/supportNetRedWeixinUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject supportNetRedWeixinUserList(String netRedUserId,int pageSize,int pageNo) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	if(StringUtil.isEmpty(netRedUserId)){
	    		object.setDesc("网红用户不能为空");
	    	}
	    	Map<String,Object> param = new HashMap<String, Object>();
			Page page =  new Page(pageNo, pageSize);
			param.put("limit", page.getPageSize());
			param.put("offset", page.getOffset());
	    	param.put("netRedUserId", netRedUserId);
	    	
	    	Page<VoteRecord> pageResult = new Page<VoteRecord>();
	    	pageResult = weixinVoteRecordService.findBySupportNetRedPage(page,param);
	    	List<VoteRecord> voteRecordList = pageResult.getResult();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	for(VoteRecord voteRecord : voteRecordList){
	    		if(voteRecord.getCreateTime() != null){
	    			voteRecord.setCreateTimeStr(sdf.format(voteRecord.getCreateTime()));
	    		}
	    		if(voteRecord.getType() != 0){
	    			voteRecord.setTypeName(voteRecord.getType()==1?"成功投票助力":"为他打call");
	    		}
	    	}
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
	public String meSupport (String voteUserId) {
    	if(StringUtil.isEmpty(voteUserId)){
    		return indexPage;
    	}
		return "/redNet/meSupport";
	}
	
	
	/**
	 * 我支持的网红列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/iSupportNetRedUserList", method = {RequestMethod.GET,RequestMethod.POST})
	public ResponseObject iSupportNetRedUserList(String voteUserId ,int pageSize,int pageNo) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
	    	if(StringUtil.isEmpty(voteUserId)){
	    		object.setDesc("微信用户不能为空");
	    		return object;
	    	}
	    	Map<String,Object> param = new HashMap<String, Object>();
			Page page =  new Page(pageNo, pageSize);
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
	public @ResponseBody ResponseObject userVote(String voteUserId,String netRedUserId,String count,String type) {
    	ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			
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
	
	
	/**
	 * 打call充值支付
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/payRecharge", method = {RequestMethod.GET, RequestMethod.POST })
	public String payRecharge(HttpServletRequest request,Model model) {
		WeixinClientUtil weixinClientUtil = new WeixinClientUtil();
		String requestUrl = request.getRequestURL().toString(); 
    	String access_token = "";
    	String jsapi_ticket = "";
    	access_token = getToken();
    	jsapi_ticket = getJsapiTicketFromDB();
		Map<String, Object>  configMap = weixinClientUtil.getWxConfig(request,requestUrl,access_token,jsapi_ticket);
		if(configMap != null && configMap.size() > 0){
			if(configMap.get("new_access_token") != null){
				updateTokenToDB(configMap.get("new_access_token").toString());
			}
			if(configMap.get("new_jsapi_ticket") != null){
				updateJsapiTicketDB(configMap.get("new_jsapi_ticket").toString());
			}
			model.addAttribute("appId",configMap.get("appId"));
			model.addAttribute("configTimestamp",configMap.get("timestamp"));
			model.addAttribute("configNonceStr",configMap.get("nonceStr"));
			model.addAttribute("signature",configMap.get("signature"));
			model.addAttribute("userId",request.getParameter("userId"));
		}
		return "weichat/index/rechargePrice";
	}
	
	
	/**
	 * 获取付款充值h5 付款参数
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getRechargePayParam", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody List<WxSign>  getRechargePayParam(HttpServletRequest request) {
		List<WxSign> wxSignList = new ArrayList<WxSign>();
		WxSign wxSign = new WxSign();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		ResponseObject object = initResponseObject();
		
		object = weixinClientApiService.reCharge(request, object);
		
		returnMap = (Map<String, Object>) object.getResponse();
		
		//封装h5页面调用参数
		Map<String ,Object > signMap=new HashMap<String ,Object >();
        String nonceStr = UUID.randomUUID().toString().substring(0, 15);
        long timestamp = System.currentTimeMillis() / 1000;
        String prepayid = returnMap.get("prepayid").toString();
        signMap.put("appId", WeixinConfigure.APPID);
        signMap.put("timeStamp", timestamp+"");
        signMap.put("package", "prepay_id="+prepayid);
        signMap.put("signType", "MD5");
        signMap.put("nonceStr", nonceStr);
        String paySign = Signature.getSign(signMap);
        wxSign.setPaytimestamp(timestamp+"");
        wxSign.setPaynonceStr( nonceStr);
        wxSign.setPaySign(paySign );
        wxSign.setPaypackage("prepay_id="+prepayid);
        wxSignList.add(wxSign);
	    return wxSignList;
	}
	
	
	
	/**
	 * object 返回设置
	 * @param object
	 * @param code
	 */
	public ResponseObject initResponseObject() {
		ResponseObject object = new ResponseObject();
		object.setCode("");
		object.setResponse("");
		object.setDesc("");
		object.setDate(DateUtils.getCurrentDateTime("yyyyMMddHHmmss"));
		return object;
	}
	
	

	/**
	 * 获取access_token
	 * @param code 微信端返回的code
	 * @return
	 */
	public String getToken(){
		String access_token = "";
		Map<String, String> map = new HashMap<String, String>();
		map.clear();
    	map.put("code", Constant.ACCESS_TOKEN);	//从表中查出参数值。然后进行赋值
    	List<SysParameter> spList = sysParameterService.findByMap(map);
    	if(spList!=null && spList.size() > 0){
    		SysParameter sysParameter = spList.get(0);
    		if(sysParameter.getValue() != null && sysParameter.getUpdateWhen() != null){
    			Date date = new Date();
    			if(sysParameter.getUpdateWhen().getTime() > date.getTime() ){
    				access_token = sysParameter.getValue();
    			}
    		}
    	}else{
    		//重新获取
    		CommonUtil commonUtil = new CommonUtil();
    		String new_access_token = commonUtil.getToken();
    		updateTokenToDB(new_access_token);
    	}
    	return access_token;
	}
	
	
	/**
	 * 更新access_token 缓存到数据库的
	 * @return
	 */
	public String updateTokenToDB(String new_access_token){
		Map<String, String> map = new HashMap<String, String>();
		map.clear();
    	map.put("code", Constant.ACCESS_TOKEN);	//从表中查出参数值。然后进行赋值
    	List<SysParameter> spList = sysParameterService.findByMap(map);
    	if(spList!=null && spList.size() > 0){
    		SysParameter sysParameter = spList.get(0);
    		if(sysParameter.getValue() != null && sysParameter.getUpdateWhen() != null){
    			 Calendar calendar = Calendar.getInstance ();
    		     calendar.add (Calendar.SECOND, 7200);//有效时间向后推了2个小时
    		     sysParameter.setValue(new_access_token);
    		     sysParameter.setUpdateWhen(calendar.getTime());
    		     sysParameterService.update(sysParameter);
    	     }
    	}
    	return new_access_token;
	}
	
	
	
	
	/**
	 * 获取缓存到数据库的jsapi_ticket
	 * @return
	 */
	public String getJsapiTicketFromDB(){
		String jsapi_ticket = "";
		Map<String, String> map = new HashMap<String, String>();
		map.clear();
    	map.put("code", Constant.JSAPI_TICKET);	//从表中查出参数值。然后进行赋值
    	List<SysParameter> spList = sysParameterService.findByMap(map);
    	if(spList!=null && spList.size() > 0){
    		SysParameter sysParameter = spList.get(0);
    		if(sysParameter.getValue() != null && sysParameter.getUpdateWhen() != null){
    			Date date = new Date();
    			if(sysParameter.getUpdateWhen().getTime() > date.getTime() ){
    				jsapi_ticket = sysParameter.getValue();
    			}
    		}
    	}
    	return jsapi_ticket;
	}
	
	/**
	 * 更新JSAPI_TICKET 缓存到数据库的
	 * @return
	 */
	public String updateJsapiTicketDB(String new_jsapi_ticket){
		Map<String, String> map = new HashMap<String, String>();
		map.clear();
    	map.put("code", Constant.JSAPI_TICKET);	//从表中查出参数值。然后进行赋值
    	List<SysParameter> spList = sysParameterService.findByMap(map);
    	if(spList!=null && spList.size() > 0){
    		SysParameter sysParameter = spList.get(0);
    		if(sysParameter.getValue() != null && sysParameter.getUpdateWhen() != null){
    			 Calendar calendar = Calendar.getInstance ();
    		     calendar.add (Calendar.SECOND, 7200);
    		     sysParameter.setValue(new_jsapi_ticket);
    		     sysParameter.setUpdateWhen(calendar.getTime());
    		     sysParameterService.update(sysParameter);
    	     }
    	}
    	return new_jsapi_ticket;
	}
	
	
	/**
	 * 跳转到 微信公众号 入口  个人中心页
	 * @param request
	 * @param model
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7a7bc6c16b83c47d&redirect_uri=https%3A%2F%2Fwww.hehuanginfo.com%2Fdiys%2FBo%2Fmeowred%2FsupportMeCallAndVoteCount&response_type=code&scope=snsapi_base&state=123#wechat_redirect
	 * @return
	 */
	@RequestMapping(value="/userInfo", method = {RequestMethod.GET, RequestMethod.POST })
	public String userInfo(HttpServletRequest request) {
		
		String weiChatCode = request.getParameter("code");
		String userId =  request.getParameter("userId");
		log.error("code = "+ weiChatCode);
		WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
		if(StringUtil.isNotEmpty(weiChatCode) && StringUtil.isEmpty(userId)){//不为空时 已经获取，为空时未获取 获取用户信息
			weixinUserInfo = getWeixinUserInfo(weiChatCode,weixinUserInfo);
			userId = weixinUserInfo.getId().toString(); 
		}
		//每进入这个页面 增加一次访问记录
		AccessRecord record = new AccessRecord();
		record.setCreateTime(new Date());
		record.setAttentionUserId(Long.valueOf(userId));
		accessRecordService.save(record);
		
    	if(StringUtil.isNotEmpty(userId)){
    		log.error("userId=" + userId);
    	}else{
    		log.error("微信授权用户信息错误！");
    		return indexPage;
    	}
    	return "redirect:/clientNew/redNet/userInfoPage?voteUserId="+userId;
	}
	
	/**
	 * 我 - 个人信息
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/userInfoPage", method = {RequestMethod.GET,RequestMethod.POST})
	public String userInfoPage (String voteUserId) {
		return "/redNet/userInfo";
	}
	
 	/**
	 * 查询个人信息
	 */
    @RequestMapping(value = "/getUserInfo", method = { RequestMethod.GET,RequestMethod.POST })
    @ResponseBody
    public ResponseObject getUserInfo(String id) {
        ResponseObject object = initResponseObject();
        object.setCode(Constant.RESPONSE_PARAMS_ERROR);
        object.setDesc("参数错误");
        try {
            if (StringUtil.isNotEmpty(id)) {
                NetRedUser netRedUser = userOptionService.getById(Long.parseLong(id));
                object.setResponse(netRedUser);
    	    	object.setCode(Constant.RESPONSE_SUCCESS_CODE);
    	    	object.setDesc(Constant.RESPONSE_SUCCESS_DESC);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            object.setCode(Constant.RESPONSE_ERROR_CODE);
            object.setDesc("服务器有点忙，请稍后再试");
        }
        return object;
        
    }
	
    
    /**
	 * 跳转到 微信公众号 入口 网红大赛页
	 * @param request
	 * @param model
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7a7bc6c16b83c47d&redirect_uri=https%3A%2F%2Fwww.hehuanginfo.com%2Fdiys%2FBo%2Fmeowred%2FsupportMeCallAndVoteCount&response_type=code&scope=snsapi_base&state=123#wechat_redirect
	 * @return
	 */
	@RequestMapping(value="/netRedGame", method = {RequestMethod.GET, RequestMethod.POST })
	public String netRedGame(HttpServletRequest request) {
		
		String weiChatCode = request.getParameter("code");
		String userId =  request.getParameter("userId");
		log.error("code = "+ weiChatCode);
		WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
		if(StringUtil.isNotEmpty(weiChatCode) && StringUtil.isEmpty(userId)){//不为空时 已经获取，为空时未获取 获取用户信息
			weixinUserInfo = getWeixinUserInfo(weiChatCode,weixinUserInfo);
			userId = weixinUserInfo.getId().toString(); 
		}
		//每进入这个页面 增加一次访问记录
		AccessRecord record = new AccessRecord();
		record.setCreateTime(new Date());
		record.setAttentionUserId(Long.valueOf(userId));
		accessRecordService.save(record);
		
    	if(StringUtil.isNotEmpty(userId)){
    		log.error("userId=" + userId);
    	}else{
    		log.error("微信授权用户信息错误！");
    		return indexPage;
    	}
    	return "redirect:/clientNew/redNet/netRedGamePage?voteUserId="+userId;
	}
	
    
    /**
	 * 撩 - 网红大赛
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/netRedGamePage", method = {RequestMethod.GET,RequestMethod.POST})
	public String netRedGamePage () {
    	return indexPage;
	}
	
	/**
     * 参选选手 投票 访问量
     * 
     * @param request
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/supperCount", method = { RequestMethod.GET, RequestMethod.POST })
    public Map<String,Object> supperCount(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<String,Object>();
        int supperCount =  userOptionService.supperCount();
        int personCount = userOptionService.getCount(new HashMap<String, Object>()).intValue();
        int visitCount = userOptionService.accessRecordCount();
        map.put("supperCount", supperCount);
        map.put("personCount", personCount);
        map.put("visitCount", visitCount);
        return map;
    }
    
    /**
     * 分页查询 网红列表
     * @param request
     * @param model
     * @return 分页查询
     */
    @ResponseBody
    @RequestMapping(value = "/findNetRedListPage", method = { RequestMethod.GET,RequestMethod.POST })
    public Map<String, Object> findNetRedListPage(HttpServletRequest request, String content ,String pageSize ,String pageNo ) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtil.isEmpty(pageNo))
            pageNo = "1";
        if (StringUtil.isEmpty(pageSize))
            pageSize = "10";
        Page page = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("limit", page.getPageSize());
        searchMap.put("offset", page.getOffset());
        List<NetRedUser> list = new ArrayList<NetRedUser>();
        Page<NetRedUser> result = userOptionService.findByPage(page, searchMap);
        list = result.getResult();
        if (StringUtil.isNotEmpty(list)) {
            map.put("list", list);
            map.put("pageNo", page.getPageNo());
            map.put("pageSize", page.getPageSize());
            map.put("totalItems", page.getTotalItems());
            map.put("totalPages", page.getTotalPages());
        }
        for (NetRedUser user : list) {
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("netRedUserId", user.getId());
            param.put("type", 1);
            int count = recordService.getCount(param).intValue();
            param.put("netRedUserId", user.getId());
            param.put("type", 2);
            user.setFirstImage(host+user.getFirstImage());
            int callCount = recordService.getCount(param).intValue();
            user.setCount(count + callCount * 10);
        }
        map.put("list", list);
        return map;
    }
    
	
}
