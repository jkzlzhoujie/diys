package cn.temobi.complex.action.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.temobi.complex.entity.SysConfigParam;
import cn.temobi.complex.entity.SystemSeting;
import cn.temobi.complex.entity.SystemUser;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SystemSetingService;
import cn.temobi.complex.service.SystemUserService;
import cn.temobi.core.action.BoBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.Page;
import cn.temobi.core.util.StringUtil;

import com.salim.encrypt.MD5;

@SuppressWarnings("serial")
@Controller
public class SystemUserController extends BoBaseController{

	@Resource(name="systemUserService")
	private SystemUserService systemUserService;
	
	@Resource(name="systemSetingService")
	private SystemSetingService systemSetingService;
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	/**
	 * 登录页面
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, ModelMap model) {
		try {
			String error=request.getParameter("error");
			String key = request.getParameter("key");
			Cookie[] cookieArr = request.getCookies();
			if(StringUtil.isNotEmpty(cookieArr)) {
				String username = "";
				String password = "";
				String record = "";
				for (int i = 0; i < cookieArr.length; i++) {
					if(cookieArr[i].getName().equals("username")) {
						cookieArr[i].setMaxAge(0);
						username = cookieArr[i].getValue();
					}
					if(cookieArr[i].getName().equals("password")) {
						cookieArr[i].setMaxAge(0);
						password = cookieArr[i].getValue();
					}
					if(cookieArr[i].getName().equals("record")) { 
						cookieArr[i].setMaxAge(0);
						record = cookieArr[i].getValue();
					}
				}
				if(StringUtil.isNotEmpty(record) && "0".equals(record)
						&& StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password)) {
					model.addAttribute("username",username);
					model.addAttribute("password",password);
					model.addAttribute("record",record);
				}
			}
			if(StringUtil.isNotEmpty(request.getParameter("username"))) {
				model.addAttribute("username",request.getParameter("username"));
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "/bo/login";
	}

	
	/**
	 * 用户登录数据处理
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/submit", method = {RequestMethod.GET, RequestMethod.POST })
	public String submit(HttpServletRequest request,ModelMap model,HttpServletResponse response) {
		try {
			Object sessionUser = request.getSession().getAttribute(Constant.SESSION_USER_INFO);
			Object privilege_user = request.getSession().getAttribute("privilege");
			if(StringUtil.isEmpty(sessionUser) || StringUtil.isEmpty(privilege_user)){//防止刚登录刷新页面再跳到登录页面 
				Map map=new HashMap();
				String userName = request.getParameter("username");
				String password = request.getParameter("password");
				if(StringUtil.isEmpty(userName)){
					model.addAttribute("error", "1");
					return "redirect:/Bo/login";
				}
				model.addAttribute("username", userName.trim());
				map.put("username", userName.trim());
				if(StringUtil.isEmpty(password)){
					model.addAttribute("error", "2");
					return "redirect:/Bo/login";
				}
				map.put("password", MD5.getMD5(password));
				SystemUser user = systemUserService.findUser(map);
				if(StringUtil.isEmpty(user)){
					model.addAttribute("error", "3");
					return "redirect:/Bo/login";
				}
				if(user.getStatus() == 1){
					model.addAttribute("error", "4");
					return "redirect:/Bo/login";
				}
				String cookieType = request.getParameter("cookieType"); //客户端需要保存的cookie类型，1保存密码
				if (cookieType !=null && cookieType.equals("1") ) { //web端勾选保存密码但未勾选自动登录时    添加用户名，密码cookie ,删除自动登录的cookie
					Cookie cookieUserName = new Cookie("username", userName);
					cookieUserName.setPath("/");
					cookieUserName.setMaxAge(Constant.COOKIE_OUTTIME_LONG); //设置cookie的有效时间
					Cookie cookiePassword = new Cookie("password", password);
					cookiePassword.setPath("/");
					cookiePassword.setMaxAge(Constant.COOKIE_OUTTIME_LONG);
					response.addCookie(cookieUserName);
					response.addCookie(cookiePassword);
				}
				String privilege=user.getRights();
				request.getSession().setAttribute(Constant.SESSION_USER_INFO, user);
				request.getSession().setAttribute("privilege",privilege);
				model.addAttribute("privilege",privilege);
			}else{
				model.addAttribute("privilege",privilege_user);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "/bo/index";
	}
	
	/**
	 * 用户列表处理
	 * @param request
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/user_list", method = {RequestMethod.GET, RequestMethod.POST })
	public String user_list(HttpServletRequest request,ModelMap model) {
		try {
			Map<String, String> map = new HashMap<String, String>();
			Page page = getPage(request.getParameter("pageNo"), request.getParameter("pageSize"));
			Page<SystemUser> pageResult = new Page<SystemUser>();
			pageResult = systemUserService.findByPage(page, map);
			List<SystemUser> list = pageResult.getResult();
			for(SystemUser systemUser: list) {
				systemUser.setCreatedWhen(systemUser.getCreatedWhen().substring(0,systemUser.getCreatedWhen().length()-2));
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
		return "bo/systemuser/user_list";
	}
	
	/**
	 * 用户注册数据处理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_save", method = {RequestMethod.GET, RequestMethod.POST })
	public String user_save(HttpServletRequest request,ModelMap model) {
		try {
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			SystemUser user = new SystemUser();
			user.setRealName(request.getParameter("realName"));
			user.setStatus(0);
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				user.setId(Long.valueOf(request.getParameter("id")));
				systemUserService.update(user);
			}else {
				String password = request.getParameter("password");
				String username=request.getParameter("username");
				if(StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password)) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("username", username);
					SystemUser systemUser=systemUserService.findUser(map);
					if(StringUtil.isNotEmpty(systemUser)) {//判断账号是否被注册
						model.addAttribute("error", "此账号已注册！");
						return "redirect:/Bo/regist";
					}
					user.setRights("pwd_edit");
					user.setUsername(username);
					user.setPassword(MD5.getMD5(password));
					systemUserService.save(user);
				}
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user_list";
	}
	
	/**
	 * 删除处理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_del", method = {RequestMethod.GET, RequestMethod.POST })
	public String user_del(HttpServletRequest request,ModelMap model)
	{
		try {
			String id = request.getParameter("id");
			String pageSize = request.getParameter("pageSize");
			String pageNo = request.getParameter("pageNo");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				systemUserService.delete(Long.valueOf(id));
			}
			model.addAttribute("pageNo", pageNo);
			model.addAttribute("pageSize", pageSize);
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/user_list";
	}
	
	/**
	 * 判断账号是否被占用
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/user_verify", method = {RequestMethod.GET, RequestMethod.POST })
	public void user_verify(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		try {
			String username = request.getParameter("username");
			if(StringUtil.isNotEmpty(username)) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username);
				SystemUser systemUser=systemUserService.findUser(map);
				if(StringUtil.isNotEmpty(systemUser)) {//判断账号是否被注册
					model.addAttribute("error", "此账号已注册！");
					response.getWriter().print(false);
				}else{
					response.getWriter().print(true);
				}
			}
		}catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * 编辑处理
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/edit_user", method = {RequestMethod.GET, RequestMethod.POST })
	public String edit(HttpServletRequest request,ModelMap model) {
		try {
			String id = request.getParameter("id");
			if(StringUtil.isNotEmpty(id) && !"0".equals(id)) {
				SystemUser user=systemUserService.getById(Long.valueOf(id));
				model.addAttribute("user",user);
			}	
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemuser/user_edit";
	}
	
	/**
	 * 修改密码
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pwd_edit", method = {RequestMethod.GET, RequestMethod.POST })
	public String updateToPwd(HttpServletRequest request,ModelMap model) {
		SystemUser user = (SystemUser) request.getSession().getAttribute(Constant.SESSION_USER_INFO);
		String password = request.getParameter("password");
		try {
			if(StringUtil.isNotEmpty(user) && StringUtil.isNotEmpty(password)) {
				SystemUser systemUser=new SystemUser();
				systemUser.setPassword(MD5.getMD5(password));
				systemUser.setId(user.getId());
				systemUserService.update(systemUser);
				request.getSession().removeAttribute(Constant.SESSION_USER_INFO);
				return "bo/systemuser/prompt";
			}
		}catch(Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/systemuser/pwd_edit";
	}
	
	/**
	 * 安全退出
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/exit", method = {RequestMethod.GET, RequestMethod.POST })
	public String exit(HttpSession session, HttpServletRequest request, Model model) {
		session.removeAttribute(Constant.SESSION_USER_INFO);
		String key = request.getParameter("key");
		model.addAttribute("key", key);
		return "redirect:/Bo/login";
	}
	
	@RequestMapping(value="/error", method = {RequestMethod.GET, RequestMethod.POST })
	public String error() {
		return "/bo/error";
	}
	
	
	/**
	 * 系统设置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/setting", method = {RequestMethod.GET, RequestMethod.POST })
	public String setting(HttpServletRequest request,ModelMap model) {
		try {
			SystemSeting systemSeting = systemSetingService.getById(null);
			model.addAttribute("entity",systemSeting);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "0");
    		List<SysConfigParam> scpList = sysConfigParamService.findByMap(map);
    		if(scpList != null && scpList.size() > 0)
    		{
    			for(SysConfigParam sysConfigParam:scpList)
    			{
    				if("videoUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("videoUrl",sysConfigParam.getParamValue());
    				}else if("zContent".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("zContent",sysConfigParam.getParamValue());
    				}else if("feebackContent".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("feebackContent",sysConfigParam.getParamValue());
    				}else if("shareNum".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("shareNum",sysConfigParam.getParamValue());
    				}else if("godPUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("godPUrl",sysConfigParam.getParamValue());
    				}else if("pNum".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("pNum",sysConfigParam.getParamValue());
    				}else if("h5Url".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("h5Url",sysConfigParam.getParamValue());
    				}else if("privateUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("privateUrl",sysConfigParam.getParamValue());
    				}else if("promoteStr".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("promoteStr",sysConfigParam.getParamValue());
    				}
    				else if("playUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("playUrl",sysConfigParam.getParamValue());
    				}else if("iosDownUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("iosDownUrl",sysConfigParam.getParamValue());
    				}else if("iosShopUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("iosShopUrl",sysConfigParam.getParamValue());
    				}else if("iosShareUrl".equals(sysConfigParam.getEnParamName()))
    				{
    					model.addAttribute("iosShareUrl",sysConfigParam.getParamValue());
    				}
    				
    			}
    		}
			
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "bo/setting";
	}
	
	/**
	 * 系统设置
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/settingSave", method = {RequestMethod.GET, RequestMethod.POST })
	public String setting(HttpServletRequest request,ModelMap model,SystemSeting systemSeting) {
		try {
			SystemSeting oldSystemSeting = systemSetingService.getById(null);
			systemSeting.setThemeNum(oldSystemSeting.getThemeNum());
			systemSeting.setClassifyNum(oldSystemSeting.getClassifyNum());
			systemSetingService.update(systemSeting);
			
			String videoUrl = request.getParameter("videoUrl");
			String zContent = request.getParameter("zContent");
			String feebackContent = request.getParameter("feebackContent");
			String shareNum = request.getParameter("shareNum");
			String godPUrl = request.getParameter("godPUrl");
			String pNum = request.getParameter("pNum");
			String h5Url = request.getParameter("h5Url");
			String privateUrl = request.getParameter("privateUrl");
			String promoteStr = request.getParameter("promoteStr");
			String playUrl = request.getParameter("playUrl");
			String iosDownUrl = request.getParameter("iosDownUrl");
			String iosShopUrl = request.getParameter("iosShopUrl");
			String iosShareUrl = request.getParameter("iosShareUrl");
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("type", "0");
    		List<SysConfigParam> scpList = sysConfigParamService.findByMap(map);
    		if(scpList != null && scpList.size() > 0)
    		{
    			for(SysConfigParam sysConfigParam:scpList)
    			{
    				sysConfigParamService.delete(sysConfigParam);
    			}
    		}
    		SysConfigParam sysConfigParam = new SysConfigParam();
			sysConfigParam.setType("0");
			if(StringUtil.isNotEmpty(videoUrl))
			{
				sysConfigParam.setEnParamName("videoUrl");
				sysConfigParam.setParamValue(videoUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(zContent))
			{
				sysConfigParam.setEnParamName("zContent");
				sysConfigParam.setParamValue(zContent);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(feebackContent))
			{
				sysConfigParam.setEnParamName("feebackContent");
				sysConfigParam.setParamValue(feebackContent);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(shareNum))
			{
				sysConfigParam.setEnParamName("shareNum");
				sysConfigParam.setParamValue(shareNum);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(godPUrl))
			{
				sysConfigParam.setEnParamName("godPUrl");
				sysConfigParam.setParamValue(godPUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(pNum))
			{
				sysConfigParam.setEnParamName("pNum");
				sysConfigParam.setParamValue(pNum);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(h5Url))
			{
				sysConfigParam.setEnParamName("h5Url");
				sysConfigParam.setParamValue(h5Url);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(privateUrl))
			{
				sysConfigParam.setEnParamName("privateUrl");
				sysConfigParam.setParamValue(privateUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(promoteStr))
			{
				sysConfigParam.setEnParamName("promoteStr");
				sysConfigParam.setParamValue(promoteStr);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(playUrl))
			{
				sysConfigParam.setEnParamName("playUrl");
				sysConfigParam.setParamValue(playUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(iosDownUrl))
			{
				sysConfigParam.setEnParamName("iosDownUrl");
				sysConfigParam.setParamValue(iosDownUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(iosShopUrl))
			{
				sysConfigParam.setEnParamName("iosShopUrl");
				sysConfigParam.setParamValue(iosShopUrl);
				sysConfigParamService.save(sysConfigParam);
			}
			if(StringUtil.isNotEmpty(iosShareUrl))
			{
				sysConfigParam.setEnParamName("iosShareUrl");
				sysConfigParam.setParamValue(iosShareUrl);
				sysConfigParamService.save(sysConfigParam);
			}
		}catch (Exception e) {
			log.error(e.getMessage());
			return "bo/error";
		}
		return "redirect:/Bo/setting";
	}
}
