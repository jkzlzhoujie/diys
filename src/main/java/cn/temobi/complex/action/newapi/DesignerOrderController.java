package cn.temobi.complex.action.newapi;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import org.apache.commons.codec.binary.Base64;
import org.springframework.util.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tencent.common.MD5;

import cn.temobi.complex.entity.AccountUser;
import cn.temobi.complex.entity.AccountUserBuy;
import cn.temobi.complex.entity.AccountUserLog;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.service.AccountUserBuyService;
import cn.temobi.complex.service.AccountUserService;
import cn.temobi.complex.service.AccountWalletLogService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.core.action.ClientApiBaseController;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.CommonUtil;
import cn.temobi.core.util.IpUtil;
import cn.temobi.core.util.PropertiesHelper;
import cn.temobi.core.util.RandomUtils;
import cn.temobi.core.util.StringUtil;

/**
 * 对外及外部项目接入  接口
 * @author zhouj
 * 
 * 2016年5月18日10:57:55
 */
@SuppressWarnings("serial")
@Controller
@RequestMapping("/designerOrder")
public class DesignerOrderController extends ClientApiBaseController{
	
	String host = PropertiesHelper.getProperty(Constant.SERVER_INFORMATION, "host_url"); 
	
	@Resource(name="sysParameterService")
	private SysParameterService sysParameterService;
	
	@Resource(name="cmUserInfoService")
	private CmUserInfoService cmUserInfoService;
	
	@Resource(name="accountWalletLogService")
	private AccountWalletLogService accountWalletLogService;
	
	@Resource(name="accountUserService")
	private AccountUserService accountUserService;
	
	@Resource(name="accountUserBuyService")
	private AccountUserBuyService accountUserBuyService;
	
	/**
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDuoBaoUrl", method = {RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody ResponseObject getDuoBaoUrl(HttpServletRequest request) {
		ResponseObject object = initResponseObject();
		object.setCode(Constant.RESPONSE_PARAMS_ERROR);
		object.setDesc("参数错误");
	    try{

			Map<String,Object> map = new HashMap<String, Object>();
			getDefaultPara(request, map, null);
			CmUserInfo cmUserInfo = (CmUserInfo) map.get("cmUserInfo");
			map.put("userId", cmUserInfo.getId());
				
			object.setResponse("");
    		object.setCode(Constant.RESPONSE_SUCCESS_CODE);
    		object.setDesc("成功");
	    	
    		
	    }catch(Exception e) {
	    	e.printStackTrace();
			log.error(e.getMessage());
			object.setCode(Constant.RESPONSE_ERROR_CODE);
			object.setDesc("服务器有点忙，请稍后再试");
		}
	    return object;
	}
	
	

	    public static void main(String[] args) throws Exception {

	        String cid = "2016001";
	        String uid = "15321";
	        String mobile = "18206060967";


			 System.out.println("");
			

	    }
	
	
}
