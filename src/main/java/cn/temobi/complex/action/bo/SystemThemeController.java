package cn.temobi.complex.action.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salim.cache.CacheHelper;
import com.tencent.xinge.XingeApp;

import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.ThemeDto;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.entity.ThemeLove;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.complex.service.ClassifyService;
import cn.temobi.complex.service.CountService;
import cn.temobi.complex.service.MessageService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.complex.service.ThemeService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.PageModel;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 主题管理
 * @author hjf
 * 2014 三月 18 09:25:57
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/theme")
public class SystemThemeController extends BoBaseController {
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="themeService")
	public ThemeService themeService;
	
	@Resource(name="classifyService")
	public ClassifyService classifyService;
	
	@Resource(name="systemSetingService")
	public SystemSetingService systemSetingService;
	
	@Resource(name="messageService")
	public MessageService messageService;
	
	/**
	 * 主题列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			String name = request.getParameter("name");//名称
			String status = request.getParameter("status");
			String classifyId = request.getParameter("classifyId");
			String h5Push = request.getParameter("h5Push");
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(name)) {
				map.put("themeName", name);
				model.addAttribute("name", name);
			}
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status", status);
			}
			if(StringUtil.isNotEmpty(classifyId)) {
				map.put("classifyId", classifyId);
				model.addAttribute("classifyId", classifyId);
			}
			if(StringUtil.isNotEmpty(h5Push)) {
				map.put("h5Push", h5Push);
				model.addAttribute("h5Push", h5Push);
			}
			Map<String,String> classifyMap = new HashMap<String, String>();
			classifyMap.put("type", "1");
			List<Classify> classifyList = classifyService.findByMap(classifyMap);
			model.addAttribute("classifyList", classifyList);
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Theme> pageResult = new Page<Theme>();
			pageResult = themeService.findByPage(page, map);
			List<Theme> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(Theme theme: list) {
					theme.setCreatedWhen(theme.getCreatedWhen().substring(0,theme.getCreatedWhen().length()-2));
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
		return "bo/theme/list";
	}
	
	
	@RequestMapping(value="/refreshCacheTheme",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String refreshCacheTheme(HttpServletRequest request,Model model,BlackListWord entity){
		
		Map<String,Object> map = new HashMap<String, Object>();
		String classifyId = request.getParameter("classifyId");
		if(StringUtil.isEmpty( classifyId )){
			return "1";
		}
		
		List<ThemeDto> dtolist = new ArrayList<ThemeDto>();
		int count = themeService.getCount(map).intValue();
		if(count > 0){
			int totalPage = count/300;	//每页300条
			int yuNum = count%300;	
			if( yuNum > 0){
				totalPage = totalPage +1;
			}
			for (int pageNo = 1; pageNo <= totalPage; pageNo++) {
		    	CacheHelper.getInstance().getInstance().remove("theme@themeDto"+classifyId.trim()+pageNo);
			}
		}
		
		return "";
	}
	
	
	/**
	 * 分类编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String, String>();
			map.put("type", "1");
			List<Classify> list = classifyService.findByMap(map);
			model.addAttribute("classifyList", list);
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Theme theme = themeService.getById(Long.parseLong(id));
				model.addAttribute("entity", theme);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/theme/edit";
	}
	
	/**
	 * 分类新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,Theme theme) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				if(StringUtil.isNotEmpty(theme.getSort()) && theme.getSort() == 0)
				{
					theme.setSort(null);
				}
				themeService.update(theme);
			}else{
				SystemSeting systemSeting = systemSetingService.getById(null);
				systemSeting.setThemeNum(systemSeting.getThemeNum()+1);
				systemSetingService.update(systemSeting);
				Classify classify = classifyService.getById(theme.getClassifyId());
				classify.setThemeNum(classify.getThemeNum()+1);
				classifyService.update(classify);
				theme.setLoveNum(0L);
				themeService.save(theme);
				
//				String sendMsg = request.getParameter("sendMsg");
//				String msgTitle = request.getParameter("msgTitle");
//				String msgContent = request.getParameter("msgContent");
//				
//				if("1".equals(sendMsg) && StringUtil.isNotEmpty(msgTitle) && StringUtil.isNotEmpty(msgContent))
//				{
//					//正式
//					//XingeApp.pushAllAndroid(2100119777, "d237e98979cba351deb0a3bfbe3278ba", msgTitle, msgContent);
//					//测试
//					XingeApp.pushAllAndroid(2100135766, "d6f26b6ffa31d4786628c404988159ac", msgTitle, msgContent);
//					
//					Message message = new Message();
//					message.setContent(msgContent);
//					message.setThemeId(theme.getId()+"");
//					message.setTitle(msgTitle);
//					message.setType("4");
//					messageService.save(message);
//				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/theme/list";
	}
	
	/**
	 * 表情包删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Theme theme = themeService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(theme)) {
					themeService.delete(theme);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/theme/list";
	}
	
	/**
	 * 主题状态修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/us", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String updateStatus(HttpServletRequest request,Model model) {
		String packetIdStr = request.getParameter("packetIdStr") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(packetIdStr) && StringUtil.isNotEmpty(status)) {
				String[] packetIdArr = packetIdStr.split(",");
				for(int i=0; i<packetIdArr.length; i++) {
					Theme theme = themeService.getById(Long.parseLong(packetIdArr[i]));
					if(StringUtil.isNotEmpty(theme)) {
						theme.setStatus(status);
						themeService.update(theme);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
	
	
	/**
	 * 主题push修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/updatePush", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String updatePush(HttpServletRequest request,Model model) {
		String themeId = request.getParameter("themeId") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(themeId) && StringUtil.isNotEmpty(status)) {
				Theme theme = themeService.getById(Long.parseLong(themeId));
				if(StringUtil.isNotEmpty(theme)) {
					theme.setH5Push(status);
					themeService.update(theme);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
}
