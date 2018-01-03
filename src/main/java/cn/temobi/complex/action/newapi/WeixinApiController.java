package cn.temobi.complex.action.newapi;

import cn.temobi.complex.entity.AccessRecord;
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
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

import com.aliyuncs.http.HttpRequest;
import com.google.gson.Gson;
import com.salim.cache.CacheHelper;
import com.sms.SmsMessageUtil;
import com.tencent.common.Signature;
import com.weichat.common.CommonUtil;
import com.weichat.common.Token;
import com.weichat.common.WeixinClientUtil;
import com.weichat.common.WeixinUser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URLEncoder;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Decoder;

@Controller
@RequestMapping({"/weixin"})
public class WeixinApiController extends ClientApiBaseController{
	
  private final String indexPage = "RedNet/index";
  private final String contextPath = "/diys/jsproot/RedNet/";
  private final String WEIXIN_userId = "current_weixinUser";
  
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
  @Resource(name="weixinVoteRecordService")
  private WeixinVoteRecordService recordService;
  @Resource(name="weixinVoteRecordService")
  private WeixinVoteRecordService weixinVoteRecordService;
  
  /**
   * 落地报名
   * @param request
   * @return
   */
  @RequestMapping(value={"/landRegistration"}, method={RequestMethod.GET, RequestMethod.POST})
  public String landRegistration(HttpServletRequest request){
    String weiChatCode = request.getParameter("code");
    String userId = request.getParameter("userId");
    WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
    if ((StringUtil.isNotEmpty(weiChatCode)) && (StringUtil.isEmpty(userId))){
      weixinUserInfo = getWeixinUserInfo(weiChatCode, weixinUserInfo);
      userId = weixinUserInfo.getId().toString();
    }
    log.error("userId = " + userId);
    HttpSession  session =  request.getSession();
    session.setAttribute(WEIXIN_userId, userId);
    
    AccessRecord record = new AccessRecord();
    record.setCreateTime(new Date());
    record.setAttentionUserId(Long.valueOf(userId));
    accessRecordService.save(record);
    Map<String, Object> map = new HashMap();
    map.put("weichatUserId", userId);
    List<NetRedUser> netRedUsers = userOptionService.findByMap(map);
    String voteUserId = "";
    String status = "0";
//    if ((netRedUsers != null) && (netRedUsers.size() > 0))
//    {
//      voteUserId = ((NetRedUser)netRedUsers.get(0)).getId().toString();
//      status = "1";
//    }
//    else
//    {
//      voteUserId = userId;
//    }
    if (StringUtil.isNotEmpty(userId)){
      log.error("userId=" + userId);
    }
    else{
      log.error("微信授权用户信息错误！");
      return "redNet/index";
    }
	return "redNet/signUpinfo";
//    return "redirect:/clientNew/weixin/signUpinfo?voteUserId=" + voteUserId;
  }
  
