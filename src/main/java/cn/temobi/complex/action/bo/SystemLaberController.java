package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.LaberConfigure;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.SysBackupLabels;
import cn.temobi.complex.entity.SysHotLabels;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.service.LaberConfigureService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.SysBackupLabelsService;
import cn.temobi.complex.service.SysHotLabelsService;
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
@RequestMapping("/laber")
public class SystemLaberController extends BoBaseController {
	
	
	@Resource(name="laberService")
	public LaberService laberService;
	
	@Resource(name="sysBackupLabelsService")
	public SysBackupLabelsService sysBackupLabelsService;
	
	@Resource(name="laberConfigureService")
	public LaberConfigureService laberConfigureService;
	
	@Resource(name="systemSetingService")
	public SystemSetingService systemSetingService;

	@Resource(name="sysHotLabelsService")
	public SysHotLabelsService sysHotLabelsService;
	
	@Resource(name="pmProductInfoService")
	public PmProductInfoService pmProductInfoService;
	
	
	/**
	 * 标签列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type))
			{
				map.put("type", type);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Laber> pageResult = new Page<Laber>();
			pageResult = laberService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/list";
	}
	
	/**
	 * 标签编辑
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
				Laber laber = laberService.getById(Long.parseLong(id));
				model.addAttribute("entity", laber);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/edit";
	}
	
	/**
	 * 验证标签唯一
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/check", method = {RequestMethod.GET, RequestMethod.POST })
	public void check(HttpServletRequest request,HttpServletResponse response) {
		try {
			String id = request.getParameter("id") ;
			String name = request.getParameter("name");
			Map<String,String> map = new HashMap<String, String>();
			map.put("laberName", name);
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				map.put("laberId", id);
			}
			List<Laber> list = laberService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				response.getWriter().print(false);
			}else{
				response.getWriter().print(true);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	
	/**
	 * 标签新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,Laber laber) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				laberService.update(laber);
			}else{
				laber.setStatus("1");
				laberService.save(laber);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/list";
	}
	
	/**
	 * 标签删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Laber laber = laberService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(laber)) {
					laberService.delete(laber);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/list";
	}
	
	
	/**
	 * 标签列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/configure_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String configureList(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<LaberConfigure> pageResult = new Page<LaberConfigure>();
			pageResult = laberConfigureService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
			SystemSeting systemSeting = systemSetingService.getById(null);
			String hotLaber = systemSeting.getHotLaber();
			model.addAttribute("hotLaber", hotLaber);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/configure_list";
	}
	
	/**
	 * 标签编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/configure_edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String configureEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				LaberConfigure laberConfigure = laberConfigureService.getById(Long.parseLong(id));
				model.addAttribute("entity", laberConfigure);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/configure_edit";
	}
	
	/**
	 * 标签新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/configure_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String configureSave(HttpServletRequest request,ModelMap model,LaberConfigure laberConfigure) {
		try {
			String id = request.getParameter("id") ;
			Map<String,String> searchmap = new HashMap<String, String>();
			searchmap.put("laberName", laberConfigure.getLaberTotal());
			List<Laber> list = laberService.findByMap(searchmap);
			if(list != null && list.size() > 0)
			{
			}else{
				Laber laber = new Laber();
				laber.setName(laberConfigure.getLaberTotal());
				laber.setStatus("1");
				laber.setType("4");
				laberService.save(laber);
			}
			String laberSmall = laberConfigure.getLaberSmall();
			String arr[] = laberSmall.split(",");
			if(arr != null && arr.length > 0)
			{
				for(String temp:arr)
				{
					searchmap.put("laberName", temp);
					list = laberService.findByMap(searchmap);
					if(list != null && list.size() > 0)
					{
					}else{
						Laber laber = new Laber();
						laber.setName(temp);
						laber.setStatus("1");
						laber.setType("4");
						laberService.save(laber);
					}
				}
			}
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				laberConfigureService.update(laberConfigure);
			}else{
				laberConfigureService.save(laberConfigure);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/configure_list";
	}
	
	/**
	 * 标签删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/configure_del", method = {RequestMethod.GET, RequestMethod.POST })
	public String configureDel(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				LaberConfigure laberConfigure = laberConfigureService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(laberConfigure)) {
					laberConfigureService.delete(laberConfigure);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/configure_list";
	}
	
	
	/**
	 * 标签编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/hotedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String hotedit(HttpServletRequest request,Model model) {
		try {
			SystemSeting systemSeting = systemSetingService.getById(null);
			String hotLaber = systemSeting.getHotLaber();
			model.addAttribute("hotLaber", hotLaber);
			List<Laber> laberList = laberService.findAll();
			model.addAttribute("laberList", laberList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/hot_edit";
	}
	
	
	/**
	 * 标签新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/hotsave", method = {RequestMethod.GET, RequestMethod.POST })
	public String hotsave(HttpServletRequest request,ModelMap model) {
		try {
			String hotLaber = request.getParameter("hotLaber");
			Map<String,String> searchmap = new HashMap<String, String>();
			searchmap.put("laberName", hotLaber);
			List<Laber> list = laberService.findByMap(searchmap);
			Laber laber = null;
			if(list != null && list.size() > 0)
			{
				laber = list.get(0);
			}else{
				laber = new Laber();
				laber.setName(hotLaber);
				laber.setStatus("1");
				laber.setType("4");
				laberService.save(laber);
			}
			SystemSeting systemSeting = systemSetingService.getById(null);
			systemSeting.setHotLaber(hotLaber);
			systemSetingService.update(systemSeting);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/configure_list";
	}
	
	/**
	 * 备用标签列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/backuplist", method = {RequestMethod.GET, RequestMethod.POST })
	public String backuplist(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysBackupLabels> pageResult = new Page<SysBackupLabels>();
			pageResult = sysBackupLabelsService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/backuplist";
	}
	
	/**
	 * 备用标签删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delbackup", method = {RequestMethod.GET, RequestMethod.POST })
	public String delbackup(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				SysBackupLabels sysBackupLabels = sysBackupLabelsService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysBackupLabels)) {
					sysBackupLabelsService.delete(sysBackupLabels);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/backuplist";
	}
	
	/**
	 * 热门标签列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/userhotlist", method = {RequestMethod.GET, RequestMethod.POST })
	public String userhotlist(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysHotLabels> pageResult = new Page<SysHotLabels>();
			pageResult = sysHotLabelsService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/userhot_list";
	}
	
	/**
	 * 标签编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/userhotedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String userhotedit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysHotLabels sysHotLabels = sysHotLabelsService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysHotLabels);
				Laber laber = laberService.getById(sysHotLabels.getLabelId());
				model.addAttribute("laber", laber);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/laber/userhot_edit";
	}
	
	/**
	 * 验证  热门标签
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/hotCheck", method = {RequestMethod.GET, RequestMethod.POST })
	public void hotCheck(HttpServletRequest request,HttpServletResponse response) {
		try {
			String id = request.getParameter("id") ;
			String laberName = request.getParameter("laberName");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("laberName", laberName);
			List<Laber> list = laberService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				map.put("laberId", list.get(0).getId());
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					map.put("hotId", id);
				}
				List<SysHotLabels> hotslist = sysHotLabelsService.findByMap(map);
				if(hotslist != null && hotslist.size() > 0)
				{
					response.getWriter().print(false);
				}else{
					response.getWriter().print(true);
				}
			}else{
				response.getWriter().print(true);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 标签新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/userhotsave", method = {RequestMethod.GET, RequestMethod.POST })
	public String userhotsave(HttpServletRequest request,ModelMap model,SysHotLabels sysHotLabels) {
		try {
			String id = request.getParameter("id") ;
			String laberName = request.getParameter("laberName") ;
			if(StringUtil.isEmpty(laberName))
			{
				return "bo/error";
			}
			Map<String,String> searchmap = new HashMap<String, String>();
			searchmap.put("laberName", laberName);
			List<Laber> list = laberService.findByMap(searchmap);
			Laber laber = null;
			if(list != null && list.size() > 0)
			{
				laber = list.get(0);
			}else{
				laber = new Laber();
				laber.setName(laberName);
				laber.setStatus("1");
				laber.setType("4");
				laberService.save(laber);
			}
			sysHotLabels.setLabelId(laber.getId());
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Map<String,String> map = new HashMap<String, String>();
				map.put("audit", "1");
				map.put("isPublic", "1");
				map.put("laber", laberName);
				int num = pmProductInfoService.getCount(map).intValue();
				sysHotLabels.setProductCount(num);
				sysHotLabelsService.update(sysHotLabels);
			}else{
				Map<String,String> map = new HashMap<String, String>();
				map.put("audit", "1");
				map.put("isPublic", "1");
				map.put("laber", laberName);
				int num = pmProductInfoService.getCount(map).intValue();
				sysHotLabels.setProductCount(num);
				sysHotLabelsService.save(sysHotLabels);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/userhotlist";
	}
	
	/**
	 * 标签删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/userhotdel", method = {RequestMethod.GET, RequestMethod.POST })
	public String userhotdel(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				SysHotLabels sysHotLabels = sysHotLabelsService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysHotLabels)) {
					sysHotLabelsService.delete(sysHotLabels);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/laber/userhotlist";
	}
}
