package cn.temobi.complex.action.bo;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.dto.OrderFinanceDto;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.OrderFinance;
import cn.temobi.complex.entity.PmTopicJoinProducts;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.OrderFinanceService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.FileUtil;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping(value="finance")
public class SystemFinanceController extends BoBaseController{

	@Resource(name="orderFinanceService")
	private OrderFinanceService orderFinanceService;
	
	@Resource(name="pmTopicJoinProductsService")
	private PmTopicJoinProductsService pmTopicJoinProductsService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	
	
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
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			map.put("topicId", "1");
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			String accountBuyId = request.getParameter("accountBuyId");
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			String payType = request.getParameter("payType");
			if(StringUtil.isNotEmpty(payType)) {
				map.put("payType", payType);
				model.addAttribute("payType",payType);
			}
			String price = request.getParameter("price");
			if(StringUtil.isNotEmpty(price)) {
				map.put("price", price);
				model.addAttribute("price",price);
			}
			String balancePrice = request.getParameter("balancePrice");
			if(StringUtil.isNotEmpty(balancePrice)) {
				map.put("balancePrice", balancePrice);
				model.addAttribute("balancePrice",balancePrice);
			}
			String remark = request.getParameter("remark");
			if(StringUtil.isNotEmpty(remark)) {
				map.put("remark", remark);
				model.addAttribute("remark",remark);
			}
			String exStatus = request.getParameter("exStatus");
			if(StringUtil.isNotEmpty(exStatus)) {
				map.put("exStatus", exStatus);
				model.addAttribute("exStatus",exStatus);
			}
			String acceptUserId = request.getParameter("acceptUserId");
			if(StringUtil.isNotEmpty(acceptUserId)) {
				map.put("acceptUserId", acceptUserId);
				model.addAttribute("acceptUserId",acceptUserId);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<OrderFinanceDto> pageResult = new Page<OrderFinanceDto>();
			pageResult = orderFinanceService.findByPage(page, map);
			List<OrderFinanceDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(OrderFinanceDto orderFinanceDto: list) {
					orderFinanceDto.setAddTime(orderFinanceDto.getAddTime().substring(0,orderFinanceDto.getAddTime().length()-2));
					if(StringUtil.isNotEmpty(orderFinanceDto.getAcceptUserId()))
					{
						CmUserInfo cmUserInfo = cmUserInfoService.getById(orderFinanceDto.getAcceptUserId());
						if(StringUtil.isNotEmpty(cmUserInfo))
						{
							orderFinanceDto.setSjNickName(cmUserInfo.getNickName());
						}
					}
				}
			}
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			format.setMinimumFractionDigits(2);
			Number sum = orderFinanceService.getSum(map);
			if(StringUtil.isNotEmpty(sum)) {
				model.addAttribute("sum",format.format(sum));
			}else{
				model.addAttribute("sum","0.00");
			}
			
