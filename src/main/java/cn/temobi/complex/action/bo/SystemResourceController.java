package cn.temobi.complex.action.bo;

import java.io.File;
import java.util.ArrayList;
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

import cn.temobi.complex.dto.MaterialResourceDto;
import cn.temobi.complex.dto.MaterialUseDto;
import cn.temobi.complex.entity.Banner;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.Material;
import cn.temobi.complex.entity.MaterialCount;
import cn.temobi.complex.entity.MaterialResource;
import cn.temobi.complex.entity.MaterialUse;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.CountService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.MaterialResourceService;
import cn.temobi.complex.service.MaterialService;
import cn.temobi.complex.service.MaterialUseService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.CnToSpell;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.FileCtrlUtil;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.ZipUtil;

/**
 * 资源管理
 * @author hjf
 * 2014 三月 18 09:25:57
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/rs")
public class SystemResourceController extends BoBaseController {
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="bannerService")
	public BannerService bannerService;
	
	
	@Resource(name="materialService")
	public MaterialService materialService;
	
	@Resource(name="materialResourceService")
	public MaterialResourceService materialResourceService;
	
	@Resource(name="laberService")
	public LaberService laberService;

	@Resource(name="materialUseService")
	private MaterialUseService materialUseService;
	
	/**
	 * 素材做为表情列表
	 */
	@RequestMapping(value="/material_use_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String materialUseList(HttpServletRequest request,Model model) {
		try {
			String materialId = request.getParameter("materialId");
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(materialId)) {
				map.put("materialId", materialId);
				model.addAttribute("materialId", materialId);
			}
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<MaterialUseDto> pageResult = new Page<MaterialUseDto>();
			pageResult = materialUseService.findByPageDto(page, map);
			List<MaterialUseDto> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(MaterialUseDto materialUseDto:list){
					if(StringUtil.isNotEmpty(materialUseDto.getAddTime())){
						materialUseDto.setAddTime(materialUseDto.getAddTime().substring(0,materialUseDto.getAddTime().length()-2));
					}
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
		return "bo/rs/material_use_list";
	}
	
	@RequestMapping(value="/material_use_edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String materialUseEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			String materialId = request.getParameter("materialId");
			if(StringUtil.isNotEmpty(materialId)&&!"0".equals(materialId)) {
				model.addAttribute("materialId", materialId);
			}
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				MaterialUse materialUse = materialUseService.getById(Long.parseLong(id));
				model.addAttribute("entity", materialUse);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/material_use_edit";
	}
	
	@RequestMapping(value="/material_use_save", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String materialUseSave(HttpServletRequest request,ModelMap model,MaterialUse materialUse) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				MaterialUse materUse = materialUseService.getById(Long.parseLong(id));
				materUse.setUseSeq(materialUse.getUseSeq());
				materialUseService.update(materUse);
			}else{
				materialUse.setType("1");
				materialUseService.save(materialUse);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	@RequestMapping(value="/material_use_delete", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String materialUseDelete(HttpServletRequest request) {
		String id = request.getParameter("id") ;
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) ) {
				materialUseService.delete(id);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	@RequestMapping(value="/check", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody int check(HttpServletRequest request,ModelMap model) {
		String materialId = request.getParameter("materialId") ;
		Map<String, String> map = new HashMap<String, String>();
		if(StringUtil.isNotEmpty(materialId)) {
			map.put("materialId", materialId);
			model.addAttribute("materialId", materialId);
		}
		Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
		Page<MaterialUseDto> pageResult = new Page<MaterialUseDto>();
		pageResult = materialUseService.findByPageDto(page, map);
		List<MaterialUseDto> list = pageResult.getResult();
		if(StringUtil.isNotEmpty(list)){
			return 1;
		}
		return 0;
	}
	/**
	 * 资源列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pk_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String pk_list(HttpServletRequest request,Model model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			String name = request.getParameter("name");//名称
			String status = request.getParameter("status");
			String type = request.getParameter("type");
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			if(StringUtil.isNotEmpty(name)) {
				map.put("name", name);
				model.addAttribute("name", name);
			}
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status", status);
			}
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type);
				model.addAttribute("type", type);
			}
			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried", orderFried);
				model.addAttribute("orderFried",orderFried);
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Material> pageResult = new Page<Material>();
			pageResult = materialService.findByPage(page, map);
			List<Material> list = pageResult.getResult();
			for(Material material:list){
				if(StringUtil.isNotEmpty(material.getCreatedWhen())){
					material.setCreatedWhen(material.getCreatedWhen().substring(0,material.getCreatedWhen().length()-2));
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
		return "bo/rs/pk_list";
	}
	
	/**
	 * 表情包编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_pk", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit_pk(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Material material = materialService.getById(Long.parseLong(id));
				model.addAttribute("entity", material);
			}
			String status = request.getParameter("status");//表情包状态
			model.addAttribute("status", status);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/pk_edit";
	}
	
	/**
	 * 表情包新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/pk_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save_pk(HttpServletRequest request,ModelMap model,Material material) {
		try {
			String id = request.getParameter("id") ;
			String status = request.getParameter("mStatus");//表情包状态
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				material.setStatus(Integer.parseInt(status));
				materialService.update(material);
			}else{
				if(StringUtil.isEmpty(material.getBusinessType()))
				{
					material.setBusinessType(null);
				}
				save(material, material.getDownUrl());
			}
			model.addAttribute("status", status);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rs/pk_list";
	}
	
	/**
	 * 表情包删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/pk_del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del_pk(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			String status =request.getParameter("status");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				Material material = materialService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(material)) {
					MaterialResource materialResource = new MaterialResource();
					materialResource.setMaterialId(material.getId());
					materialResourceService.delete(materialResource);
					File file = new File(FileUtil.getPathTomcat()+material.getZipPath());
					FileCtrlUtil.delRecursion(file);
					if(StringUtil.isNotEmpty(material.getDownUrl())) {
						File downFile = new File(FileUtil.getPathTomcat() + material.getDownUrl());
						FileCtrlUtil.delDir(downFile);
					}
					materialService.delete(material);
				}
			}
			model.addAttribute("status", status);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rs/pk_list";
	}
	
	public static void main(String[] args) {
		String path = "D:/Program Files (x86)/apache-tomcat-7.0.57/webapps/diys/rs/emoji/fsh20150422113521381/";
		System.out.println(path);
		File file = new File(path);
		FileCtrlUtil.delDir(file);
	}
	
	
	
	/**
	 * 表情包状态修改
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/us", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String updateStatus(HttpServletRequest request,Model model) {
		String packetIdStr = request.getParameter("packetIdStr") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(packetIdStr) && StringUtil.isNotEmpty(status)) {
				String[] packetIdArr = packetIdStr.split(",");
				for(int i=0; i<packetIdArr.length; i++) {
					Material material = materialService.getById(Long.parseLong(packetIdArr[i]));
					if(StringUtil.isNotEmpty(material)) {
						material.setStatus(Integer.parseInt(status));
						materialService.update(material);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
	
	
	/**
	 * 表情列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/exp_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String exp_list(HttpServletRequest request,Model model) {
		try {
			String pkId = request.getParameter("pkId");//表情包ID
			String status = request.getParameter("status");//表情包ID
			model.addAttribute("status", status);
			if(StringUtil.isNotEmpty(pkId)) {
				model.addAttribute("pkId", pkId);//保证是一个包下面的表情
				Map<String, String> map = new HashMap<String, String>();
				map.put("materialId",pkId);
				Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
				Page<MaterialResource> pageResult = new Page<MaterialResource>();
				pageResult = materialResourceService.findByPage(page, map);
				List<MaterialResource> list = pageResult.getResult();
				model.addAttribute("list",list);
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/exp_list";
	}
	
	/**
	 * 表情编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_exp", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit_exp(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			String pkId = request.getParameter("pkId");
			String status = request.getParameter("status");
			model.addAttribute("status", status);
			List<Laber> laberList = laberService.findAll();
			model.addAttribute("laberList", laberList);
			if(StringUtil.isNotEmpty(pkId) &&!"0".equals(pkId)) {
				model.addAttribute("pkId", pkId);
				if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
					MaterialResource materialResource = materialResourceService.getById(Long.valueOf(id));
					model.addAttribute("entity", materialResource);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/exp_edit";
	}
	
	/**
	 * 表情新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/exp_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String exp_save(HttpServletRequest request,Model model,MaterialResource materialResource) {
		String pkId = request.getParameter("pkId");
		String id = request.getParameter("id") ;
		String status = request.getParameter("status") ;
		model.addAttribute("pkId", pkId);
		model.addAttribute("id", id);
		model.addAttribute("status", status);
		try {
			materialResource.setMaterialId(Long.parseLong(pkId));
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				if(StringUtil.isNotEmpty(materialResource.getImageUrl()) &&
						materialResource.getImageUrl().indexOf("upload") != -1) {//复制图片，删除图片，修改路径
					MaterialResource oldMl = materialResourceService.getById(materialResource.getId());
					FileCtrlUtil.copyImage(FileUtil.getPathTomcat() + materialResource.getImageUrl(),FileUtil.getPathTomcat()+oldMl.getImageUrl());
					FileCtrlUtil.delFile(FileUtil.getPathTomcat() + materialResource.getImageUrl());
					materialResource.setImageUrl(oldMl.getImageUrl());
				}
				materialResourceService.update(materialResource);
			}else {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("materialId", materialResource.getMaterialId());
				int imageName = materialResourceService.maxId(map).intValue() + 1;//获取图片名称 最大值+1
				Material material = materialService.getById(materialResource.getMaterialId());
				if(StringUtil.isNotEmpty(materialResource.getImageUrl())) {//判断保存路径
					FileCtrlUtil.copyImage(FileUtil.getPathTomcat() + materialResource.getImageUrl(),FileUtil.getPathTomcat() + material.getZipPath() + imageName+".png");
					FileCtrlUtil.delFile(FileUtil.getPathTomcat() + materialResource.getImageUrl());
					materialResource.setImageUrl(material.getZipPath()+imageName+".png");
				}
				materialResource.setImageName(imageName+"");
				if(StringUtil.isEmpty(materialResource.getType()))
				{
					materialResource.setType(null);
				}
				materialResourceService.save(materialResource);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rs/exp_list";
	}
	
	/**
	 * 表情删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/exp_del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del_exp(HttpServletRequest request, Model model) {
		String pkId = request.getParameter("pkId");
		String id = request.getParameter("id") ;
		String status = request.getParameter("status") ;
		model.addAttribute("pkId", pkId);
		model.addAttribute("id", id);
		model.addAttribute("status", status);
		try {
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) && StringUtil.isNotEmpty(pkId)) {
				MaterialResource materialResource = materialResourceService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(materialResource))
				{
					FileCtrlUtil.delFile(FileUtil.getPathTomcat() + materialResource.getImageUrl());
					materialResourceService.delete(materialResource);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rs/exp_list";
	}
	

	//保存单个表情信息
	public void save(Material material,String packetPath) throws Exception{
		String homePath = FileUtil.getPathTomcat();
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path");
		String resourceName = CnToSpell.getPinYinHeadChar(material.getName())+DateUtils.getCurrentDateTime("yyyyMMddHHmmssS");
		String endPath = homePath + resourcePath + "material_zip/";
		File end = new File(endPath);
		if(!end.exists()){
			end.mkdirs();
		}
		String path = resourcePath + "emoji/"+resourceName+"/";
		String downUrl = endPath + resourceName+".zip";
		FileCtrlUtil.copyImage(homePath + packetPath,downUrl);
		FileCtrlUtil.delFile(homePath + packetPath);
		Map<String,String> map = new HashMap<String, String>();
		Map<String, Integer> relation = ZipUtil.deCompressFile(downUrl, homePath+path,map);
		material.setZipPath(path);
		material.setDownUrl(resourcePath + "material_zip/"+resourceName+".zip");
		materialService.save(material);
		for(int i=1; i<=relation.size(); i++) {
			MaterialResource materialResource = new MaterialResource();
			materialResource.setMaterialId(material.getId());
			materialResource.setImageName(i+"");
			materialResource.setName(ZipUtil.getKey(relation, i));
			materialResource.setImageUrl(path+i+"."+map.get("suffix"));
			materialResourceService.save(materialResource);
		}
	}
	
	
	/**
	 * 表情打包
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/compress", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String compress(HttpServletRequest request, Model model) {
		String packetIdStr = request.getParameter("packetIdStr") ;
		try {
			if(StringUtil.isNotEmpty(packetIdStr)) {
				String[] packetIdArr = packetIdStr.split(",");
				boolean flag = compress(packetIdArr);
				if(flag == false) return "error";
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "error";
		}
		return "";
	}
	
	public boolean compress(String[] packetIdArr) throws Exception {
		String homePath = FileUtil.getPathTomcat();
		String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path"); 
		for(String packetIdStr:packetIdArr) {
			Long packetId = Long.valueOf(packetIdStr);
			// 打包
			Material material = materialService.getById(packetId);
			String decompressRelPath = material.getZipPath();
			String resourceName = CnToSpell.getPinYinHeadChar(material.getName())+DateUtils.getCurrentDateTime("yyyyMMddHHmmssS");
			String zipDir = homePath + resourcePath + "material_zip/";			//压缩后文件目录路径
			String zipPath = zipDir +  resourceName + ".zip";//压缩后文件路径
			String downUrl = resourcePath + "material_zip/" +  resourceName + ".zip";
			String srcPath = homePath + decompressRelPath;//源文件全目录路径
			String zipSrcRoot = homePath + resourcePath + material.getName()+"/";//压缩前文件存放根目录路径
			FileCtrlUtil.createDir(zipSrcRoot);//创建压缩前文件存放根目录
			FileCtrlUtil.copyDirectiory(srcPath, zipSrcRoot);
			File srcFile = new File(zipSrcRoot);
			boolean isCompress = ZipUtil.compress(srcFile, zipDir,zipPath);
			if(isCompress) {
				material.setDownUrl(downUrl);
				materialService.update(material);
			} else {
				return false;
			}
		}
		return true;
	}
	
	private String changeTopicNameToEn(String topicName) {
		String tmpTopicName = topicName;
		int underline = topicName.indexOf("_") == -1? topicName.length():topicName.indexOf("_");
		if(underline != -1)
			tmpTopicName = topicName.substring(0, underline);
		String zhcnFirstLetter = CnToSpell.getPinYinHeadChar(tmpTopicName) + System.currentTimeMillis();
		return zhcnFirstLetter;
	}
	
	
	/**
	 * 所有表情列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/all_exp_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String all_exp_list(HttpServletRequest request,Model model) {
		try {
				Map<String, String> map = new HashMap<String, String>();
				String type =  request.getParameter("type");
				map.put("type", type);
				model.addAttribute("type", type);
				Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
				Page<MaterialResource> pageResult = new Page<MaterialResource>();
				pageResult = materialResourceService.findByPageTo(page, map);
				List<MaterialResource> list = pageResult.getResult();
		    	List<MaterialResourceDto> dtoList = new ArrayList<MaterialResourceDto>();
		    	if(list!= null && list.size() > 0) {
		    		MaterialResourceDto materialResourceDto;
		    		for(MaterialResource mr:list)
		    		{
		    			materialResourceDto = new MaterialResourceDto();
		    			materialResourceDto.setId(mr.getId());
		    			materialResourceDto.setName(mr.getName());
		    			materialResourceDto.setMaterialId(mr.getMaterialId());
		    			materialResourceDto.setImageUrl(host+mr.getImageUrl());
		    			materialResourceDto.setLaberName(mr.getLaberName());
		    			Material material = materialService.getById(mr.getMaterialId());
		    			if(StringUtil.isNotEmpty(material))
		    			{
		    				materialResourceDto.setMaterialName(material.getName());
		    				if("1".equals(material.getType()+""))
		    				{
		    					materialResourceDto.setType("贴图");
		    				}else if("2".equals(material.getType()+"")){
		    					materialResourceDto.setType("颜文字");
		    				}else if("3".equals(material.getType()+"")){
		    					materialResourceDto.setType("表情");
		    				}
		    			}
		    			dtoList.add(materialResourceDto);
		    		}
				}
				model.addAttribute("list",dtoList);
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/all_exp_list";
	}
	
	
	/**
	 * 批量打标签
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/all_edit_exp", method = {RequestMethod.GET, RequestMethod.POST })
	public String all_edit_exp(HttpServletRequest request,Model model) {
		try {
			String pageNo = request.getParameter("pageNo");
			model.addAttribute("pageNo", pageNo);
			String ids = request.getParameter("ids");
			model.addAttribute("ids", ids);
			List<Laber> laberList = laberService.findAll();
			model.addAttribute("laberList", laberList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/rs/all_exp_edit";
	}
	
	
	/**
	 * 批量打标签
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/all_exp_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String all_exp_save(HttpServletRequest request,Model model,String laberName) {
		try {
			String ids = request.getParameter("ids");
			String pageNo = request.getParameter("pageNo");
			model.addAttribute("pageNo", pageNo);
			if(StringUtil.isNotEmpty(ids) && StringUtil.isNotEmpty(laberName))
			{
				String StringArr[] = laberName.split(",");
				if(StringArr != null && StringArr.length > 0)
				{
					Map<String,String> searchmap = new HashMap<String, String>();
					for(String temp:StringArr)
					{
						searchmap.put("laberName", temp);
						List<Laber> list = laberService.findByMap(searchmap);
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
				String[] arr = ids.split(",");
				for(int i=0;i<arr.length;i++)
				{
					MaterialResource materialResource = materialResourceService.getById(Long.parseLong(arr[i]));
					materialResource.setLaberName(laberName);
					materialResourceService.update(materialResource);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/rs/all_exp_list";
	}
	
	
	/**
	 * 打标签
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/makeLaber", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String makeLaber(HttpServletRequest request,Model model,String laberName) {
		try {
			String rsId = request.getParameter("rsId");
			String rsDesc = request.getParameter("rsDesc");
			if(StringUtil.isNotEmpty(rsId) && StringUtil.isNotEmpty(rsDesc))
			{
				String[] arr = rsDesc.split(",");
				if(arr != null && arr.length > 0)
				{
					for(int i=0;i<arr.length;i++)
					{
						Map<String,String> map = new HashMap<String, String>();
						map.put("name", arr[i]);
						List<Laber> list = laberService.findByMap(map);
						if(list == null || list.size() <= 0)
						{
							Laber laber = new Laber();
							laber.setName(arr[i]);
							laber.setType("2");
							laber.setStatus("1");
							laberService.save(laber);
						}
					}
					MaterialResource materialResource = materialResourceService.getById(Long.parseLong(rsId));
					materialResource.setLaberName(rsDesc);
					materialResourceService.update(materialResource);
				}
				
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
}
