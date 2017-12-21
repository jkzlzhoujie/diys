package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.entity.BlackListNickName;
import cn.temobi.complex.entity.BlackListUserEquipment;
import cn.temobi.complex.service.BlackListNickNameService;
import cn.temobi.complex.service.BlackListUserEquipmentService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/black")
public class BlackListController extends BoBaseController{
	
	@Resource(name="blackListNickNameService")
	private BlackListNickNameService blackListNickNameService;
	@Resource(name="blackListUserEquipmentService")
	private BlackListUserEquipmentService blackListUserEquipmentService;
	
	
	//用户昵称黑名单
	@RequestMapping(value="/blacklistNickName",method={RequestMethod.GET,RequestMethod.POST})
	public String blacklistNickName(HttpServletRequest request,Model model){
		try {
			String content = request.getParameter("content");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(content)){
				map.put("content", content);
				model.addAttribute("content",content);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = blackListNickNameService.findByPage(page,map);
			List<BlackListNickName> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/product/blackNickName_list";
	}
	
	@RequestMapping(value="/blackNickNameEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String blackNickNameEdit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			BlackListNickName entity = blackListNickNameService.getById(Long.parseLong(id));
			model.addAttribute("entity",entity);
		}
		return "bo/product/blackNickName_edit";
	}
	
	@RequestMapping(value="/blackNickNameSave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blackNickNameSave(HttpServletRequest request,Model model,BlackListNickName entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListNickNameService.update(entity);
		}else{
			blackListNickNameService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/blackNickNameDelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blackNickNameDelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListNickNameService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	
	
	
	// 用户设备黑名单
	@RequestMapping(value="/blacklistUserEquipment",method={RequestMethod.GET,RequestMethod.POST})
	public String blacklistUserEquipment(HttpServletRequest request,Model model){
		try {
			String content = request.getParameter("content");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(content)){
				map.put("content", content);
				model.addAttribute("content",content);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = blackListUserEquipmentService.findByPage(page,map);
			List<BlackListUserEquipment> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/product/blackUserEquipment_list";
	}
	
	@RequestMapping(value="/blackUserEquipmentEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String blackUserEquipmentEdit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			BlackListUserEquipment entity = blackListUserEquipmentService.getById(Long.parseLong(id));
			model.addAttribute("entity",entity);
		}
		return "bo/product/blackUserEquipment_edit";
	}
	
	@RequestMapping(value="/blackUserEquipmentSave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blackUserEquipmentSave(HttpServletRequest request,Model model,BlackListUserEquipment entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListUserEquipmentService.update(entity);
		}else{
			blackListUserEquipmentService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/blackUserEquipmentDelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blackUserEquipmentDelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListUserEquipmentService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
	
}
