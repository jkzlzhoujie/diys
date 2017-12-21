package cn.temobi.complex.action.bo;

import java.text.NumberFormat;
import java.util.ArrayList;
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

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dto.AccountUserBoDto;
import cn.temobi.complex.dto.OrderFinanceDto;
import cn.temobi.complex.entity.AccountRedPacketLog;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.AccountWithdraw;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.CmUserPrivilege;
import cn.temobi.complex.entity.CmUserSign;
import cn.temobi.complex.entity.CmUserSignLog;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmCommodityInfo;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SysPackage;
import cn.temobi.complex.entity.SysPrivilege;
import cn.temobi.complex.entity.SysPrivilegeContent;
import cn.temobi.complex.entity.SysPrivilegeContentType;
import cn.temobi.complex.entity.SysPrivilegePackage;
import cn.temobi.complex.service.AccountRedPacketLogService;
import cn.temobi.complex.service.AccountUserBuyService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.AccountWithdrawService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.CmUserPrivilegeService;
import cn.temobi.complex.service.CmUserSignLogService;
import cn.temobi.complex.service.CmUserSignService;
import cn.temobi.complex.service.OrderService;
import cn.temobi.complex.service.PmCommodityInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.SysPrivilegeContentService;
import cn.temobi.complex.service.SysPrivilegeContentTypeService;
import cn.temobi.complex.service.SysPrivilegePackageService;
import cn.temobi.complex.service.SysPrivilegeService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/account")
public class AccountUserController extends BoBaseController{

	@Resource(name="accountUserService")
	private AccountUserService accountUserService;
	
	@Resource(name="accountWalletLogService")
	private AccountWalletLogService accountWalletLogService;
	
	@Resource(name="accountUserBuyService")
	private AccountUserBuyService accountUserBuyService;
	
	@Resource(name="accountRedPacketLogService")
	private AccountRedPacketLogService accountRedPacketLogService;
	
	@Resource(name="accountWithdrawService")
	private AccountWithdrawService accountWithdrawService;
	
	@Resource(name="cmUserSignService")
	private CmUserSignService cmUserSignService;
	
	@Resource(name="cmUserSignLogService")
	private CmUserSignLogService cmUserSignLogService;
	
