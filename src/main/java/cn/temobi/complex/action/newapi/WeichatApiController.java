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


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencent.common.Configure;
import com.tencent.common.Signature;
import com.tencent.common.WeixinConfigure;
import com.weichat.common.CommonUtil;
import com.weichat.common.Token;
import com.weichat.common.WeixinClientUtil;
import com.weichat.common.WeixinUser;


import cn.temobi.complex.dto.VoteRecordDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.VoteRecord;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.complex.entity.WxSign;
import cn.temobi.complex.service.SysParameterService;
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
@RequestMapping("/weichat")
public class WeichatApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="weixinUserInfoService")
	private WeixinUserInfoService weixinUserInfoService;
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="weixinClientApiService")
	private WeixinClientApiService weixinClientApiService;
	
	/**
	 * 配置好  从预约界面开始，授权 同意完回调到预约界面
	 * 第一步，起点
	 * 微信前面三个菜单链接都要配
	 * 
	 * 
	 * 跳转到 落地报名
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/landRegistration", method = {RequestMethod.GET, RequestMethod.POST })
	public String landRegistration(HttpServletRequest request,Model model) {
		
		String indexPage = "redirect:/client/health/healthIndex";
		
		String weiChatCode = request.getParameter("code");
		String userId =  request.getParameter("userId");
		log.error("code = "+ weiChatCode);
		WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
		if(StringUtil.isNotEmpty(weiChatCode) && weiChatCode!=null && StringUtil.isEmpty(userId)){//不为空时 已经获取，为空时未获取 获取用户信息
			weixinUserInfo = getWeixinUserInfo(weiChatCode,weixinUserInfo);
			userId = weixinUserInfo.getId().toString();
		}
		
//		else{
//			Object sessionUser = request.getSession().getAttribute(Constant.WEICHAT_NETRED_USER);
//			if(sessionUser != null){
//				userId = sessionUser.toString();
//			}else{
//				if(weixinUserInfo != null && weixinUserInfo.getId() != null ){
//					userId = weixinUserInfo.getId().toString();
//				}else{
//					userId =  request.getParameter("userId");
//				}
//			}
//		}
		
    	if(StringUtil.isNotEmpty(userId)){
    		log.error("userId=" + userId);
    		model.addAttribute("userId",userId);
    	}else{
    		log.error("微信授权用户信息错误！");
    		return indexPage;
    	}
		return "weichat/me";
	}
	
	/**
	 * 获取微信用户信息
	 * @param code
	 * @param cmUserInfo
	 * @return
	 */
	public WeixinUserInfo getWeixinUserInfo(String code,WeixinUserInfo cmUserInfo ){
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
				log.error("token: accessToken = " +token.getAccessToken() + ", openId =" + token.getOpenid() );
				WeixinUser userInfo = new WeixinUser();
				userInfo =  commonUtil.getUserInfo(token.getAccessToken(), token.getOpenid(),userInfo);
				weixinUserInfo.setCity(userInfo.getCity());
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
	 * 充值支付
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
    	access_token = getTokenFromDB();
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
	 * 获取缓存到数据库的access_token
	 * @return
	 */
	public String getTokenFromDB(){
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
    	}
    	return access_token;
	}
	
	/**
	 * 获取缓存到数据库的access_token
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
	 * 更新access_token 缓存到数据库的
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
    		     calendar.add (Calendar.SECOND, 7200);
    		     sysParameter.setValue(new_access_token);
    		     sysParameter.setUpdateWhen(calendar.getTime());
    		     sysParameterService.update(sysParameter);
    	     }
    	}
    	return new_access_token;
	}
	
	
	
	
}