			Map<String,String> cmUserMap = new HashMap<String, String>();
			cmUserMap.put("userType", "1");
			List<CmUserInfo> cmUserList = cmUserInfoService.findByMap(cmUserMap);
			model.addAttribute("cmUserList", cmUserList);
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/finance/list";
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
			String joinId = request.getParameter("joinId");
			String accountBuyId = request.getParameter("accountBuyId");
			Map<String,String> map = new HashMap<String, String>();
			map.put("joinId", joinId);
			List<OrderFinance> list = orderFinanceService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				model.addAttribute("entity", list.get(0));
			}
			model.addAttribute("joinId", joinId);
			model.addAttribute("accountBuyId", accountBuyId);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/finance/edit";
	}
	

	/**
	 * 新增
	 * @param request
	 * @param model
	 * @param packet
	 * @return
	 */
	@RequestMapping(value="/save", method = {RequestMethod.GET, RequestMethod.POST })
	public String save(HttpServletRequest request,ModelMap model,OrderFinance orderFinance) {
		try {
			String joinId = request.getParameter("joinId") ;
			String accountBuyId = request.getParameter("accountBuyId") ;
			Map<String,String> map = new HashMap<String, String>();
			map.put("joinId", joinId);
			List<OrderFinance> list = orderFinanceService.findByMap(map);
			if(list != null && list.size() > 0)
			{
				orderFinanceService.update(orderFinance);
			}else{
				orderFinance.setJoinId(Long.parseLong(joinId));
				if(StringUtil.isNotEmpty(accountBuyId))
				{
					orderFinance.setAccountBuyId(Long.parseLong(accountBuyId));
				}
				orderFinanceService.save(orderFinance);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/topic/list";
	}
	
	//导出execl
	@RequestMapping(value = "/exportExcel", method = { RequestMethod.GET, RequestMethod.POST })
	public@ResponseBody String execl(HttpSession session,HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		try {
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			map.put("topicId", "1");
			String joinId = request.getParameter("joinId");
			if(StringUtil.isNotEmpty(joinId)) {
				map.put("joinId", joinId);
				model.addAttribute("joinId",joinId);
			}
			String accountBuyId = request.getParameter("accountBuyId");
			if(StringUtil.isNotEmpty(accountBuyId)) {
				map.put("accountBuyId", accountBuyId);
				model.addAttribute("accountBuyId",accountBuyId);
			}
			String payType = request.getParameter("payType");
			if(StringUtil.isNotEmpty(payType)) {
				map.put("payType", payType);
				model.addAttribute("payType",payType);
			}
			String price = request.getParameter("price");
			if(StringUtil.isNotEmpty(price)) {
				map.put("price", price);
				model.addAttribute("price",price);
			}
			String balancePrice = request.getParameter("balancePrice");
			if(StringUtil.isNotEmpty(balancePrice)) {
				map.put("balancePrice", balancePrice);
				model.addAttribute("balancePrice",balancePrice);
			}
			String remark = request.getParameter("remark");
			if(StringUtil.isNotEmpty(remark)) {
				map.put("remark", remark);
				model.addAttribute("remark",remark);
			}
			String exStatus = request.getParameter("exStatus");
			if(StringUtil.isNotEmpty(exStatus)) {
				map.put("exStatus", exStatus);
				model.addAttribute("exStatus",exStatus);
			}
			String acceptUserId = request.getParameter("acceptUserId");
			if(StringUtil.isNotEmpty(acceptUserId)) {
				map.put("acceptUserId", acceptUserId);
				model.addAttribute("acceptUserId",acceptUserId);
			}
			String userId = request.getParameter("userId");
			if(StringUtil.isNotEmpty(userId)) {
				map.put("userId", userId);
				model.addAttribute("userId",userId);
			}
			Page page = getPage("1", "500000");
			Page<OrderFinanceDto> pageResult = new Page<OrderFinanceDto>();
			pageResult = orderFinanceService.findByPage(page, map);
			List<OrderFinanceDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				for(OrderFinanceDto orderFinanceDto: list) {
					orderFinanceDto.setAddTime(orderFinanceDto.getAddTime().substring(0,orderFinanceDto.getAddTime().length()-2));
					if(StringUtil.isNotEmpty(orderFinanceDto.getAcceptUserId()))
					{
						CmUserInfo cmUserInfo = cmUserInfoService.getById(orderFinanceDto.getAcceptUserId());
						if(StringUtil.isNotEmpty(cmUserInfo))
						{
							orderFinanceDto.setSjNickName(cmUserInfo.getNickName());
						}
					}
				}
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
	public void xslpoi(List<OrderFinanceDto> list,HttpServletResponse response) {
		//创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		//在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("订购列表");
		//在sheet中添加表头第0行
		HSSFRow row = sheet.createRow((int) 0);
		
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION); //设置水平对齐方式
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);////设置垂直对齐方式
		
		String[] headers = {"彩绘Id","购买Id","用户ID","价格","支付类型","支付时间",
				"结算金额","审核状态","设计师","备注"};
		
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
			OrderFinanceDto orderFinanceDto = list.get(i);
			//创建单元格，并设置值
			row.createCell(0).setCellValue(orderFinanceDto.getJoinId());
			row.createCell(1).setCellValue(orderFinanceDto.getAccountBuyId());
			row.createCell(2).setCellValue(orderFinanceDto.getUserId());
			row.createCell(3).setCellValue(orderFinanceDto.getPrice());
			if("1".equals(orderFinanceDto.getPayType())) {
				row.createCell(4).setCellValue("微信");
			}else if("2".equals(orderFinanceDto.getPayType())){
				row.createCell(4).setCellValue("支付宝");
			}
			row.createCell(5).setCellValue(orderFinanceDto.getAddTime());
			row.createCell(6).setCellValue(orderFinanceDto.getBalancePrice());
			if("1".equals(orderFinanceDto.getStatus())) {
				row.createCell(7).setCellValue("已审核");
			}else {
				row.createCell(7).setCellValue("未审核");
			}
			row.createCell(8).setCellValue(orderFinanceDto.getSjNickName());
			row.createCell(9).setCellValue(orderFinanceDto.getRemark());
		}
		//将文件存到指定位置
		try {
			FileUtil.exportToExcel(wb, response, "订购列表.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
