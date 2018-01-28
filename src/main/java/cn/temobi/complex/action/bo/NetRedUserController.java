package cn.temobi.complex.action.bo;

import java.text.SimpleDateFormat;
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

import cn.temobi.complex.dao.UserOptionDao;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.NetRedUser;
import cn.temobi.complex.entity.NetRedUserLabImg;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.entity.WeixinUserInfo;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.UserOptionService;
import cn.temobi.complex.service.WeixinUserInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/netRed")
public class NetRedUserController extends BoBaseController{
	@Resource(name="userOptionService")
	private UserOptionService service;
	@Resource(name="weixinUserInfoService")
	private WeixinUserInfoService weixinUserInfoService;
	@Resource(name = "userOptionService")
	private UserOptionService userOptionService;
	
	@RequestMapping(value = "/netRedUserInfoList")
		public String findPage(HttpServletRequest request,
	            ModelMap model) {
	        Map<String, Object> map = new HashMap<String, Object>();
	        String id = request.getParameter("id");
	        String name = request.getParameter("name");
	        String area = request.getParameter("area");
	        String pageSize = request.getParameter("pageSize");
	        String pageNo = request.getParameter("pageNo");
	        if (StringUtil.isEmpty(pageNo))
	            pageNo = "1";
	        if (StringUtil.isEmpty(pageSize))
	            pageSize = "10";
	        Page page = new Page(Integer.parseInt(pageNo),
	                Integer.parseInt(pageSize));
	        Map<String, Object> searchMap = new HashMap<String, Object>();
	        searchMap.put("id", id);
	        searchMap.put("content", name);
	        searchMap.put("area", area);
	        searchMap.put("limit", page.getPageSize());
	        searchMap.put("offset", page.getOffset());
	        List<NetRedUser> list = new ArrayList<NetRedUser>();
	        try {
	            Page<NetRedUser> result = service.findByPage(page, searchMap);
	            list = result.getResult();
	            if (StringUtil.isNotEmpty(list)) {
	            	model.addAttribute("list", list);
	            	model.addAttribute("pageNo", page.getPageNo());
	            	model.addAttribute("pageSize", page.getPageSize());
	            	model.addAttribute("totalItems", page.getTotalItems());
	            	model.addAttribute("totalPages", page.getTotalPages());
	            }

	        } catch (Exception e) {
				log.error(e.getMessage());
				e.printStackTrace();
				return "bo/error";
			}
			return "bo/netRedUser/netRedUser_list";
	    }
	
	
	@RequestMapping(value = "/weixinUserInfoList")
	public String weixinUserInfoList(HttpServletRequest request,
            ModelMap model) {
        Map<String, Object> map = new HashMap<String, Object>();
        String name = request.getParameter("nickname");
        String pageSize = request.getParameter("pageSize");
        String pageNo = request.getParameter("pageNo");
        if (StringUtil.isEmpty(pageNo))
            pageNo = "1";
        if (StringUtil.isEmpty(pageSize))
            pageSize = "10";
        Page page = new Page(Integer.parseInt(pageNo),
                Integer.parseInt(pageSize));
        Map<String, Object> searchMap = new HashMap<String, Object>();
        searchMap.put("nickname", name);
        searchMap.put("limit", page.getPageSize());
        searchMap.put("offset", page.getOffset());
        List<WeixinUserInfo> list = new ArrayList<WeixinUserInfo>();
        try {
            Page<WeixinUserInfo> result = weixinUserInfoService.findByPage(page, searchMap);
            list = result.getResult();
            if (StringUtil.isNotEmpty(list)) {
            	model.addAttribute("list", list);
            	model.addAttribute("pageNo", page.getPageNo());
            	model.addAttribute("pageSize", page.getPageSize());
            	model.addAttribute("totalItems", page.getTotalItems());
            	model.addAttribute("totalPages", page.getTotalPages());
            }

        } catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/netRedUser/weixinUser_list";
    }
	
	@RequestMapping(value = "/netRedUserEdit")
	public String designerEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
            if (StringUtil.isNotEmpty(id)) {
                NetRedUser netRedUser = service.getById(Long.parseLong(id));
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
                if(netRedUser !=null && netRedUser.getCreateTime() != null ){
                	netRedUser.setCreateTimeStr(sdf2.format(netRedUser.getCreateTime()));
                }
				model.addAttribute("entity", netRedUser);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/netRedUser/netRedUser_edit";
	}
	
	@RequestMapping(value="/netRedUserEditSave")
	public String designereditsave(HttpServletRequest request,Model model,NetRedUser user){
	
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(user.getId())) {
			 NetRedUser netRedUser = service.getById(Long.parseLong(id));
			 netRedUser.setGameRounds(user.getGameRounds());
            service.update(netRedUser);
        } else {
                service.save(user);
        }
		return "redirect:/Bo/netRed/netRedUserInfoList";
	}
	
	
	@RequestMapping(value="/netRedUserDelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String netRedUserDelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			service.delete(Long.parseLong(id));
		}
		return "";
	}
	
	@ResponseBody
	@RequestMapping(value="/netRedUserBatchDelete",method={RequestMethod.GET,RequestMethod.POST})
	public int netRedUserBatchDelete(HttpServletRequest request, ModelMap model) throws Exception{
		try {
			String idStr = request.getParameter("arr");
			if(StringUtil.isNotEmpty(idStr)){
				String [] ids = idStr.split(","); 
				for(int i = 0; i < ids.length ; i++){
					service.delete(Long.parseLong(ids[i]));
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
		return 1;
	}
	
	
	
}
