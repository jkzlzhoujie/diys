package cn.temobi.complex.action.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import cn.temobi.complex.dao.PmProductCollectPicDao;
import cn.temobi.complex.dto.AllTypeDto;
import cn.temobi.complex.dto.CmUserInfoListDto;
import cn.temobi.complex.dto.PmProductInfoDto;
import cn.temobi.complex.dto.ThemeUsedByPdtDto;
import cn.temobi.complex.entity.BlackListWord;
import cn.temobi.complex.entity.CirclePostPic;
import cn.temobi.complex.entity.CmTalenInfo;
import cn.temobi.complex.entity.CmUserFans;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserMessage;
import cn.temobi.complex.entity.Laber;
import cn.temobi.complex.entity.PmProductCollectPic;
import cn.temobi.complex.entity.PmProductDiscuss;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.PmProductPraises;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.PmTopicPsProducts;
import cn.temobi.complex.entity.ProductRecommend;
import cn.temobi.complex.entity.SysColumn;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.BlackListService;
import cn.temobi.complex.service.CmTalenInfoService;
import cn.temobi.complex.service.CmUserFansService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserMessageService;
import cn.temobi.complex.service.LaberService;
import cn.temobi.complex.service.PmProductCollectPicService;
import cn.temobi.complex.service.PmProductDiscussService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmProductPraisesService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.PmTopicPsProductsService;
import cn.temobi.complex.service.ProductRecommendService;
import cn.temobi.complex.service.SysColumnService;
import cn.temobi.complex.service.SysProductTypeInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.PageModel;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.PushUtil;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.UUIDGenerator;
import cn.temobi.core.util.ZipUtil;

