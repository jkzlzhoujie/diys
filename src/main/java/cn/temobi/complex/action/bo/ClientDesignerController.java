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

import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CmDesignerHonor;
import cn.temobi.complex.entity.CmDesignerInfo;
import cn.temobi.complex.entity.CmDesignerInfoPic;
import cn.temobi.complex.entity.CmDesignerProductFormat;
import cn.temobi.complex.entity.CmDesignerProductInfo;
import cn.temobi.complex.entity.CmDesignerSaleComment;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.service.CmDesignerHonorService;
import cn.temobi.complex.service.CmDesignerInfoPicService;
import cn.temobi.complex.service.CmDesignerProductFormatService;
import cn.temobi.complex.service.CmDesignerProductInfoService;
import cn.temobi.complex.service.CmDesignerSaleCommentService;
import cn.temobi.complex.service.CmDesignerService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/designer")
public class ClientDesignerController extends BoBaseController{

	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="cmDesignerService")
	private CmDesignerService cmDesignerService;
	
	@Resource(name="cmDesignerInfoPicService")
	private CmDesignerInfoPicService cmDesignerInfoPicService;
	
	@Resource(name="cmDesignerProductInfoService")
	private CmDesignerProductInfoService cmDesignerProductInfoService;
	
	@Resource(name="cmDesignerProductFormatService")
	private CmDesignerProductFormatService cmDesignerProductFormatService;
	
	@Resource(name="cmDesignerHonorService")
	private CmDesignerHonorService cmDesignerHonorService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
   
	@Resource(name="cmDesignerSaleCommentService")
	private CmDesignerSaleCommentService cmDesignerSaleCommentService;
	
	
	
    /**
	 * 设计师列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerList")
	public String designerList(HttpServletRequest request, ModelMap model) {
		
		try {
			String userId = request.getParameter("userId");
			String realName = request.getParameter("realName");
			String userSex = request.getParameter("userSex");
			String status = request.getParameter("status");
			
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(userId)){
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			if(StringUtil.isNotEmpty(realName)){
				map.put("realName", realName);
				model.addAttribute("realName",realName);
			}
			if(StringUtil.isNotEmpty(userSex)){
				map.put("userSex", userSex);
				model.addAttribute("userSex",userSex);
			}
			if(StringUtil.isNotEmpty(status)){
				map.put("status", status);
				model.addAttribute("status",status);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmDesignerService.findByPage(page,map);
			List<CirclePost> list = pageResult.getResult();
			
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/designer/designer_list";
	}
	
	/**
	 * 设计师认证修改页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerEdit")
	public String designerEdit(HttpServletRequest request, ModelMap model) {
		
		
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)){
				CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmDesignerInfo);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/designer/designer_edit";
	}
	
	/**
	 * 设计师认证保存修改
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/designerEditSave",method={RequestMethod.GET,RequestMethod.POST})
	public String designereditsave(HttpServletRequest request,Model model,CmDesignerInfo cmDesignerInfo){
	
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmDesignerInfo.setUpdateTime(DateUtils.getCurrDateTimeStr());
			cmDesignerInfo.setId(Long.parseLong(id));
			cmDesignerService.update(cmDesignerInfo);
		}else{
			String userId = request.getParameter("userId");  	//获取传上来的用户ID号
			if(StringUtil.isNotEmpty(userId)){
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(userId));
				if(cmUserInfo == null){		//如果用户的ID不存在则得到的对像为空值，返回错误信息。
					model.addAttribute("message", "该用户不存在！");
					return "bo/error";
				}else{
					//以下进行时间为空时，将空字符串转为NULL进行保存。
					String finishTime =cmDesignerInfo.getFinishSchoolTime();
					if(finishTime == ""){
						cmDesignerInfo.setFinishSchoolTime(null);
					}
					cmDesignerInfo.setCreateTime(DateUtils.getCurrDateTimeStr());
					cmDesignerService.save(cmDesignerInfo);
				}
			}
			
		}
		return "redirect:/Bo/designer/designerList";
	}
	
	/**
	 * 展示设计师9张作品
	 * @param request
	 * @param model
	 * @param cmDesignerInfopic
	 * @return
	 */
	@RequestMapping(value = "/designerInfoPicEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String designerPic(HttpServletRequest request,Model model,CmDesignerInfoPic cmDesignerInfoPic){
		
		try {
			String designerId = request.getParameter("id");
			if(StringUtil.isNotEmpty(designerId)){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("designerId", designerId);
				List<CmDesignerInfoPic> cmDesignerInfoPicList = cmDesignerInfoPicService.findByMap(param);
				if(cmDesignerInfoPicList!=null && cmDesignerInfoPicList.size() > 0 ){
					model.addAttribute("list",cmDesignerInfoPicList);
				}else{
					model.addAttribute("list",null);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/designer/designerPic_edit";
	}
	
	/**
	 * 设计师商品信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerProductInfoList")
	public String designerProductInfoList(HttpServletRequest request, ModelMap model) {
		
		try {
			String id = request.getParameter("id");
			String designerId = request.getParameter("designerId");
			String name = request.getParameter("name");
			
			Map<String,String> map = new HashMap<String,String>();
			
			if(StringUtil.isNotEmpty(id)){
				map.put("id", id);
				model.addAttribute("id",id);
			}
			if(StringUtil.isNotEmpty(designerId)){
				map.put("designerId", designerId);
				model.addAttribute("designerId",designerId);
			}
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmDesignerProductInfoService.findByPage(page,map);
			List<CmDesignerProductInfo> list = pageResult.getResult();
			
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/designer/designerProductInfo_list";
	}
	
	/**
	 * 新增或编辑商品信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerProductInfoEdit")
	public String designerProductInfoEdit(HttpServletRequest request, ModelMap model) {
		
		
		try {
			String id = request.getParameter("id");
			
			if(StringUtil.isNotEmpty(id)){
				CmDesignerProductInfo cmDesignerProductInfo = cmDesignerProductInfoService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmDesignerProductInfo);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/designer/designerProductInfo_edit";
	}
	
	/**
	 * 作品信息保存
	 * @param request
	 * @param model
	 * @param cmDesignerProductInfo
	 * @return
	 */
	@RequestMapping(value="/designerProductInfosave",method={RequestMethod.GET,RequestMethod.POST})
	public String designerProductInfosave(HttpServletRequest request,Model model,CmDesignerProductInfo cmDesignerProductInfo){
		
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmDesignerProductInfo.setUpdateTime(DateUtils.getCurrDateTimeStr());
			cmDesignerProductInfo.setId(Long.parseLong(id));
			cmDesignerProductInfoService.update(cmDesignerProductInfo);
		}else{
			
			Long designerId = cmDesignerProductInfo.getDesignerId();
			CmDesignerInfo cmDesignerInfo = cmDesignerService.getById(designerId);
			
			if(cmDesignerInfo == null){		//如果用户的ID不存在则得到的对像为空值，返回错误信息。
				model.addAttribute("message", "该编号设计师不存在！");
				return "bo/error";
			}else{
				cmDesignerProductInfo.setCreateTime(DateUtils.getCurrDateTimeStr());
				cmDesignerProductInfoService.save(cmDesignerProductInfo);
			}
		}
		return "redirect:/Bo/designer/designerProductInfoList";
	}
	
	/**
	 * 商品规格列表
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerProductFormatList")
	public String designerProductFormatList(HttpServletRequest request, ModelMap model) {
		
		try {
			String id = request.getParameter("id");
			Map<String,String> map = new HashMap<String,String>();
			
			if(StringUtil.isNotEmpty(id)){
				map.put("productId", id);
				model.addAttribute("productId",id);
			}else{
				String productId = request.getParameter("productId");
				if(StringUtil.isNotEmpty(productId)){
					map.put("productId", productId);
					model.addAttribute("productId",productId);
				}
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmDesignerProductFormatService.findByPage(page,map);
			List<CmDesignerProductInfo> list = pageResult.getResult();
			
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/designer/designerProductFormat_list";
	}
	
	/**
	 * 新增或编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerProductFormatEdit")
	public String designerProductFormatEdit(HttpServletRequest request, ModelMap model) {
		
		
		try {
			String id = request.getParameter("id");
			String productId = request.getParameter("productId");
			model.addAttribute("productId", productId);
			
			if(StringUtil.isNotEmpty(id)){
				CmDesignerProductFormat cmDesignerProductFormat = cmDesignerProductFormatService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmDesignerProductFormat);
			}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/designer/designerProductFormat_edit";
	}
	
	/**
	 * 规格信息保存
	 * @param request
	 * @param model
	 * @param cmDesignerInfo
	 * @return
	 */
	@RequestMapping(value="/designerProductFormatEditsave",method={RequestMethod.GET,RequestMethod.POST})
	public String designerProductFormatEditsave(HttpServletRequest request,Model model,CmDesignerProductFormat cmDesignerProductFormat){
		
		String id = request.getParameter("id");
		String productId = request.getParameter("productId");
			model.addAttribute("productId", productId);
		
		if(StringUtil.isNotEmpty(id)){
			
			cmDesignerProductFormat.setId(Long.parseLong(id));
			cmDesignerProductFormatService.update(cmDesignerProductFormat);
		}else{
			cmDesignerProductFormat.setCreateTime(DateUtils.getCurrDateTimeStr());
			cmDesignerProductFormatService.save(cmDesignerProductFormat);
		}
		return "redirect:/Bo/designer/designerProductFormatList?id="+productId;
	}
	
	/**
	 * 删除作品规格，直接删除
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/designerProductFormatDelete")
	public @ResponseBody String designerProductFormatDelete(HttpServletRequest request,ModelMap model){
		
		String id = request.getParameter("id");
		CmDesignerProductFormat cmDesignerProductFormat = cmDesignerProductFormatService.getById(Long.parseLong(id));
		String productId = cmDesignerProductFormat.getProductId()+"";
		model.addAttribute("productId", productId);
		
		if(StringUtil.isNotEmpty(id)){
			cmDesignerProductFormatService.delete(Long.parseLong(id));
		}
		return "redirect:/Bo/designer/designerProductFormatList?id="+productId;
	}
	
	/**
	 * 查看设计师荣誉
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerHonorList")
	public String designerHonorList(HttpServletRequest request, ModelMap model) {
		
		try {
			String id = request.getParameter("id");
			Map<String,String> map = new HashMap<String,String>();
			
			if(StringUtil.isNotEmpty(id)){
				map.put("designerId", id);
				model.addAttribute("designerId",id);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmDesignerHonorService.findByPage(page,map);
			List<CmDesignerProductInfo> list = pageResult.getResult();
			
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/designer/designerHonor_list";
	}
	
	/**
	 * 编辑设计师荣誉
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerHonorEdit")
	public String designerHonorEdit(HttpServletRequest request, ModelMap model) {
		
		
		try {
			String designerId = request.getParameter("id");
			if(StringUtil.isNotEmpty(designerId)){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("designerId", designerId);
				List<CmDesignerHonor> cmDesignerHonorList = cmDesignerHonorService.findByMap(param);
				if(cmDesignerHonorList!=null && cmDesignerHonorList.size() > 0 ){
					model.addAttribute("list",cmDesignerHonorList);
				}else{
					model.addAttribute("list",null);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/designer/designerHonor_edit";
	}
	
	/**
	 * 保存或新增设计师荣誉
	 * @param request
	 * @param model
	 * @param cmDesignerHonor
	 * @return
	 */
	@RequestMapping(value="/designerHonorSave",method={RequestMethod.GET,RequestMethod.POST})
	public String designerHonorSave(HttpServletRequest request,Model model,CmDesignerHonor cmDesignerHonor){
		
		String id = request.getParameter("id");
		String designerId = request.getParameter("designerId");
			model.addAttribute("designerId", designerId);
		
		if(StringUtil.isNotEmpty(id)){
			
			cmDesignerHonor.setId(Long.parseLong(id));
			cmDesignerHonorService.update(cmDesignerHonor);
		}else{
			cmDesignerHonor.setCreateTime(DateUtils.getCurrDateTimeStr());
			cmDesignerHonorService.save(cmDesignerHonor);
		}
		return "redirect:/Bo/designer/designerHonorList?id="+designerId;
	}
	
	/**
	 * 商品订单评论信息
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/designerSaleCommentList")
	public String designerSaleCommentList(HttpServletRequest request, ModelMap model) {
		
		try {
			String id = request.getParameter("id");
			String orderNO = request.getParameter("orderNO");
			
			Map<String,String> map = new HashMap<String,String>();
			
			if(StringUtil.isNotEmpty(id)){
				map.put("id", id);
				model.addAttribute("id",id);
			}
			
			if(StringUtil.isNotEmpty(orderNO)){
				map.put("orderNO", orderNO);
				model.addAttribute("orderNO",orderNO);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmDesignerSaleCommentService.findByPage(page,map);
			List<CmDesignerSaleComment> list = pageResult.getResult();
			
			if(StringUtil.isNotEmpty(list)){
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/designer/designerSaleComment_list";
	}
	
}
