package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.temobi.complex.entity.CmCircle;
import cn.temobi.complex.entity.CmCircleProduct;
import cn.temobi.complex.entity.CmCircleUser;
import cn.temobi.complex.entity.CmCircleUserFollow;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.service.CmCircleProductService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmCircleUserFollowService;
import cn.temobi.complex.service.CmCircleUserLogService;
import cn.temobi.complex.service.CmCircleUserService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

@Controller
@RequestMapping("/circle")
public class CircleController extends BoBaseController{

	@Resource(name="cmCircleServive")
	private CmCircleServive cmCircleServive;
	
	@Resource(name="cmCircleProductService")
	private CmCircleProductService cmCircleProductService;
	
	@Resource(name="cmCircleUserService")
	private CmCircleUserService cmCircleUserService;
	
	@Resource(name="cmCircleUserLogService")
	private CmCircleUserLogService cmCircleUserLogService;
	
	@Resource(name="cmCircleUserFollowService")
	private CmCircleUserFollowService cmCircleUserFollowService;

	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;

	@Resource(name="pmProductInfoService")
	private PmProductInfoService pmProductInfoService;

	/**
	 * 兴趣圈列表
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public String list(HttpServletRequest request,Model model){
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String nickName = request.getParameter("nickName");
			String name = request.getParameter("name");
			String depict = request.getParameter("depict");
			String isRecommend = request.getParameter("isRecommend");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			if(StringUtil.isNotEmpty(nickName)){
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(depict)){
				map.put("depict", depict);
				model.addAttribute("depict",depict);
			}
			if(StringUtil.isNotEmpty(isRecommend)){
				map.put("isRecommend", isRecommend);
				map.put("placeRecommend","desc");
				model.addAttribute("isRecommend",isRecommend);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmCircleServive.findByPageTwo(page,map);
			List<CmCircle> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(CmCircle cmCircle:list){
					cmCircle.setAddTime(cmCircle.getAddTime().substring(0, cmCircle.getAddTime().length()-2));
				}
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
		return "bo/circle/list";
	}
	
	@RequestMapping(value="/edit",method={RequestMethod.GET,RequestMethod.POST})
	public String edit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CmCircle cmCircle = cmCircleServive.getById(Long.parseLong(id));
			model.addAttribute("entity",cmCircle);
		}
		return "bo/circle/edit";
	}
	
	@RequestMapping(value="/save",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int save(HttpServletRequest request,CmCircle entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){			
			if("2".equals(entity.getFlag())){
				Map<String,String> map = new HashMap<String,String>();
				map.put("flag", "2");
				map.put("circleId", entity.getId()+"");
				cmCircleProductService.upByCircleId(map);
				cmCircleUserService.upByCircleId(map);
				entity.setImageNum(0);
				entity.setUserNum(0);
				cmCircleServive.update(entity);				
			}else{
				cmCircleServive.updateCm(entity);
			}
		}else{	
			Map<String,String> namemap = new HashMap<String,String>();
			namemap.put("name", entity.getName());
			Long number = cmCircleServive.check(namemap);
			if(number== 0 ){
				CmUserInfo cmUserInfo = cmUserInfoService.getById(entity.getUserId());
				if(StringUtil.isNotEmpty(cmUserInfo))
				{
					entity.setClientId(cmUserInfo.getClientId());
				}else{
					return 2;
				}
				cmCircleServive.create(entity);
			}else{
				return 1;
			}
		}
		return 0;
	}
	
	/**
	@RequestMapping(value="delete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String delete(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				cmCircleServive.delete(Long.parseLong(id));
				cmCircleProductService.delBycircleId(id);
				cmCircleUserService.delBycircleId(id);
				cmCircleUserLogService.delBycircleId(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
	/**
	 * 圈子和作品关联表
	 */
	@RequestMapping(value="/productlist",method={RequestMethod.GET,RequestMethod.POST})
	public String productlist(HttpServletRequest request,Model model){
		try {
			String productId = request.getParameter("productId");
			String name = request.getParameter("name");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(productId)){
				map.put("productId", productId);
				model.addAttribute("productId",productId);
			}
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmCircleProductService.findByPageTwo(page,map);
			List<CmCircleProduct> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(CmCircleProduct cmCircleProduct:list){
					cmCircleProduct.setAddTime(cmCircleProduct.getAddTime().substring(0, cmCircleProduct.getAddTime().length()-2));
				}
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
		return "bo/circle/productlist";
	}
	
