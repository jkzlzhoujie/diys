package cn.temobi.complex.action.bo;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.AllTypeDto;
import cn.temobi.complex.dto.PmTopicPsProductsDto;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CmBusiScope;
import cn.temobi.complex.entity.CmUserExtendInfo;
import cn.temobi.complex.entity.CmUserImageIntroduce;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodity;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmTopicCase;
import cn.temobi.complex.entity.PmTopicInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.SysIndustryInfo;
import cn.temobi.complex.entity.SysProductTypeInfo;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.entity.Theme;
import cn.temobi.complex.service.CmBusiScopeService;
import cn.temobi.complex.service.CmUserExtendInfoService;
import cn.temobi.complex.service.CmUserImageIntroduceService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.complex.service.PmCommodityService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmTopicCaseService;
import cn.temobi.complex.service.PmTopicInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.PmTopicPsProductsService;
import cn.temobi.complex.service.SysIndustryInfoService;
import cn.temobi.complex.service.SysProductTypeInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.easemob.api.EasemobIUtil;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;

import com.salim.cache.CacheHelper;
import com.salim.encrypt.MD5;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/topic")
public class TopicController extends BoBaseController{

	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="pmTopicInfoService")
	private PmTopicInfoService pmTopicInfoService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="pmTopicCaseService")
	private PmTopicCaseService pmTopicCaseService;
	
	@Resource(name="cmUserExtendInfoService")
	private CmUserExtendInfoService cmUserExtendInfoService;
	
	@Resource(name="sysIndustryInfoService")
	private SysIndustryInfoService sysIndustryInfoService;
	
	@Resource(name="sysProductTypeInfoService")
	private SysProductTypeInfoService sysProductTypeInfoService;
	
	@Resource(name="cmUserImageIntroduceService")
	private CmUserImageIntroduceService cmUserImageIntroduceService;
	
	@Resource(name="cmBusiScopeService")
	private CmBusiScopeService cmBusiScopeService;
	
	@Resource(name="pmTopicPsProductsService")
	private PmTopicPsProductsService pmTopicPsProductsService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="pmCommodityService")
	private PmCommodityService pmCommodityService;
	
	@Resource(name="pmCommodityInfoService")
	private PmCommodityInfoService pmCommodityInfoService;
	
	/**
	 * 订单列表
	 */
	@RequestMapping(value="/orderList",method={RequestMethod.GET,RequestMethod.POST})
	public String orderList(Model model,HttpServletRequest request){
		try{
			Map<String,String> map = new HashMap<String,String>();
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);

			String orderNo = request.getParameter("orderNo");//结束时间
			if(StringUtil.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
				model.addAttribute("orderNo",orderNo);
			}
			String nickName = request.getParameter("nickName");//结束时间
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			String status = request.getParameter("status");//结束时间
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			String startDate = request.getParameter("startDate");//结束时间
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");//结束时间
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"),request.getParameter("pageNum"));
			Page<Order> pageResult = new Page<Order>();
			pageResult = orderService.findByPage(page, map);
			List<Order> list = pageResult.getResult();
			for(Order order:list){
				order.setAddTime(order.getAddTime().substring(0,order.getAddTime().length()-2));
			}
			Number sum = orderService.getSum(map);
			if(StringUtil.isNotEmpty(sum)) {
				model.addAttribute("sum",format.format(sum));
			}else{
				model.addAttribute("sum","0.00");
			}
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize",pageResult.getPageSize());
			model.addAttribute("totalItems",pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}	
		
		return "bo/topic/orderList";
	}
	
	@RequestMapping(value="/editOrder",method={RequestMethod.GET,RequestMethod.POST})
	public String editOrder(Model model,HttpServletRequest request){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			Order order = orderService.getById(Long.parseLong(id));
			model.addAttribute("entity",order);
		}
		return "bo/topic/editOrder";
	}
	
	@RequestMapping(value="/orderSave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String orderSave(Model model,HttpServletRequest request,Order order){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			Order orderNew = orderService.getById(Long.parseLong(id));
			orderNew.setRemark(order.getRemark());
			orderService.update(orderNew);
		}	
		return "";
	}
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list", method = {RequestMethod.GET, RequestMethod.POST })
	public String list(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmTopicInfo> pageResult = new Page<PmTopicInfo>();
			pageResult = pmTopicInfoService.findByPage(page, map);
			List<PmTopicInfo> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/list";
	}
	
	/**
	 * 编辑
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
				PmTopicInfo pmTopicInfo = pmTopicInfoService.getById(Long.parseLong(id));
				model.addAttribute("entity", pmTopicInfo);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/edit";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,PmTopicInfo pmTopicInfo) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				pmTopicInfoService.update(pmTopicInfo);
			}else{
				pmTopicInfoService.save(pmTopicInfo);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/list";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/del", method = {RequestMethod.GET, RequestMethod.POST })
	public String del(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmTopicInfo pmTopicInfo = pmTopicInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmTopicInfo)) {
					pmTopicInfoService.delete(pmTopicInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/list";
	}
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/customList", method = {RequestMethod.GET, RequestMethod.POST })
	public String customList(HttpServletRequest request,Model model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			String remakFilter = request.getParameter("remakFilter");
			String nickName = request.getParameter("nickName"); 
			String contact = request.getParameter("contact");
			String joinType = request.getParameter("joinType");
			String acceptUserId = request.getParameter("acceptUserId");
			String acceptRemark = request.getParameter("acceptRemark");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String orderNo = request.getParameter("orderNo");
			String status = request.getParameter("status");
			
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
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
			
			String qqContact = request.getParameter("qqContact");
			if(StringUtil.isNotEmpty(qqContact)) {
				map.put("qqContact", qqContact);
				model.addAttribute("qqContact",qqContact);
			}
			
			if(StringUtil.isNotEmpty(acceptRemark)) {
				if(StringUtil.isNotEmpty(remakFilter)) {
					map.put("remakFilter", acceptRemark);
					model.addAttribute("remakFilter", remakFilter);
				}else{
					map.put("acceptRemark", acceptRemark);
				}
				model.addAttribute("acceptRemark",acceptRemark);
			}
			
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName", nickName);
			}
			if(StringUtil.isNotEmpty(contact)) {
				map.put("contact", contact);
				model.addAttribute("contact", contact);
			}
			if(StringUtil.isNotEmpty(joinType)) {
				map.put("joinType", joinType);
				model.addAttribute("joinType", joinType);
			}
			if(StringUtil.isNotEmpty(acceptUserId)) {
				map.put("acceptUserId", acceptUserId);
				model.addAttribute("acceptUserId",acceptUserId);
			}
			
			if(StringUtil.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
				model.addAttribute("orderNo",orderNo);
			}
			
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			
			
			Map<String,String> cmUserMap = new HashMap<String, String>();
			cmUserMap.put("userType", "2");
			
			List<CmUserInfo> cmUserList = new ArrayList<CmUserInfo>();
			cmUserList = (List<CmUserInfo>) CacheHelper.getInstance().get("cmUserInfo@userType=2");//大画家
			if(cmUserList == null || cmUserList.size()==0){
				cmUserList = cmUserInfoService.findByMap(cmUserMap);
				CacheHelper.getInstance().set(60*60*24,"cmUserInfo@userType=2", cmUserList);
			}
			
			model.addAttribute("cmUserList", cmUserList);
			map.put("topicId","2");

			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			pageResult = pmTopicJoinProductsService.findByPageDtoTo(page, map);
			List<PmTopicJoinProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicJoinProducts pmTopicJoinProducts:list)
				{
					List<AllTypeDto> typelist = sysProductTypeInfoService.findAllType(map);
					if(typelist != null && typelist.size() > 0)
					{
						for(AllTypeDto allTypeDto:typelist)
						{
							if(pmTopicJoinProducts.getJoinType().equals(allTypeDto.getId()+""))
							{
								pmTopicJoinProducts.setJoinType(allTypeDto.getName());
							}
						}
					}
					pmTopicJoinProducts.setJoinTime(pmTopicJoinProducts.getJoinTime().substring(0,pmTopicJoinProducts.getJoinTime().length()-2));
					CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
					pmTopicJoinProducts.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
					pmTopicJoinProducts.setNickName(cmUserInfo.getNickName());
				}
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
		return "bo/topic/customList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/customEdit",method={RequestMethod.GET,RequestMethod.POST})
	public String customEdit(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		String topicType = request.getParameter("topicType");
		model.addAttribute("topicType", topicType);
		Map<String,String> cmUserMap = new HashMap<String, String>();
		if("1".equals(topicType))
		{	
			cmUserMap.put("userType", "1"); 
		}else if("2".equals(topicType))
		{
			cmUserMap.put("userType", "2");
		}else{
			cmUserMap.put("userType", "1");
		}
		List<CmUserInfo> cmUserList = new ArrayList<CmUserInfo>();
		cmUserList = (List<CmUserInfo>) CacheHelper.getInstance().get("cmUserInfo@desinger");//设计师
		if((cmUserList == null || cmUserList.size()==0 )&& topicType.equals("1")){
			cmUserList = cmUserInfoService.findByMap(cmUserMap);
			CacheHelper.getInstance().set(60*60*24,"cmUserInfo@desinger", cmUserList);
		}
		if((cmUserList == null || cmUserList.size()==0 )&& topicType.equals("2")){
			cmUserList = (List<CmUserInfo>) CacheHelper.getInstance().get("cmUserInfo@userType=2");//大画家
			if(cmUserList == null || cmUserList.size()==0){
				cmUserList = cmUserInfoService.findByMap(cmUserMap);
				CacheHelper.getInstance().set(60*60*24,"cmUserInfo@userType=2", cmUserList);
			}
		}
		
		model.addAttribute("cmUserList", cmUserList);
		if(StringUtil.isNotEmpty(id)){
			PmTopicJoinProducts pm = pmTopicJoinProductsService.getById(Long.parseLong(id));
			model.addAttribute("entity", pm);
		}
		return "bo/topic/customEdit";
	}
	
	@RequestMapping(value="/customSave",method={RequestMethod.GET,RequestMethod.POST})
	public String customSave(ModelMap model,HttpServletRequest request,PmTopicJoinProducts pm){
		String id = request.getParameter("id");
		String topicType = request.getParameter("topicType");
		model.addAttribute("topicType", topicType);
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(Long.parseLong(id));
			pmTopicJoinProducts.setAcceptUserId(pm.getAcceptUserId());
			pmTopicJoinProducts.setAcceptRemark(pm.getAcceptRemark());
			pmTopicJoinProducts.setDesignUrl(pm.getDesignUrl());
			pmTopicJoinProducts.setDescription(pm.getDescription());
			pmTopicJoinProducts.setDistributeUser(pm.getDistributeUser());
			pmTopicJoinProductsService.update(pmTopicJoinProducts);
			String thumbnail = request.getParameter("thumbnail");
			String url = request.getParameter("url");
			if(StringUtil.isNotEmpty(thumbnail) && StringUtil.isNotEmpty(url))
			{
				PmProductInfo pmProductInfo = pmProductInfoService.getById(pmTopicJoinProducts.getProductId());
				pmProductInfo.setUrl(url);
				pmProductInfo.setThumbnail(thumbnail);
				pmProductInfoService.update(pmProductInfo);
			}
		}
		return "redirect:/Bo/topic/customList";
	}
	
	@RequestMapping(value="/customDelete",method={RequestMethod.GET,RequestMethod.POST})
	public String customDelete(ModelMap model,HttpServletRequest request){
		String id = request.getParameter("id");
		String topicType = request.getParameter("topicType");
		model.addAttribute("topicType", topicType);
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			PmTopicJoinProducts pm = pmTopicJoinProductsService.getById(Long.parseLong(id));
			if(StringUtil.isNotEmpty(pm)) {
				pmTopicJoinProductsService.delete(pm);
				
				PmProductInfo pmProductInfo  = pmProductInfoService.getById(pm.getProductId());
				if(StringUtil.isNotEmpty(pmProductInfo))
				{
					pmProductInfo.setIsPublic(99);
					pmProductInfoService.update(pmProductInfo);
				}
			}
		}
		return "redirect:/Bo/topic/customList";
	}
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/caseList", method = {RequestMethod.GET, RequestMethod.POST })
	public String caseList(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmTopicCase> pageResult = new Page<PmTopicCase>();
			pageResult = pmTopicCaseService.findByPage(page, map);
			List<PmTopicCase> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/caseList";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/editCase", method = {RequestMethod.GET, RequestMethod.POST })
	public String editCase(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				PmTopicCase pmTopicCase = pmTopicCaseService.getById(Long.parseLong(id));
				model.addAttribute("entity", pmTopicCase);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/caseEdit";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/saveCase", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,PmTopicCase pmTopicCase) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				pmTopicCaseService.update(pmTopicCase);
			}else{
				pmTopicCaseService.save(pmTopicCase);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/caseList";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delCase", method = {RequestMethod.GET, RequestMethod.POST })
	public String delCase(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmTopicCase pmTopicCase = pmTopicCaseService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmTopicCase)) {
					pmTopicCaseService.delete(pmTopicCase);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/caseList";
	}

	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/drawUserList")
	public String drawUserList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String nickName = request.getParameter("nickName");//结束时间
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			String startDate = request.getParameter("startDate");//结束时间
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");//结束时间
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}

			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried", orderFried);
				model.addAttribute("orderFried",orderFried);
			}else{
				map.put("orderFried", "created_when");
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}else{
				map.put("sequence", "desc");
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			map.put("userType", "2");
			Page<CmUserInfo> pageResult = new Page<CmUserInfo>();
			pageResult = cmUserInfoService.findByPage(page, map);
			List<CmUserInfo> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/draw_user_list";
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/drawUserEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String drawUserEdit(HttpServletRequest request,Model model) {
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
			CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(id));
			model.addAttribute("entity", cmUserInfo);
			model.addAttribute("cmUserExtendInfo", cmUserExtendInfo);
			
			map.put("userId", id);
			List<CmUserImageIntroduce> imagelist = cmUserImageIntroduceService.findByMap(map);
			model.addAttribute("imagelist", imagelist);
			List<CmBusiScope> bslist = cmBusiScopeService.findByMap(map);
			model.addAttribute("bslist", bslist);
		}
		map.put("parentIndustryIdTo", "0");
		List<SysIndustryInfo> list = sysIndustryInfoService.findByMap(map);
		model.addAttribute("infList", list);
		
		List<AllTypeDto> typelist = sysProductTypeInfoService.findAllType(map);
		model.addAttribute("typelist", typelist);
		return "bo/topic/draw_user_edit";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/drawUserSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String drawUserSave(HttpServletRequest request,ModelMap model,CmUserInfo cmUserInfo,CmUserExtendInfo cmUserExtendInfo) {
		String[] typeId = request.getParameterValues("typeId");
		String[] images = request.getParameterValues("images");
		Map<String,Object> map = new HashMap<String, Object>();
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			CmUserInfo newCmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
			CmUserExtendInfo newCmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(id));
			
			newCmUserInfo.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
			newCmUserInfo.setNickName(cmUserInfo.getNickName());
			newCmUserInfo.setSex(cmUserInfo.getSex());
			newCmUserInfo.setBirth(cmUserInfo.getBirth());
			newCmUserInfo.setCity(cmUserInfo.getCity());
			
			newCmUserExtendInfo.setIntroduce(cmUserExtendInfo.getIntroduce());
			newCmUserExtendInfo.setSignature(cmUserExtendInfo.getSignature());
			newCmUserExtendInfo.setCoverThumbnail(cmUserExtendInfo.getCoverThumbnail());
			newCmUserExtendInfo.setIdentityInfo(cmUserExtendInfo.getIdentityInfo());
			newCmUserExtendInfo.setCareer(cmUserExtendInfo.getCareer());
			cmUserInfoService.update(newCmUserInfo);
			cmUserExtendInfoService.update(newCmUserExtendInfo);
			map.put("userId", id);
			if(typeId != null && typeId.length > 0)
			{
				cmBusiScopeService.deleteByUserId(map);
				CmBusiScope cmBusiScope = null;
				for(String tempId:typeId)
				{
					cmBusiScope = new CmBusiScope();
					SysProductTypeInfo sysProductTypeInfo = sysProductTypeInfoService.getById(Long.parseLong(tempId));
					cmBusiScope.setFirstTypeId(sysProductTypeInfo.getParentId());
					cmBusiScope.setTypeId(sysProductTypeInfo.getId());
					cmBusiScope.setUserId(Long.parseLong(id));
					cmBusiScopeService.save(cmBusiScope);
				}
			}
			
			if(images != null && images.length > 0)
			{
				cmUserImageIntroduceService.deleteByUserId(map);
				CmUserImageIntroduce cmUserImageIntroduce = null;
				for(String tempUrl:images)
				{
					cmUserImageIntroduce = new CmUserImageIntroduce();
					cmUserImageIntroduce.setUserId(Long.parseLong(id));
					cmUserImageIntroduce.setImageUrl(tempUrl);
					cmUserImageIntroduceService.save(cmUserImageIntroduce);
				}
			}
		}else{
		    cmUserInfo.setMobile("draw"+RandomUtils.getRandomStr(8));
			cmUserInfo.setPassword(MD5.getMD5("123456"));
			cmUserInfo.setRegisterFrom("0");
			cmUserInfo.setLevel(1);
			cmUserInfo.setUserType(2);
			cmUserInfoService.save(cmUserInfo);
			cmUserExtendInfo.setUserId(cmUserInfo.getId());
			cmUserExtendInfoService.save(cmUserExtendInfo);
			
			EasemobIUtil.createNewIMUserSingle("heh" + cmUserInfo.getId());
			
			if(typeId != null && typeId.length > 0)
			{
				CmBusiScope cmBusiScope = null;
				for(String tempId:typeId)
				{
					cmBusiScope = new CmBusiScope();
					SysProductTypeInfo sysProductTypeInfo = sysProductTypeInfoService.getById(Long.parseLong(tempId));
					cmBusiScope.setFirstTypeId(sysProductTypeInfo.getParentId());
					cmBusiScope.setTypeId(sysProductTypeInfo.getId());
					cmBusiScope.setUserId(cmUserInfo.getId());
					cmBusiScopeService.save(cmBusiScope);
				}
			}
			
			if(images != null && images.length > 0)
			{
				CmUserImageIntroduce cmUserImageIntroduce = null;
				for(String tempUrl:images)
				{
					cmUserImageIntroduce = new CmUserImageIntroduce();
					cmUserImageIntroduce.setUserId(cmUserInfo.getId());
					cmUserImageIntroduce.setImageUrl(tempUrl);
					cmUserImageIntroduceService.save(cmUserImageIntroduce);
				}
			}
		}
		
		return "redirect:/Bo/topic/drawUserList";
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/deleteDrawUser", method = {RequestMethod.GET, RequestMethod.POST })
	public String deleteDrawUser(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				CmUserInfo cmUserInfo = cmUserInfoService.getById(Long.parseLong(id));
				CmUserExtendInfo cmUserExtendInfo = cmUserExtendInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(cmUserInfo)) {
					cmUserInfoService.delete(cmUserInfo);
				}
				if(StringUtil.isNotEmpty(cmUserExtendInfo)) {
					cmUserExtendInfoService.delete(cmUserExtendInfo);
				}
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("userId", id);
				List<PmProductInfo> list = pmProductInfoService.findByMap(map);
				if(list != null && list.size() > 0)
				{
					for(PmProductInfo pmProductInfo:list){
						pmProductInfoService.delete(pmProductInfo);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/drawUserList";
	}
	
	/**
	 * 原图新增
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pYedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String pYedit(HttpServletRequest request,Model model) {
		
		return "bo/topic/pYedit";
	}
	
	/**
	 * 原图保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pYsave", method = {RequestMethod.GET, RequestMethod.POST })
	public String pYsave(HttpServletRequest request,Model model,PmTopicJoinProducts pmTopicJoinProducts) {
		try {
			String thumbnail = request.getParameter("thumbnail");
			PmProductInfo resource = new PmProductInfo();
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUrl(pmTopicJoinProducts.getUrl());
			resource.setThumbnail(thumbnail);
			resource.setIsPublic(2);
			resource.setAudit(1);
			resource.setCreateType(0);
			resource.setCreateFrom("0");
			resource.setUserId(pmTopicJoinProducts.getUserId());
			pmProductInfoService.save(resource);
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicJoinProducts.setTopicId(topicList.get(0).getId());
			}
			pmTopicJoinProducts.setProductId(resource.getId());
			pmTopicJoinProductsService.save(pmTopicJoinProducts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/Bo/topic/customList?topicType=0";
	}
	
	
	/**
	 * 原图保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pTsave", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody int pTsave(HttpServletRequest request,Model model,PmTopicPsProducts pmTopicPsProducts) {
		try {
			String thumbnail = request.getParameter("thumbnail");
			String url = request.getParameter("url");
			PmProductInfo resource = new PmProductInfo();
			resource.setResourceId(UUIDGenerator.getUUID());
			resource.setUserId(pmTopicPsProducts.getPsUserId());
			resource.setUrl(url);
			resource.setThumbnail(thumbnail);
			resource.setIsPublic(2);
			resource.setAudit(1);
			resource.setCreateType(1);
			resource.setCreateFrom("0");
			pmProductInfoService.save(resource);
			
			PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(pmTopicPsProducts.getJoinId());
			pmTopicJoinProducts.setJoinProducts(pmTopicJoinProducts.getJoinProducts()+1);
			pmTopicJoinProductsService.update(pmTopicJoinProducts);
			
			pmTopicPsProducts.setSrcProductId(pmTopicJoinProducts.getProductId());
			pmTopicPsProducts.setProductId(resource.getId());
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("topicType", "0");
			List<PmTopicInfo> topicList = pmTopicInfoService.findByMap(map);
			if(topicList != null && topicList.size() > 0)
			{
				pmTopicJoinProducts.setTopicId(topicList.get(0).getId());
			}
			pmTopicPsProductsService.save(pmTopicPsProducts);
			CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicPsProducts.getPsUserId());
			PushUtil.pullOneMessage(pmTopicJoinProducts.getUserId()+"", "主人，"+cmUserInfo.getNickName()+"P了你的图，快来看看", "16", pmTopicJoinProducts.getId()+"","");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 原图新增
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pTedit", method = {RequestMethod.GET, RequestMethod.POST })
	public String pTedit(HttpServletRequest request,Model model) {
		model.addAttribute("joinId", request.getParameter("joinId"));
		return "bo/topic/pTedit";
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pTlist", method = {RequestMethod.GET,RequestMethod.POST})
	public String pTlist(HttpServletRequest request,Model model) {
	    try{
	     	Map<String,Object> map = new HashMap<String, Object>();
	     	String joinId = request.getParameter("joinId");
	     	model.addAttribute("joinId", joinId);
	     	String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
	     	String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endPrice = request.getParameter("endPrice");
			if(StringUtil.isNotEmpty(endPrice)) {
				map.put("endPrice", endPrice);
				model.addAttribute("endPrice",endPrice);
			}
			
			String startPrice = request.getParameter("startPrice");
			if(StringUtil.isNotEmpty(startPrice)) {
				map.put("startPrice", startPrice);
				model.addAttribute("startPrice",startPrice);
			}
			
			String isGet = request.getParameter("isGet");
			if(StringUtil.isNotEmpty(isGet)) {
				map.put("isGet", isGet);
				model.addAttribute("isGet",isGet);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			String nickName = request.getParameter("nickName");
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName.trim());
				model.addAttribute("nickName",nickName.trim());
			}
	    	String pageSize = request.getParameter("pageSize");
	    	String pageNo = request.getParameter("pageNo");
	    	if(StringUtil.isEmpty(pageNo)) pageNo = "1";
	    	if(StringUtil.isEmpty(pageSize)) pageSize = "18";
	    	int pageNoNum = Integer.parseInt(pageNo);
	    	int pageSizeNum = Integer.parseInt(pageSize);
			Page page =  new Page<PmTopicPsProductsDto>(pageNoNum,pageSizeNum);
			if( joinId != null){
				map.put("joinId", joinId);
			}
			map.put("audit", "1");
	    	Page<PmTopicPsProducts> pageResult = pmTopicPsProductsService.findByPageTwo(page, map);
			List<PmTopicPsProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicPsProducts pmTopicPsProducts:list){
					PmProductInfo pmProductInfo = pmProductInfoService.getById(pmTopicPsProducts.getSrcProductId());
					pmTopicPsProducts.setSrcUrl(pmProductInfo.getUrl());
					pmTopicPsProducts.setSrcThumbnail(pmProductInfo.getThumbnail());
				}
			}
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
	    }catch(Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
	    return "bo/topic/pTlist";
	}
	
	/**
	 * 扩展列表
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/commodityInfoList", method = {RequestMethod.GET, RequestMethod.POST })
	public String commodityInfoList(HttpServletRequest request,Model model) {
		try{
			Map<String, String> map = new HashMap<String, String>();
			String commodityId = request.getParameter("commodityId");
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmCommodityInfo> pageResult = new Page<PmCommodityInfo>();		
			if(StringUtil.isNotEmpty(commodityId)) {
				map.put("commodityId", commodityId);
				model.addAttribute("commodityId",commodityId);
				pageResult = pmCommodityInfoService.findByPage(page, map);
				List<PmCommodityInfo> list = pageResult.getResult();
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
		
		return "bo/topic/commodityInfoList";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/editCommodityInfo", method = {RequestMethod.GET, RequestMethod.POST })
	public String editCommodityInfo(HttpServletRequest request,Model model) {
		try{
			String id = request.getParameter("id");
			String commodityId = request.getParameter("commodityId");
			model.addAttribute("commodityId", commodityId);
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(id));
				model.addAttribute("entity", pmCommodityInfo);
			}
			
		}catch(Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
	    return "bo/topic/editCommodityInfo";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveCommodityInfo", method = {RequestMethod.GET, RequestMethod.POST })
	public String saveCommodityInfo(HttpServletRequest request,Model model,PmCommodityInfo pmCommodityInfo) {
		try{
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				pmCommodityInfoService.update(pmCommodityInfo);
			}else{
				pmCommodityInfoService.save(pmCommodityInfo);
			}
			
		}catch(Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
	    return "redirect:/Bo/topic/listCommodity";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delCommodityInfo", method = {RequestMethod.GET, RequestMethod.POST })
	public String delCommodityInfo(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmCommodityInfo)) {
					pmCommodityInfoService.delete(pmCommodityInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/listCommodity";
	}
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/commodityStatus", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String commodityStatus(HttpServletRequest request,Model model) {
		String id = request.getParameter("id") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(status)) {
				PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmCommodity)) {
					pmCommodity.setStatus(status);
					pmCommodityService.update(pmCommodity);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
	
	/**
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/commodityInfoStatus", method = {RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String commodityInfoStatus(HttpServletRequest request,Model model) {
		String id = request.getParameter("id") ;
		String status = request.getParameter("status");
		try {
			if(StringUtil.isNotEmpty(id) && StringUtil.isNotEmpty(status)) {
				PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmCommodityInfo)) {
					pmCommodityInfo.setStatus(status);
					pmCommodityInfoService.update(pmCommodityInfo);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}		
		return "";
	}
	
	/**
	 * 刷新缓存
	 * 1 商品列表
	 * 2 app 菜单列表
	 * @param request
	 * @param model
	 * @param entity
	 * @return
	 */
	@RequestMapping(value="/refreshCache",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String refreshCache(HttpServletRequest request,Model model,BlackListWord entity){
		CacheHelper.getInstance().getInstance().remove("commodityList");
		CacheHelper.getInstance().getInstance().remove("index@appMenu");
		CacheHelper.getInstance().getInstance().remove("cmUserInfo@desinger");//彩绘设计师
		CacheHelper.getInstance().getInstance().remove("cmUserInfo@userType=2");//大画家
		CacheHelper.getInstance().getInstance().remove("circle@circlePostListhot1");
		CacheHelper.getInstance().getInstance().remove("circle@circlePostListhot2");
		return "";
	}
	
	
	 @RequestMapping(value={"/refreshCacheByKey"}, method={org.springframework.web.bind.annotation.RequestMethod.GET, org.springframework.web.bind.annotation.RequestMethod.POST})
	  @ResponseBody
	  public String refreshCacheByKey(HttpServletRequest request, Model model, BlackListWord entity)
	  {
	    String key = request.getParameter("key");
	    CacheHelper.getInstance(); CacheHelper.getInstance().remove(key);
	    return "";
	  }
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/listCommodity", method = {RequestMethod.GET, RequestMethod.POST })
	public String listCommodity(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String name = request.getParameter("name");
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name", name);
			}
			
			String price = request.getParameter("price");
			if(StringUtil.isNotEmpty(price)){
				map.put("price", price);
				model.addAttribute("price", price);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmCommodity> pageResult = new Page<PmCommodity>();
			pageResult = pmCommodityService.findByPage(page, map);
			List<PmCommodity> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/listCommodity";
	}
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/compCommodity", method = {RequestMethod.GET, RequestMethod.POST })
	public String compCommodity(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(id));
				
				model.addAttribute("entity", pmCommodity);
				
				if(StringUtil.isNotEmpty(pmCommodity.getUrl()))
				{
					model.addAttribute("imagelist",Arrays.asList(pmCommodity.getUrl().split(",")));
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/topic/editCommodity";
	}
	
	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/saveCommodity", method = {RequestMethod.GET, RequestMethod.POST })
	public String saveCommodity(HttpServletRequest request,ModelMap model,PmCommodity pmCommodity) {
		try {
			String id = request.getParameter("id") ;
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				pmCommodityService.update(pmCommodity);
			}else{
				pmCommodityService.save(pmCommodity);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/listCommodity";
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delCommodity", method = {RequestMethod.GET, RequestMethod.POST })
	public String delCommodity(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");//表情包ID
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmCommodity pmCommodity = pmCommodityService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmCommodity)) {
					pmCommodityService.delete(pmCommodity);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/listCommodity";
	}
	
	//导出execl
	@RequestMapping(value = "/exportExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String execl(HttpSession session,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try {
			Map<String,String> map = new HashMap<String,String>();
			String orderNo = request.getParameter("orderNo");//结束时间
			if(StringUtil.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
			}
			String nickName = request.getParameter("nickName");//结束时间
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
			}
			String status = request.getParameter("status");//结束时间
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
			}
			String startDate = request.getParameter("startDate");//结束时间
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
			}
			String endDate = request.getParameter("endDate");//结束时间
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
			}
			List<Order> list = orderService.findByMap2(map);
			for(Order order:list){
				order.setAddTime(order.getAddTime().substring(0,order.getAddTime().length()-2));
			}
			if(list.size() > 0)
			{
				this.xslpoi(list,response);
			}else{
				return "sorry, no files!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "";
	}
	
	//导出审核excel
	public void xslpoi(List<Order> list,HttpServletResponse response) {
		//创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("订单列表");
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);
		//创建单元格
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); //设置水平对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);////设置垂直对齐方式
		//表头
		HSSFCell cell = row.createCell(0);
		cell = row.createCell(0);
		cell.setCellValue("订单号");
		cell.setCellStyle(style);
		cell = row.createCell(1);
		cell.setCellValue("用户名");
		cell.setCellStyle(style);
		cell = row.createCell(2);
		cell.setCellValue("金额");
		cell.setCellStyle(style);
		cell = row.createCell(3);
		cell.setCellValue("支付类型");
		cell.setCellStyle(style);
		cell = row.createCell(4);
		cell.setCellValue("订购类型");
		cell.setCellStyle(style);
		cell = row.createCell(5);
		cell.setCellValue("支付状态");
		cell.setCellStyle(style);
		cell = row.createCell(6);
		cell.setCellValue("备注");
		cell.setCellStyle(style);
		cell = row.createCell(7);
		cell.setCellValue("订购时间");
		cell.setCellStyle(style);
		sheet.setColumnWidth(0, 8000);
		sheet.setColumnWidth(1, 8000);
		sheet.setColumnWidth(2, 8000);
		sheet.setColumnWidth(3, 8000);
		sheet.setColumnWidth(4, 8000);
		sheet.setColumnWidth(5, 8000);
		sheet.setColumnWidth(6, 5000);
		sheet.setColumnWidth(7, 5000);
		int index = 0;
		int rowIndex = 0;
		int nameIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			if(i/50000 != index) {
				index ++;
				nameIndex ++;
				rowIndex = 0;
				sheet = wb.createSheet("订单列表"+nameIndex);	
				HSSFRow row1 = sheet.createRow((int) 0);
				//创建单元格
				HSSFCellStyle style1 = wb.createCellStyle();
				style1.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); //设置水平对齐方式
				style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);////设置垂直对齐方式
				//表头
				HSSFCell cell1 = row1.createCell(0);
				cell1 = row1.createCell(0);
				cell1.setCellValue("订单号");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(1);
				cell1.setCellValue("用户名");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(2);
				cell1.setCellValue("金额");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(3);
				cell1.setCellValue("支付类型");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(4);
				cell1.setCellValue("订购类型");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(5);
				cell1.setCellValue("支付状态");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(6);
				cell1.setCellValue("备注");
				cell1.setCellStyle(style);
				cell1 = row1.createCell(7);
				cell1.setCellValue("订购时间");
				cell1.setCellStyle(style);
				sheet.setColumnWidth(0, 8000);
				sheet.setColumnWidth(1, 8000);
				sheet.setColumnWidth(2, 8000);
				sheet.setColumnWidth(3, 8000);
				sheet.setColumnWidth(4, 8000);
				sheet.setColumnWidth(5, 8000);
				sheet.setColumnWidth(6, 5000);
				sheet.setColumnWidth(7, 5000);
			}
			row = sheet.createRow((int) rowIndex + 1);
			Order order = list.get(i);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(order.getOrderNo());
			row.createCell(1).setCellValue(order.getNickName());
			row.createCell(2).setCellValue(order.getAmount());
			if("1".equals(order.getPayType())) {
				row.createCell(3).setCellValue("微信");
			}else if("2".equals(order.getPayType())){
				row.createCell(3).setCellValue("支付宝");
			}
			if("1".equals(order.getType())) {
				row.createCell(4).setCellValue("私人订制");
			}else if("2".equals(order.getType())) {
				row.createCell(5).setCellValue("悬赏求P");
			}else if("3".equals(order.getType())) {
				row.createCell(5).setCellValue("充值");
			}
			if("0".equals(order.getStatus())) {
				row.createCell(5).setCellValue("未支付");
			}else if("1".equals(order.getStatus())) {
				row.createCell(5).setCellValue("已支付");
			}else if("2".equals(order.getStatus())) {
				row.createCell(5).setCellValue("支付失败");
			}else if("3".equals(order.getStatus())) {
				row.createCell(5).setCellValue("已取消");
			}else if("4".equals(order.getStatus())) {
				row.createCell(5).setCellValue("已删除");
			}
			row.createCell(6).setCellValue(order.getRemark());
			row.createCell(7).setCellValue(order.getAddTime());
			rowIndex ++;
		}
		//将文件存到指定位置
		try {
			String name = "订单列表.xls";
			FileUtil.exportToExcel(wb, response, name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