import com.salim.cache.CacheHelper;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/product")
public class ProductController extends BoBaseController{

	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="bannerService")
	private BannerService bannerService;
	
	@Resource(name="cmTalenInfoService")
	private CmTalenInfoService cmTalenInfoService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="laberService")
	private LaberService laberService;
	
	@Resource(name="cmUserFansService")
	private CmUserFansService cmUserFansService;
	
	@Resource(name="pmProductPraisesService")
	private PmProductPraisesService pmProductPraisesService;
	
	@Resource(name="cmUserMessageService")
	private CmUserMessageService cmUserMessageService;
	
	@Resource(name="pmProductDiscussService")
	private PmProductDiscussService pmProductDiscussService;
	
	@Resource(name="sysProductTypeInfoService")
	private SysProductTypeInfoService sysProductTypeInfoService;
	
	@Resource(name="blackListService")
	private BlackListService blackListService;
	
	@Resource(name="pmTopicPsProductsService")
	private PmTopicPsProductsService pmTopicPsProductsService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="sysColumnService")
	public SysColumnService sysColumnService;
	
	@Resource(name = "pmProductCollectPicService")
	private PmProductCollectPicService pmProductCollectPicService;
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	/**
	 * 作品与主题关联表
	 */
	@RequestMapping(value = "/theme_product_list")
	public String themeProductList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			String themeId = request.getParameter("themeId");
			if(StringUtil.isNotEmpty(themeId)) {
				map.put("themeId", themeId);
				model.addAttribute("themeId",themeId);
			}
			String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			String createFrom = request.getParameter("createFrom");
			if(StringUtil.isNotEmpty(createFrom)) {
				map.put("createFrom", createFrom.trim());
				model.addAttribute("createFrom",createFrom.trim());
			}
			
			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried1", orderFried);
				model.addAttribute("orderFried",orderFried);
			}else{
				map.put("orderFried", "createdwhen");
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ThemeUsedByPdtDto> pageResult = new Page<ThemeUsedByPdtDto>();
			pageResult = pmProductInfoService.findByPageDtoTo2(page, map);	
			List<ThemeUsedByPdtDto> list = pageResult.getResult();		
			for(ThemeUsedByPdtDto themeUsedByPdtDto: list) {
				themeUsedByPdtDto.setCreatedWhen(themeUsedByPdtDto.getCreatedWhen().substring(0,themeUsedByPdtDto.getCreatedWhen().length()-2));
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
		return "bo/product/theme_used_by_pdt";
	}
	
	
	/**
	 * 评论禁词
	 */
	@RequestMapping(value="/blacklist",method={RequestMethod.GET,RequestMethod.POST})
	public String blacklist(HttpServletRequest request,Model model){
		try {
			String content = request.getParameter("content");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(content)){
				map.put("content", content);
				model.addAttribute("content",content);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = blackListService.findByPage(page,map);
			List<BlackListWord> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo",pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages",pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/product/black_list";
	}
	
	@Resource(name="productRecommendService")
	private ProductRecommendService productRecommendService;


	@RequestMapping(value="/blackedit",method={RequestMethod.GET,RequestMethod.POST})
	public String blackedit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			BlackListWord entity = blackListService.getById(Long.parseLong(id));
			model.addAttribute("entity",entity);
		}
		return "bo/product/black_edit";
	}
	
	@RequestMapping(value="/blacksave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blacksave(HttpServletRequest request,Model model,BlackListWord entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListService.update(entity);
		}else{
			blackListService.save(entity);
		}
		return "";
	}
	
	@RequestMapping(value="/blackdelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String blackdelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			blackListService.delete(Long.parseLong(id));
		}
		return "";
	}
	
	/**
	 * 作品推荐列表
	 */
	@RequestMapping(value="/prlist",method={RequestMethod.GET,RequestMethod.POST})
	public String prlist(HttpServletRequest request,Model model){
		try {
			
			Map<String,Object> columnmap = new HashMap<String, Object>();
			columnmap.put("isOnline", "1");
			List<SysColumn> columnlist = sysColumnService.findByMap(columnmap);
			model.addAttribute("columnlist",columnlist);
			Map<String,String> map = new HashMap<String,String>();
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type))
			{
				map.put("type", type);
				model.addAttribute("type", type);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = productRecommendService.findByPageTwo(page,map);
			List<ProductRecommend> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(ProductRecommend productRecommend:list){
					productRecommend.setCreatedWhen(productRecommend.getCreatedWhen().substring(0,productRecommend.getCreatedWhen().length()-2));
				}
				model.addAttribute("list",list);
				model.addAttribute("pageNo",pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages",pageResult.getTotalPages());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/product/prlist";
	}
	
	@RequestMapping(value="/predit",method={RequestMethod.GET,RequestMethod.POST})
	public String predit(HttpServletRequest request,Model model)
	{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("isOnline", "1");
		List<SysColumn> columnlist = sysColumnService.findByMap(map);
		model.addAttribute("columnlist",columnlist);
		String id = request.getParameter("id");
		String productId = request.getParameter("productId");
		if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
			ProductRecommend productRecommend  = productRecommendService.getById(Long.parseLong(id));
			model.addAttribute("entity",productRecommend);
		}else if(StringUtil.isNotEmpty(productId)){
			ProductRecommend productRecommend  = new ProductRecommend();
			productRecommend.setProductId(Long.parseLong(productId));
			model.addAttribute("entity",productRecommend); 
			
		}
		return "bo/product/predit";
	}
	
	@RequestMapping(value="/prsave",method={RequestMethod.GET,RequestMethod.POST})
	public@ResponseBody int prsave(HttpServletRequest request,Model model,ProductRecommend productRecommend){
		try {
			String id = request.getParameter("id");
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("productId", productRecommend.getProductId());
			map.put("type", productRecommend.getType());
			if(StringUtil.isNotEmpty(id)){
				map.put("id",id);
				
				Long count = (Long)productRecommendService.getCount(map);
				if(count != null && count > 0)
				{
					return 0;
				}
				productRecommendService.update(productRecommend);
			}else{
				
				Long count = (Long)productRecommendService.getCount(map);
				
				if(count != null && count > 0)
				{
					return 0;
				}
				productRecommendService.save(productRecommend);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return 0;
		}
		return 1;
	}
	
	@RequestMapping(value="/prdelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String prdelete(HttpServletRequest request,Model model){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			productRecommendService.delete(id);
		}
		return "";
	}
	
	
	@RequestMapping(value="messageEdit")
	public String messageEdit(HttpServletRequest request, ModelMap model){
		try {
			String userId = request.getParameter("id");
			model.addAttribute("userId",userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "bo/product/messageEdit";
	}
	
	@RequestMapping(value="messageSave")
	public @ResponseBody String messageSave(HttpServletRequest request, ModelMap model){
		String userId = request.getParameter("userId"); 
		String content = request.getParameter("content"); 
		if(StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(content))
		{
			PushUtil.pullOneMessage(userId, content, "15", "", "");
		}
		return "";
	}
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
			String laber = request.getParameter("laber");
			if(StringUtil.isNotEmpty(laber)) {
				map.put("laber", laber);
				model.addAttribute("laber",laber);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
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
			String createFrom = request.getParameter("createFrom");
			if(StringUtil.isNotEmpty(createFrom)) {
				map.put("createFrom", createFrom.trim());
				model.addAttribute("createFrom",createFrom.trim());
			}
			
			String orderFried = request.getParameter("orderFried");
			if(StringUtil.isNotEmpty(orderFried)) {
				map.put("orderFried1", orderFried);
				model.addAttribute("orderFried",orderFried);
			}else{
				map.put("orderFried", "createdwhen");
			}
			String sequence = request.getParameter("sequence");
			if(StringUtil.isNotEmpty(sequence)) {
				map.put("sequence", sequence);
				model.addAttribute("sequence",sequence);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmProductInfoDto> pageResult = new Page<PmProductInfoDto>();
			String isPublic = request.getParameter("isPublic");
			if(StringUtil.isEmpty(isPublic))
			{
				isPublic = "1";
			}
			map.put("audit", "1");
			map.put("isPublic", isPublic);
			model.addAttribute("isPublic", isPublic);
			pageResult = pmProductInfoService.findByPageDtoTo(page, map);
			
			List<PmProductInfoDto> list = pageResult.getResult();
			
			for(PmProductInfoDto pmProductInfoDto: list) {
				pmProductInfoDto.setCreatedWhen(pmProductInfoDto.getCreatedWhen().substring(0,pmProductInfoDto.getCreatedWhen().length()-2));
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
		return "bo/product/list";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/mylist")
	public String mylist(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");//结束时间
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmProductInfoDto> pageResult = new Page<PmProductInfoDto>();
			map.put("orderFried", "createdwhen");
			map.put("audit", "1");
			pageResult = pmProductInfoService.findByPageDto(page, map);
			List<PmProductInfoDto> list = pageResult.getResult();
			for(PmProductInfoDto pmProductInfoDto: list) {
				pmProductInfoDto.setCreatedWhen(pmProductInfoDto.getCreatedWhen().substring(0,pmProductInfoDto.getCreatedWhen().length()-2));
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
		return "bo/product/mylist";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/examinelist")
	public String examinelist(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmProductInfoDto> pageResult = new Page<PmProductInfoDto>();
			map.put("orderFried", "createdwhen");
			map.put("audit", "0");
			map.put("examineRight", "examineRight");
			pageResult = pmProductInfoService.findByPageDto(page, map);
			List<PmProductInfoDto> list = pageResult.getResult();
			for(PmProductInfoDto pmProductInfoDto: list) {
				pmProductInfoDto.setCreatedWhen(pmProductInfoDto.getCreatedWhen().substring(0,pmProductInfoDto.getCreatedWhen().length()-2));
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
		return "bo/product/examinelist";
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/examineupdate", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String examineupdate(HttpServletRequest request, Model model) {
		try {
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id) && StringUtil.isNotEmpty(status)) {
				PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmProductInfo)) {
					if("1".equals(status))
					{
						if(pmProductInfo.getIsPublic() == 1)
						{
							CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
							cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
							cmUserInfoService.update(cmUserInfo);
						}else if(pmProductInfo.getIsPublic() == 2){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("productId", pmProductInfo.getId());
							List<PmTopicPsProducts> list = pmTopicPsProductsService.findByMap(map);
							if(list != null && list.size() > 0)
							{
								PmTopicPsProducts pmTopicPsProducts = list.get(0);
								PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(pmTopicPsProducts.getJoinId());
								pmTopicJoinProducts.setJoinProducts(pmTopicJoinProducts.getJoinProducts()+1);
								pmTopicJoinProductsService.update(pmTopicJoinProducts);
							}
						}
						
						pmProductInfo.setAudit(1);
						pmProductInfoService.update(pmProductInfo);
						
						return "0";
					}else if("2".equals(status))
					{
						if(pmProductInfo.getIsPublic() == 1)
						{
							CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
							cmUserInfo.setProductCount(cmUserInfo.getProductCount()-1);
							cmUserInfoService.update(cmUserInfo);
						}else if(pmProductInfo.getIsPublic() == 2){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("productId", pmProductInfo.getId());
							List<PmTopicPsProducts> list = pmTopicPsProductsService.findByMap(map);
							if(list != null && list.size() > 0)
							{
								PmTopicPsProducts pmTopicPsProducts = list.get(0);
								PmTopicJoinProducts pmTopicJoinProducts = pmTopicJoinProductsService.getById(pmTopicPsProducts.getJoinId());
								pmTopicJoinProducts.setJoinProducts(pmTopicJoinProducts.getJoinProducts()-1);
								pmTopicJoinProductsService.update(pmTopicJoinProducts);
							}
						}
						
						pmProductInfo.setAudit(0);
						pmProductInfoService.update(pmProductInfo);
						
						return "0";
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/batchExamine", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String batchExamine(HttpServletRequest request, Model model) {
		try {
			String ids = request.getParameter("ids");
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(ids) && !"0".equals(ids) && StringUtil.isNotEmpty(status)) {
				String arr[] = ids.split(",");
				for(int i=0;i<arr.length;i++)
				{
					PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(arr[i]));
					if(StringUtil.isNotEmpty(pmProductInfo)) {
						if("1".equals(status))
						{
							pmProductInfo.setAudit(1);
							pmProductInfoService.update(pmProductInfo);
							
						}else if("2".equals(status))
						{
							pmProductInfo.setAudit(0);
							pmProductInfoService.update(pmProductInfo);
							
						}
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/batchUpload", method = {RequestMethod.GET, RequestMethod.POST })
	public String batchUpload(HttpServletRequest request, Model model) {
		try {
			String url = request.getParameter("url");
			if(StringUtil.isNotEmpty(url))
			{
				
				String resourcePath = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "resource_path");
				List<PmProductInfo> list = new ArrayList<PmProductInfo>();
				ZipUtil.deCompressImage(url, resourcePath+"userImage/admin/", resourcePath+"thumbnailImage/admin/",list);
				if(list != null && list.size() > 0)
				{
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("registerFrom", "0");
					map.put("productCount", 0);
					List<CmUserInfo> userlist = cmUserInfoService.findByMap(map);
					if(userlist == null || userlist.size() <= 0 || list.size() > userlist.size())
					{
						Map<String,Object> map2 = new HashMap<String, Object>();
						map2.put("registerFrom", "0");
						userlist = cmUserInfoService.findByMap(map2);
					}
					for(PmProductInfo pmProductInfo:list)
					{
						int b = (int) (Math.random()*userlist.size());
						CmUserInfo cmUserInfo = userlist.get(b);
						pmProductInfo.setUserId(cmUserInfo.getId());
						pmProductInfo.setResourceId(UUIDGenerator.getUUID());
						pmProductInfo.setIsPublic(1);
						pmProductInfo.setAudit(1);
						pmProductInfo.setCreateFrom("0");
						pmProductInfoService.save(pmProductInfo);
						
						cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
						cmUserInfoService.update(cmUserInfo);
					}
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/product/list";
	}
	
	/**
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method = {RequestMethod.GET, RequestMethod.POST })
	public String delete(HttpServletRequest request, Model model) {
		String isPublic = request.getParameter("isPublic");
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
				if(StringUtil.isNotEmpty(pmProductInfo)) {
					pmProductInfoService.delete(pmProductInfo);
					
					CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
					cmUserInfo.setProductCount(cmUserInfo.getProductCount()-1);
					cmUserInfoService.update(cmUserInfo);
				}	
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/product/list?isPublic="+isPublic;
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
			model.addAttribute("entity", pmProductInfo);
		}
		String userId = request.getParameter("userId");
		model.addAttribute("userId", userId);
		String isPublic = request.getParameter("isPublic");
		model.addAttribute("isPublic", isPublic);
		
		List<AllTypeDto> typelist = sysProductTypeInfoService.findAllType(null);
		model.addAttribute("typelist", typelist);
		return "bo/product/edit";
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/jumpEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String jumpEdit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
			if(pmProductInfo.getJumpType() == 1)
			{
				model.addAttribute("jumpContent1", pmProductInfo.getJumpText());
			}else if(pmProductInfo.getJumpType() == 2){
				model.addAttribute("jumpContent2", pmProductInfo.getJumpText());
			}else if(pmProductInfo.getJumpType() == 3){
				model.addAttribute("jumpContent3", pmProductInfo.getJumpText());
			}
			model.addAttribute("entity", pmProductInfo);
		}
		return "bo/product/jump_edit";
	}
	
	/**
	 * 保存
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/jumpSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String jumpSave(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
			String jumpType = request.getParameter("jumpType");
			String jumpContent1 = request.getParameter("jumpContent1");
			String jumpContent2 = request.getParameter("jumpContent2");
			String jumpContent3 = request.getParameter("jumpContent3");
			pmProductInfo.setJumpType(Integer.parseInt(jumpType));
			if("1".equals(jumpType))
			{
				pmProductInfo.setJumpText(jumpContent1);
			}else if("2".equals(jumpType)){
				pmProductInfo.setJumpText(jumpContent2);
			}else if("2".equals(jumpType)){
				pmProductInfo.setJumpText(jumpContent3);
			}
			
			pmProductInfoService.update(pmProductInfo);
		}
		return "bo/product/jump_edit";
	}
	
	
	/**
	 * 更新
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/update", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String update(HttpServletRequest request,ModelMap model,PmProductInfo pmProductInfo,HttpServletResponse response) {
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");
		String isPublic = request.getParameter("isPublic");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
			PmProductInfo oldPmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
			int hosScore = oldPmProductInfo.getHotScore() +pmProductInfo.getHotSystemScore();
			int magicScore = oldPmProductInfo.getMagicScore()+pmProductInfo.getMagicSystemScore();
			oldPmProductInfo.setHotSystemScore(pmProductInfo.getHotSystemScore());
			oldPmProductInfo.setMagicSystemScore(pmProductInfo.getMagicSystemScore());
			oldPmProductInfo.setHotScore(hosScore);
			oldPmProductInfo.setMagicScore(magicScore);
			if("4".equals(isPublic))
			{
				oldPmProductInfo.setTypeId(pmProductInfo.getTypeId());
				oldPmProductInfo.setProductName(pmProductInfo.getProductName());
			}
			oldPmProductInfo.setDescription(pmProductInfo.getDescription());
			oldPmProductInfo.setThumbnail(pmProductInfo.getThumbnail());
			oldPmProductInfo.setUrl(pmProductInfo.getUrl());
			oldPmProductInfo.setProductLabel(pmProductInfo.getProductLabel());
			pmProductInfoService.update(oldPmProductInfo);
		}else{
			pmProductInfo.setResourceId(UUIDGenerator.getUUID());
			if(StringUtil.isNotEmpty(isPublic) && "4".equals(isPublic))
			{
				pmProductInfo.setIsPublic(4);
				pmProductInfo.setUserId(Long.parseLong(userId));
			}else{
				pmProductInfo.setIsPublic(1);
			}
			pmProductInfo.setAudit(1);
			pmProductInfo.setHotScore(pmProductInfo.getHotSystemScore());
			pmProductInfo.setMagicScore(pmProductInfo.getMagicSystemScore());
			pmProductInfo.setCreateFrom("0");
			pmProductInfoService.save(pmProductInfo);
			
			CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductInfo.getUserId());
			cmUserInfo.setProductCount(cmUserInfo.getProductCount()+1);
			cmUserInfoService.update(cmUserInfo);
		}
		return "0";
	}
	
	/**
	 * 
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/zUpdate", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String zUpdate(HttpServletRequest request,ModelMap model,HttpServletResponse response) {
		String id = request.getParameter("id");
		String zProNum = request.getParameter("zProNum");
		String serProNum = request.getParameter("serProNum");
		if(StringUtil.isNotEmpty(id) && !"0".equals(id) && StringUtil.isNotEmpty(zProNum) && !"0".equals(zProNum) && StringUtil.isNotEmpty(serProNum) && !"0".equals(serProNum)) {
			PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
			
	    	pmProductInfo.setSearchCount(pmProductInfo.getSearchCount()+Integer.parseInt(serProNum));
	    	pmProductInfo.setPraiseCount(pmProductInfo.getPraiseCount()+Integer.parseInt(zProNum));
	    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2*Integer.parseInt(zProNum)+Integer.parseInt(serProNum));
	    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+2*Integer.parseInt(zProNum));
	    	pmProductInfoService.update(pmProductInfo);
	    	
	    	CmUserInfo otherUser = cmUserInfoService.getById(pmProductInfo.getUserId());
	    	otherUser.setPraisesCount(otherUser.getPraisesCount()+Integer.parseInt(zProNum));
	    	cmUserInfoService.update(otherUser);
	    	int num = (int) (Math.random()*11)+10; 
	    	Map<String,Object> map = new HashMap<String, Object>();
	    	map.put("limit", num);
	    	List<CmUserInfo> userlist = cmUserInfoService.findRand(map);
			CmUserInfo cmUserInfo = null;
			for(int j=0;j<num;j++)
			{
				cmUserInfo = userlist.get(j);
				
				//查看数据
	    		if(pmProductInfo.getUserId() - cmUserInfo.getId() != 0)
	    		{
	    			CmUserFans cmUserFans = new CmUserFans();
			    	cmUserFans.setUserId(pmProductInfo.getUserId());
			    	cmUserFans.setFansUserId(cmUserInfo.getId());
			    	
			    	List<CmUserFans> fanslist = cmUserFansService.findAll(cmUserFans);
			    	if(fanslist == null || fanslist.size() <= 0)
			    	{
			    		cmUserFansService.save(cmUserFans);
				    	
				    	otherUser.setFansCount(otherUser.getFansCount()+1);
				    	cmUserInfoService.update(otherUser);
				    	
				    	cmUserInfo.setAttentionsCount(cmUserInfo.getAttentionsCount()+1);
				    	cmUserInfoService.update(cmUserInfo);
			    	}
	    		}
	    		
	    		//点赞数据
	    		Map<String,Object> searchMap = new HashMap<String, Object>();
		    	searchMap.put("productId", pmProductInfo.getId());
		    	searchMap.put("type", "0");
		    	searchMap.put("userId", cmUserInfo.getId());
		    	List<PmProductPraises> pralist = pmProductPraisesService.findByMap(searchMap);
		    	if(pralist == null || pralist.size() <= 0)
		    	{
		    		PmProductPraises pmProductPraises = new PmProductPraises();
			    	pmProductPraises.setPraiseUserId(cmUserInfo.getId());
			    	pmProductPraises.setProductId( pmProductInfo.getId());
			    	pmProductPraises.setType(0);
			    	pmProductPraisesService.save(pmProductPraises);
			    	
			    	//添加消息
			    	CmUserMessage cmUserMessage = new CmUserMessage();
			    	cmUserMessage.setContent("赞了");
		    		cmUserMessage.setType(1);
			    	cmUserMessage.setProductId(pmProductInfo.getId());
			    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
			    	cmUserMessage.setUserId(pmProductInfo.getUserId());
			    	cmUserMessage.setSendUserId(cmUserInfo.getId());
			    	cmUserMessageService.save(cmUserMessage);
		    	}
			}
		}
		
		return "0";
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
							laber.setType("4");
							laber.setStatus("1");
							laberService.save(laber);
						}
					}
					PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(rsId));
					pmProductInfo.setProductLabel(rsDesc);
					pmProductInfoService.update(pmProductInfo);
				}
				
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/discussEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String discussEdit(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		model.addAttribute("id", id);
		return "bo/product/discuss_edit";
	}
	
	
	/**
	 * 评论提交
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/discussSave", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody int discussSave(HttpServletRequest request,Model model) {
		String id = request.getParameter("id");
		Map<String,Object> map2 = new HashMap<String, Object>();
		map2.put("registerFrom", "0");
		List<CmUserInfo> userlist = cmUserInfoService.findByMap(map2);
		int b = (int) (Math.random()*userlist.size());
		CmUserInfo cmUserInfo = userlist.get(b);
    	
    	String description = request.getParameter("description");
    	
    	PmProductInfo pmProductInfo = pmProductInfoService.getById(Long.parseLong(id));
    	
    	PmProductDiscuss pmProductDiscuss = new PmProductDiscuss();
    	pmProductDiscuss.setProductId(Long.parseLong(id));
    	pmProductDiscuss.setUserId(pmProductInfo.getUserId());
    	pmProductDiscuss.setDiscussUserId(cmUserInfo.getId());
    	pmProductDiscuss.setContent(HtmlUtils.htmlEscape(description));
    	pmProductDiscuss.setParentDiscussId(0L);
    	pmProductDiscussService.save(pmProductDiscuss);
    	
    	pmProductInfo.setDiscussCount(pmProductInfo.getDiscussCount()+1);
    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()+2);
    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()+10);
    	pmProductInfoService.update(pmProductInfo);
    	
    	cmUserInfo.setDiscussCount(cmUserInfo.getDiscussCount()+1);
    	cmUserInfoService.update(cmUserInfo);
    	
    	CmUserMessage cmUserMessage = new CmUserMessage();
    	cmUserMessage.setContent(description);
		cmUserMessage.setType(4);
		cmUserMessage.setRelId(pmProductDiscuss.getId());
    	cmUserMessage.setProductId(pmProductInfo.getId());
    	cmUserMessage.setProductUrl(pmProductInfo.getThumbnail());
    	cmUserMessage.setUserId(pmProductInfo.getUserId());
    	cmUserMessage.setSendUserId(cmUserInfo.getId());
    	cmUserMessageService.save(cmUserMessage);
    	
    	PushUtil.pullOneMessage(pmProductInfo.getUserId()+"", cmUserInfo.getNickName()+Constant.P_STRING, "12", pmProductInfo.getId()+"", "");
		return 0;
	}
	
	@RequestMapping(value="/discussList", method = {RequestMethod.GET, RequestMethod.POST })
	public String discussList(HttpServletRequest request,Model model) {
		
		try {
			Map<String, String> map = new HashMap<String, String>();
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			String discussUserId = request.getParameter("discussUserId");
			if(StringUtil.isNotEmpty(discussUserId)) {
				map.put("discussUserId", discussUserId);
				model.addAttribute("discussUserId",discussUserId);
			}
			String parentDiscussId = request.getParameter("parentDiscussId");
			if(StringUtil.isNotEmpty(parentDiscussId)) {
				map.put("parentDiscussId", parentDiscussId);
				model.addAttribute("parentDiscussId",parentDiscussId);
			}
			String content = request.getParameter("content");
			if(StringUtil.isNotEmpty(content)) {
				map.put("content", content);
				model.addAttribute("content",content);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmProductDiscuss> pageResult = new Page<PmProductDiscuss>();
			pageResult = pmProductDiscussService.findByPage(page, map);
			List<PmProductDiscuss> list = pageResult.getResult();
				model.addAttribute("list",pageResult.getResult());
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
		} catch (Exception e) {		
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/product/discussList";
	}
	
	
	/**
	 * 消息删除
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/discussDelete", method = {RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String discussDelete(HttpServletRequest request, Model model) {
		try {
			String ids = request.getParameter("ids");
			if(StringUtil.isNotEmpty(ids)) {
				String arr[] = ids.split(",");
				for(String id:arr)
				{
					PmProductDiscuss pmProductDiscuss = pmProductDiscussService.getById(Long.parseLong(id));
					if(StringUtil.isNotEmpty(pmProductDiscuss)){
						pmProductDiscussService.delete(pmProductDiscuss);
						if(pmProductDiscuss.getParentDiscussId() == 0)
						{
							PmProductInfo pmProductInfo = pmProductInfoService.getById(pmProductDiscuss.getProductId());
							if(pmProductInfo.getDiscussCount() <= 1 ){
								pmProductInfo.setDiscussCount(0);
					    	}else{
					    		pmProductInfo.setDiscussCount(pmProductInfo.getDiscussCount()-1);
					    	}
					    	pmProductInfo.setHotScore(pmProductInfo.getHotScore()-2);
					    	pmProductInfo.setMagicScore(pmProductInfo.getMagicScore()-10);
					    	pmProductInfoService.update(pmProductInfo);
					    	
					    	CmUserInfo cmUserInfo = cmUserInfoService.getById(pmProductDiscuss.getDiscussUserId());
					    	if(cmUserInfo.getDiscussCount() <= 1 ){
					    		cmUserInfo.setDiscussCount(0);
					    	}else{
					    		cmUserInfo.setDiscussCount(cmUserInfo.getDiscussCount()-1);
					    	}
					    	cmUserInfoService.update(cmUserInfo);
					    	
					    	Map<String,Object> map = new HashMap<String, Object>();
					    	map.put("parentDiscussId", pmProductDiscuss.getId());
					    	List<PmProductDiscuss> list = pmProductDiscussService.findByMap(map);
					    	if(list != null && list.size() > 0)
					    	{
					    		for(PmProductDiscuss p:list)
					    		{
					    			if(StringUtil.isNotEmpty(p))
					    			{
					    				pmProductDiscussService.delete(p);
					    			}
					    		}
					    	}
						}else{
							PmProductDiscuss parentDiscuss = pmProductDiscussService.getById(pmProductDiscuss.getParentDiscussId());
							parentDiscuss.setReplys(parentDiscuss.getReplys()-1);
							parentDiscuss.setHotScore(parentDiscuss.getHotScore()-2);
					    	pmProductDiscussService.update(parentDiscuss);
						}
					}
					
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "0";
	}
	
	
	@RequestMapping(value="/refreshCache",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String refreshCache(HttpServletRequest request,Model model,BlackListWord entity){
		
		Map<String,Object> columnMap = new HashMap<String, Object>();
		
		if(StringUtil.isNotEmpty(request.getParameter("type"))){
			columnMap.put("type", request.getParameter("type").trim());
		}
		List<SysColumn> columnlist = sysColumnService.findByMap(columnMap);
		List<Long> templist;
		List<PmProductInfoDto> list;
		for(int i=0;i<columnlist.size();i++){
			String type = columnlist.get(i).getType();
			// 1 最热 3 最新 4 主题 5 P图秀 
			if(type.equals("4"))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				Map<String,Object> index4Map = new HashMap<String, Object>();
	    		index4Map.put("type", type);
	    		index4Map.put("limit", 27*30);
				index4Map.put("offset", 0);
	    		List<ProductRecommend> reList = productRecommendService.findByMap(index4Map);
	    		templist = new ArrayList<Long>();
    			if(reList != null && reList.size() > 0)
    			{
    				for(ProductRecommend productRecommend:reList)
    				{
    					templist.add(productRecommend.getProductId());
    				}
    			}
    			CacheHelper.getInstance().set(60*60*24,"index@reList", templist);
				
    			PageModel onePageModel = new PageModel(templist, 27);
    			List<Long> onePageList = onePageModel.getObjects(1);
    			
				map.put("productList", onePageList);
    			map.put("ids", StringUtils.join(templist.toArray(), ","));
    			list = pmProductInfoService.findDtoMap(map);
				CacheHelper.getInstance().set(60*60*24, "index@"+type+"@page", list);
			}else if(type.equals("1"))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("orderFried", "hot_score");
	    		map.put("audit", "1");
				map.put("isPublic", "1");
				map.put("limit", 27*30);
				map.put("offset", 0);
				list = pmProductInfoService.findDtoMap(map);
				
				CacheHelper.getInstance().set(60*60*24, "index@"+type, list);
			}else if(type.equals("2"))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("orderFried", "magic_score");
	    		map.put("audit", "1");
				map.put("isPublic", "1");
				map.put("limit", 27*30);
				map.put("offset", 0);
				list = pmProductInfoService.findDtoMap(map);
				
				CacheHelper.getInstance().set(60*60*24, "index@"+type, list);
			}else if(type.equals("5"))
			{
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("audit", "1");
				map.put("limit", 27*30);
				map.put("offset", 0);
				List<PmTopicPsProducts> tempPsList = pmTopicPsProductsService.findMapIndex(map);
				
				CacheHelper.getInstance().set(60*60*24, "index@"+type, tempPsList);
			}else if(columnlist.get(i).getReleaseType().equals("2")){
				Map<String,Object> map = new HashMap<String, Object>();
				Map<String,Object> index4Map = new HashMap<String, Object>();
				index4Map.put("type",type);
				index4Map.put("limit", 27*30);
				index4Map.put("offset", 0);
				List<ProductRecommend> reList = productRecommendService.findByMap(index4Map);
				templist = new ArrayList<Long>();
				if(reList != null && reList.size() > 0)
				{
					for(ProductRecommend productRecommend:reList)
					{
						templist.add(productRecommend.getProductId());
					}
				}
				list = new ArrayList<PmProductInfoDto>();
				if(templist != null && templist.size() > 0)
				{
					map.put("productList", templist);
					map.put("ids",StringUtils.join(templist.toArray(), ","));
					
					list = pmProductInfoService.findDtoMap(map);
					CacheHelper.getInstance().set(60*60*24, "index@"+type, list);
				}
			}
		}
		
		return "";
	}
	
	
	@RequestMapping(value="/refreshDrCache",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String refreshDrCache(HttpServletRequest request,Model model,BlackListWord entity){
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", "1");
		map.put("type", "show_banner6&4");
		map.put("currentDate", DateUtils.getCurrDateStr());
		int num = bannerService.getCount(map).intValue();
		Map<String,Object> drmap = new HashMap<String, Object>();
		drmap.put("type", "1");
		List<CmTalenInfo> cmTalenInfoList = cmTalenInfoService.findByMap(drmap);
		map.put("limit", num*5+cmTalenInfoList.size());
		map.put("offset", 0);
		map.put("orderFried", "hot_score");
		List<Long> idList = pmProductInfoService.findIdList(map);
		if(idList.size() > 0)
		{
			if(cmTalenInfoList !=null && cmTalenInfoList.size() > 0)
			{
				for(CmTalenInfo cmTalenInfo:cmTalenInfoList)
				{
					idList.remove(cmTalenInfo.getUserId());
				}
			}
			if(cmTalenInfoList !=null && cmTalenInfoList.size() > 0)
			{
				for(CmTalenInfo cmTalenInfo:cmTalenInfoList)
				{
					if(cmTalenInfo.getTalenSeq()-1>=idList.size())
					{
						idList.add(cmTalenInfo.getUserId());
					}else{
						idList.add(cmTalenInfo.getTalenSeq()-1, cmTalenInfo.getUserId());
					}
				}
			}
			map.clear();
			map.put("list", idList);
			map.put("ids", StringUtils.join(idList.toArray(), ","));
			List<CmUserInfoListDto> drlist = cmUserInfoService.findByList(map);
			CacheHelper.getInstance().set(60*60*24, "index@drList", drlist);
		}
		
		List<CmUserInfoListDto> drList = null;
		List<Long> seaid = new ArrayList<Long>();
		Map<String,Object> dtmap = new HashMap<String, Object>();
		dtmap.put("type", "2");
		List<CmTalenInfo> cmTalenInfoList2 = cmTalenInfoService.findByMap(dtmap);
		if(cmTalenInfoList2.size() > 0)
		{
			for(CmTalenInfo cmTalenInfo:cmTalenInfoList2)
			{
				seaid.add(cmTalenInfo.getUserId());
			}
			if(seaid != null && seaid.size() > 0)
			{
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("list", seaid);
				searchMap.put("ids", StringUtils.join(seaid.toArray(), ","));
				drList = cmUserInfoService.findByList(searchMap);
				CacheHelper.getInstance().set(60*60*24, "colouredIndex@drList", drList);
			}
		}
		return "";
	}
	
	
	
	/**
	 * 查看作品相册图片
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/productCollectEdit")
	public String productCollectEdit(HttpServletRequest request, ModelMap model) {
		try {
			String productId = request.getParameter("id");
			if(StringUtil.isNotEmpty(productId)){
				Map<String,Object> param = new HashMap<String,Object>();
				param.put("productId", productId);
				List<PmProductCollectPic> pmProductCollectPicList = pmProductCollectPicService.findByMap(param);
				if(pmProductCollectPicList!=null && pmProductCollectPicList.size() > 0 ){
					model.addAttribute("list",pmProductCollectPicList);
				}else{
					model.addAttribute("list",null);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/product/productCollect_edit";
	}
	
	
	
}
