package cn.temobi.complex.action.bo;

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

import cn.temobi.complex.entity.Application;
import cn.temobi.complex.service.ApplicationCountService;
import cn.temobi.complex.service.ApplicationService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 18 09:25:57
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/application")
public class SystemApplicationController extends BoBaseController {
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="applicationService")
	public ApplicationService applicationService;
	@Resource(name="applicationCountService")
	public ApplicationCountService applicationCountService;
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Application> pageResult = new Page<Application>();
			pageResult = applicationService.findByPage(page, map);
			List<Application> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(Application application:list)
				{
					map.clear();
					map.put("applicationId", application.getId().toString());
					map.put("type","1");
					int uploadNum = applicationCountService.getCount(map).intValue();
					application.setUploadCount(uploadNum);
					map.put("type","2");
					int clickNum = applicationCountService.getCount(map).intValue();
					application.setClickCount(clickNum);
					application.setUrl(host+application.getUrl());
				}
				model.addAttribute("list",list);
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/application/list";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Application application = applicationService.getById(Long.parseLong(id));
				model.addAttribute("entity", application);
				if(StringUtil.isNotEmpty(application.getImage()))
				{
					List<String> list = Arrays.asList(application.getImage().split(","));
					model.addAttribute("imagelist", list);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/application/edit";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,Application application) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				applicationService.update(application);
			}else{
				applicationService.save(application);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/application/list";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Application application = applicationService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(application)) {
					applicationService.delete(application);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/application/list";
	}
}
