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
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.entity.QqList;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.complex.entity.SysScore;
import cn.temobi.complex.entity.SysType;
import cn.temobi.complex.entity.SysXuanshangSet;
import cn.temobi.complex.service.QqListService;
import cn.temobi.complex.service.SysColumnService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysIndustryInfoService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.complex.service.SysProductTypeInfoService;
import cn.temobi.complex.service.SysScoreService;
import cn.temobi.complex.service.SysTypeService;
import cn.temobi.complex.service.SysXuanshangSetService;
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
@RequestMapping("/setting")
public class SettingController extends BoBaseController {
	
	
	@Resource(name="sysIndustryInfoService")
	public SysIndustryInfoService sysIndustryInfoService;
	
	@Resource(name="sysConfigParamService")
	public SysConfigParamService sysConfigParamService;
	
	@Resource(name="sysProductTypeInfoService")
	public SysProductTypeInfoService sysProductTypeInfoService;
	
	@Resource(name="sysScoreService")
	public SysScoreService sysScoreService;
	
	@Resource(name="sysColumnService")
	public SysColumnService sysColumnService;
	
	@Resource(name="sysTypeService")
	public SysTypeService sysTypeService;
	
	@Resource(name="qqListService")
	public QqListService qqListService;
	
	@Resource(name="sysParameterService")
	public SysParameterService sysParameterService;
	
	@Resource(name="sysXuanshangSetService")
	public SysXuanshangSetService sysXuanshangSetService;
	
