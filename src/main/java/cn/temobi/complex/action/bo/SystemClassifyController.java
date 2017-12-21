package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.service.ClassifyService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

/**
 * 分类管理
 * @author hjf
 * 2014 三月 18 09:25:57
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/classify")
public class SystemClassifyController extends BoBaseController {
	
	
	@Resource(name="classifyService")
	public ClassifyService classifyService;
	
	
	@Resource(name="systemSetingService")
	public SystemSetingService systemSetingService;
	
	
	
	/**
	 * 分类列表
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
			String type = request.getParameter("type");
			
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			if(StringUtil.isNotEmpty(type)){
				map.put("type", type);
				model.addAttribute("type",type);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Classify> pageResult = new Page<Classify>();
			pageResult = classifyService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/classify/list";
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
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Classify classify = classifyService.getById(Long.parseLong(id));
				model.addAttribute("entity", classify);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/classify/edit";
	}
	
	/**
	 * 分类新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,Classify classify) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Classify oldClassify = classifyService.getById(classify.getId());
				if(classify.getLoveNum() == 0)
				{
					classify.setLoveNum(oldClassify.getLoveNum());
				}
				classifyService.update(classify);
			}else{
				SystemSeting systemSeting = systemSetingService.getById(null);
				systemSeting.setClassifyNum(systemSeting.getClassifyNum()+1);
				systemSetingService.update(systemSeting);
				classify.setThemeNum(0);
				classifyService.save(classify);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/classify/list";
	}
	
	/**
	 * 分类删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Classify classify = classifyService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(classify)) {
					classifyService.delete(classify);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/classify/list";
	}
}