	@RequestMapping(value="/productedit",method={RequestMethod.GET,RequestMethod.POST})
	public String productedit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CmCircleProduct cmCircleProduct = cmCircleProductService.getById(Long.parseLong(id));
			model.addAttribute("entity",cmCircleProduct);
		}
		return "bo/circle/productedit";
	}
	
	@RequestMapping(value="/productsave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int productsave(HttpServletRequest request,CmCircleProduct entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmCircleProductService.update(entity);
			//当列表只展示正常状态 的作品！
			if("2".equals(entity.getFlag())){
				CmCircle cmCircle=cmCircleServive.getById(entity.getCircleId());
				cmCircle.setImageNum(cmCircle.getImageNum()-1);
				cmCircleServive.update(cmCircle);
			}
		}else{
			Map<String,String> map = new HashMap<String,String>();
			map.put("circleId", entity.getCircleId().toString());
			map.put("productId", entity.getProductId().toString());
			Long number = cmCircleProductService.check(map);
			//等于0表示表中没有重复的
			if(number== 0 ){			
				CmCircle cmCircle=cmCircleServive.getById(entity.getCircleId());
				if(StringUtil.isNotEmpty(cmCircle)){
					cmCircleProductService.save(entity);
					cmCircle.setImageNum(cmCircle.getImageNum()+1);
					cmCircleServive.update(cmCircle);
				}else{
					return 2;
				}
			}else{
				return 1;
			}
		}
		return 0;
	}
	
	/**
	@RequestMapping(value="productdelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String productdelete(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			String circleId = request.getParameter("circleId");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				cmCircleProductService.delete(Long.parseLong(id));
				CmCircle cmCircle=cmCircleServive.getById(Long.parseLong(circleId));
				cmCircle.setImageNum(cmCircle.getImageNum()-1);
				cmCircleServive.update(cmCircle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
	
	/**
	 * 圈子和用户关联表
	 */
	@RequestMapping(value="/userlist",method={RequestMethod.GET,RequestMethod.POST})
	public String userlist(HttpServletRequest request,Model model){
		try {
			String nickName = request.getParameter("nickName");
			String name = request.getParameter("name");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(nickName)){
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmCircleUserService.findByPageTwo(page,map);
			List<CmCircleUser> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(CmCircleUser cmCircleUser:list){
					cmCircleUser.setAddTime(cmCircleUser.getAddTime().substring(0, cmCircleUser.getAddTime().length()-2));
				}
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
		return "bo/circle/userlist";
	}
	
	@RequestMapping(value="/useredit",method={RequestMethod.GET,RequestMethod.POST})
	public String useredit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CmCircleUser cmCircleUser = cmCircleUserService.getById(Long.parseLong(id));
			model.addAttribute("entity",cmCircleUser);
		}
		return "bo/circle/useredit";
	}
	
	@RequestMapping(value="/usersave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int usersave(HttpServletRequest request,CmCircleUser entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmCircleUserService.update(entity);
			if("2".equals(entity.getFlag())){
				CmCircle cmCircle=cmCircleServive.getById(entity.getCircleId());
				cmCircle.setUserNum(cmCircle.getUserNum()-1);
				cmCircleServive.update(cmCircle);
			}
		}else{
			Map<String,String> map = new HashMap<String,String>();
			map.put("circleId", entity.getCircleId().toString());
			map.put("userId", entity.getUserId().toString());
			Long number = cmCircleUserService.check(map);
			//等于0表示表中没有重复的
			if(number==0){
				CmCircle cmCircle=cmCircleServive.getById(entity.getCircleId());
				if(StringUtil.isNotEmpty(cmCircle)){
					cmCircleUserService.save(entity);
					cmCircle.setUserNum(cmCircle.getUserNum()+1);
					cmCircleServive.update(cmCircle);
				}else{
					return 2;
				}
			}else{
				return 1;
			}
		}
		return 0;
	}
	
	/**
	@RequestMapping(value="userdelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String userdelete(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			String circleId = request.getParameter("circleId");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				cmCircleUserService.delete(Long.parseLong(id));
				CmCircle cmCircle=cmCircleServive.getById(Long.parseLong(circleId));
				cmCircle.setUserNum(cmCircle.getUserNum()-1);
				cmCircleServive.update(cmCircle);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
	
	
	/**
	 * 用户浏览圈子记录
	 */
	/**
	@RequestMapping(value="/userloglist",method={RequestMethod.GET,RequestMethod.POST})
	public String userloglist(HttpServletRequest request,Model model){
		try {
			String nickName = request.getParameter("nickName");
			String name = request.getParameter("name");
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(name)){
				map.put("name", name);
				model.addAttribute("name",name);
			}
			if(StringUtil.isNotEmpty(nickName)){
				map.put("nickName", nickName);
				model.addAttribute("nickName",nickName);
			}
			if(StringUtil.isNotEmpty(startDate)){
				map.put("startDate", startDate);
				model.addAttribute("startDate",startDate);
			}
			if(StringUtil.isNotEmpty(endDate)){
				map.put("endDate", endDate);
				model.addAttribute("endDate",endDate);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmCircleUserLogService.findByPage(page,map);
			List<CmCircleUserLog> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(CmCircleUserLog cmCircleUserLog:list){
					cmCircleUserLog.setAddTime(cmCircleUserLog.getAddTime().substring(0, cmCircleUserLog.getAddTime().length()-2));
				}
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
		return "bo/circle/userloglist";
	}
	
	@RequestMapping(value="/userlogedit",method={RequestMethod.GET,RequestMethod.POST})
	public String userlogedit(HttpServletRequest request,Model model)
	{
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			CmCircleUserLog cmCircleUserLog = cmCircleUserLogService.getById(Long.parseLong(id));
			model.addAttribute("entity",cmCircleUserLog);
		}
		return "bo/circle/userlogedit";
	}
	
	@RequestMapping(value="/userlogsave",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody int userlogsave(HttpServletRequest request,CmCircleUserLog entity){
		String id = request.getParameter("id");
		if(StringUtil.isNotEmpty(id)){
			cmCircleUserLogService.update(entity);
		}else{
			Map<String,String> map = new HashMap<String,String>();
			map.put("circleId", entity.getCircleId().toString());
			map.put("userId", entity.getUserId().toString());
			Long number = (Long)cmCircleUserLogService.check(map);
			//等于0表示表中没有重复的
			if(number==0){
				cmCircleUserLogService.save(entity);
			}else{
				return 1;
			}
		}
		return 0;
	}
	
	
	@RequestMapping(value="userlogdelete",method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String userlogdelete(HttpServletRequest request){
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id)&&!"0".equals(id)){
				cmCircleUserLogService.delete(Long.parseLong(id));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	*/
	
	/**
	 * 关注圈子列表
	 */
	@RequestMapping(value="/followlist",method={RequestMethod.GET,RequestMethod.POST})
	public String followlist(HttpServletRequest request,Model model){
		try {
			String circleId = request.getParameter("circleId");
			Map<String,String> map = new HashMap<String,String>();
			if(StringUtil.isNotEmpty(circleId)){
				map.put("circleId", circleId);
				model.addAttribute("circleId",circleId);
			}
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page pageResult = cmCircleUserFollowService.findByPage(page,map);
			List<CmCircleUserFollow> list = pageResult.getResult();
			if(StringUtil.isNotEmpty(list)){
				for(CmCircleUserFollow cmCircleUserFollow:list){
					cmCircleUserFollow.setAddTime(cmCircleUserFollow.getAddTime().substring(0, cmCircleUserFollow.getAddTime().length()-2));
				}
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
		return "bo/circle/followlist";
	}
}