	/**
	 * 首页栏目
	 */
	@RequestMapping(value="/columnlist", method = {RequestMethod.GET, RequestMethod.POST })
	public String columnlist(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysColumn> pageResult = sysColumnService.findByPage(page, map);
			List<SysColumn> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(SysColumn sysColumn:list){
					sysColumn.setAddTime(sysColumn.getAddTime().substring(0,sysColumn.getAddTime().length()-2));
				}
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize",pageResult.getPageSize());
				model.addAttribute("totalItems",pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/columnlist";
	}
	
	@RequestMapping(value="/columnedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String columnedit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysColumn sysColumn = sysColumnService.getById(Long.parseLong(id));
				model.addAttribute("entity",sysColumn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/columnedit";
	}
	
	@RequestMapping(value="/columnsave", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String columnsave(HttpServletRequest request,Model model,SysColumn entity) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			sysColumnService.update(entity);
		}else{
			sysColumnService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/columndelete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String columndelete(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			sysColumnService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	/**
	 * 悬赏设置
	 */
	@RequestMapping(value="/sysXuanshangSetList", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysXuanshangSetlist(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			String type = request.getParameter("type");
			String name = request.getParameter("name");
			String status = request.getParameter("status");
			
			if(StringUtil.isNotEmpty(type)){
				map.put("type", type);
				model.addAttribute("type",type);
			}
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysXuanshangSet> pageResult = sysXuanshangSetService.findByPage(page, map);
			List<SysXuanshangSet> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize",pageResult.getPageSize());
				model.addAttribute("totalItems",pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/sysXuanshangSet_list";
	}
	
	@RequestMapping(value="/sysXuanshangSetEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysXuanshangSetedit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysXuanshangSet sysXuanshangSet = sysXuanshangSetService.getById(Long.parseLong(id));
				model.addAttribute("entity",sysXuanshangSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/sysXuanshangSet_edit";
	}
	
	@RequestMapping(value="/sysXuanshangSetSave", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sysXuanshangSetsave(HttpServletRequest request,Model model,SysXuanshangSet entity) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			sysXuanshangSetService.update(entity);
		}else{
			sysXuanshangSetService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/sysXuanshangSetDelete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sysXuanshangSetdelete(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			sysXuanshangSetService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	
	
	/**
	 * 类型
	 */
	@RequestMapping(value="/typelist", method = {RequestMethod.GET, RequestMethod.POST })
	public String typelist(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)){
				map.put("type", type);
				model.addAttribute("type",type);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysType> pageResult = sysTypeService.findByPage(page, map);
			List<SysType> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize",pageResult.getPageSize());
				model.addAttribute("totalItems",pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
				
			}
			
			map.put("type", "0");
			List<SysType> typeList  = sysTypeService.findByMap(map);
			model.addAttribute("typeList",typeList);
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/typelist";
	}
	
	@RequestMapping(value="/typeedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String typeedit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysType sysType = sysTypeService.getById(Long.parseLong(id));
				model.addAttribute("entity",sysType);
			}
			Map<String,String> map = new HashMap<String,String>();
			map.put("type", "0");
			List<SysType> typeList  = sysTypeService.findByMap(map);
			model.addAttribute("typeList",typeList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/typeedit";
	}
	
	@RequestMapping(value="/typesave", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String typesave(HttpServletRequest request,Model model,SysType entity) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			sysTypeService.update(entity);
		}else{
			sysTypeService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/typedelete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String typedelete(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			sysTypeService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	
	/**
	 * 用户分值规则表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/scorelist", method = {RequestMethod.GET, RequestMethod.POST })
	public String scorelist(HttpServletRequest request,Model model) {
		try {
			String flag = request.getParameter("flag");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("flag", flag);
			model.addAttribute("flag", flag);
			List<SysScore> list = sysScoreService.findByMap(map);
			model.addAttribute("list",list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/scorelist";
	}
	
	@RequestMapping(value="/scoreedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String scoreedit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysScore sysScore = sysScoreService.getById(Long.parseLong(id));
				model.addAttribute("entity",sysScore);
			}
			String flag = request.getParameter("flag");
			model.addAttribute("flag", flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/scoreedit";
	}
	
	@RequestMapping(value="/scoresave", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String scoresave(HttpServletRequest request,Model model,SysScore entity) {
		String id = request.getParameter("id");
		String flag = request.getParameter("flag");
		entity.setFlag(flag);
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			sysScoreService.update(entity);
		}else{
			sysScoreService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/scoredelete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String scoredelete(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			sysScoreService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	//作家
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/writterlist", method = {RequestMethod.GET, RequestMethod.POST })
	public String writterlist(HttpServletRequest request,Model model) {
			
		Map<String, String> map = new HashMap<String, String>();
		String parentId = request.getParameter("parentId");
		model.addAttribute("parentId", parentId);
		if(StringUtil.isNotEmpty(parentId))
    	{
    		map.put("parentId", parentId);
    	}else{
    		map.put("parentId", "0");
    	}
		Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
		Page<SysProductTypeInfo> pageResult = new Page<SysProductTypeInfo>();
		pageResult = sysProductTypeInfoService.findByPage(page, map);
		model.addAttribute("list",pageResult.getResult());
		model.addAttribute("pageNo", pageResult.getPageNo());
		model.addAttribute("pageSize", pageResult.getPageSize());
		model.addAttribute("totalItems", pageResult.getTotalItems());
		model.addAttribute("totalPages", pageResult.getTotalPages());
		return "bo/setting/writterlist";
	}
	
	@RequestMapping(value="/writteredit",method={RequestMethod.GET,RequestMethod.POST})
	public String writteredit(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		model.addAttribute("parentId", parentId);
		if(StringUtil.isNotEmpty(id)){
			SysProductTypeInfo sys = sysProductTypeInfoService.getById(Long.parseLong(id));
			model.addAttribute("entity", sys);
		}
		return "bo/setting/writteredit";
	}
	
	@RequestMapping(value="/writtersave",method={RequestMethod.GET,RequestMethod.POST})
	public String writtersave(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		model.addAttribute("parentId", parentId);
		String name = request.getParameter("name");
		String typeColor = request.getParameter("typeColor");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			//修改
			SysProductTypeInfo sys = sysProductTypeInfoService.getById(Long.parseLong(id));
			sys.setName(name);
			sys.setTypeColor(typeColor);
			sysProductTypeInfoService.update(sys);
		}else{
			//2新增
			SysProductTypeInfo sys = new SysProductTypeInfo();
			sys.setName(name);
			sys.setTypeColor(typeColor);
			if(StringUtil.isNotEmpty(parentId)){	
				sys.setLevel(2);
				sys.setParentId(Long.parseLong(parentId));
			}else{
				sys.setLevel(1);
				sys.setParentId(0L);
			}
			sysProductTypeInfoService.save(sys);
			if(StringUtil.isNotEmpty(parentId)){	
				sys.setPath(parentId+","+sys.getId());
				sysProductTypeInfoService.update(sys);
			}
		}
		return "redirect:/Bo/setting/writterlist";
	}
	
	@RequestMapping(value="/delete",method = {RequestMethod.GET, RequestMethod.POST })
	public String delete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		model.addAttribute("parentId", parentId);
		sysProductTypeInfoService.delete(id);
		return "redirect:/Bo/setting/writterlist";
	}
	/**
	 * 行业列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String industryId = request.getParameter("industryId");
			model.addAttribute("industryId", industryId);
			if(StringUtil.isNotEmpty(industryId))
	    	{
	    		map.put("parentIndustryId", industryId);
	    	}else{
	    		map.put("parentIndustryId", "0");
	    	}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysIndustryInfo> pageResult = new Page<SysIndustryInfo>();
			pageResult = sysIndustryInfoService.findByPage(page, map);
			model.addAttribute("list",pageResult.getResult());
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize()); 
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/list";
	}
	
	/**
	 * 渠道列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/changelist", method = {RequestMethod.GET, RequestMethod.POST })
	public String changelist(HttpServletRequest request,Model model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "1");
			List<SysConfigParam> list = sysConfigParamService.findByMap(map);
			model.addAttribute("list",list);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/changelist";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/changeAlledit", method = {RequestMethod.GET, RequestMethod.POST })
	public String changeAlledit(HttpServletRequest request,Model model) {
		try {
			String ids = request.getParameter("ids");
			model.addAttribute("ids", ids);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/changeAlledit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/changeAllsave", method = {RequestMethod.GET, RequestMethod.POST })
	public String changeAllsave(HttpServletRequest request,ModelMap model) {
		String ids = request.getParameter("ids");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		String expand1 = request.getParameter("expand1");
		String expand2 = request.getParameter("expand2");
		String expand3 = request.getParameter("expand3");
		try {
			if(StringUtil.isNotEmpty(ids))
			{
				String arr[] = ids.split(",");
				for(String id:arr)
				{
					SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
					if(StringUtil.isNotEmpty(sysConfigParam))
					{
						if(StringUtil.isNotEmpty(status))
						{
							sysConfigParam.setStatus(status);
						}
						if(StringUtil.isNotEmpty(flag))
						{
							sysConfigParam.setFlag(flag);
						}
						if(StringUtil.isNotEmpty(expand1))
						{
							sysConfigParam.setExpand1(expand1);
						}
						if(StringUtil.isNotEmpty(expand2))
						{
							sysConfigParam.setExpand2(expand2);
						}
						if(StringUtil.isNotEmpty(expand3))
						{
							sysConfigParam.setExpand3(expand3);
						}
						sysConfigParamService.update(sysConfigParam);
					}
				}
				
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/setting/changelist";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/changeedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String changeedit(HttpServletRequest request,Model model) {
		try {
			String key = request.getParameter("key");
			if(StringUtil.isNotEmpty(key))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("enParamName", key);
				List<SysConfigParam> list = sysConfigParamService.findByMap(map);
				if(list.size() > 0)
				{
					model.addAttribute("entity", list.get(0));
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/changeedit";
	}
	
	/**
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/changesave", method = {RequestMethod.GET, RequestMethod.POST })
	public String changesave(HttpServletRequest request,ModelMap model) {
		String key = request.getParameter("key");
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String status = request.getParameter("status");
		String flag = request.getParameter("flag");
		String expand1 = request.getParameter("expand1");
		String expand2 = request.getParameter("expand2");
		String expand3 = request.getParameter("expand3");
		try {
			if(StringUtil.isNotEmpty(key) && StringUtil.isNotEmpty(name))
			{
				if(StringUtil.isNotEmpty(id) && !"0".equals(id))
				{
					SysConfigParam sysConfigParam = sysConfigParamService.getById(Long.parseLong(id));
					sysConfigParam.setEnParamName(key);
					sysConfigParam.setParamValue(name);
					sysConfigParam.setStatus(status);
					sysConfigParam.setFlag(flag);
					sysConfigParam.setExpand1(expand1);
					sysConfigParam.setExpand2(expand2);
					sysConfigParam.setExpand3(expand3);
					sysConfigParamService.update(sysConfigParam);
				}else{
					SysConfigParam sysConfigParam = new SysConfigParam();
					sysConfigParam.setType("1");
					sysConfigParam.setEnParamName(key);
					sysConfigParam.setParamValue(name);
					sysConfigParam.setStatus(status);
					sysConfigParam.setFlag(flag);
					sysConfigParam.setExpand1(expand1);
					sysConfigParam.setExpand2(expand2);
					sysConfigParam.setExpand3(expand3);
					sysConfigParamService.save(sysConfigParam);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/setting/changelist";
	}
	
	/**
	 * 行业编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		try {
			String industryId = request.getParameter("industryId");
			model.addAttribute("industryId", industryId);
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysIndustryInfo sysIndustryInfo = sysIndustryInfoService.getById(Long.parseLong(id));
				model.addAttribute("entity", sysIndustryInfo);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/edit";
	}
	
	/**
	 * 行业新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model) {
		String industryId = request.getParameter("industryId");
		try {
			String id = request.getParameter("id") ;
			String name = request.getParameter("name") ;
			String imageUrl = request.getParameter("imageUrl") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				SysIndustryInfo sysIndustryInfo = sysIndustryInfoService.getById(Long.parseLong(id));
				sysIndustryInfo.setName(name);
				sysIndustryInfo.setImageUrl(imageUrl);
				sysIndustryInfoService.update(sysIndustryInfo);
			}else{
				SysIndustryInfo sysIndustryInfo = new SysIndustryInfo();
				sysIndustryInfo.setName(name);
				sysIndustryInfo.setImageUrl(imageUrl);
				if(StringUtil.isEmpty(industryId) || "0".equals(industryId))
				{
					sysIndustryInfo.setLevel(1);
					sysIndustryInfo.setParentIndustryId(0L);
				}else{
					sysIndustryInfo.setLevel(2);
					sysIndustryInfo.setParentIndustryId(Long.parseLong(industryId));
				}
				sysIndustryInfoService.save(sysIndustryInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/setting/list?industryId="+industryId;
	}
	
	/**
	 * 行业删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		String industryId = request.getParameter("industryId");
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				SysIndustryInfo sysIndustryInfo = sysIndustryInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(sysIndustryInfo)) {
					sysIndustryInfoService.delete(sysIndustryInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/setting/list?industryId="+industryId;
	}
	
	
	
	
	//QQ群管理
	@RequestMapping(value="/qqlist",method={RequestMethod.GET,RequestMethod.POST})
	public String qqlist(HttpServletRequest request,Model model){
		try {
			String qqName = request.getParameter("qqName");
			String qqId = request.getParameter("qqId");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(qqName)){
				map.put("qqName", qqName);
				model.addAttribute("qqName",qqName);
			}
			if(StringUtil.isNotEmpty(qqId)){
				map.put("qqId", qqId);
				model.addAttribute("qqId",qqId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = qqListService.findByPage(page,map);
			List<QqList> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/qqlist_list";
	}
	
	@RequestMapping(value="/qqlistEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String qqlistEdit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			QqList entity = qqListService.getById(Long.parseLong(id));
			model.addAttribute("entity",entity);
		}
		return "bo/setting/qqlist_edit";
	}
	
	@RequestMapping(value="/qqlistSave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String qqlistSave(HttpServletRequest request,Model model,QqList entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			qqListService.update(entity);
		}else{
			qqListService.save(entity);
		}
		return "";
	}
	
	
	/**
	 * 系统参数
	 */
	@RequestMapping(value="/sysParameterlist", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysParameterlist(HttpServletRequest request,Model model) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			String code = request.getParameter("code");
			if(StringUtil.isNotEmpty(code)){
				map.put("code", code);
				model.addAttribute("code",code);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SysParameter> pageResult = sysParameterService.findByPage(page, map);
			List<SysParameter> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize",pageResult.getPageSize());
				model.addAttribute("totalItems",pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
				
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting/sysParameterlist";
	}
	
	@RequestMapping(value="/sysParameteredit", method = {RequestMethod.GET, RequestMethod.POST })
	public String sysParameteredit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				SysParameter sysParameter = sysParameterService.getById(Long.parseLong(id));
				model.addAttribute("entity",sysParameter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/setting/sysParameteredit";
	}
	
	@RequestMapping(value="/sysParametersave", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String sysParametersave(HttpServletRequest request,Model model,SysParameter entity) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			sysParameterService.update(entity);
		}else{
			sysParameterService.save(entity);
		}
		return "";
	}
	
}
