package cn.temobi.complex.action.bo;

import java.io.File;
import java.util.Arrays;
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

import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.ClientSp;
import cn.temobi.complex.entity.ClientVersion;
import cn.temobi.complex.entity.Feedback;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.service.ClientSpService;
import cn.temobi.complex.service.ClientVersionService;
import cn.temobi.complex.service.FeedbackService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.FileCtrlUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 客户端版本管理
 * @author hjf
 * 2014 五月 15 14:44:30
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/client")
public class SystemClientController extends BoBaseController {
	
	@Resource(name="clientVersionService")
	private ClientVersionService clientVersionService;
	
	@Resource(name="clientSpService")
	private ClientSpService clientSpService;
	
	@Resource(name="feedbackService")
	private FeedbackService feedbackService;
	
	@Resource(name="systemSetingService")
	private SystemSetingService systemSetingService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	/**
	 * 版本列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/vs_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String vs_list(HttpServletRequest request,Model model) {
		try {
			String name = request.getParameter("name");//版本名称
			String code = request.getParameter("code");//版本号
			String channelId = request.getParameter("channelId");//渠道ID
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(code)) {
				map.put("code", code);
				model.addAttribute("code", code);
			}
			if(StringUtil.isNotEmpty(name)) {
				map.put("name", name);
				model.addAttribute("name", name);
			}
			if(StringUtil.isNotEmpty(channelId)) {
				map.put("channelId", channelId);
				model.addAttribute("channelId", channelId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ClientVersion> pageResult = new Page<ClientVersion>();
			pageResult = clientVersionService.findByPage(page, map);
			List<ClientVersion> list = pageResult.getResult();
			for(ClientVersion clientVersion:list){
				clientVersion.setDownUrl(host+clientVersion.getDownUrl());
				if(StringUtil.isNotEmpty(clientVersion.getCreatedWhen())){
					clientVersion.setCreatedWhen(clientVersion.getCreatedWhen().substring(0,clientVersion.getCreatedWhen().length()-2));
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
		return "bo/version/vs_list";
	}
	
	/**
	 * 版本编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_vs", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit_vs(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				ClientVersion clientVersion = clientVersionService.getById(Long.valueOf(id));
				if(StringUtil.isNotEmpty(clientVersion.getCreatedWhen())){
					clientVersion.setCreatedWhen(clientVersion.getCreatedWhen().substring(0,clientVersion.getCreatedWhen().length()-2));
				}
				model.addAttribute("entity", clientVersion);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/version/vs_edit";
	}
	
	/**
	 * appList
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/app_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String app_list(HttpServletRequest request,Model model) {
		return "bo/version/app_list";
	}
	
	/**
	 * 版本新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/vs_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String vs_save(HttpServletRequest request,ModelMap model,ClientVersion clientVersion) {
		try {
				String homePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "home_path_prefix");
				String id = request.getParameter("id") ;
				if(StringUtil.isNotEmpty(clientVersion)) {
					String appUrl = clientVersion.getDownUrl();
					File file = new File(homePath+appUrl);
					String size = FileCtrlUtil.convertFileSize(file.length());
					clientVersion.setSize(size);
					if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
						clientVersionService.update(clientVersion);
					}else {
						clientVersion.setOs(1);
						clientVersionService.save(clientVersion);
					}
				}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/client/vs_list";
	}
	
	
	/**
	 * 启动页列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/sp_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String sp_list(HttpServletRequest request,Model model) {
		try {
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			String versionCode = request.getParameter("versionCode");
			
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type);
				model.addAttribute("type", type);
			}
			if(StringUtil.isNotEmpty(name)) {
				map.put("name", name);
				model.addAttribute("name", name);
			}
			if(StringUtil.isNotEmpty(versionCode)) {
				map.put("versionCode", versionCode);
				model.addAttribute("versionCode", versionCode);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ClientSp> pageResult = new Page<ClientSp>();
			pageResult = clientSpService.findByPage(page, map);
			List<ClientSp> list = pageResult.getResult();
			map.put("type", "1");
			List<SysConfigParam> changeList = sysConfigParamService.findByMap(map);
			for(ClientSp clientSp:list){
				if(StringUtil.isNotEmpty(clientSp.getCreatedWhen())){
					clientSp.setCreatedWhen(clientSp.getCreatedWhen().substring(0,clientSp.getCreatedWhen().length()-2));
				}
				
				for(SysConfigParam sysConfigParam:changeList)
				{
					if(StringUtil.isNotEmpty(clientSp.getClientChannel()) && sysConfigParam.getEnParamName().equals(clientSp.getClientChannel()))
					{
						clientSp.setClientChannel(sysConfigParam.getParamValue());
					}
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
		return "bo/version/sp_list";
	}
	
	/**
	 * 启动页编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_sp", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit_sp(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				ClientSp clientSp = clientSpService.getById(Long.valueOf(id));
				if(StringUtil.isNotEmpty(clientSp.getCreatedWhen())){
					clientSp.setCreatedWhen(clientSp.getCreatedWhen().substring(0,clientSp.getCreatedWhen().length()-2));
				}
				model.addAttribute("entity", clientSp);
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("type", "1");
			List<SysConfigParam> changeList = sysConfigParamService.findByMap(map);
			model.addAttribute("changeList",changeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/version/sp_edit";
	}
	
	/**
	 * 启动页新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/sp_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String vs_sp(HttpServletRequest request,ModelMap model,ClientSp clientSp) {
		try {
				String id = request.getParameter("id") ;
				if(StringUtil.isNotEmpty(clientSp)) {
					if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
						clientSpService.update(clientSp);
					}else {
						clientSpService.save(clientSp);
					}
				}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/client/sp_list";
	}
	
	@RequestMapping(value="/sp_del", method = {RequestMethod.GET, RequestMethod.POST })
	public String sp_del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				ClientSp clientSp = clientSpService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(clientSp)) {
					clientSpService.delete(clientSp);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/client/sp_list";
	}
	
	
	/**
	 * 反馈列表
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fb_list")
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String remark = request.getParameter("remark");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			if(StringUtil.isNotEmpty(remark)) {
				map.put("remark", remark);
				model.addAttribute("remark",remark);
			}
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Feedback> pageResult = new Page<Feedback>();
			pageResult = feedbackService.findByPage(page, map);
			List<Feedback> list = pageResult.getResult();
			for(Feedback feedback: list) {
				feedback.setCreatedWhen(feedback.getCreatedWhen().substring(0,feedback.getCreatedWhen().length()-2));
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
		return "bo/version/fb_list";
	}
	
	@RequestMapping(value = "/fb_edit")
	public String edit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Feedback feeback = feedbackService.getById(Long.parseLong(id));
				model.addAttribute("entity",feeback);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/version/fb_edit";
	}
	
	@RequestMapping(value = "/fb_save")
	public @ResponseBody String save(HttpServletRequest request, ModelMap model,Feedback entity) {
		try {
			String id = request.getParameter("id");
			System.out.println("entity="+entity.getRemark());
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				feedbackService.update(entity);
			}else{
				feedbackService.save(entity);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "";
	}
	/**
	 * 反馈列表
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fb_type_list")
	public String typeList(HttpServletRequest request, ModelMap model) {
		try {
			SystemSeting systemSeting = systemSetingService.getById(null);
			if(StringUtil.isNotEmpty(systemSeting.getFeedbackType()))
			{
				List<String> list = Arrays.asList(systemSeting.getFeedbackType().split(","));
				model.addAttribute("list",list);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/version/fb_type_list";
	}
	
	
	/**
	 * 启动页编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/fbTedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String fbTedit(HttpServletRequest request,Model model) {
		return "bo/version/fb_type_edit";
	}
	
	/**
	 * 启动页新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/fbTsave", method = {RequestMethod.GET, RequestMethod.POST })
	public String fbTsave(HttpServletRequest request,ModelMap model,ClientSp clientSp) {
		try {
			String name = request.getParameter("name");
			SystemSeting systemSeting = systemSetingService.getById(null);
			if(StringUtil.isNotEmpty(systemSeting.getFeedbackType()))
			{
				systemSeting.setFeedbackType(systemSeting.getFeedbackType()+name+",");
			}else{
				systemSeting.setFeedbackType(name+",");
			}
			systemSetingService.update(systemSeting);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/client/fb_type_list";
	}
	
	@RequestMapping(value="/fbTdel", method = {RequestMethod.GET, RequestMethod.POST })
	public String fbTdel(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				SystemSeting systemSeting = systemSetingService.getById(null);
				if(StringUtil.isNotEmpty(systemSeting.getFeedbackType()))
				{
					systemSeting.setFeedbackType(systemSeting.getFeedbackType().replace(id+",", ""));
				}
				systemSetingService.update(systemSeting);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/client/fb_type_list";
	}
}
