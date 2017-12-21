package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import cn.temobi.complex.entity.SysAdvert;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.service.PmScoreLogService;
import cn.temobi.complex.service.SysAdvertService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.WriteHTMLUtil;

@Controller
@RequestMapping("/advert")
public class SysAdvertController extends BoBaseController{

	private String html = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width,height=device-height,initial-scale=1.0,maximum-scale=1.0,user-scalable=0;' /></head><body>";
	private String html2 = "</body></html>";
	
	@Resource(name="sysAdvertService")
	private SysAdvertService sysAdvertService;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request,Model model){
		try {
			Map<String,String> map = new HashMap<String,String>();
			String title = request.getParameter("title");
			String startDate = request.getParameter("startDate");
			String endtDate = request.getParameter("endtDate");
			if(StringUtil.isNotEmpty(title)){
				model.addAttribute("title",title);
				map.put("title", title);
			}
			if(StringUtil.isNotEmpty(startDate)){
				model.addAttribute("startDate",startDate);
				map.put("startDate", startDate);
			}
			if(StringUtil.isNotEmpty(endtDate)){
				model.addAttribute("endtDate",endtDate);
				map.put("endtDate", endtDate);
			}
			Page page = getPage(request.getParameter("pageNo"),request.getParameter("pageSize"));
			
			Page pageResult = sysAdvertService.findByPage(page, map);
			List<SysAdvert> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(SysAdvert sysAdvert:list){
					sysAdvert.setAddTime(sysAdvert.getAddTime().substring(0, sysAdvert.getAddTime().length()-2));
				}
				model.addAttribute("list", list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize",pageResult.getPageSize());
				model.addAttribute("totalItems",pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return "bo/advert/list";
	}
	
	@RequestMapping(value="/edit",method={RequestMethod.GET,RequestMethod.POST})
	public String edit(HttpServletRequest request,Model model){
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysAdvert sysAdvert = sysAdvertService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysAdvert);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/advert/edit";
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String save(HttpServletRequest request,SysAdvert entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			sysAdvertService.update(entity);
		}else{
			entity.setStatus("0");
			sysAdvertService.save(entity);
		}
		if("1".equals(entity.getType()))
		{
			String fileName = "hdIndex"+entity.getId()+".html";
			WriteHTMLUtil.writeHtm(fileName, html+entity.getDepict()+html2, "UTF-8");
		}
		return "";
	}
	
	@RequestMapping(value="delete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String delete(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				sysAdvertService.delete(Long.parseLong(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value="/updateStatus", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String updateStatus(HttpServletRequest request,Model model) {
		String id = request.getParameter("id") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(status)) {
				SysAdvert entity = sysAdvertService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(entity)) {
					entity.setStatus(status);
					sysAdvertService.update(entity);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
}
