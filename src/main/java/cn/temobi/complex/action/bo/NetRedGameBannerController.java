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


import cn.temobi.complex.entity.NetRedGameBanner;
import cn.temobi.complex.service.NetRedGameBannerService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/netRedGameBanner")
public class NetRedGameBannerController extends BoBaseController{
	
	@Resource(name="netRedGameBannerService")
	private NetRedGameBannerService netRedGameBannerService;
	
	@RequestMapping(value = "/netRedGameBannerInfoList")
		public String findPage(HttpServletRequest request,
	            ModelMap model) {
	        Map<String, Object> map = new HashMap<String, Object>();
	        String id = request.getParameter("id");
	        String note = request.getParameter("note");
	        String type = request.getParameter("type");
	        String status = request.getParameter("status");
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
	        searchMap.put("note", note);
	        searchMap.put("type", type);
	        searchMap.put("status", status);
	        searchMap.put("limit", page.getPageSize());
	        searchMap.put("offset", page.getOffset());
	        List<NetRedGameBanner> list = new ArrayList<NetRedGameBanner>();
	        try {
	            Page<NetRedGameBanner> result = netRedGameBannerService.findByPage(page, searchMap);
	            list = result.getResult();
	            if (StringUtil.isNotEmpty(list)) {
	            	for(NetRedGameBanner netRedGameBanner :list){
	            		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日 hh时mm分ss秒");
	                    if(netRedGameBanner !=null && netRedGameBanner.getCreateTime() != null ){
	                    	netRedGameBanner.setCreateTimeStr(sdf2.format(netRedGameBanner.getCreateTime()));
	                    }
	            	}
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
			return "bo/netRedUser/netRedGameBanner_list";
	    }
	
	@RequestMapping(value = "/netRedGameBannerEdit")
	public String designerEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
            if (StringUtil.isNotEmpty(id)) {
                NetRedGameBanner netRedGameBanner = netRedGameBannerService.getById(Long.parseLong(id));
				model.addAttribute("entity", netRedGameBanner);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/netRedUser/netRedGameBanner_edit";
	}
	
	@RequestMapping(value="/netRedGameBannerEditSave")
	public String designereditsave(HttpServletRequest request,Model model,NetRedGameBanner netRedGameBanner){
	
		String id = request.getParameter("id");
		if (StringUtil.isNotEmpty(netRedGameBanner.getId()) &&  netRedGameBanner.getId() !=0) {
			NetRedGameBanner gameBanner = netRedGameBannerService.getById(Long.parseLong(id));
            netRedGameBannerService.update(netRedGameBanner);
        } else {
        	netRedGameBanner.setStatus(1);
            netRedGameBannerService.save(netRedGameBanner);
        }
		return "redirect:/Bo/netRedGameBanner/netRedGameBannerInfoList";
	}
	
	@RequestMapping(value="/netRedGameBannerDelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String netRedGameBannerDelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			NetRedGameBanner netRedGameBanner = netRedGameBannerService.getById(Long.parseLong(id));
			netRedGameBanner.setStatus(0);
			netRedGameBannerService.update(netRedGameBanner);
		}
		return "";
	}
	
	
}
