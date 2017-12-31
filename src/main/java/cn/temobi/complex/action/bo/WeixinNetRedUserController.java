package cn.temobi.complex.action.bo;

import java.text.SimpleDateFormat;
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
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.service.SystemRightsService;
import cn.temobi.complex.service.SystemUserService;
import cn.temobi.complex.service.UserOptionService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

/** 
 * 微信 网红用户管理
 * @author hjf
 * 2014-3-5 下午03:26:19
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/weixinUser")
public class WeixinNetRedUserController extends BoBaseController{
	
	@Resource(name="userOptionService")
	private UserOptionService userOptionService;
	
	/**
	 * 列表的展示
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/netRedUserInfoList", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model)
	{
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<NetRedUser> pageResult = new Page<NetRedUser>();
			pageResult = userOptionService.findByPage(page, map);
			List<NetRedUser> list = pageResult.getResult();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			for(NetRedUser netRedUser:list){
				if(netRedUser.getCreateTime() != null){
					netRedUser.setCreateTimeStr(sdf.format(netRedUser.getCreateTime()));
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
		return "bo/netRedUser/netRedUser_list";
	}
	
	/**
	 * 单个删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/netRedUser_del",method=RequestMethod.GET)
	public String delete(HttpServletRequest request,Model model) {
		 try {
			 String id = request.getParameter("id") ;
			 String pageSize = request.getParameter("pageSize");
			 String pageNo = request.getParameter("pageNo");
			 if(StringUtil.isNotEmpty(id)){
				 userOptionService.delete(id);
			 }
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		} 
		return "redirect:/Bo/netRed/netRedUser_list";
	 }
	
	
	/**
	 * 修改权限
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_netRedUser",method={RequestMethod.POST,RequestMethod.GET})
	public String edit(HttpServletRequest request,Model model) {
		try {
			String id=request.getParameter("id");
			if(StringUtil.isNotEmpty(id)){
				NetRedUser netRedUser = userOptionService.getById(Long.valueOf(id));
				if(netRedUser.getCreateTime() != null){
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
					netRedUser.setCreateTimeStr(sdf.format(netRedUser.getCreateTime()));
				}
				model.addAttribute("entity", netRedUser);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/netRedUser/netRedUser_edit";
	}
	

	/**
	 * 审核保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/netRedUser_save",method=RequestMethod.POST)
	public String save(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			NetRedUser netRedUser = userOptionService.getById(Long.valueOf(id));
			netRedUser.setGameRounds(Integer.valueOf(request.getParameter("gameRounds")));
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				netRedUser.setId(Long.parseLong(id));
				userOptionService.update(netRedUser);
			}else {
				userOptionService.save(netRedUser);
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/netRed/weixinNetRedUser_netRedUserInfo";
	}
	
	
	
}
