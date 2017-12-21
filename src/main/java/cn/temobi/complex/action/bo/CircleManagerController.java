package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.temobi.complex.entity.CircleType;
import cn.temobi.complex.service.CircleTypeService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

@Controller
@RequestMapping("/circleManager")
public class CircleManagerController extends BoBaseController{

	@Resource(name="circleTypeService")
	private CircleTypeService circleTypeService;
	
	/**
	 * 圈子类型管理列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/circleTypeList")
	public String circleTypeList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = circleTypeService.findByPage(page,map);
			List<CircleType> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circleType_list";
	}
	
	@RequestMapping(value = "/circleTypeEdit")
	public String circleTypeEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				CircleType circleType = circleTypeService.getById(Long.parseLong(id));
				model.addAttribute("entity", circleType);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/circle/circleType_edit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/circleTypeSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String circleTypeSave(HttpServletRequest request,ModelMap model,CircleType circleType) {
		String id = request.getParameter("id");
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				circleTypeService.update(circleType);
			}else{
				circleTypeService.save(circleType);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/circleManager/circleTypeList";
	}
	
	@RequestMapping(value = "/circleTypeDelete")
	public String circleTypeDelete(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				circleTypeService.delete(id);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/circleManager/circleTypeList";
	}
	
	
}
