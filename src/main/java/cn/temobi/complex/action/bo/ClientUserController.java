package cn.temobi.complex.action.bo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.CmTalenInfoDto;
import cn.temobi.complex.dto.CmUserGroupDto;
import cn.temobi.complex.dto.PmProductAccusationDto;
import cn.temobi.complex.entity.CmGroup;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.complex.entity.CmUserGroup;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.PmProductAccusation;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmScoreLog;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.complex.service.CmGroupService;
import cn.temobi.complex.service.CmTalenInfoService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserFansService;
import cn.temobi.complex.service.CmUserGroupService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.PmProductAccusationService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.StartStatisticsService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysIndustryInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.easemob.api.EasemobIUtil;
import cn.temobi.core.util.DateRandom;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.NameUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.MD5;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/user")
public class ClientUserController extends BoBaseController{

	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="startStatisticsService")
	private StartStatisticsService startStatisticsService;
	
	@Resource(name="cmUserExtendInfoService")
	private CmUserExtendInfoService cmUserExtendInfoService;
	
	@Resource(name="cmTalenInfoService")
	private CmTalenInfoService cmTalenInfoService;
	
	@Resource(name="pmProductAccusationService")
	private PmProductAccusationService pmProductAccusationService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="laberService")
	private LaberService laberService;
	
	@Resource(name="cmUserFansService")
	private CmUserFansService cmUserFansService;
	
	@Resource(name="pmProductPraisesService")
	private PmProductPraisesService pmProductPraisesService;
	
	@Resource(name="sysIndustryInfoService")
	private SysIndustryInfoService sysIndustryInfoService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="pmScoreLogService")
	private PmScoreLogService pmScoreLogService;
	
	@Resource(name="cmGroupService")
	private CmGroupService cmGroupService;
	
	@Resource(name="cmUserGroupService")
	private CmUserGroupService cmUserGroupService;


	
	
	/**
	 * 分组管理
	 */
	@RequestMapping(value = "/group_list")
	public String groupList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)) {
				map.put("name", name);
				model.addAttribute("name",name);
			}
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmGroup> pageResult = new Page<CmGroup>();
			pageResult = cmGroupService.findByPage(page, map);
			List<CmGroup> list = pageResult.getResult();
			for(CmGroup cmGroup: list) {
				cmGroup.setCreateWhen(cmGroup.getCreateWhen().substring(0,cmGroup.getCreateWhen().length()-2));
			}
			model.addAttribute("list",list);
			List<CmGroup> groupList = cmGroupService.findAll();
			model.addAttribute("groupList", groupList);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/group_list";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/group_edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String groupEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				CmGroup cmGroup = cmGroupService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmGroup);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/group_edit";
	}

	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/group_save", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int groupSave(HttpServletRequest request,Model model,CmGroup cmGroup) {
		try {
			String id = request.getParameter("id");
			String name = request.getParameter("name");
			Map<String,Object> map = new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(name))
			{
				map.put("name", name);
				List<CmGroup> list = cmGroupService.findByMap(map);
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					//修改时判断名字是否更改
					CmGroup group = cmGroupService.getById(Long.parseLong(id));
					if(!group.getName().equals(cmGroup.getName())){
						//修改时判断更改的名字是否已存在
						if(list != null && list.size() > 0)
						{
							return 1;
						}
					}				
					cmGroupService.update(cmGroup);
					map.put("groupId", id);
					cmUserGroupService.updateByGroupId(map);
				}else{
					//新增时判断起的名字是否已存在
					if(list != null && list.size() > 0)
					{
						return 1;
					}
					cmGroupService.save(cmGroup);					
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 2;
		}
		return 0;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/group_delete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String groupDelete(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				cmUserGroupService.deleteByGroupId(id);
				cmGroupService.delete(id);
				
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "";
	}
	
	/**
	 * 用户分组列表
	 */
	@RequestMapping(value = "/user_group_list")
	public String userGroupList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)) {
				map.put("name", name);
				model.addAttribute("name",name);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String remark = request.getParameter("remark");
			if(StringUtil.isNotEmpty(remark)) {
				map.put("remark", remark);
				model.addAttribute("remark",remark);
			}
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmUserGroupDto> pageResult = new Page<CmUserGroupDto>();
			pageResult = cmUserGroupService.findByPageDto(page, map);
			List<CmUserGroupDto> list = pageResult.getResult();
			for(CmUserGroupDto cmUserGroupDto: list) {
				cmUserGroupDto.setCreateWhen(cmUserGroupDto.getCreateWhen().substring(0,cmUserGroupDto.getCreateWhen().length()-2));
			}
			model.addAttribute("list",list);
			List<CmGroup> groupList = cmGroupService.findAll();
			model.addAttribute("groupList", groupList);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/user_group_list";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_group_edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String userGroupEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			List<CmGroup> groupList = cmGroupService.findAll();
			model.addAttribute("groupList", groupList);
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				CmUserGroup cmUserGroup = cmUserGroupService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmUserGroup);
			}else{
				String userId = request.getParameter("userId");
				model.addAttribute("userId", userId);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/user_group_edit";
	}
	
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_group_save", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int userGroupSave(HttpServletRequest request,Model model,CmUserGroup cmUserGroup) {
		try {
			String id = request.getParameter("id");
			String userId = request.getParameter("userId");
			String name = request.getParameter("name");
			
			//是否已存在
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("userId", userId);
			List<CmUserGroup> list = cmUserGroupService.findByMap(map);
			
			//设置GroupId
			Map<String,Object> map1 = new HashMap<String, Object>();
			map1.put("name", name);
			List<CmGroup> list1 = cmGroupService.findByMap(map1);
			if(list1 != null && list1.size() > 0){
				cmUserGroup.setGroupId(list1.get(0).getId());
			}
			
			if(StringUtil.isNotEmpty(name)&&StringUtil.isNotEmpty(userId) && !"0".equals(userId)){
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					//修改时判断UserId和name有没有更改
					CmUserGroup userGroup = cmUserGroupService.getById(Long.parseLong(id));
					if(!cmUserGroup.getName().equals(userGroup.getName())|| !cmUserGroup.getUserId().equals(userGroup.getUserId())){
						if(list != null && list.size() > 0)
						{
							return 1;
						}
					}
					cmUserGroupService.update(cmUserGroup);
				}else{
					if(list != null && list.size() > 0)
					{
						return 1;
					}
					cmUserGroupService.save(cmUserGroup);
					
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 2;
		}
		return 0;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/user_group_delete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String userGroupDelete(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				cmUserGroupService.delete(id);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "";
	}
	
	
	@RequestMapping(value = "/pushMsg")
	public String pushMsg(HttpServletRequest request, ModelMap model){
		try{
			String userId = request.getParameter("id");
			String type = request.getParameter("type");
			model.addAttribute("id",userId);
			model.addAttribute("type",type);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		
		return "bo/user/message_edit";
	}
	
	
	@RequestMapping(value = "/messageSave")
	public@ResponseBody String messageSave(HttpServletRequest request, ModelMap model){
		String id = request.getParameter("id");
		String content = request.getParameter("content");
		String type = request.getParameter("type");
		if(StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(content) && StringUtil.isNotEmpty(type))
		{
			if(type.equals("1"))
			{
				PushUtil.pullOneMessage(id, content, "15", "", "");
			}else if(type.equals("2"))
			{
				List<String> list  = new ArrayList<String>();
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("groupId", id);
				List<CmUserGroup> grouplist = cmUserGroupService.findByMap(map);
				if(grouplist != null && grouplist.size() > 0)
				{
					for(CmUserGroup cmUserGroup:grouplist)
					{
						list.add(cmUserGroup.getUserId() + "");
					}
				}
				PushUtil.pushAccountListMultiple(list, content,"15", "", "");
			}
		}
		return "";
	}
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String isWeixin = request.getParameter("isWeixin");//结束时间
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&& !"0".equals(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
			String identity = request.getParameter("identity");
			if(StringUtil.isNotEmpty(identity)) {
				map.put("identity", identity);
				model.addAttribute("identity",identity);
			}
			String channel = request.getParameter("channel");//渠道
			if(StringUtil.isNotEmpty(isWeixin)) {
				map.put("isWeixin", isWeixin);
				model.addAttribute("isWeixin",isWeixin);
			}
			String isQq = request.getParameter("isQq");//结束时间
			if(StringUtil.isNotEmpty(isQq)) {
				map.put("isQq", isQq);
				model.addAttribute("isQq",isQq);
			}
			String isWeibo = request.getParameter("isWeibo");//结束时间
			if(StringUtil.isNotEmpty(isWeibo)) {
				map.put("isWeibo", isWeibo);
				model.addAttribute("isWeibo",isWeibo);
			}
			String mobile = request.getParameter("mobile");//结束时间
			if(StringUtil.isNotEmpty(mobile)) {
				map.put("mobileTo", mobile);
				model.addAttribute("mobile",mobile);
			}
			String nickName = request.getParameter("nickName");//结束时间
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			String startDate = request.getParameter("startDate");//结束时间
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String registerFrom = request.getParameter("registerFrom");//结束时间
			if(StringUtil.isNotEmpty(registerFrom)) {
				map.put("registerFrom", registerFrom);
				model.addAttribute("registerFrom",registerFrom);
			}
			String endDate = request.getParameter("endDate");//结束时间
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}

			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried", orderFried);
				model.addAttribute("orderFried",orderFried);
			}else{
				map.put("orderFried", "created_when");
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}else{
				map.put("sequence", "desc");
			}
			if(StringUtil.isNotEmpty(channel)) {
				map.put("clientChannel", channel.trim());
				model.addAttribute("channel",channel.trim());
			}
			String isBan = request.getParameter("isBan");
			if(StringUtil.isNotEmpty(isBan)) {
				map.put("isBan", isBan);
				model.addAttribute("isBan",isBan);
			}
			String imei = request.getParameter("imei");
			if(StringUtil.isNotEmpty(imei)) {
				map.put("imei", imei);
				model.addAttribute("imei",imei);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmUserInfo> pageResult = new Page<CmUserInfo>();
			map.put("allUser", "0");
			pageResult = cmUserInfoService.findByPageTwo(page, map);
			List<CmUserInfo> list = pageResult.getResult();
			
			List<SysConfigParam> changeList = new ArrayList<SysConfigParam>();
			List<SysConfigParam>  tempList = (List<SysConfigParam>) CacheHelper.getInstance().get("sysConfigParam@changeList");
			if(tempList == null || tempList.size()==0){
				map.put("type", "1");
				changeList = sysConfigParamService.findByMap(map);
				for(CmUserInfo cmUserInfo: list) {
					cmUserInfo.setCreatedWhen(cmUserInfo.getCreatedWhen().substring(0,cmUserInfo.getCreatedWhen().length()-2));
					for(SysConfigParam sysConfigParam:changeList)
					{
						if(sysConfigParam.getEnParamName().equals(cmUserInfo.getClientChannel()))
						{
							cmUserInfo.setClientChannel(sysConfigParam.getParamValue());
						}
					}
				}
				CacheHelper.getInstance().set(60*60*24,"sysConfigParam@changeList", changeList);
			}else{
				changeList = tempList;
			}
			
			model.addAttribute("list",list);
			model.addAttribute("changeList",changeList);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/user_list";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
			CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(id));
			model.addAttribute("entity", cmUserInfo);
			model.addAttribute("cmUserExtendInfo", cmUserExtendInfo);
		}
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("parentIndustryIdTo", "0");
		List<SysIndustryInfo> list = sysIndustryInfoService.findByMap(map);
		model.addAttribute("infList", list);
		
		
		return "bo/user/user_edit";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,CmUserInfo cmUserInfo,CmUserExtendInfo cmUserExtendInfo) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			CmUserInfo oldCmUserInfo = cmUserInfoService.getById(cmUserInfo.getId());
			cmUserInfo.setUserType(oldCmUserInfo.getUserType());
			cmUserInfoService.update(cmUserInfo);
			cmUserExtendInfo.setUserId(cmUserInfo.getId());
			cmUserExtendInfoService.update(cmUserExtendInfo);
		}else{
			cmUserInfo.setPassword(MD5.getMD5(cmUserInfo.getPassword()));
			cmUserInfo.setRegisterFrom("0");
			cmUserInfo.setLevel(1);
			
			if(StringUtil.isEmpty(cmUserInfo.getNickName()))
			{
				cmUserInfo.setNickName(NameUtil.getRanName());
			}
			if(StringUtil.isEmpty(cmUserInfo.getHeadImageUrl()))
			{
				int a = (int) (Math.random()*123)+1;
	    		cmUserInfo.setHeadImageUrl(host+"/diys/rs/default/logo/"+a+".jpg");
			}
			if(StringUtil.isEmpty(cmUserExtendInfo.getCoverThumbnail()))
			{
				int b = (int) (Math.random()*73)+1;
	    		cmUserExtendInfo.setCoverThumbnail(host+"/diys/rs/default/cover/"+b+".jpg");
			}
			if(StringUtil.isEmpty(cmUserExtendInfo.getAttentionLabel()))
			{
				Map<String,Object> laberMap = new HashMap<String, Object>();
	    		laberMap.put("type", "4");
	    		laberMap.put("limit", 3);
				List<String> laberList = laberService.findRand(laberMap);
	    		cmUserExtendInfo.setAttentionLabel(listToStringTo(laberList));
			}
			cmUserInfoService.save(cmUserInfo);
			cmUserExtendInfo.setUserId(cmUserInfo.getId());
			cmUserExtendInfoService.save(cmUserExtendInfo);
			
			EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
		}
		
		return "redirect:/Bo/user/list";
	}
	
	/**
	 * 批量新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/moreAdd", method = {RequestMethod.GET, RequestMethod.POST })
	public String moreAdd(HttpServletRequest request,ModelMap model) {
	    for(int i=0;i<20;i++)
	    {
	    	CmUserInfo cmUserInfo = new CmUserInfo();
		    CmUserExtendInfo cmUserExtendInfo = new CmUserExtendInfo();
		    cmUserInfo.setMobile("cs"+RandomUtils.getRandomStr(8));
			cmUserInfo.setPassword(MD5.getMD5("123456"));
			cmUserInfo.setRegisterFrom("0");
			cmUserInfo.setLevel(1);
			int sex=Math.random()>0.5?1:0; 
			cmUserInfo.setSex(sex);
			cmUserInfo.setNickName(NameUtil.getRanName());
			
			Date randomDate = DateRandom.randomDate("1970-01-01", "2007-03-01");  
            
			cmUserInfo.setBirth(DateUtils.format(randomDate));
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("parentIndustryIdTo", "0");
			List<SysIndustryInfo> list = sysIndustryInfoService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				int b = (int) (Math.random()*list.size());
				cmUserExtendInfo.setCareer(list.get(b).getName());
			}
			
			int a = (int) (Math.random()*123)+1;
    		cmUserInfo.setHeadImageUrl(host+"/diys/rs/default/logo/"+a+".jpg");
    		int b = (int) (Math.random()*73)+1;
    		cmUserExtendInfo.setCoverThumbnail(host+"/diys/rs/default/cover/"+b+".jpg");
    		Map<String,Object> laberMap = new HashMap<String, Object>();
    		laberMap.put("type", "4");
    		laberMap.put("limit", 3);
    		List<String> laberList = laberService.findRand(laberMap);
    		cmUserExtendInfo.setAttentionLabel(listToStringTo(laberList));
			cmUserInfoService.save(cmUserInfo);
			cmUserExtendInfo.setUserId(cmUserInfo.getId());
			int coverPraises = (int) (Math.random()*100)+1;
			cmUserExtendInfo.setCoverPraises(coverPraises);
			cmUserExtendInfoService.save(cmUserExtendInfo);
			
			EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
	    }
		return "redirect:/Bo/user/list";
	}
	
	/**
	 * 批量点赞
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/batchZ", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String batchZ(HttpServletRequest request,ModelMap model) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("audit", "1");
		map.put("isPublic", "1");
		List<PmProductInfo> list =pmProductInfoService.findByMap(map);
		
		List<CmUserInfo> userlist = cmUserInfoService.findAll();
		if(list != null && list.size() > 0)
		{
			PmProductInfo pmProductInfo = null;
			for(int i=0;i<list.size();i++)
			{
				pmProductInfo = list.get(i);
				//循环的次数
				int searchCount = 1000+(int) (Math.random()*1000);
				int praiseCount = 1000+(int) (Math.random()*1000);
				
		    	pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+searchCount);
		    	pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+praiseCount);
		    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2*praiseCount+searchCount);
		    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2*praiseCount);
		    	pmProductInfoService.update(pmProductInfo);
		    	
		    	CmUserInfo otherUser = cmUserInfoService.getById(pmProductInfo.getUserId());
		    	otherUser.setPraisesCount(otherUser.getPraisesCount()+praiseCount);
		    	cmUserInfoService.update(otherUser);
				
		    	int num = (int) (Math.random()*11)+10; 
				CmUserInfo cmUserInfo = null;
				for(int j=0;j<num;j++)
				{
					int b = (int) (Math.random()*userlist.size());
					cmUserInfo = userlist.get(b);
					
					//查看数据
		    		if(pmProductInfo.getUserId() - cmUserInfo.getId() != 0)
		    		{
		    			CmUserFans cmUserFans = new CmUserFans();
				    	cmUserFans.setUserId(pmProductInfo.getUserId());
				    	cmUserFans.setFansUserId(cmUserInfo.getId());
				    	
				    	List<CmUserFans> fanslist = cmUserFansService.findAll(cmUserFans);
				    	if(fanslist == null || fanslist.size() <= 0)
				    	{
				    		cmUserFansService.save(cmUserFans);
					    	
					    	otherUser.setFansCount(otherUser.getFansCount()+1);
					    	cmUserInfoService.update(otherUser);
					    	
					    	cmUserInfo.setAttentionsCount(cmUserInfo.getAttentionsCount()+1);
					    	cmUserInfoService.update(cmUserInfo);
				    	}
		    		}
		    		
		    		//点赞数据
		    		Map<String,Object> searchMap = new HashMap<String, Object>();
			    	searchMap.put("productId", pmProductInfo.getId());
			    	searchMap.put("type", "0");
			    	searchMap.put("userId", cmUserInfo.getId());
			    	List<PmProductPraises> pralist = pmProductPraisesService.findByMap(searchMap);
			    	if(pralist == null || pralist.size() <= 0)
			    	{
			    		PmProductPraises pmProductPraises = new PmProductPraises();
				    	pmProductPraises.setPraiseUserId(cmUserInfo.getId());
				    	pmProductPraises.setProductId( pmProductInfo.getId());
				    	pmProductPraises.setType(0);
				    	pmProductPraisesService.save(pmProductPraises);
				    	
				    	//添加消息
				    	CmUserMessage cmUserMessage = new CmUserMessage();
				    	cmUserMessage.setContent("赞了");
			    		cmUserMessage.setType(1);
				    	cmUserMessage.setProductId(pmProductInfo.getId());
				    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
				    	cmUserMessage.setUserId(pmProductInfo.getUserId());
				    	cmUserMessage.setSendUserId(cmUserInfo.getId());
				    	cmUserMessageService.save(cmUserMessage);
			    	}
				}
			}
		}
		return "成功";
	}
	
	
	/**
	 * 单个用户批量点赞
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/userBatchZ", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String userBatchZ(HttpServletRequest request,ModelMap model) {
		String userId = request.getParameter("userId");
		if(StringUtil.isNotEmpty(userId)){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("audit", "1");
			map.put("isPublic", "1");
			map.put("userId", userId);
			map.put("createFrom", "1");
			map.put("limit", 20);
			List<PmProductInfo> list =pmProductInfoService.findNotPraises(map);
			
			CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
			
			if(list != null && list.size() > 0)
			{
				PmProductInfo pmProductInfo = null;
				for(int i=0;i<list.size();i++)
				{
					pmProductInfo = list.get(i);
					//点赞数据
		    		Map<String,Object> searchMap = new HashMap<String, Object>();
			    	searchMap.put("productId", pmProductInfo.getId());
			    	searchMap.put("type", "0");
			    	searchMap.put("userId", cmUserInfo.getId());
			    	List<PmProductPraises> pralist = pmProductPraisesService.findByMap(searchMap);
			    	if(pralist == null || pralist.size() <= 0)
			    	{
						int searchCount = 1;
						int praiseCount = 1;
				    	pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+searchCount);
				    	pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+praiseCount);
				    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2*praiseCount+searchCount);
				    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2*praiseCount);
				    	pmProductInfoService.update(pmProductInfo);
				    	
				    	CmUserInfo otherUser = cmUserInfoService.getById(pmProductInfo.getUserId());
				    	otherUser.setPraisesCount(otherUser.getPraisesCount()+praiseCount);
				    	cmUserInfoService.update(otherUser);
						
						//查看数据
			    		if(pmProductInfo.getUserId() - cmUserInfo.getId() != 0)
			    		{
			    			CmUserFans cmUserFans = new CmUserFans();
					    	cmUserFans.setUserId(pmProductInfo.getUserId());
					    	cmUserFans.setFansUserId(cmUserInfo.getId());
					    	
					    	List<CmUserFans> fanslist = cmUserFansService.findAll(cmUserFans);
					    	if(fanslist == null || fanslist.size() <= 0)
					    	{
					    		cmUserFansService.save(cmUserFans);
						    	
						    	otherUser.setFansCount(otherUser.getFansCount()+1);
						    	cmUserInfoService.update(otherUser);
						    	
						    	cmUserInfo.setAttentionsCount(cmUserInfo.getAttentionsCount()+1);
						    	cmUserInfoService.update(cmUserInfo);
					    	}
			    		}
		    		
		    		
			    		PmProductPraises pmProductPraises = new PmProductPraises();
				    	pmProductPraises.setPraiseUserId(cmUserInfo.getId());
				    	pmProductPraises.setProductId( pmProductInfo.getId());
				    	pmProductPraises.setType(0);
				    	pmProductPraisesService.save(pmProductPraises);
				    	
				    	//添加消息
				    	CmUserMessage cmUserMessage = new CmUserMessage();
				    	cmUserMessage.setContent("赞了");
			    		cmUserMessage.setType(1);
				    	cmUserMessage.setProductId(pmProductInfo.getId());
				    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
				    	cmUserMessage.setUserId(pmProductInfo.getUserId());
				    	cmUserMessage.setSendUserId(cmUserInfo.getId());
				    	cmUserMessageService.save(cmUserMessage);
				    	
				    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.Z_STRING, "11", pmProductInfo.getId()+"", "");
				    }
				}
			}
		}
		return "成功";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/drlist")
	public String drlist(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried", orderFried);
				model.addAttribute("orderFried",orderFried);
			}
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type);
				model.addAttribute("type",type);
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String mobile = request.getParameter("mobile");
			if(StringUtil.isNotEmpty(mobile)) {
				map.put("mobile", mobile);
				model.addAttribute("mobile",mobile);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmTalenInfoDto> pageResult = new Page<CmTalenInfoDto>();
			pageResult = cmTalenInfoService.findDtoByPage(page, map);
			List<CmTalenInfoDto> list = pageResult.getResult();
			Map<String,Object> searchMap = new HashMap<String, Object>();
			for(CmTalenInfoDto cmTalenInfoDto: list) {
				cmTalenInfoDto.setCreatedWhen(cmTalenInfoDto.getCreatedWhen().substring(0,cmTalenInfoDto.getCreatedWhen().length()-2));
				searchMap.put("clientId", cmTalenInfoDto.getClientId());
				String time = startStatisticsService.findMaxTime(searchMap);
				if(StringUtil.isNotEmpty(time))
				{
					cmTalenInfoDto.setLoginTime(time.substring(0,time.length()-2));
				}
			}
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/dr_list";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/dredit", method = {RequestMethod.GET, RequestMethod.POST })
	public String drEdit(HttpServletRequest request,Model model) {
		try {
			String userId = request.getParameter("id");
			String talenId = request.getParameter("talenId");
			model.addAttribute("talenId", talenId);
			if(StringUtil.isNotEmpty(userId) && !"0".equals(userId))
			{
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
				model.addAttribute("entity", cmUserInfo);
				if(StringUtil.isNotEmpty(talenId))
				{
					CmTalenInfo cmTalenInfo = cmTalenInfoService.getById(Long.parseLong(talenId));
					model.addAttribute("sort",cmTalenInfo.getTalenSeq());
					model.addAttribute("type",cmTalenInfo.getType());
				}
			}else{
				return "bo/error";
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/dr_edit";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/cleckDr", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody int cleckDr(HttpServletRequest request,Model model) {
		try {
			String userId = request.getParameter("id");
			if(StringUtil.isNotEmpty(userId) && !"0".equals(userId))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userId", userId);
				List<CmTalenInfo> list = cmTalenInfoService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					return 1;
				}
			}else{
				return 0;
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 1;
		}
		return 0;
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/drsave", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody int drsave(HttpServletRequest request,Model model) {
		try {
			String userId = request.getParameter("id");
			String talenId = request.getParameter("talenId");
			String sort = request.getParameter("sort");
			String type = request.getParameter("type");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userId", userId);
			map.put("type", type);
			if(StringUtil.isNotEmpty(talenId) && !"0".equals(talenId) && StringUtil.isNotEmpty(sort))
			{
				map.put("id",talenId);
				
				Long count = (Long)cmTalenInfoService.getCount(map);
				if(count != null && count > 0)
				{
					return 0;
				}
				CmTalenInfo cmTalenInfo = cmTalenInfoService.getById(Long.parseLong(talenId));
				cmTalenInfo.setTalenSeq(Integer.parseInt(sort));
				cmTalenInfo.setType(type);
				cmTalenInfoService.update(cmTalenInfo);
			}else{
				Long count = (Long)cmTalenInfoService.getCount(map);
				if(count != null && count > 0)
				{
					return 0;
				}
				
				CmTalenInfo cmTalenInfo = new CmTalenInfo();
				cmTalenInfo.setTalenSeq(Integer.parseInt(sort));
				cmTalenInfo.setType(type);
				cmTalenInfo.setUserId(Long.parseLong(userId));
				cmTalenInfoService.save(cmTalenInfo);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
		return 1;
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/drdel", method = {RequestMethod.GET, RequestMethod.POST })
	public String drdel(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CmTalenInfo cmTalenInfo = cmTalenInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(cmTalenInfo)) {
					cmTalenInfoService.delete(cmTalenInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user/drlist";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/accusationlist")
	public String accusationlist(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			String accusationUserId = request.getParameter("accusationUserId");
			if(StringUtil.isNotEmpty(accusationUserId)) {
				map.put("accusationUserId", accusationUserId);
				model.addAttribute("accusationUserId",accusationUserId);
			}
			String nickName = request.getParameter("nickName");
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String isDistort = request.getParameter("isDistort");
			if(StringUtil.isNotEmpty(isDistort)) {
				map.put("isDistort", isDistort);
				model.addAttribute("isDistort",isDistort);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			String accusationNickName = request.getParameter("accusationNickName");
			if(StringUtil.isNotEmpty(accusationNickName)) {
				map.put("accusationNickName", accusationNickName);
				model.addAttribute("accusationNickName",accusationNickName);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmProductAccusationDto> pageResult = new Page<PmProductAccusationDto>();
			pageResult = pmProductAccusationService.findDtoByPage(page, map);
			List<PmProductAccusationDto> list = pageResult.getResult();
			for(PmProductAccusationDto pmProductAccusationDto: list) {
				pmProductAccusationDto.setCreatedWhen(pmProductAccusationDto.getCreatedWhen().substring(0,pmProductAccusationDto.getCreatedWhen().length()-2));
			}
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/user/accusation_list";
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/accusationupdate", method = {RequestMethod.GET, RequestMethod.POST })
	public String accusationupdate(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmProductAccusation pmProductAccusation = pmProductAccusationService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmProductAccusation)) {
					pmProductAccusation.setIsDistort(1);
					pmProductAccusationService.update(pmProductAccusation);
					
					PmProductInfo pmProductInfo = pmProductInfoService.getById(pmProductAccusation.getProductId());
					pmProductInfo.setAudit(1);
					pmProductInfoService.update(pmProductInfo);
					
					CmUserMessage cmUserMessage = new CmUserMessage();
					String content = "亲，你于"+DateUtils.getYear()+"年"+DateUtils.getMonth()+"月"+DateUtils.getDay()+"日被举报下线的图片已经重新上线，谢谢支持。";
			    	cmUserMessage.setContent(content);
		    		cmUserMessage.setType(7);
			    	cmUserMessage.setProductId(pmProductInfo.getId());
			    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
			    	cmUserMessage.setUserId(pmProductInfo.getUserId());
			    	cmUserMessage.setSendUserId(pmProductAccusation.getAccusationUserId());
			    	cmUserMessageService.save(cmUserMessage);
			    	
			    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", content, "10", pmProductInfo.getId()+"", "");
					
					CmUserInfo accusationCmUserInfo = cmUserInfoService.getById(pmProductAccusation.getAccusationUserId());
					accusationCmUserInfo.setAccuErrCount(accusationCmUserInfo.getAccuErrCount()+1);
					cmUserInfoService.update(accusationCmUserInfo);
					
					
					CmUserInfo cmUserInfoTo = cmUserInfoService.getById(pmProductAccusation.getAccusationUserId());
			    	pmScoreLogService.addScore("25", cmUserInfoTo, cmUserInfoTo.getClientId()+"", pmProductAccusation.getProductId()+"","");
			    	cmUserInfoService.update(cmUserInfoTo);
					
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("isDistort", "0");
					map.put("userId", pmProductAccusation.getUserId());
					List<PmProductAccusation> list = pmProductAccusationService.findByMap(map);
					if(list == null || list.size() <= 0)
					{
						CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductAccusation.getUserId());
						cmUserInfo.setIsAccusation(0);
						cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
						cmUserInfoService.update(cmUserInfo);
					}else{
						CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductAccusation.getUserId());
						cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
						cmUserInfoService.update(cmUserInfo);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user/accusationlist";
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/accusationStatus", method = {RequestMethod.GET, RequestMethod.POST })
	public String accusationStatus(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmProductAccusation pmProductAccusation = pmProductAccusationService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmProductAccusation)) {
					pmProductAccusation.setIsDistort(2);
					pmProductAccusationService.update(pmProductAccusation);
					
					
					PmProductInfo pmProductInfo = pmProductInfoService.getById(pmProductAccusation.getProductId());
					pmProductInfo.setAudit(2);
					pmProductInfoService.update(pmProductInfo);
					
					CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductAccusation.getUserId());
					
					pmScoreLogService.addScore("13", cmUserInfo, cmUserInfo.getClientId()+"", pmProductAccusation.getProductId()+"","");
					cmUserInfo.setIsAccusation(1);
					cmUserInfoService.update(cmUserInfo);
			    	
			    	CmUserInfo cmUserInfoTo = cmUserInfoService.getById(pmProductAccusation.getAccusationUserId());
			    	pmScoreLogService.addScore("24", cmUserInfoTo, cmUserInfoTo.getClientId()+"", pmProductAccusation.getProductId()+"","");
			    	cmUserInfoTo.setAccuCount(cmUserInfoTo.getAccuCount()-1);
			    	cmUserInfoService.update(cmUserInfoTo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user/accusationlist";
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/accusationCancel", method = {RequestMethod.GET, RequestMethod.POST })
	public String accusationCancel(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			    pmScoreLogService.accusationCancel(id);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user/accusationlist";
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/resetNum", method = {RequestMethod.GET, RequestMethod.POST })
	public String resetNum(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
				if(cmUserInfo != null)
				{
					if("1".equals(status))
					{
						cmUserInfo.setAccuErrCount(0);
					}else if("2".equals(status))
					{
						cmUserInfo.setAccuErrCount(4);
					}
					cmUserInfoService.update(cmUserInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user/list";
	}
	
	/**
	 * 验证手机号唯一
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/check", method = {RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request,HttpServletResponse response) {
		try {
			String mobile = request.getParameter("mobile");
			if(StringUtil.isNotEmpty(mobile))
			{
				Map<String,String> map = new HashMap<String, String>();
				map.put("mobile", mobile);
		    	List<CmUserInfo> list = cmUserInfoService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					response.getWriter().print(false);
				}else{
					response.getWriter().print(true);
				}
			}else{
				response.getWriter().print(true);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/setUser", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String examineupdate(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) && StringUtil.isNotEmpty(status)) {
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(cmUserInfo)) {
					cmUserInfo.setUserType(Integer.parseInt(status));
					cmUserInfoService.update(cmUserInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	//禁止评论
	@RequestMapping(value="/isBan", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String isBan(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			String isBan = request.getParameter("isBan");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) && StringUtil.isNotEmpty(isBan)) {
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(cmUserInfo)) {
					cmUserInfo.setIsBan(Integer.parseInt(isBan));
					cmUserInfoService.update(cmUserInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "";
	}
	
	public static String listToStringTo(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	public static void main(String[] args) {
		for(int i=0;i<100;i++)
		{
			int searchCount = 1000+(int) (Math.random()*1000);
			System.out.println(searchCount);
		}
	}
	
    public static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间  
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后  
        while (dEnd.after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量  
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }
}
