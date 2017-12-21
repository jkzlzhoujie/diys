package cn.temobi.complex.action.bo;

import java.util.Date;
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

import cn.temobi.complex.entity.Application;
import cn.temobi.complex.entity.BackgroundPic;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.MenuList;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.complex.entity.SysPrivilege;
import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.complex.entity.SysType;
import cn.temobi.complex.entity.SysTypeTime;
import cn.temobi.complex.service.ApplicationService;
import cn.temobi.complex.service.BackgroundPicService;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.MenuListService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysPackageService;
import cn.temobi.complex.service.SysPrivilegeContentService;
import cn.temobi.complex.service.SysPrivilegeContentTypeService;
import cn.temobi.complex.service.SysPrivilegePackageService;
import cn.temobi.complex.service.SysPrivilegeService;
import cn.temobi.complex.service.SysTypeService;
import cn.temobi.complex.service.SysTypeTimeService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.WriteHTMLUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/operate")
public class OperateController extends BoBaseController{

	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	String hostURL = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url1"); 
	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="sysTypeService")
	private SysTypeService sysTypeService;
	
	@Resource(name="sysTypeTimeService")
	private SysTypeTimeService sysTypeTimeService;
	
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	@Resource(name="sysPackageService")
	private SysPackageService sysPackageService;
	
	@Resource(name="sysPrivilegeService")
	private SysPrivilegeService sysPrivilegeService;
	
	@Resource(name="sysPrivilegePackageService")
	private SysPrivilegePackageService sysPrivilegePackageService;
	
	@Resource(name="sysPrivilegeContentTypeService")
	private SysPrivilegeContentTypeService sysPrivilegeContentTypeService;
	
	@Resource(name="sysPrivilegeContentService")
	private SysPrivilegeContentService  sysPrivilegeContentService;
	
	@Resource(name="menuListService")
	private MenuListService  menuListService;
	
	@Resource(name="backgroundPicService")
	private BackgroundPicService  backgroundPicService;
	
	@Resource(name="applicationService")
	private ApplicationService applicationService;
	
	
	@RequestMapping(value = "/anList")
	public String anList(HttpServletRequest request, ModelMap model) {
		try {
			String timeId = request.getParameter("timeId");
			model.addAttribute("timeId", timeId);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "2");
			map.put("expand1", timeId);
			List<SysConfigParam> list = sysConfigParamService.findByMap(map);
			model.addAttribute("list",list);
			
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "4");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/an_list";
	}
	
	@RequestMapping(value = "/anEdit")
	public String anEdit(HttpServletRequest request, ModelMap model) {
		try {
			String timeId = request.getParameter("timeId");
			model.addAttribute("timeId", timeId);
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysConfigParam);
			}
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "4");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/an_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/anSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String anSave(HttpServletRequest request,ModelMap model) {
		String timeId = request.getParameter("timeId");
		String enParamName = request.getParameter("enParamName");
		String paramValue = request.getParameter("paramValue");
		String cnParamName = request.getParameter("cnParamName");
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(enParamName) && StringUtil.isNotEmpty(paramValue))
			{
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParam.setCnParamName(cnParamName);
					sysConfigParam.setParamValue(paramValue);
					sysConfigParam.setExpand1(timeId);
					sysConfigParamService.update(sysConfigParam);
				}else{
					SysConfigParam sysConfigParam = new SysConfigParam();
					sysConfigParam.setType("2");
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParam.setParamValue(paramValue);
					sysConfigParam.setCnParamName(cnParamName);
					sysConfigParam.setExpand1(timeId);
					sysConfigParamService.save(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/anList";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/anDel", method = {RequestMethod.GET, RequestMethod.POST })
	public String anDel(HttpServletRequest request,ModelMap model) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysConfigParam)){
					sysConfigParamService.delete(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/anList";
	}
	
	@RequestMapping(value = "/levelList")
	public String levelList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "3");
			List<SysConfigParam> list = sysConfigParamService.findByMap(map);
			model.addAttribute("list",list);
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "3");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/level_list";
	}
	
	@RequestMapping(value = "/levelEdit")
	public String levelEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysConfigParam);
			}
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "3");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/level_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/levelSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String levelSave(HttpServletRequest request,ModelMap model) {
		String enParamName = request.getParameter("enParamName");
		String paramValue = request.getParameter("paramValue");
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(enParamName) && StringUtil.isNotEmpty(paramValue))
			{
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParam.setParamValue(paramValue);
					sysConfigParamService.update(sysConfigParam);
				}else{
					SysConfigParam sysConfigParam = new SysConfigParam();
					sysConfigParam.setType("3");
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParam.setParamValue(paramValue);
					sysConfigParamService.save(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/levelList";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/levelDel", method = {RequestMethod.GET, RequestMethod.POST })
	public String levelDel(HttpServletRequest request,ModelMap model) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysConfigParam)){
					sysConfigParamService.delete(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/levelList";
	}
	

	@RequestMapping(value = "/helpList")
	public String helpList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "4");
			List<SysConfigParam> list = sysConfigParamService.findByMap(map);
			model.addAttribute("list",list);
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "2");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/help_list";
	}
	
	@RequestMapping(value = "/helpEdit")
	public String helpEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysConfigParam);
				String fileName = "help"+sysConfigParam.getEnParamName()+".html";
				model.addAttribute("content", WriteHTMLUtil.readFile(fileName));
			}
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "2");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/help_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/helpSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String helpSave(HttpServletRequest request,ModelMap model) {
		String enParamName = request.getParameter("enParamName");
		String content = request.getParameter("content");
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(enParamName) && StringUtil.isNotEmpty(content))
			{
				SysConfigParam sysConfigParam = null;
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParamService.update(sysConfigParam);
				}else{
					sysConfigParam = new SysConfigParam();
					sysConfigParam.setType("4");
					sysConfigParam.setEnParamName(enParamName);
					sysConfigParamService.save(sysConfigParam);
				}
				
				String fileName = "help"+sysConfigParam.getEnParamName();
				WriteHTMLUtil.writeHtm(fileName+".html", html+content+html2, "UTF-8");
				sysConfigParam.setParamValue(hostURL+"/diys/client/v42/hdIndex?name="+fileName);
				sysConfigParamService.update(sysConfigParam);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/helpList";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/helpDel", method = {RequestMethod.GET, RequestMethod.POST })
	public String helpDel(HttpServletRequest request,ModelMap model) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysConfigParam)){
					sysConfigParamService.delete(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/helpList";
	}
	
	
	/**
	 * banner图列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/bannerList", method = {RequestMethod.GET, RequestMethod.POST })
	public String bannerList(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			
			String name = request.getParameter("name");
			model.addAttribute("name", name);
			map.put("name", name);
			
			String type = request.getParameter("type");
			model.addAttribute("type", type);
			map.put("type", type);
			
			String actionType = request.getParameter("actionType");
			model.addAttribute("actionType", actionType);
			map.put("actionType", actionType);
			
			String system = request.getParameter("system");
			model.addAttribute("system", system);
			map.put("system", system);
			
			String status = request.getParameter("status");
			model.addAttribute("status", status);
			map.put("status", status);
			
			
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Banner> pageResult = new Page<Banner>();
			pageResult = bannerService.findByPage(page, map);
			List<Banner> list = pageResult.getResult();
			for(Banner banner:list){
				if(StringUtil.isNotEmpty(banner.getCreatedWhen())){
					banner.setCreatedWhen(banner.getCreatedWhen().substring(0,banner.getCreatedWhen().length()-2));
				}
			}
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "1");
			List<SysType> typeList = sysTypeService.findByMap(typeMap);
			model.addAttribute("typeList",typeList);
			typeMap.put("type", "5");
			List<SysType> jumpList = sysTypeService.findByMap(typeMap);
			model.addAttribute("jumpList",jumpList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/banner_list";
	}
	
	/**
	 * banner编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/bannerEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String bannerEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Banner banner = bannerService.getById(Long.valueOf(id));
				model.addAttribute("entity", banner);
			}
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "1");
			List<SysType> typeList = sysTypeService.findByMap(map);
			model.addAttribute("typeList",typeList);
			map.put("type", "5");
			List<SysType> jumpList = sysTypeService.findByMap(map);
			model.addAttribute("jumpList",jumpList);
			map.put("status","1" );
			List<Application> applicationList = applicationService.findByMap(map);
			model.addAttribute("applicationList",applicationList);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/banner_edit";
	}
	
	/**
	 * banner新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/bannerSave", method = {RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public int bannerSave(HttpServletRequest request,ModelMap model,Banner banner) {
		String id = request.getParameter("id") ;
		try {
			if(banner.getType().equals("show_banner5") || banner.getType().equals("show_banner6"))
			{
				if(StringUtil.isEmpty(banner.getExtend()))
				{
					return 1;
				}
				banner.setType(banner.getType()+"&"+banner.getExtend());
			}
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				banner.setId(Long.valueOf(id));
				bannerService.update(banner);
			}else {
				bannerService.save(banner);
			}
			if(StringUtil.isNotEmpty(banner.getContent()) && banner.getActionType().equals("jump1"))
			{
				String fileName = "banner"+banner.getId();
				WriteHTMLUtil.writeHtm(fileName+".html", html+banner.getContent()+html2, "UTF-8");
				banner.setClickUrl(hostURL+"/diys/client/v42/hdIndex?name="+fileName);
				bannerService.update(banner);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 1;
		}
		return 0;
	}
	
	/**
	 * banner删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/bannerDel", method = {RequestMethod.GET, RequestMethod.POST })
	public String bannerDel(HttpServletRequest request) {
		String id = request.getParameter("id") ;
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) ) {
				bannerService.delete(id);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/bannerList";
	}
	
	
	@RequestMapping(value = "/timeList")
	public String timeList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "1");
			List<SysConfigParam> list = sysTypeTimeService.findByMap(map);
			model.addAttribute("list",list);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/time_list";
	}
	
	@RequestMapping(value = "/timeEdit")
	public String timeEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysTypeTime sysTypeTime = sysTypeTimeService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysTypeTime);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/time_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/timeSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String timeSave(HttpServletRequest request,ModelMap model,SysTypeTime sysTypeTime) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				sysTypeTimeService.update(sysTypeTime);
			}else{
				sysTypeTimeService.save(sysTypeTime);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/timeList";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/timeDel", method = {RequestMethod.GET, RequestMethod.POST })
	public String timeDel(HttpServletRequest request,ModelMap model) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysTypeTime sysTypeTime = sysTypeTimeService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysTypeTime)){
					sysTypeTimeService.delete(sysTypeTime);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/timeList";
	}
	
	
	
	//系统套餐 列表 -- 管理
	@RequestMapping(value = "/sysPackageList")
	public String sysPackageList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String name = request.getParameter("name");
			String content = request.getParameter("content");
			String status = request.getParameter("status");
			String code = request.getParameter("code");
			
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(content)){
				map.put("content", content);
				model.addAttribute("content",content);
			}
			if(StringUtil.isNotEmpty(code)){
				map.put("code", code);
				model.addAttribute("code",code);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = sysPackageService.findByPage(page,map);
			List<SysPackage> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPackage_list";
	}
	
	@RequestMapping(value = "/sysPackageEdit")
	public String sysPackageEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysPackage sysPackage = sysPackageService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysPackage);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPackage_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/sysPackageSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysPackageSave(HttpServletRequest request,ModelMap model,SysPackage sysPackage) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				sysPackage.setUpdateWhen( new Date() );
				sysPackageService.update(sysPackage);
			}else{
				sysPackageService.save(sysPackage);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/sysPackageList";
	}
	
	
	//系统列表 特权-- 管理
	@RequestMapping(value = "/sysPrivilegeList")
	public String sysPrivilegeList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String status = request.getParameter("status");
			
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(code)){
				map.put("code", code);
				model.addAttribute("code",code);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = sysPrivilegeService.findByPage(page,map);
			List<SysPrivilege> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPrivilege_list";
	}
	
	@RequestMapping(value = "/sysPrivilegeEdit")
	public String sysPrivilegeEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysPrivilege sysPrivilege = sysPrivilegeService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysPrivilege);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPrivilege_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/sysPrivilegeSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysPrivilegeSave(HttpServletRequest request,ModelMap model,SysPrivilege sysPrivilege) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				sysPrivilege.setUpdateWhen( new Date() );
				sysPrivilegeService.update(sysPrivilege);
			}else{
				sysPrivilegeService.save(sysPrivilege);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/sysPrivilegeList";
	}
	
	//配置特权  套餐
	@RequestMapping(value = "/sysPrivilegePackageEdit")
	public String sysPrivilegePackageEdit(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			String privilegeId = request.getParameter("id");
			if(StringUtil.isNotEmpty(privilegeId))
			{
				param.put("privilegeId", privilegeId);
				model.addAttribute("privilegeId",privilegeId);
				List<SysPrivilegePackage> sysPrivilegePackageList = sysPrivilegePackageService.findByMap(param);
				model.addAttribute("sysPrivilegePackageList", sysPrivilegePackageList);
				List<SysPrivilegeContent> sysPrivilegeContentList = sysPrivilegeContentService.findByMap(param);
				model.addAttribute("sysPrivilegeContentList", sysPrivilegeContentList);
			}
			param.clear();
			param.put("status", "1");
			List<SysPackage> sysPackageList = sysPackageService.findByMap(param);
			model.addAttribute("sysPackageList", sysPackageList);
			List<SysPrivilegeContentType> sysPrivilegeContentTypeList = sysPrivilegeContentTypeService.findByMap(param);
			model.addAttribute("sysPrivilegeContentTypeList", sysPrivilegeContentTypeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPrivilege_package_edit";
	}
	
	//配置特权  保存套餐
	@RequestMapping(value = "/sysPrivilegePackageSave")
	public String sysPrivilegePackageSave(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> param = new HashMap<String, Object>();
			String privilegeId = request.getParameter("privilegeId");
			String[] packageIds = request.getParameterValues("packageId");
			if(StringUtil.isNotEmpty(privilegeId))
			{
				sysPrivilegePackageService.setPrivilegePackage(privilegeId,packageIds);
			}
			String[] contentIds = request.getParameterValues("contentId");
			if(StringUtil.isNotEmpty(contentIds))
			{
				sysPrivilegeContentService.setPrivilegeContent(privilegeId,contentIds);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/sysPrivilegeList";
	}


	//系统列表 特权内容-- 管理
	@RequestMapping(value = "/sysPrivilegeContentTypeList")
	public String sysPrivilegeContentTypeList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String typeCode = request.getParameter("typeCode");
			String status = request.getParameter("status");
			String content = request.getParameter("content");
			
			if(StringUtil.isNotEmpty(typeCode)){
				map.put("typeCode", typeCode);
				model.addAttribute("typeCode",typeCode);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			if(StringUtil.isNotEmpty(content)){
				map.put("content", content);
				model.addAttribute("content",content);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = sysPrivilegeContentTypeService.findByPage(page,map);
			List<SysPrivilegeContentType> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPrivilegeContentType_list";
	}
	
	@RequestMapping(value = "/sysPrivilegeContentTypeEdit")
	public String sysPrivilegeContentTypeEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				SysPrivilegeContentType sysPrivilegeContentType = sysPrivilegeContentTypeService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysPrivilegeContentType);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/sysPrivilegeContentType_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/sysPrivilegeContentTypeSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysPrivilegeContentTypeSave(HttpServletRequest request,ModelMap model,SysPrivilegeContentType sysPrivilegeContentType) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				sysPrivilegeContentType.setUpdateWhen( new Date() );
				sysPrivilegeContentTypeService.update(sysPrivilegeContentType);
			}else{
				sysPrivilegeContentTypeService.save(sysPrivilegeContentType);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/sysPrivilegeContentTypeList";
	}
	
	
	

	/**
	 * 首页App菜单管理列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/menuList")
	public String menuList(HttpServletRequest request, ModelMap model) {
		try {
			
			String name = request.getParameter("name");
			Map<String,Object> map = new HashMap<String, Object>();
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
				
			}
			map.put("type", "5");
			List<SysType> jumpList = sysTypeService.findByMap(map);
			model.addAttribute("jumpList",jumpList);
			map.put("status","1" );
			List<Application> applicationList = applicationService.findByMap(map);
			model.addAttribute("applicationList",applicationList);
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = menuListService.findByPage(page,map);
			List<MenuList> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/menuList_list";
	}
	
	@RequestMapping(value = "/menuListEdit")
	public String menuListEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				MenuList menuList = menuListService.getById(Long.parseLong(id));
				model.addAttribute("entity", menuList);
			}
			
			Map<String,Object> typeMap = new HashMap<String, Object>();
			typeMap.put("type", "5");
			List<SysType> jumpList = sysTypeService.findByMap(typeMap);
			model.addAttribute("jumpList",jumpList);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("status","1" );
			List<Application> applicationList = applicationService.findByMap(map);
			model.addAttribute("applicationList",applicationList);
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/menuList_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/menuListSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String menuListSave(HttpServletRequest request,ModelMap model,MenuList menuList) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{	
				menuListService.update(menuList);
			}else{
				menuListService.save(menuList);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/menuList";
	}
	
	/**
	 * 背景图 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/backgroundPic")
	public String backgroundPic(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = backgroundPicService.findByPage(page,map);
			List<BackgroundPic> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/backgroundPic_list";
	}
	
	@RequestMapping(value = "/backgroundPicEdit")
	public String backgroundPicEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				BackgroundPic backgroundPic = backgroundPicService.getById(Long.parseLong(id));
				model.addAttribute("entity", backgroundPic);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/operate/backgroundPic_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/backgroundPicSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String backgroundPicSave(HttpServletRequest request,ModelMap model,BackgroundPic backgroundPic) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				backgroundPicService.update(backgroundPic);
			}else{
				backgroundPicService.save(backgroundPic);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/operate/backgroundPic";
	}
	
	
	
}