	@Resource(name="orderService")
	private OrderService orderService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="pmCommodityInfoService")
	private PmCommodityInfoService pmCommodityInfoService;
	
	@Resource(name="cmUserPrivilegeService")
	private CmUserPrivilegeService  cmUserPrivilegeService;
	
	@Resource(name="sysPrivilegeService")
	private SysPrivilegeService  sysPrivilegeService;
	
	@Resource(name="sysPrivilegePackageService")
	private SysPrivilegePackageService sysPrivilegePackageService;
	
	@Resource(name="sysPrivilegeContentService")
	private SysPrivilegeContentService sysPrivilegeContentService;
	
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&& !"0".equals(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
			String channel = request.getParameter("channel");//渠道
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel.trim());
				model.addAttribute("channel",channel.trim());
			}
			String os = request.getParameter("os");//渠道
			if(StringUtil.isNotEmpty(os)) {
				map.put("os", os.trim());
				model.addAttribute("os",os.trim());
			}
			String mobile = request.getParameter("mobile");//结束时间
			if(StringUtil.isNotEmpty(mobile)) {
				map.put("mobile", mobile);
				model.addAttribute("mobile",mobile);
			}
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
			Page<AccountUserBoDto> pageResult = new Page<AccountUserBoDto>();
			pageResult = accountUserService.findByPageBo(page, map);
			List<AccountUserBoDto> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/user_list";
	}
	
	@RequestMapping(value = "/walletList")
	public String walletList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String accountBuyId = request.getParameter("accountBuyId");
			if(StringUtil.isNotEmpty(accountBuyId)&& !"0".equals(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			String orderNo = request.getParameter("orderNo");
			if(StringUtil.isNotEmpty(orderNo)) {
				map.put("likeOrderNo", orderNo.trim());
				model.addAttribute("orderNo",orderNo.trim());
			}
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type.trim());
				model.addAttribute("type",type.trim());
			}
			String havaType = request.getParameter("havaType");
			if(StringUtil.isNotEmpty(havaType)) {
				map.put("havaType", havaType);
				model.addAttribute("havaType",havaType);
			}
			String useType = request.getParameter("useType");
			if(StringUtil.isNotEmpty(useType)) {
				map.put("useType", useType);
				model.addAttribute("useType",useType);
			}
			String payType = request.getParameter("payType");
			if(StringUtil.isNotEmpty(payType)) {
				map.put("payType", payType);
				model.addAttribute("payType",payType);
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
			map.put("status", "1");
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<AccountWalletLog> pageResult = new Page<AccountWalletLog>();
			pageResult = accountWalletLogService.findByPage(page, map);
			List<AccountWalletLog> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		
			Number totalPrice = accountWalletLogService.sumPrice(map);
			model.addAttribute("totalPrice",totalPrice.doubleValue());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/wallet_list";
	}
	
	@RequestMapping(value = "/buyList")
	public String buyList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&& !"0".equals(id)) {
				map.put("id", id);
				model.addAttribute("id",id);
			}
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)&& !"0".equals(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			String price = request.getParameter("price");
			if(StringUtil.isNotEmpty(price)) {
				map.put("price", price.trim());
				model.addAttribute("price",price.trim());
			}
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type.trim());
				model.addAttribute("type",type.trim());
			}
			String payType = request.getParameter("payType");
			if(StringUtil.isNotEmpty(payType)) {
				map.put("payType", payType);
				model.addAttribute("payType",payType);
			}
			String status = request.getParameter("status");
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
			Page<AccountUserBuy> pageResult = new Page<AccountUserBuy>();
			pageResult = accountUserBuyService.findByPage(page, map);
			List<AccountUserBuy> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
			Number totalPrice = accountUserBuyService.sumPrice(map);
			model.addAttribute("totalPrice",totalPrice.doubleValue());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/buy_list";
	}
	
	
	@RequestMapping(value = "/redpacketList")
	public String redpacketList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)&& !"0".equals(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type.trim());
				model.addAttribute("type",type.trim());
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
			Page<AccountRedPacketLog> pageResult = new Page<AccountRedPacketLog>();
			pageResult = accountRedPacketLogService.findByPage(page, map);
			List<AccountRedPacketLog> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
			Number totalPrice = accountRedPacketLogService.sumPrice(map);
			if(StringUtil.isEmpty(totalPrice))
			{
				totalPrice = 0;
			}
			model.addAttribute("totalPrice",totalPrice.doubleValue());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/redpacket_list";
	}
	
	@RequestMapping(value = "/withdrawList")
	public String withdrawList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String account = request.getParameter("account");
			if(StringUtil.isNotEmpty(account)) {
				map.put("account", account.trim());
				model.addAttribute("account",account.trim());
			}
			String financeStatus = request.getParameter("financeStatus");
			if(StringUtil.isNotEmpty(financeStatus)) {
				map.put("financeStatus", financeStatus.trim());
				model.addAttribute("financeStatus",financeStatus.trim());
			}
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			String payType = request.getParameter("payType");
			if(StringUtil.isNotEmpty(payType)) {
				map.put("payType", payType);
				model.addAttribute("payType",payType);
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
			Page<AccountWithdraw> pageResult = new Page<AccountWithdraw>();
			pageResult = accountWithdrawService.findByPage(page, map);
			List<AccountWithdraw> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
			Number totalPrice = accountWithdrawService.sumPrice(map);
			model.addAttribute("totalPrice",totalPrice.doubleValue());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/withdraw_list";
	}
	
	
	/**
	 * 编辑
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/examineEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				AccountWithdraw accountWithdraw = accountWithdrawService.getById(Long.parseLong(id));
				model.addAttribute("entity", accountWithdraw);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/examine_edit";
	}
	

	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/examineSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String examineSave(HttpServletRequest request,ModelMap model) {
		try {
			String id = request.getParameter("id");
			String remark = request.getParameter("remark");
			String financeStatus = request.getParameter("financeStatus");
			Map<String,Object> passMap = new HashMap<String, Object>();
			passMap.put("id", id);
			passMap.put("remark", remark);
			passMap.put("financeStatus", financeStatus);
			accountWithdrawService.examine(passMap);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/account/withdrawList";
	}
	
	
	@RequestMapping(value = "/signList")
	public String signList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmUserSign> pageResult = new Page<CmUserSign>();
			pageResult = cmUserSignService.findByPage(page, map);
			List<CmUserSign> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/sign_list";
	}
	
	
	@RequestMapping(value = "/signLogList")
	public String signLogList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
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
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmUserSignLog> pageResult = new Page<CmUserSignLog>();
			pageResult = cmUserSignLogService.findByPage(page, map);
			List<CmUserSignLog> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/sign_log_list";
	}
	
	
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

			String orderNo = request.getParameter("orderNo");
			if(StringUtil.isNotEmpty(orderNo)) {
				map.put("orderNo", orderNo);
				model.addAttribute("orderNo",orderNo);
			}
			String accountBuyId = request.getParameter("accountBuyId");
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			String nickName = request.getParameter("nickName");
			if(StringUtil.isNotEmpty(nickName)) {
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			String status = request.getParameter("status");
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			String type = request.getParameter("type");
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type);
				model.addAttribute("type",type);
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
		
		return "bo/account/order_list";
	}
	
	
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/colouredList", method = {RequestMethod.GET, RequestMethod.POST })
	public String colouredList(HttpServletRequest request,Model model) {
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
			String accountBuyId = request.getParameter("accountBuyId");
			String status = request.getParameter("status");
			String channel = request.getParameter("channel");
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel);
				model.addAttribute("channel",channel);
			}
			
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
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
			
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			
			
			Map<String,String> cmUserMap = new HashMap<String, String>();
			cmUserMap.put("userType", "1");//设计师
			long t1 = System.currentTimeMillis();
			List<CmUserInfo> cmUserList = new ArrayList<CmUserInfo>();
			cmUserList = (List<CmUserInfo>) CacheHelper.getInstance().get("cmUserInfo@desinger");//设计师
			if(cmUserList == null || cmUserList.size()==0){
				cmUserList = cmUserInfoService.findByMap(cmUserMap);
				CacheHelper.getInstance().set(60*60*24,"cmUserInfo@desinger", cmUserList);
			}
			
			model.addAttribute("cmUserList", cmUserList);
			long t2 = System.currentTimeMillis();
			map.put("topicId","1");
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			//只有一个topicId参数时 ，直接查总表数量
			pageResult = pmTopicJoinProductsService.findByPageDtoTo(page, map);
			long t3 = System.currentTimeMillis();
			List<PmTopicJoinProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicJoinProducts pmTopicJoinProducts:list)
				{
					if("1".equals(pmTopicJoinProducts.getJoinType()))
					{
						pmTopicJoinProducts.setJoinType("Q版");
					}else if("2".equals(pmTopicJoinProducts.getJoinType()))
					{
						pmTopicJoinProducts.setJoinType("彩绘");
					}else if("1,2".equals(pmTopicJoinProducts.getJoinType())){
						pmTopicJoinProducts.setJoinType(pmTopicJoinProducts.getJoinType().replaceAll("1", "Q版").replaceAll("2", "彩绘"));
					}else if("2,1".equals(pmTopicJoinProducts.getJoinType())){
						pmTopicJoinProducts.setJoinType(pmTopicJoinProducts.getJoinType().replaceAll("1", "Q版").replaceAll("2", "彩绘"));
					}else{
						PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(pmTopicJoinProducts.getJoinType()));
						if(StringUtil.isNotEmpty(pmCommodityInfo))
						{
							pmTopicJoinProducts.setJoinType(pmCommodityInfo.getName());
						}
					}
					pmTopicJoinProducts.setJoinTime(pmTopicJoinProducts.getJoinTime().substring(0,pmTopicJoinProducts.getJoinTime().length()-2));
				}
				model.addAttribute("list",list);
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
			}
			long t4 = System.currentTimeMillis();
			List<PmCommodityInfo> infolist = pmCommodityInfoService.findAll();
			long t5 = System.currentTimeMillis();
			log.error("t2-t1="+(t2-t1)+",t3-t2="+(t3-t2)+",t4-t3="+(t4-t3)+",t5-t4="+(t5-t4));
			model.addAttribute("infolist",infolist);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/coloured_list";
	}
	
	//导出 彩绘订单execl
	@RequestMapping(value = "/exportColouredListExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String execl(HttpSession session,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
			String accountBuyId = request.getParameter("accountBuyId");
			String status = request.getParameter("status");
			String channel = request.getParameter("channel");
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel);
				model.addAttribute("channel",channel);
			}
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
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
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			Map<String,String> cmUserMap = new HashMap<String, String>();
			cmUserMap.put("userType", "1");
			List<CmUserInfo> cmUserList = new ArrayList<CmUserInfo>();
			cmUserList = (List<CmUserInfo>) CacheHelper.getInstance().get("cmUserInfo@desinger");//设计师
			if(cmUserList == null || cmUserList.size()==0){
				cmUserList = cmUserInfoService.findByMap(cmUserMap);
				CacheHelper.getInstance().set(60*60*24,"cmUserInfo@desinger", cmUserList);
			}
			model.addAttribute("cmUserList", cmUserList);
			map.put("topicId","1");
			
			Page page = getPage("1", "500000");
			
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			pageResult = pmTopicJoinProductsService.findByPageDtoTo(page, map);
			
			List<PmTopicJoinProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicJoinProducts pmTopicJoinProducts:list)
				{
					if("1".equals(pmTopicJoinProducts.getJoinType()))
					{
						pmTopicJoinProducts.setJoinType("Q版");
					}else if("2".equals(pmTopicJoinProducts.getJoinType()))
					{
						pmTopicJoinProducts.setJoinType("彩绘");
					}else if("1,2".equals(pmTopicJoinProducts.getJoinType())){
						pmTopicJoinProducts.setJoinType(pmTopicJoinProducts.getJoinType().replaceAll("1", "Q版").replaceAll("2", "彩绘"));
					}else if("2,1".equals(pmTopicJoinProducts.getJoinType())){
						pmTopicJoinProducts.setJoinType(pmTopicJoinProducts.getJoinType().replaceAll("1", "Q版").replaceAll("2", "彩绘"));
					}else{
						PmCommodityInfo pmCommodityInfo = pmCommodityInfoService.getById(Long.parseLong(pmTopicJoinProducts.getJoinType()));
						if(StringUtil.isNotEmpty(pmCommodityInfo))
						{
							pmTopicJoinProducts.setJoinType(pmCommodityInfo.getName());
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
	public void xslpoi(List<PmTopicJoinProducts> list,HttpServletResponse response) {
		//创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("彩绘订单列表");
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);
		
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); //设置水平对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);////设置垂直对齐方式
		
		String[] headers = {"彩绘Id","购买Id","用户ID","用户昵称","需求类型","设计师",
				"派单人","需求","手机号","QQ号","备注","状态","渠道","手机壳类型","收货人","收货地址","时间"};
		
		HSSFCell cell = null;
		//表头
		if(headers!=null && headers.length !=0){
			for(int i=0; i<headers.length; i++){
				cell = row.createCell((short) i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
		}
		
		
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			PmTopicJoinProducts pmTopicJoinProduct = list.get(i);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(pmTopicJoinProduct.getId());
			if(StringUtil.isNotEmpty(pmTopicJoinProduct.getAccountBuyId())){
				row.createCell(1).setCellValue(pmTopicJoinProduct.getAccountBuyId());
			}
			row.createCell(2).setCellValue(pmTopicJoinProduct.getUserId());
			row.createCell(3).setCellValue(pmTopicJoinProduct.getNickName());
			row.createCell(4).setCellValue(pmTopicJoinProduct.getJoinType());
			
			//设计师
			row.createCell(5).setCellValue(pmTopicJoinProduct.getAcceptUserName());
			row.createCell(6).setCellValue(pmTopicJoinProduct.getDistributeUser());
			row.createCell(7).setCellValue(pmTopicJoinProduct.getDescription());
			row.createCell(8).setCellValue(pmTopicJoinProduct.getContact());
			row.createCell(9).setCellValue(pmTopicJoinProduct.getQqContact());
			row.createCell(10).setCellValue(pmTopicJoinProduct.getAcceptRemark());
			if("0".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(11).setCellValue("未支付");
			}else if("1".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(11).setCellValue("已支付");
			}else if("2".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(11).setCellValue("支付失败");
			}else if("3".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(11).setCellValue("已取消");
			}else if("4".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(11).setCellValue("已删除");
			}else if("5".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(11).setCellValue("已退款");
			}
			row.createCell(12).setCellValue(pmTopicJoinProduct.getChannel());
			row.createCell(13).setCellValue(pmTopicJoinProduct.getPhoneShell());
			row.createCell(14).setCellValue(pmTopicJoinProduct.getReceivePerson());
			row.createCell(15).setCellValue(pmTopicJoinProduct.getReceiveAddress());
			if(StringUtil.isNotEmpty(pmTopicJoinProduct.getJoinTime())){
				row.createCell(16).setCellValue(pmTopicJoinProduct.getJoinTime());
			}
		}
		//将文件存到指定位置
		try {
			FileUtil.exportToExcel(wb, response, "彩绘订单列表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 列表
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/pList", method = {RequestMethod.GET, RequestMethod.POST })
	public String pList(HttpServletRequest request,Model model) {
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
			String accountBuyId = request.getParameter("accountBuyId");
			String status = request.getParameter("status");
			String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
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
			
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
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
			
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			map.put("topicId","3");
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			pageResult = pmTopicJoinProductsService.findByPageDtoTo(page, map);
			List<PmTopicJoinProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicJoinProducts pmTopicJoinProducts:list)
				{
					pmTopicJoinProducts.setJoinTime(pmTopicJoinProducts.getJoinTime().substring(0,pmTopicJoinProducts.getJoinTime().length()-2));
//					CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
//					pmTopicJoinProducts.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
//					pmTopicJoinProducts.setNickName(cmUserInfo.getNickName());
					
//					if(StringUtil.isNotEmpty(pmTopicJoinProducts.getAcceptUserId())){
//						CmUserInfo asUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getAcceptUserId());
//						pmTopicJoinProducts.setAcceptUserName(asUserInfo.getNickName());
//					}
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
		return "bo/account/p_list";
	}
	
	//导出 悬赏求P execl
	@RequestMapping(value = "/exportPListExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String exportPListExcel(HttpSession session,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
			String accountBuyId = request.getParameter("accountBuyId");
			String status = request.getParameter("status");
			String productId = request.getParameter("productId");
			if(StringUtil.isNotEmpty(productId)) {
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
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
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
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
			
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			
			if(StringUtil.isNotEmpty(status)) {
				map.put("status", status);
				model.addAttribute("status",status);
			}
			map.put("topicId","3");
			Page page = getPage("1", "500000");
			Page<PmTopicJoinProducts> pageResult = new Page<PmTopicJoinProducts>();
			pageResult = pmTopicJoinProductsService.findByPageDtoTo(page, map);
			List<PmTopicJoinProducts> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(PmTopicJoinProducts pmTopicJoinProducts:list)
				{
					pmTopicJoinProducts.setJoinTime(pmTopicJoinProducts.getJoinTime().substring(0,pmTopicJoinProducts.getJoinTime().length()-2));
					CmUserInfo cmUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getUserId());
					pmTopicJoinProducts.setHeadImageUrl(cmUserInfo.getHeadImageUrl());
					pmTopicJoinProducts.setNickName(cmUserInfo.getNickName());
					
					if(StringUtil.isNotEmpty(pmTopicJoinProducts.getAcceptUserId())){
						CmUserInfo asUserInfo = cmUserInfoService.getById(pmTopicJoinProducts.getAcceptUserId());
						pmTopicJoinProducts.setAcceptUserName(asUserInfo.getNickName());
					}
				}
			}
			if(list.size() > 0)
			{
				this.xslPListpoi(list,response);
			}else{
				return "sorry, no files!";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return "";
	}
	
	//导出审核excel 悬赏求P
	public void xslPListpoi(List<PmTopicJoinProducts> list,HttpServletResponse response) {
		//创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("悬赏求P订单列表");
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);
		
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); //设置水平对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);////设置垂直对齐方式
		
		String[] headers = {"悬赏求Pid","用户ID","用户昵称","获得赏金用户","是否采纳",
				"金额","参与人数","需求","状态","时间"};
		
		HSSFCell cell = null;
		//表头
		if(headers!=null && headers.length !=0){
			for(int i=0; i<headers.length; i++){
				cell = row.createCell((short) i);
				cell.setCellValue(headers[i]);
				cell.setCellStyle(style);
			}
		}
		
		
		for (int i = 0; i < list.size(); i++) {
			row = sheet.createRow((int) i + 1);
			PmTopicJoinProducts pmTopicJoinProduct = list.get(i);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(pmTopicJoinProduct.getId());
			row.createCell(1).setCellValue(pmTopicJoinProduct.getUserId());
			row.createCell(2).setCellValue(pmTopicJoinProduct.getNickName());
			row.createCell(3).setCellValue(pmTopicJoinProduct.getAcceptUserName());
			
			if("0".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(4).setCellValue("未采纳");
			}else if("1".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(4).setCellValue("已采纳");
			}else if("2".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(4).setCellValue("已退回");
			}
			row.createCell(5).setCellValue(pmTopicJoinProduct.getPrice());
			row.createCell(6).setCellValue(pmTopicJoinProduct.getJoinProducts());
			row.createCell(7).setCellValue(pmTopicJoinProduct.getDescription());
			if("0".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(8).setCellValue("未支付");
			}else if("1".equals(pmTopicJoinProduct.getStatus())) {
				row.createCell(8).setCellValue("已支付");
			}else if("2".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(8).setCellValue("支付失败");
			}else if("3".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(8).setCellValue("已取消");
			}else if("4".equals(pmTopicJoinProduct.getStatus())){
				row.createCell(8).setCellValue("已删除");
			}
			if(StringUtil.isNotEmpty(pmTopicJoinProduct.getJoinTime())){
				row.createCell(9).setCellValue(pmTopicJoinProduct.getJoinTime());
			}
		}
		//将文件存到指定位置
		try {
			FileUtil.exportToExcel(wb, response, "悬赏求P订单列表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 退款
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/refund", method = {RequestMethod.GET, RequestMethod.POST })
	public String refund(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id))
			{
				Map<String,Object> passMap = new HashMap<String, Object>();
				passMap.put("accountBuyId", id);
				passMap.put("remark", content);
				pmTopicJoinProductsService.refund(passMap, null);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/p_list";
	}
	
	/**
	 * 退款
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/refundEdit", method = {RequestMethod.GET, RequestMethod.POST })
	public String refundEdit(HttpServletRequest request,Model model) {
		try {
			String id = request.getParameter("id");
			model.addAttribute("id", id);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/refund_edit";
	}
	
	/**
	 * 用户特权列表
	 */
	@RequestMapping(value = "/userPrivilegeList")
	public String userPrivilegeList(HttpServletRequest request, ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)&& !"0".equals(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			String userNickName = request.getParameter("userNickName");
			if(StringUtil.isNotEmpty(userNickName)) {
				map.put("userNickName", userNickName.trim());
				model.addAttribute("userNickName",userNickName.trim());
			}
			String privilegeName = request.getParameter("privilegeName");
			if(StringUtil.isNotEmpty(privilegeName)) {
				map.put("privilegeName", privilegeName);
				model.addAttribute("privilegeName",privilegeName);
			}
			
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CmUserPrivilege> pageResult = new Page<CmUserPrivilege>();
			pageResult = cmUserPrivilegeService.findByPage(page, map);
			List<CmUserPrivilege> list = pageResult.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/userPrivilege_list";
	}
	
	
	@RequestMapping(value = "/userPrivilegeEdit")
	public String userPrivilegeEdit(HttpServletRequest request, ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id))
			{
				CmUserPrivilege cmUserPrivilege = cmUserPrivilegeService.getById(Long.parseLong(id));
				model.addAttribute("entity", cmUserPrivilege);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/account/userPrivilege_edit";
	}
	
	
}
