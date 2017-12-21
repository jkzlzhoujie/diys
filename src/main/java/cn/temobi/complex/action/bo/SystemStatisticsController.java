package cn.temobi.complex.action.bo;

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

import cn.temobi.complex.dto.CountDto;
import cn.temobi.complex.dto.PUserDto;
import cn.temobi.complex.dto.ShareCountDto;
import cn.temobi.complex.dto.ShareDto;
import cn.temobi.complex.entity.Classify;
import cn.temobi.complex.entity.Client;
import cn.temobi.complex.entity.Count;
import cn.temobi.complex.entity.MaterialCount;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.ResourceShare;
import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.ThemeUseCount;
import cn.temobi.complex.service.ClassifyService;
import cn.temobi.complex.service.ClientService;
import cn.temobi.complex.service.CountService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.ResourceShareService;
import cn.temobi.complex.service.StartStatisticsService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/st")
public class SystemStatisticsController extends BoBaseController{

	@Resource(name="clientService")
	private ClientService clientService;
	
	@Resource(name="resourceShareService")
	private ResourceShareService resourceShareService;
	
	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="startStatisticsService")
	private StartStatisticsService startStatisticsService;
	
	@Resource(name="countService")
	private CountService countService;
	
	@Resource(name="classifyService")
	private ClassifyService classifyService;
	
	/**
	 * 使用率前50的热门素材
	 */
	@RequestMapping(value="/material_list",method={RequestMethod.GET,RequestMethod.POST})
	public String materialList(HttpServletRequest request,Model model){
		try {
			String materialType = request.getParameter("materialType");
			Map<String,String> map = new HashMap<String,String>();
			List<MaterialCount> s = new ArrayList(); 
			if(StringUtil.isNotEmpty(materialType)){
				map.put("materialType", materialType);
				model.addAttribute("materialType",materialType);
				s= countService.materialCount(map);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<MaterialCount> pageResult = page.getListPage(page, s);
			List<MaterialCount> list = page.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}
		return "bo/systemstatistics/material_list";
	}
	
	/**
	 * 主题及主题分类排行
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ranklist", method = {RequestMethod.GET, RequestMethod.POST })
	public String ranklist(HttpServletRequest request,Model model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate", startDate);
			}
			String classifyName = request.getParameter("classifyName");
			if(StringUtil.isNotEmpty(classifyName)) {
				Map<String,String> classifyMap = new HashMap<String, String>();
				classifyMap.put("name", classifyName);
				List<Classify> classifyList = classifyService.findByMap(classifyMap);
				if(classifyList != null && classifyList.size() > 0)
				{
					map.put("classifyId", classifyList.get(0).getId()+"");
				}
				model.addAttribute("classifyName", classifyName);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate", endDate);
			}
			List<ThemeUseCount> s = new ArrayList(); 
			String status = request.getParameter("status");
			if("1".equals(status)){
				s = countService.themeUse(map);
				model.addAttribute("status",status);
			}else if("2".equals(status)){
				s = countService.classifyUse(map);
				model.addAttribute("status",status);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ThemeUseCount> pageResult = page.getListPage(page, s);
			List<ThemeUseCount> list = page.getResult();
			model.addAttribute("list",list);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/ranklist";
	}
	
	/**
	 * 每日用户行为统计
	 */
	
	@RequestMapping(value="/infolist",method = {RequestMethod.GET, RequestMethod.POST })
	public String infoList(HttpServletRequest request, ModelMap model){
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			String userId = request.getParameter("userId");
			String startDate = request.getParameter("startDate");
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			String endDate = request.getParameter("endDate");
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
				
				Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
				
				map.put("id", userId);
				model.addAttribute("userId",userId);
				
				//查询结果
				List<Count> s = countService.findCount(map);
				
				//总数
				Long totalZ=0l;
				Long totalIsZ=0l;
				Long totalP=0l;
				Long totalIsP=0l;
				Long totalProduct=0l;
				
				//分页
				if(StringUtil.isNotEmpty(s)){
					
					for(Count count:s){
						totalZ+=count.getzCount();
						totalIsZ+=count.getIsZCount();
						totalP+=count.getpCount();
						totalIsP+=count.getIsPCount();
						totalProduct+=count.getProductCount();
					}
					
					Page<Count> pageResult = page.getListPage(page, s);
					List<Count> list = page.getResult();
					
					model.addAttribute("totalProduct",totalProduct);
					model.addAttribute("totalZ",totalZ);
					model.addAttribute("totalIsZ",totalIsZ);
					model.addAttribute("totalP",totalP);
					model.addAttribute("totalIsP",totalIsP);
					model.addAttribute("list",list);
					model.addAttribute("pageNo",pageResult.getPageNo());
					model.addAttribute("pageSize",pageResult.getPageSize());
					model.addAttribute("totalItems",pageResult.getTotalItems());
					model.addAttribute("totalPages",pageResult.getTotalPages());
					
				}
				
				/**
				Calendar cal = Calendar.getInstance();
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		        Date dBegin = sdf.parse(startDate);
		        Date dEnd = sdf.parse(endDate);
		        List<Date> lDate = findDates(dBegin, dEnd);
		        List<Count> allList = new ArrayList<Count>();	
				//点赞数
				List<Count> zList = countService.getZCount(map);
 				//被赞数			
				List<Count> isZList = countService.getisZCount(map);
				//评论数
				List<Count> pList = countService.getPCount(map);
				//被评论数
				List<Count> isPList = countService.getisPCount(map);
				
				Count count;//临时实体
		        for (Date date : lDate) {
		        	count = new Count();
		            String dat = sdf.format(date);
		            count.setDate(dat);
		            for(Count entity: zList){
		            	if(java.sql.Date.valueOf(dat).before(java.sql.Date.valueOf(entity.getDate()))){
		            		break;
		            	}
		            	if(entity.getDate().equals(dat)){
		            		count.setzCount(entity.getzCount());
		            		zList.remove(entity);
		            		break;
		            	}
		            }
		            
		            for(Count entity: pList){
		            	if(java.sql.Date.valueOf(dat).before(java.sql.Date.valueOf(entity.getDate()))){
		            		break;
		            	}
		            	if(entity.getDate().equals(dat)){
		            		count.setpCount(entity.getpCount());
		            		pList.remove(entity);
		            		break;
		            	}
		            }
		            for(Count entity: isPList){
		            	if(java.sql.Date.valueOf(dat).before(java.sql.Date.valueOf(entity.getDate()))){
		            		break;
		            	}
		            	if(entity.getDate().equals(dat)){
		            		count.setIsPCount(entity.getIsPCount());
		            		isPList.remove(entity);
		            		break;
		            	}
		            }
		            for(Count entity: isZList){
		            	if(java.sql.Date.valueOf(dat).before(java.sql.Date.valueOf(entity.getDate()))){
		            		break;
		            	}
		            	if(entity.getDate().equals(dat)){
		            		count.setIsZCount(entity.getIsZCount());
		            		isZList.remove(entity);
		            		break;
		            	}
		            }
		            //整合
		            allList.add(count);
		       
		        }
		        
		        //分页
		        if(StringUtil.isNotEmpty(allList)){
			
					Page<Count> pageResult = page.getListPage(page, allList);
					List<Count> list = page.getResult();
					if(StringUtil.isNotEmpty(list)){
						model.addAttribute("list",list);
						model.addAttribute("pageNo",pageResult.getPageNo());
						model.addAttribute("pageSize",pageResult.getPageSize());
						model.addAttribute("totalItems",pageResult.getTotalItems());
						model.addAttribute("totalPages",pageResult.getTotalPages());
					}
				}*/
			
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return "bo/error";
		}	
		return "bo/systemstatistics/info_list";
	}
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/us_list")
	public String list(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			String channel = request.getParameter("channel");//渠道
			String channel1 = request.getParameter("channel1");//渠道
			String imei = request.getParameter("imei");//类型
			String osVersion = request.getParameter("osVersion");//类型
			String os = request.getParameter("os");//0:ios 1:android
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel.trim());
				model.addAttribute("channel",channel.trim());
			}
			if(StringUtil.isNotEmpty(channel1)) {
				map.put("channel", channel1.trim());
				model.addAttribute("channel1",channel1.trim());
			}
			if(StringUtil.isNotEmpty(imei)) {
				map.put("imei", imei.trim());
				model.addAttribute("imei",imei.trim());
			}
			if(StringUtil.isNotEmpty(osVersion)) {
				map.put("osVersion", osVersion.trim());
				model.addAttribute("osVersion",osVersion.trim());
			}
			if(StringUtil.isNotEmpty(os)) {
				map.put("os", os.trim());
				model.addAttribute("os",os.trim());
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<Client> pageResult = new Page<Client>();
			pageResult = clientService.findByPage(page, map);
			List<Client> list = pageResult.getResult();
			map.put("type", "1");
			List<SysConfigParam> changeList = sysConfigParamService.findByMap(map);
			for(Client client: list) {
				client.setCreatedWhen(client.getCreatedWhen().substring(0,client.getCreatedWhen().length()-2));
				for(SysConfigParam sysConfigParam:changeList)
				{
					if(sysConfigParam.getEnParamName().equals(client.getChannel()))
					{
						client.setChannel(sysConfigParam.getParamValue());
					}
				}
			}
			model.addAttribute("list",list);
			model.addAttribute("changeList",changeList);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/us_list";
	}
	
	
	/**
	 * 用户分享或者保存记录
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/image_list")
	public String image_list(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			String type = request.getParameter("type");//类型
			String imei = request.getParameter("imei");//类型
			String themeUserId = request.getParameter("themeUserId");//类型
			String themeId = request.getParameter("themeId");//类型
			String channel = request.getParameter("channel");//类型
			String useType = request.getParameter("useType");//类型
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(type)) {
				map.put("type", type);
				model.addAttribute("type",type);
			}
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(useType)) {
				map.put("useType", useType);
				model.addAttribute("useType",useType);
			}
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel);
				model.addAttribute("channel",channel);
			}
			if(StringUtil.isNotEmpty(themeUserId)) {
				map.put("themeUserId", themeUserId.trim());
				model.addAttribute("themeUserId",themeUserId.trim());
			}
			if(StringUtil.isNotEmpty(themeId)) {
				map.put("themeId", themeId.trim());
				model.addAttribute("themeId",themeId.trim());
			}
			if(StringUtil.isNotEmpty(imei)) {
				map.put("imei", imei.trim());
				model.addAttribute("imei",imei.trim());
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ResourceShare> pageResult = new Page<ResourceShare>();
			pageResult = resourceShareService.findByPage(page, map);
			
			map.put("type", "1");
			List<SysConfigParam> changeList = sysConfigParamService.findByMap(map);
			List<ResourceShare> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				List<ShareDto> dtolist = new ArrayList<ShareDto>();
				ShareDto shareDto;
				for(ResourceShare resourceShare: list) {
					shareDto = new ShareDto();
					shareDto.setImei(resourceShare.getImei());
					String shareType = resourceShare.getShareType();
					if(StringUtil.isNotEmpty(shareType))
					{
						if("1".equals(shareType))
						{
							shareDto.setShareType("QQ空间");
						}else if("2".equals(shareType))
						{
							shareDto.setShareType("QQ");
						}else if("3".equals(shareType))
						{
							shareDto.setShareType("新浪微博");
						}else if("4".equals(shareType))
						{
							shareDto.setShareType("腾讯微博");
						}else if("5".equals(shareType))
						{
							shareDto.setShareType("微信");
						}else if("6".equals(shareType))
						{
							shareDto.setShareType("朋友圈");
						}else if("7".equals(shareType))
						{
							shareDto.setShareType("短信");
						}else if("8".equals(shareType))
						{
							shareDto.setShareType("人人");
						}
					}
					shareDto.setAddTime(resourceShare.getCreatedWhen().substring(0,resourceShare.getCreatedWhen().length()-2));
					if(StringUtil.isNotEmpty(resourceShare.getUseType()))
					{
						if("1".equals(resourceShare.getUseType()))
						{
							shareDto.setUseType("分享");
						}else if("2".equals(resourceShare.getUseType()))
						{
							shareDto.setUseType("本地保存");
						}
					}
					map.put("resourceId", resourceShare.getResourceId());
					List<PmProductInfo> reList = pmProductInfoService.findByMap(map);
					if(reList != null && reList.size() > 0)
					{
						shareDto.setImage(reList.get(0).getUrl());
					}
					if(StringUtil.isNotEmpty(resourceShare.getExpressIds()))
					{
						shareDto.setExpressNum(resourceShare.getExpressIds().split(",").length+"");
					}
					if(StringUtil.isNotEmpty(resourceShare.getChartletIds()))
					{
						shareDto.setChartletNum(resourceShare.getChartletIds().split(",").length+"");
					}
					if(StringUtil.isNotEmpty(resourceShare.getYanWritings()))
					{
						shareDto.setYanWritingNum(resourceShare.getYanWritings().split(",").length+"");
					}
					if(StringUtil.isNotEmpty(resourceShare.getWritings()))
					{
						shareDto.setWritingNum(resourceShare.getWritings().split("#@*").length+"");
					}
					Client client = clientService.getById(resourceShare.getClientId());
					if(client != null)
					{
						shareDto.setMachine(client.getMachine());
					}
					shareDto.setThemeId(resourceShare.getThemeId());
					shareDto.setThemeUserId(resourceShare.getThemeUserId());
					shareDto.setImageUrl(resourceShare.getImageUrl());
					
					for(SysConfigParam sysConfigParam:changeList)
					{
						if(sysConfigParam.getEnParamName().equals(resourceShare.getChannel()))
						{
							shareDto.setChannel(sysConfigParam.getParamValue());
						}
					}
					
					dtolist.add(shareDto);
				}
				model.addAttribute("list",dtolist);
				model.addAttribute("pageNo", pageResult.getPageNo());
				model.addAttribute("pageSize", pageResult.getPageSize());
				model.addAttribute("totalItems", pageResult.getTotalItems());
				model.addAttribute("totalPages", pageResult.getTotalPages());
			}
			model.addAttribute("changeList",changeList);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/image_list";
	}
	
	
	/**
	 * 用户分享或者保存记录
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/count_list")
	public String count_list(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			String themeUserId = request.getParameter("themeUserId");//类型
			String themeId = request.getParameter("themeId");//类型
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(themeUserId)) {
				map.put("themeUserId", themeUserId.trim());
				model.addAttribute("themeUserId",themeUserId.trim());
			}
			if(StringUtil.isNotEmpty(themeId)) {
				map.put("themeId", themeId.trim());
				model.addAttribute("themeId",themeId.trim());
			}
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<ShareCountDto> pageResult = new Page<ShareCountDto>();
			pageResult = countService.findByPageTo1(page, map);
			List<ShareCountDto> list = pageResult.getResult();
			if(list != null && list.size() > 0)
			{
				Map<String,Object> seMap = new HashMap<String, Object>();
				for(ShareCountDto shareCountDto:list)
				{
					shareCountDto.setCreatedWhen(shareCountDto.getCreatedWhen().substring(0,shareCountDto.getCreatedWhen().length()-2));
					seMap.put("themeId", shareCountDto.getThemeId());
					shareCountDto.setShareCount(countService.countNum(seMap)+"");
				}
			}
			int total = countService.sumShare(map).intValue();
			map.put("other", "str");
			int total2 = countService.sumShare(map).intValue();
			model.addAttribute("list",list);
			model.addAttribute("total",total);
			model.addAttribute("total2",total2);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/count_list";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/day_count")
	public String dayCount(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			String channel = request.getParameter("channel");//渠道
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(channel)) {
				map.put("channel", channel.trim());
				model.addAttribute("channel",channel.trim());
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<CountDto> pageResult = new Page<CountDto>();
			pageResult = countService.findByPageTo(page, map);
			List<CountDto> list = pageResult.getResult();
			CountDto totalDto = countService.countAll(map);
			java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
			if(list != null && list.size() > 0)
			{
				for(CountDto countDto:list)
				{
					countDto.setLv(df.format((double)countDto.getUserNum()/countDto.getClientNum()*100));
				}
			}
			map.put("type", "1");
			List<SysConfigParam> changeList = sysConfigParamService.findByMap(map);
			model.addAttribute("list",list);
			model.addAttribute("changeList",changeList);
			totalDto.setLv(df.format((double)totalDto.getUserNum()/totalDto.getClientNum()*100));
			model.addAttribute("totalDto",totalDto);
			model.addAttribute("pageNo", pageResult.getPageNo());
			model.addAttribute("pageSize", pageResult.getPageSize());
			model.addAttribute("totalItems", pageResult.getTotalItems());
			model.addAttribute("totalPages", pageResult.getTotalPages());
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/day_count";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pCount")
	public String pCount(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}else{
				map.put("startDate", DateUtils.getCurrDateStr());
				model.addAttribute("startDate",DateUtils.getCurrDateStr());
			}
			
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}else{
				map.put("endDate", DateUtils.getCurrDateStr());
				model.addAttribute("endDate",DateUtils.getCurrDateStr());
			}
			if(StringUtil.isNotEmpty(startDate) || StringUtil.isNotEmpty(endDate)) {
				List<PUserDto> list = countService.userCount(map);
				map.put("status", "status");
				map.put("topicId", "3");
				List<PUserDto> plistTo = countService.pDayCount(map);
				map.put("price", "price");
				List<PUserDto> plist = countService.pDayCount(map);
				List<PUserDto> userPList = countService.countUserP(map);
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
				if(list != null && list.size() > 0)
				{
					for(PUserDto pUserDto:list)
					{
						for(PUserDto temp:plist)
						{
							if(pUserDto.getTime().equals(temp.getTime())){
								pUserDto.setAvgPrice(temp.getAvgPrice());
								pUserDto.setMaxPrice(temp.getMaxPrice());
								pUserDto.setMinPrice(temp.getMinPrice());
								pUserDto.setTotalPrice(temp.getTotalPrice());
								pUserDto.setPriceUserNum(temp.getPriceUserNum());
							}
						}
						
						for(PUserDto temp:plistTo)
						{
							if(pUserDto.getTime().equals(temp.getTime())){
								pUserDto.setpUserNum(temp.getPriceUserNum());
							}
						}
						for(PUserDto temp:userPList)
						{
							if(pUserDto.getTime().equals(temp.getTime())){
								pUserDto.setCountUserP(temp.getCountUserP());
							}
						}
						if(pUserDto.getpUserNum() != 0)
						{
							pUserDto.setPriceRate(df.format((double)pUserDto.getPriceUserNum()/pUserDto.getpUserNum()*100));
						}
					}
				}
				model.addAttribute("list",list);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/p_count";
	}
	
	
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cCount")
	public String cCount(HttpServletRequest request, ModelMap model) {
		try {
			String startDate = request.getParameter("startDate");//开始时间
			String endDate = request.getParameter("endDate");//结束时间
			Map<String, String> map = new HashMap<String, String>();
			if(StringUtil.isNotEmpty(startDate)) {
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}else{
				map.put("startDate", DateUtils.getCurrDateStr());
				model.addAttribute("startDate",DateUtils.getCurrDateStr());
			}
			
			if(StringUtil.isNotEmpty(endDate)) {
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}else{
				map.put("endDate", DateUtils.getCurrDateStr());
				model.addAttribute("endDate",DateUtils.getCurrDateStr());
			}
			
			if(StringUtil.isNotEmpty(startDate) || StringUtil.isNotEmpty(endDate)) {
				List<PUserDto> list = countService.userCount(map);
				map.put("topicId", "1");
				List<PUserDto> plistTo = countService.cDayCount(map);
				map.put("status", "status");
				List<PUserDto> plist = countService.cDayCount(map);
				java.text.DecimalFormat   df   =new   java.text.DecimalFormat("0.00");  
				if(list != null && list.size() > 0)
				{
					for(PUserDto pUserDto:list)
					{
						for(PUserDto temp:plist)
						{
							if(pUserDto.getTime().equals(temp.getTime())){
								pUserDto.setPriceUserNum(temp.getPriceUserNum());
								pUserDto.setTotalPriceTo(temp.getTotalPrice());
							}
						}
						
						for(PUserDto temp:plistTo)
						{
							if(pUserDto.getTime().equals(temp.getTime())){
								pUserDto.setpUserNum(temp.getPriceUserNum());
								pUserDto.setTotalPrice(temp.getTotalPrice());
							}
						}
						pUserDto.setPriceRateTo(df.format((double)pUserDto.getpUserNum()/pUserDto.getUserNum()*100));
						if(pUserDto.getpUserNum() != 0)
						{
							pUserDto.setPriceRate(df.format((double)pUserDto.getPriceUserNum()/pUserDto.getpUserNum()*100));
						}
					}
				}
				model.addAttribute("list",list);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemstatistics/c_count";
	}
}
