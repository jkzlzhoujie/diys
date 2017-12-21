package cn.temobi.complex.action.bo;

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
import cn.temobi.complex.entity.SystemRights;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.service.SystemRightsService;
import cn.temobi.complex.service.SystemUserService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

/** 
 * 权限管理
 * @author hjf
 * 2014-3-5 下午03:26:19
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/rh")
public class SystemRightsController extends BoBaseController{
	
	@Resource(name="systemRightsService")
	private SystemRightsService systemRightsService;
	
	@Resource(name="systemUserService")
	private SystemUserService systemUserService;
	
	/**
	 * 权限列表的展示
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/rights_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model)
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SystemRights> pageResult = new Page<SystemRights>();
			pageResult = systemRightsService.findByPage(page, map);
			List<SystemRights> list = pageResult.getResult();
			for(SystemRights systemRight:list){
				systemRight.setCreatedWhen(systemRight.getCreatedWhen().substring(0,systemRight.getCreatedWhen().length()-2));
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
		return "bo/rights/rights_list";
	}
	
	/**
	 * 单个删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rights_del",method=RequestMethod.GET)
	public String delete(HttpServletRequest request,Model model) {
		 try {
			 String id = request.getParameter("id") ;
			 String pageSize = request.getParameter("pageSize");
			 String pageNo = request.getParameter("pageNo");
			 if(StringUtil.isNotEmpty(id)){
				 String  rightCode = systemRightsService.getById(Long.valueOf(id)).getRightsCode();
				 if(StringUtil.isEmpty(rightCode)) {
					 return "bo/error";
				 }
				//此处为用户直接分配权限，没有角色。
				 List<SystemUser> list = systemUserService.findAll();
				 for(SystemUser systemUser: list) {
					 String privilegeStr = systemUser.getRights();
					 if(StringUtil.isNotEmpty(privilegeStr) && privilegeStr.contains(rightCode)) {
						 String newStr = privilegeStr.replace(rightCode, "");
						 SystemUser user = new SystemUser();
						 user.setRights(newStr);
						 user.setId(systemUser.getId());
						 systemUserService.update(user);
					 }
				 }
				 systemRightsService.delete(id);
			 }
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		} 
		return "redirect:/Bo/rh/rights_list";
	 }
	
	/**
	 * 批量删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/rights_batch", method = RequestMethod.GET)
	public String deleteByIds(HttpServletRequest request) {
		 String arr = request.getParameter("arr");
		 if(StringUtil.isNotEmpty(arr)) {
			 String[] id = arr.split(",");
			 for(int i=1;i<id.length;i++) {
				 systemRightsService.delete(id[i]);
			 }
		 }
		return "redirect:/Bo/rh/rights_list";
	}
	
	/**
	 * 修改权限
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_rights",method={RequestMethod.POST,RequestMethod.GET})
	public String edit(HttpServletRequest request,Model model) {
		try {
			String id=request.getParameter("id");
			if(StringUtil.isNotEmpty(id)){
				SystemRights systemRight = systemRightsService.getById(Long.valueOf(id));
				model.addAttribute("entity", systemRight);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rights/rights_edit";
	}
	

	/**
	 * 权限编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/rights_save",method=RequestMethod.POST)
	public String save(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			SystemRights rights = new SystemRights();
			rights.setRightsName(request.getParameter("rightsName"));
			rights.setRightsCode(request.getParameter("rightsCode"));
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				rights.setId(Long.parseLong(id));
				systemRightsService.update(rights);
			}else {
				systemRightsService.save(rights);
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rh/rights_list";
	}
	
	/**
	 * 权限分配页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/assign")
	public String assign(HttpServletRequest request, Model model) throws Exception{
		try {
			String id = request.getParameter("id");
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			if(StringUtil.isNotEmpty(id)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id",id);
				SystemUser user = systemUserService.findUser(map);
				List<SystemRights> list = systemRightsService.findAll();
				for(SystemRights systemRight: list){
					systemRight.setCreatedWhen(systemRight.getCreatedWhen().substring(0,systemRight.getCreatedWhen().length()-2));
				}
				model.addAttribute("ur",user.getRights());
				model.addAttribute("list",list);
				model.addAttribute("user",user);
				return "bo/rights/rights_allot";
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "error";
		}
		return "redirect:/Bo/rh/user_list";
	}
	
	/**
	 * 权限分配更新
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/assgin_edit")
	public String assignUpdate(HttpServletRequest request, ModelMap model) throws Exception{
		try {
			String str = request.getParameter("arr");
			String id = request.getParameter("id");
			if(id !=null && Integer.parseInt(id) !=0){
				SystemUser systemUser=new SystemUser();
				systemUser.setId(Long.valueOf(id));
				systemUser.setRights(str);
				systemUserService.update(systemUser);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "error";
		}
		return "redirect:/Bo/user_list";
	}
}
