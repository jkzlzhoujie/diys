package cn.temobi.complex.action.newapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.salim.cache.CacheHelper;

import cn.temobi.complex.dao.PmProductInfoDao;
import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.AccountWalletLog;
import cn.temobi.complex.entity.CirclePost;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.MenuList;
import cn.temobi.complex.entity.Order;
import cn.temobi.complex.entity.PmProductInfo;
import cn.temobi.complex.entity.QqList;
import cn.temobi.complex.entity.SysXuanshangSet;
import cn.temobi.complex.entity.enums.ApiNameEnum;
import cn.temobi.complex.service.AccountRedPacketLogService;
import cn.temobi.complex.service.AccountRedPacketService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.AccountWithdrawService;
import cn.temobi.complex.service.BannerService;
import cn.temobi.complex.service.CmCircleServive;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.MenuListService;
import cn.temobi.complex.service.PmProductInfoService;
import cn.temobi.complex.service.PmTopicJoinProductsService;
import cn.temobi.complex.service.QqListService;
import cn.temobi.complex.service.SysConfigParamService;
import cn.temobi.complex.service.SysXuanshangSetService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.util.OrderUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

@SuppressWarnings("serial")
@Controller
@RequestMapping("/setting")
public class SettingApiController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysConfigParamService")
	private SysConfigParamService sysConfigParamService;
	
	@Resource(name="qqListService")
	public QqListService qqListService;
	
	@Resource(name="menuListService")
	private MenuListService  menuListService;
	
	@Resource(name="sysXuanshangSetService")
	public SysXuanshangSetService sysXuanshangSetService;
	
	
	
	/**
	 * 配置列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/index", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject index(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	return sysConfigParamService.index(passMap, object);
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 获取有效的QQ群
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getQQ", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getQQ(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,String> passMap = new HashMap<String, String>();
			passMap.put("status", "1");
			List<QqList> qqList = new ArrayList<QqList>();
			qqList = qqListService.findByMap(passMap);
			if(qqList!=null && qqList.size()>0){
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				object.setDesc("成功");
				object.setResponse(qqList.get(0));
				return object;
			}else{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("没有数据，请联系客服");
			}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * APP菜单列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/appMenuList", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject appMenuList(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,Object> passMap = new HashMap<String, Object>();
	    	getDefaultPara(request, passMap, null);
	    	
	    	List<MenuList> menuList = new ArrayList<MenuList>();
	    	List<MenuList> templist = (List<MenuList>) CacheHelper.getInstance().get("index@appMenu");
	    	
	    	if(templist==null || templist.size() <= 0){
	    		passMap.put("status", "1");
	    		menuList = menuListService.findByMap(passMap);
	    		for (MenuList menu : menuList) {
	    			menu.setImageUrl(host + menu.getImageUrl());
	    		}
	    		if(menuList!= null && menuList.size() > 0 ){
	    			CacheHelper.getInstance().set(60*60*24,"index@appMenu", menuList);
	    		}
	    	}else{
	    		menuList = 	templist;
	    	}
	    	
			object.setCode(Constant.RESPONSE_SUCCESS_CODE);
			object.setDesc("成功");
			object.setResponse(menuList);
			return object;
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	/**
	 * 获取系统悬赏设置
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getSysXuanshang", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getSysXuanshang(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{
			Map<String,String> passMap = new HashMap<String, String>();
			String type = request.getParameter("type");
			if(StringUtil.isEmpty(type)){
				return object;
			}else{
				passMap.put("type", type);
			}
			List<SysXuanshangSet> list = new ArrayList<SysXuanshangSet>();
			list = sysXuanshangSetService.findByMap(passMap);
			if(list!=null && list.size()>0){
				object.setCode(Constant.RESPONSE_SUCCESS_CODE);
				object.setDesc("成功");
				object.setResponse(list);
				return object;
			}else{
				object.setCode(Constant.RESPONSE_DEFAULT_ERROR);
				object.setDesc("没有数据，请联系客服");
			}
	    	
	    }catch(Exception e) {
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	
	
}