  private WeixinUserInfo getWeixinUserInfo(String code, WeixinUserInfo cmUserInfo) {
    WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
    CommonUtil commonUtil = new CommonUtil();
    Token token = new Token();
    token = commonUtil.getOauthSecond(code, token);
    if ((token != null) && (token.getOpenid() != null)) {
      Map<String, Object> map = new HashMap();
      map.put("openId", token.getOpenid());
      List<WeixinUserInfo> weixinUserInfoList = weixinUserInfoService.findByMap(map);
      if (weixinUserInfoList != null && weixinUserInfoList.size() > 0) {
        weixinUserInfo = (WeixinUserInfo)weixinUserInfoList.get(0);
      }else{
        updateTokenToDB(token.getAccessToken());
        WeixinUser userInfo = new WeixinUser();
        userInfo = commonUtil.getUserInfo(token.getAccessToken(), token.getOpenid(), userInfo);
        weixinUserInfo.setCity(userInfo.getCity());
        weixinUserInfo.setOpenId(token.getOpenid());
        weixinUserInfo.setHeadImgUrl(userInfo.getHeadImgUrl());
        weixinUserInfo.setNickname(userInfo.getNickname());
        weixinUserInfo.setSex(userInfo.getSex());
        weixinUserInfo.setOpenId(userInfo.getOpenId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        weixinUserInfo.setSubscribeTime(sdf.format(new Date()));
        weixinUserInfoService.save(weixinUserInfo);
      }
    }
    else {
      log.error("token is null");
    }
    return weixinUserInfo;
  }
  
  @RequestMapping(value={"/userInfoPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public void userInfoPage(HttpServletResponse response, String voteUserId)throws IOException{
    response.sendRedirect("/diys/jsproot/RedNet/userInfo.html?voteUserId=" + voteUserId);
  }
  
  @RequestMapping(value={"/registrationRedio"}, method={RequestMethod.GET, RequestMethod.POST})
  public void registrationRedio(HttpServletResponse response, String voteUserId, String status)throws IOException{
    response.sendRedirect("/diys/jsproot/RedNet/video.html?voteUserId=" + voteUserId + "&status=" + status);
  }
  
  @RequestMapping(value={"/registrationPoster"}, method={RequestMethod.GET, RequestMethod.POST})
  public void registrationPoster(HttpServletResponse response, String voteUserId) throws IOException{
    response.sendRedirect("/diys/jsproot/RedNet/poster.html?voteUserId=" + voteUserId);
  }
  
  @RequestMapping(value={"/signUpinfo"}, method={RequestMethod.GET, RequestMethod.POST})
  public void signUpinfo(HttpServletResponse response, String voteUserId) throws IOException{
    response.sendRedirect("/diys/jsproot/RedNet/signUpinfo.html?voteUserId=" + voteUserId);
  }
  

  @RequestMapping(value={"/testIndexPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String testIndexPage(HttpServletRequest request, String voteUserId,Model model){
	  HttpSession  session =  request.getSession();
	  session.setAttribute(WEIXIN_userId, "8");
	  return "RedNet/index";
  }
  
  @RequestMapping(value={"/testPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String test(HttpServletRequest request, String voteUserId,Model model){
	  HttpSession  session =  request.getSession();
	  session.setAttribute(WEIXIN_userId, "8");
	  return "RedNet/userInfo";
  }
  
  @RequestMapping(value={"/userShowPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String userShowPage(HttpServletRequest request, String voteUserId,Model model){
    return "RedNet/userShow";
  }
  
  @RequestMapping(value={"/userFormPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String userFormPage(HttpServletRequest request, String voteUserId,Model model){
    return "RedNet/userForm";
  }

  @RequestMapping(value={"/signUpinfoPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String signUpinfoPage(HttpServletRequest request, String voteUserId,Model model){
    return "RedNet/signUpinfo";
  }
  
  @RequestMapping(value={"/supportMePage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String supportMePage(HttpServletRequest request, String voteUserId,Model model){
    return "RedNet/supportMe";
  }
  
  @RequestMapping(value={"/meSupportPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String meSupportPage(HttpServletRequest request, String voteUserId,Model model){
    return "RedNet/meSupport";
  }
  
  @RequestMapping(value={"/netRedGamePage"}, method={RequestMethod.GET, RequestMethod.POST})
  public String netRedGamePage(){
    return "redNet/index";
  }
  
  /**
   * 获取验证码
   * @param phone
   * @return
   */
  @ResponseBody
  @RequestMapping(value={"/getCode"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject getCode(String phone) {
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try{
      int num = (int)((Math.random()*9+1)*100000);
      long time = System.currentTimeMillis();
      SmsMessageUtil util = new SmsMessageUtil();
      if (util.sendMessage(phone, String.valueOf(num), "")) {
        object.setCode("00000");
        CachedValueAndTimeSecond(phone, String.valueOf(num), 300);
        object.setDesc("发送成功");
      } else {
        object.setCode("10002");
        object.setDesc("发送失败");
      }
    } catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙，请稍后再试");
    }
    return object;
  }
  
    
  /**
   * 注册报名添加用户
   * @param request
   * @param userStr
   * @param code
   * @return
   */
  @ResponseBody
  @RequestMapping(value={"/saveNetRedUser"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject saveNetRedUser(HttpServletRequest request, String userStr, String code ){//String weixinUserId
    ResponseObject object = initResponseObject();
//    object.setDesc("成功");
//    object.setResponse("");
//    object.setCode("00000");
//    return object;
    try
    {
      Gson gson = new Gson();
      log.error("用户 userStr" + userStr);
      NetRedUser user = (NetRedUser)gson.fromJson(userStr, NetRedUser.class);
      if (user.getCity() != null){
        String[] address = user.getCity().split(",");
        user.setProvince(address[0]);
        if (address.length >= 2) {
          user.setCity(address[1]);
        }
        if (address.length >= 2) {
          user.setTown(address[2]);
        }
      }
        HttpSession  session =  request.getSession();
   	    String userid = (String) session.getAttribute(WEIXIN_userId);
   	    log.error("用户缓存 userId" + userid);
   	    user.setWeichatUserId(Long.valueOf(userid));
	    String oldCode = (String)CacheHelper.getInstance().get(user.getPhone());
	    log.error("用户 phone" + user.getPhone() +  " code="+oldCode + " ,");
	    if (StringUtils.equals(code, oldCode)){
	      Map<String, String> map = new HashMap();
	      map.put("phone", user.getPhone());
	      List<NetRedUser> netRedList = userOptionService.findByMap(map);
	      if ((netRedList != null) && (netRedList.size() > 1)){
	        object.setCode("10000");
	        object.setDesc("手机号已注册");
	        return object;
	      }
	      user.setGameRounds(1);
	      log.error("用户--- " + gson.toJson(user));
	      userOptionService.save(user);
	      
	      object.setDesc("成功");
	      object.setResponse(user);
	      object.setCode("00000");
	    }else{
	      object.setCode("10000");
	      object.setDesc("验证码错误,请重新输入");
	    }
      
    }catch (Exception e) {
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙，请稍后再试");
    }
    return object;
  }
  
  /**
   * 更新网红个人信息
   * @param request
   * @param userStr
   * @return
   */
  @ResponseBody
  @RequestMapping(value={"/updateNetRedUser"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject updateNetRedUser(HttpServletRequest request, String userStr){
    ResponseObject object = initResponseObject();
    try{
      System.out.println(userStr);
      Gson gson = new Gson();
      NetRedUser user = (NetRedUser)gson.fromJson(userStr, NetRedUser.class);
      List<String> imagesArr = new ArrayList();
      if ((user.getImagesArr() != null) && (user.getImagesArr().length >= 0)) {
        for (int i = 0; i < user.getImagesArr().length; i++) {
          if (user.getImagesArr()[i].indexOf("data:image/") == -1)
          {
            imagesArr.add(user.getImagesArr()[i]);
          }else{
            String houzui = user.getImagesArr()[i].substring(user.getImagesArr()[i].indexOf("data:image/") + 11, user.getImagesArr()[i].indexOf(";base64"));
            String homePath = PropertiesHelper.getProperty("properties/server_information.properties", "home_path_prefix");
            String resourcePath = PropertiesHelper.getProperty("properties/server_information.properties", "resource_path");
            String savePath = resourcePath + "designer/" +  DateUtils.getCurrentDateTime("yyyyMM") + "/";
            String saveUrl = homePath + savePath;
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + houzui;
            String generateImage = saveUrl + fileName;
            if (generateImage(user.getImagesArr()[i].substring(user.getImagesArr()[i].indexOf(";base64") + 8), generateImage, saveUrl)) {
              imagesArr.add(savePath + fileName);
            }
          }
        }
      }
      if (StringUtils.isNotBlank(user.getFirstImage()) &&  (user.getFirstImage().indexOf("data:image/") > -1)){
        String houzui = user.getFirstImage().substring(user.getFirstImage().indexOf("data:image/") + 11, user.getFirstImage().indexOf(";base64"));
        String homePath = PropertiesHelper.getProperty(
          "properties/server_information.properties", "home_path_prefix");
        String resourcePath = PropertiesHelper.getProperty(
          "properties/server_information.properties", "resource_path");
        String savePath = resourcePath + "designer/" + 
          DateUtils.getCurrentDateTime("yyyyMM") + "/";
        String saveUrl = homePath + savePath;
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + houzui;
        String generateImage = saveUrl + fileName;
        if (generateImage(user.getFirstImage().substring(user.getFirstImage().indexOf(";base64") + 8), generateImage, saveUrl)) {
          user.setFirstImage(savePath + fileName);
        }
      }
      String[] strings = new String[imagesArr.size()];
      user.setImagesArr((String[])imagesArr.toArray(strings));
      userOptionService.update(user);
      object.setResponse(user);
      object.setDesc("成功");
      object.setCode("00000");
    } catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙，请稍后再试");
    }
    return object;
  }
  
  public void signUpInfo(HttpServletResponse response, String netRedUserId) throws IOException {
    if (StringUtil.isEmpty(netRedUserId)) {
      response.sendRedirect("/diys/jsproot/RedNet/index.html?voteUserId=" + netRedUserId);
    }
    response.sendRedirect("/diys/jsproot/RedNet/userForm.html?voteUserId=" + netRedUserId);
  }
  
  /**
   * 根据缓存 用户ID 获取网红用户Id
   * @return netUserId
   */
  public Long getNetRedUserIdByWeixinUserId( HttpServletRequest request){
	   long netUserId = 0;
	  	Map<String, Object> map = new HashMap();
	    map.put("weichatUserId", getSeeionUserId(request));
	    List<NetRedUser> netRedUsers = userOptionService.findByMap(map);
	    log.error("nerUser: = " + netRedUsers.size());
	    if(netRedUsers != null && netRedUsers.size() >0 ){
	    	netUserId = netRedUsers.get(0).getId();
	    }
	    return netUserId;
  }
  
  /**
   * 获取网红用户数据
   * @return
   */
  @ResponseBody
  @RequestMapping(value={"/getNetRedUser"}, method={RequestMethod.GET, RequestMethod.POST})
  public NetRedUser getNetRedUser(HttpServletRequest request){//String netRedUserId
    NetRedUser netRedUser = null;
    log.error("网红详情页 + = " + getNetRedUserIdByWeixinUserId(request));
    netRedUser = userOptionService.getById( getNetRedUserIdByWeixinUserId(request));
    netRedUser.setFirstImage(netRedUser.getFirstImage());
    log.error("输出");
    Gson gson = new Gson();
    log.error("nerUser: = " + gson.toJson(netRedUser));
    return netRedUser;
  }
  
  /**
   * 获取网红排名和得票数
   * @param request
   * @return
   */
  @RequestMapping(value={"/netRedRankAndCount"}, method={RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public ResponseObject netRedRankAndCount(HttpServletRequest request){//String netRedUserId
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try{
      Map<String, Object> param = new HashMap();
      param.put("netRedUserId", getNetRedUserIdByWeixinUserId(request));
      List<VoteRecord> voteRecords = weixinVoteRecordService.getSumCountByType(param);
      NetRedUser netRedUser = new NetRedUser();
      netRedUser.setCount(voteRecords.get(0) != null ? ((VoteRecord)voteRecords.get(0)).getCount() : 0);
      netRedUser.setCallCount(voteRecords.get(0) != null ? ((VoteRecord)voteRecords.get(0)).getCallCount() : 0);
      
      param.clear();
      param.put("count", Integer.valueOf(((VoteRecord)voteRecords.get(0)).getCount()));
      List<AccessRecord> accessRecords = accessRecordService.findNetRank(param);
      netRedUser.setRank(accessRecords.size() + 1);
      object.setResponse(netRedUser);
    } catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  @RequestMapping(value={"/supportMe"}, method={RequestMethod.GET, RequestMethod.POST})
  public void supportMe(HttpServletResponse response, String netRedUserId)throws IOException {
    if (StringUtil.isEmpty(netRedUserId)) {
      response.sendRedirect("/diys/jsproot/RedNet/index.html?voteUserId=" + netRedUserId);
    }
    response.sendRedirect("/diys/jsproot/RedNet/supportMe.html?voteUserId=" + netRedUserId);
  }
  
  @RequestMapping(value={"/meSupport"}, method={RequestMethod.GET, RequestMethod.POST})
  public void meSupport(HttpServletResponse response, String voteUserId)throws IOException {
    if (StringUtil.isEmpty(voteUserId)) {
      response.sendRedirect("/diys/jsproot/RedNet/index.html?voteUserId=" + voteUserId);
    }
    response.sendRedirect("/diys/jsproot/RedNet/meSupport.html?voteUserId=" + voteUserId);
  }
  
  @ResponseBody
  @RequestMapping(value={"/supportMeUserCount "}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject supportMeUserCount(HttpServletRequest request) {//String netRedUserId
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try{
//      if (StringUtil.isEmpty(netRedUserId))
//      {
//        object.setDesc("网红用户不能为空");
//        return object;
//      }
      String netRedUserId = String.valueOf(getNetRedUserIdByWeixinUserId(request));
      Map<String, Object> param = new HashMap();
      param.clear();
      param.put("netRedUserId", netRedUserId);
      param.put("type", Integer.valueOf(1));
      int count = weixinVoteRecordService.getCount(param).intValue();
      param.clear();
      param.put("netRedUserId", netRedUserId);
      param.put("type", Integer.valueOf(2));
      int callCount = weixinVoteRecordService.getCount(param).intValue();
      VoteRecord voteRecord = new VoteRecord();
      voteRecord.setCallCountPer(callCount);
      voteRecord.setCountPer(count);
      param.clear();
      param.put("netRedUserId", netRedUserId);
      List<VoteRecord> voteRecords = weixinVoteRecordService.getSumCountByType(param);
      voteRecord.setCallCount(voteRecords.get(0) != null ? ((VoteRecord)voteRecords.get(0)).getCallCount() : 0);
      voteRecord.setCount(voteRecords.get(0) != null ? ((VoteRecord)voteRecords.get(0)).getCount() : 0);
      object.setResponse(voteRecord);
      object.setCode("00000");
      object.setDesc("成功");
    } catch (Exception e) {
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  @ResponseBody
  @RequestMapping(value={"/supportMeUserList"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject supportMeUser(HttpServletRequest request, int pageSize, int pageNo){//String netRedUserId,
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try{
//      if (StringUtil.isEmpty(netRedUserId))
//      {
//        object.setDesc("网红用户不能为空");
//        return object;
//      }
      Map<String, Object> param = new HashMap();
      Page page = new Page(pageNo, pageSize);
      param.put("limit", Integer.valueOf(page.getPageSize()));
      param.put("offset", Integer.valueOf(page.getOffset()));
      param.put("netRedUserId", getNetRedUserIdByWeixinUserId(request));
      
      Page<VoteRecord> pageResult = new Page();
      pageResult = weixinVoteRecordService.getSupportMeVoteRecordPage(page, param);
      List<VoteRecord> voteRecordList = pageResult.getResult();
      object.setResponse(voteRecordList);
      object.setCode("00000");
      object.setDesc("成功");
    }catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  @ResponseBody
  @RequestMapping(value={"/supportNetRedWeixinUserList"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject supportNetRedWeixinUserList(HttpServletRequest request, int pageSize, int pageNo)  {//String netRedUserId,

    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try {
//      if (StringUtil.isEmpty(netRedUserId))
//      {
//        object.setDesc("网红用户不能为空");
//        return object;
//      }
      Map<String, Object> param = new HashMap();
      Page page = new Page(pageNo, pageSize);
      param.put("limit", Integer.valueOf(page.getPageSize()));
      param.put("offset", Integer.valueOf(page.getOffset()));
      param.put("netRedUserId", getNetRedUserIdByWeixinUserId(request));
      
      Page<VoteRecord> pageResult = new Page();
      pageResult = weixinVoteRecordService.findBySupportNetRedPage(page, param);
      List<VoteRecord> voteRecordList = pageResult.getResult();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      for (VoteRecord voteRecord : voteRecordList) {
        if (voteRecord.getCreateTime() != null) {
          voteRecord.setCreateTimeStr(sdf.format(voteRecord.getCreateTime()));
        }
        if (voteRecord.getType() != 0) {
          voteRecord.setTypeName(voteRecord.getType() == 1 ? "成功投票助力" : "为他打call");
        }
      }
      object.setResponse(voteRecordList);
      object.setCode("00000");
      object.setDesc("成功");
    } catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  
  @ResponseBody
  @RequestMapping(value={"/iSupportNetRedUserList"}, method={RequestMethod.GET, RequestMethod.POST})
  public ResponseObject iSupportNetRedUserList(HttpServletRequest request ,int pageSize, int pageNo){//String voteUserId,
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try {
//      if (StringUtil.isEmpty(voteUserId))
//      {
//        object.setDesc("微信用户不能为空");
//        return object;
//      }
      Map<String, Object> param = new HashMap();
      Page page = new Page(pageNo, pageSize);
      param.put("limit", Integer.valueOf(page.getPageSize()));
      param.put("offset", Integer.valueOf(page.getOffset()));
      param.put("voteUserId", getSeeionUserId(request));
      
      Page<VoteRecord> pageResult = new Page();
      pageResult = weixinVoteRecordService.getISupportNetRedVoteRecordPage(page, param);
      List<VoteRecord> voteRecordList = pageResult.getResult();
      object.setResponse(voteRecordList);
      object.setCode("00000");
      object.setDesc("成功");
    }catch (Exception e){
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  /**
   * 用户投票
   * @param request
   * @param netRedUserId
   * @param count
   * @param type
   * @return
   */
  @RequestMapping(value={"/userVote"}, method={RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public ResponseObject userVote(HttpServletRequest request, String netRedUserId, String count, String type){//String voteUserId,
  
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try{
      Map<String, Object> map = new HashMap();
      if ( (StringUtil.isEmpty(netRedUserId)) || (StringUtil.isEmpty(count)) || (StringUtil.isEmpty(type))) {
        return object;
      }
      return weixinVoteRecordService.saveVote(object, String.valueOf(getSeeionUserId(request)), netRedUserId, count, type);
    } catch (Exception e) {
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙");
    }
    return object;
  }
  
  @RequestMapping(value={"/payRecharge"}, method={RequestMethod.GET, RequestMethod.POST})
  public String payRecharge(HttpServletRequest request, Model model) {
    WeixinClientUtil weixinClientUtil = new WeixinClientUtil();
    String requestUrl = request.getRequestURL().toString();
    String access_token = "";
    String jsapi_ticket = "";
    access_token = getToken();
    jsapi_ticket = getJsapiTicketFromDB();
    Map<String, Object> configMap = WeixinClientUtil.getWxConfig(request, requestUrl, access_token, jsapi_ticket);
    if ((configMap != null) && (configMap.size() > 0)){
      if (configMap.get("new_access_token") != null) {
        updateTokenToDB(configMap.get("new_access_token").toString());
      }
      if (configMap.get("new_jsapi_ticket") != null) {
        updateJsapiTicketDB(configMap.get("new_jsapi_ticket").toString());
      }
      model.addAttribute("appId", configMap.get("appId"));
      model.addAttribute("configTimestamp", configMap.get("timestamp"));
      model.addAttribute("configNonceStr", configMap.get("nonceStr"));
      model.addAttribute("signature", configMap.get("signature"));
      model.addAttribute("userId", request.getParameter("userId"));
    }
    return "weichat/index/rechargePrice";
  }
  
  @RequestMapping(value={"/getRechargePayParam"}, method={RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public List<WxSign> getRechargePayParam(HttpServletRequest request){
    List<WxSign> wxSignList = new ArrayList();
    WxSign wxSign = new WxSign();
    Map<String, Object> returnMap = new HashMap();
    ResponseObject object = initResponseObject();
    
    object = weixinClientApiService.reCharge(request, object);
    
    returnMap = (Map)object.getResponse();
    

    Map<String, Object> signMap = new HashMap();
    String nonceStr = UUID.randomUUID().toString().substring(0, 15);
    long timestamp = System.currentTimeMillis() / 1000L;
    String prepayid = returnMap.get("prepayid").toString();
    signMap.put("appId", "wx7a7bc6c16b83c47d");
    signMap.put("timeStamp", timestamp);
    signMap.put("package", "prepay_id=" + prepayid);
    signMap.put("signType", "MD5");
    signMap.put("nonceStr", nonceStr);
    String paySign = Signature.getSign(signMap);
    wxSign.setPaytimestamp(String.valueOf(timestamp));
    wxSign.setPaynonceStr(nonceStr);
    wxSign.setPaySign(paySign);
    wxSign.setPaypackage("prepay_id=" + prepayid);
    wxSignList.add(wxSign);
    return wxSignList;
  }
  
  public ResponseObject initResponseObject(){
    ResponseObject object = new ResponseObject();
    object.setCode("");
    object.setResponse("");
    object.setDesc("");
    object.setDate(DateUtils.getCurrentDateTime("yyyyMMddHHmmss"));
    return object;
  }
  
  public String getToken(){
    String access_token = "";
    Map<String, String> map = new HashMap();
    map.clear();
    map.put("code", "access_token");
    List<SysParameter> spList = sysParameterService.findByMap(map);
    if ((spList != null) && (spList.size() > 0)){
      SysParameter sysParameter = (SysParameter)spList.get(0);
      if ((sysParameter.getValue() != null) && (sysParameter.getUpdateWhen() != null)) {
        Date date = new Date();
        if (sysParameter.getUpdateWhen().getTime() > date.getTime()) {
          access_token = sysParameter.getValue();
        }
      }
    }else{
      CommonUtil commonUtil = new CommonUtil();
      String new_access_token = commonUtil.getToken();
      updateTokenToDB(new_access_token);
    }
    return access_token;
  }
  
  public String updateTokenToDB(String new_access_token){
    Map<String, String> map = new HashMap();
    map.clear();
    map.put("code", "access_token");
    List<SysParameter> spList = sysParameterService.findByMap(map);
    if ((spList != null) && (spList.size() > 0)){
      SysParameter sysParameter = (SysParameter)spList.get(0);
      if ((sysParameter.getValue() != null) && (sysParameter.getUpdateWhen() != null)){
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, 7200);
        sysParameter.setValue(new_access_token);
        sysParameter.setUpdateWhen(calendar.getTime());
        sysParameterService.update(sysParameter);
      }
    }
    return new_access_token;
  }
  
  public String getJsapiTicketFromDB(){
    String jsapi_ticket = "";
    Map<String, String> map = new HashMap();
    map.clear();
    map.put("code", "jsapi_ticket");
    List<SysParameter> spList = sysParameterService.findByMap(map);
    if ((spList != null) && (spList.size() > 0))
    {
      SysParameter sysParameter = (SysParameter)spList.get(0);
      if ((sysParameter.getValue() != null) && (sysParameter.getUpdateWhen() != null))
      {
        Date date = new Date();
        if (sysParameter.getUpdateWhen().getTime() > date.getTime()) {
          jsapi_ticket = sysParameter.getValue();
        }
      }
    }
    return jsapi_ticket;
  }
  
  public String updateJsapiTicketDB(String new_jsapi_ticket){
    Map<String, String> map = new HashMap();
    map.clear();
    map.put("code", "jsapi_ticket");
    List<SysParameter> spList = sysParameterService.findByMap(map);
    if ((spList != null) && (spList.size() > 0))
    {
      SysParameter sysParameter = (SysParameter)spList.get(0);
      if ((sysParameter.getValue() != null) && (sysParameter.getUpdateWhen() != null))
      {
        Calendar calendar = Calendar.getInstance();
        calendar.add(13, 7200);
        sysParameter.setValue(new_jsapi_ticket);
        sysParameter.setUpdateWhen(calendar.getTime());
        sysParameterService.update(sysParameter);
      }
    }
    return new_jsapi_ticket;
  }
  
  /**
   * 用户中心
   * @param request
   * @return
   */
  @RequestMapping(value={"/userInfo"}, method={RequestMethod.GET, RequestMethod.POST})
  public String userInfo(HttpServletRequest request){
    String weiChatCode = request.getParameter("code");
    String userId = "";
    log.error("code = " + weiChatCode);
    WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
    if (StringUtil.isNotEmpty(weiChatCode)){
      weixinUserInfo = getWeixinUserInfo(weiChatCode, weixinUserInfo);
      userId = weixinUserInfo.getId().toString();
    }
    HttpSession  session =  request.getSession();
    session.setAttribute(WEIXIN_userId,userId);
    log.error("用户中心：UserId=" + userId);
    AccessRecord record = new AccessRecord();
    record.setCreateTime(new Date());
    record.setAttentionUserId(Long.valueOf(userId));
    accessRecordService.save(record);
    
    if (StringUtil.isNotEmpty(userId)){
      log.error("userId=" + userId);
    }else {
      log.error("微信授权用户信息错误！");
      return "redNet/index";
    }
    
    return "redNet/userInfo";
//    return "redirect:/clientNew/weixin/userInfoPage?voteUserId=" + userId;
  }
  
  
  
  
  @RequestMapping(value={"/getUserInfo"}, method={RequestMethod.GET, RequestMethod.POST})
  @ResponseBody
  public ResponseObject getUserInfo(HttpServletRequest request)//,String id
  {
    ResponseObject object = initResponseObject();
    object.setCode("10001");
    object.setDesc("参数错误");
    try
    {
//      if (StringUtil.isNotEmpty(id)){
    	long  weixinUserId = getSeeionUserId(request);
        Map<String, Object> map = new HashMap();
        map.put("weichatUserId", weixinUserId);
        List<NetRedUser> netRedUsers = userOptionService.findByMap(map);
        log.error("获取用户信息时 weixinUserId：" + weixinUserId);
        NetRedUser netRedUser = new NetRedUser();
	    WeixinUserInfo weinUser = weixinUserInfoService.getById(weixinUserId);
	    netRedUser.setName(weinUser.getNickname());
	    netRedUser.setCity(weinUser.getCity());
	    netRedUser.setFirstImage(weinUser.getHeadImgUrl());
        object.setResponse(netRedUser);
        object.setCode("00000");
        object.setDesc("成功");
        Gson gson = new Gson();
        log.error("用户--- " + gson.toJson(netRedUser));
//      }
    }
    catch (Exception e)
    {
      log.error(e.getMessage());
      object.setCode("10000");
      object.setDesc("服务器有点忙，请稍后再试");
    }
    return object;
  }
  
  
  public long getSeeionUserId(HttpServletRequest request){
	  HttpSession session = request.getSession();
	  Object obj = session.getAttribute(WEIXIN_userId);
	  if(obj != null){
		  log.error("session 用户Id：" + obj.toString());
		  return Long.valueOf(obj.toString());
	  }else{
		  log.error("session 未获取到 用户Id：" );
	  }
	  return 0;
  }
  
  
  @RequestMapping(value={"/netRedGame"}, method={RequestMethod.GET, RequestMethod.POST})
  public String netRedGame(HttpServletRequest request)
  {
    String weiChatCode = request.getParameter("code");
    String userId = request.getParameter("userId");
    log.error("code = " + weiChatCode);
    WeixinUserInfo weixinUserInfo = new WeixinUserInfo();
    if ((StringUtil.isNotEmpty(weiChatCode)) && (StringUtil.isEmpty(userId)))
    {
      weixinUserInfo = getWeixinUserInfo(weiChatCode, weixinUserInfo);
      userId = weixinUserInfo.getId().toString();
    }
    HttpSession  session =  request.getSession();
    Object obj = session.getAttribute(WEIXIN_userId);
    session.setAttribute(WEIXIN_userId, userId);
    AccessRecord record = new AccessRecord();
    record.setCreateTime(new Date());
    record.setAttentionUserId(Long.valueOf(userId));
    accessRecordService.save(record);
    if (StringUtil.isNotEmpty(userId)){
      log.error("userId=" + userId);
    }else
    {
      log.error("微信授权用户信息错误！");
      return indexPage;
    }
    return "redNet/index";
//    return "redirect:/clientNew/weixin/netRedGamePage?voteUserId=" + userId;
  }
  
 
  
  @ResponseBody
  @RequestMapping(value={"/supperCount"}, method={RequestMethod.GET, RequestMethod.POST})
  public Map<String, Object> supperCount(HttpServletRequest request){
    Map<String, Object> map = new HashMap();
    int supperCount = userOptionService.supperCount();
    int personCount = userOptionService.getCount(new HashMap()).intValue();
    int visitCount = userOptionService.accessRecordCount();
    map.put("supperCount", Integer.valueOf(supperCount));
    map.put("personCount", Integer.valueOf(personCount));
    map.put("visitCount", Integer.valueOf(visitCount));
    return map;
  }
  
  /**
   * 网红列表分页
   * @param request
   * @param content
   * @param pageSize
   * @param pageNo
   * @return
   */
  @ResponseBody
  @RequestMapping(value={"/findNetRedListPage"}, method={RequestMethod.GET, RequestMethod.POST})
  public Map<String, Object> findNetRedListPage(HttpServletRequest request, String content, String pageSize, String pageNo){
    Map<String, Object> map = new HashMap();
    if (StringUtil.isEmpty(pageNo)) {
      pageNo = "1";
    }
    if (StringUtil.isEmpty(pageSize)) {
      pageSize = "10";
    }
    Page page = new Page(Integer.parseInt(pageNo), Integer.parseInt(pageSize));
    Map<String, Object> searchMap = new HashMap();
    searchMap.put("limit", Integer.valueOf(page.getPageSize()));
    searchMap.put("offset", Integer.valueOf(page.getOffset()));
    List<NetRedUser> list = new ArrayList();
    Page<NetRedUser> result = userOptionService.findByPage(page, searchMap);
    list = result.getResult();
    if (StringUtil.isNotEmpty(list)) {
      map.put("list", list);
      map.put("pageNo", Integer.valueOf(page.getPageNo()));
      map.put("pageSize", Integer.valueOf(page.getPageSize()));
      map.put("totalItems", Long.valueOf(page.getTotalItems()));
      map.put("totalPages", Long.valueOf(page.getTotalPages()));
    }
    for (NetRedUser user : list){
      Map<String, Object> param = new HashMap();
      param.put("netRedUserId", user.getId());
      param.put("type", Integer.valueOf(1));
      int count = recordService.getCount(param).intValue();
      param.put("netRedUserId", user.getId());
      param.put("type", Integer.valueOf(2));
      
      user.setFirstImage(user.getFirstImage());
      int callCount = recordService.getCount(param).intValue();
      user.setCount(count + callCount * 10);
    }
    map.put("list", list);
    return map;
  }
  
  public static boolean generateImage(String imgStr, String path, String savePath) {
    boolean falg = true;
    if (imgStr == null) {
      return false;
    }
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      byte[] b = decoder.decodeBuffer(imgStr);
      for (int i = 0; i < b.length; i++) {
        if (b[i] < 0){
          int tmp43_41 = i; byte[] tmp43_39 = b;tmp43_39[tmp43_41] = ((byte)(tmp43_39[tmp43_41] + 256));
        }
      }
      File tmpFile = new File(savePath);
      if (!tmpFile.exists()) {
        tmpFile.mkdirs();
      }
      OutputStream out = new FileOutputStream(path);
      out.write(b);
      out.flush();
      out.close();
      return true;
    } catch (Exception e) {
      falg = false;
    }
    return falg;
  }
}
