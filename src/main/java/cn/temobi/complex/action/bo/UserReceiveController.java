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

import cn.temobi.complex.entity.CmUserReceive;
import cn.temobi.complex.service.CmUserReceiveService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/userReceive")
public class UserReceiveController extends BoBaseController{
	
	@Resource(name="cmUserReceiveService")
	private CmUserReceiveService cmUserReceiveService;
	
	
	//用户收货信息
	@RequestMapping(value="/user_receive_list",method={RequestMethod.GET,RequestMethod.POST})
	public String cmUserReceive(HttpServletRequest request,Model model){
		try {
			String userId = request.getParameter("userId");
			String nickName = request.getParameter("nickName");
			String name = request.getParameter("name");
			String rePhone = request.getParameter("rePhone");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(userId)){
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(rePhone)){
				map.put("rePhone", rePhone);
				model.addAttribute("rePhone",rePhone);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmUserReceiveService.findByPage(page,map);
			List<CmUserReceive> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/user/user_receive_list";
	}
	
	@RequestMapping(value="/userReceiveEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String userReceiveEdit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CmUserReceive entity = cmUserReceiveService.getById(Long.parseLong(id));
			model.addAttribute("entity",entity);
		}
		return "bo/user/user_receive_edit";
	}
	
	@RequestMapping(value="/userReceiveSave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String userReceiveSave(HttpServletRequest request,Model model,CmUserReceive entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmUserReceiveService.update(entity);
		}else{
			cmUserReceiveService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/userReceiveDelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String userReceiveDelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmUserReceiveService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	
	
	
	
	
	
	
}
