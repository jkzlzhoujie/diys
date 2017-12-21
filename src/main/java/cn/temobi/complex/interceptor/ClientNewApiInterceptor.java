package cn.temobi.complex.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import cn.temobi.complex.entity.BlackListUserEquipment;
import cn.temobi.complex.entity.CmUserInfo;
import cn.temobi.complex.entity.SysParameter;
import cn.temobi.complex.schedule.CircleJob;
import cn.temobi.complex.service.BlackListUserEquipmentService;
import cn.temobi.complex.service.CmUserInfoService;
import cn.temobi.complex.service.SysParameterService;
import cn.temobi.core.common.Constant;
import cn.temobi.core.common.ResponseObject;
import cn.temobi.core.common.SysParamConstant;
import cn.temobi.core.util.DateUtils;
import cn.temobi.core.util.SpringContextUtils;
import cn.temobi.core.util.StringUtil;
import cn.temobi.core.util.ValidateUtil;





public class ClientNewApiInterceptor extends HandlerInterceptorAdapter {
	private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private String[] checkAccessUrls;
	private String[] noCheckAccessUrls;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private static Logger log = LoggerFactory.getLogger(ClientNewApiInterceptor.class);
    
    CmUserInfoService cmUserInfoService = (CmUserInfoService) SpringContextUtils.getBean("cmUserInfoService");
    BlackListUserEquipmentService blackListUserEquipmentService = (BlackListUserEquipmentService) SpringContextUtils.getBean("blackListUserEquipmentService");
    SysParameterService sysParameterService = (SysParameterService) SpringContextUtils.getBean("sysParameterService");
    
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 String currentUrl = urlPathHelper.getLookupPathForRequest(request);
		 if(!ValidateUtil.validateData(request))
	 	 {
	 		 ResponseObject object = initResponseObject();
    		 object.setCode(Constant.RESPONSE_ERROR_10008);
    		 object.setDesc("黑名单用户,请联系客服！");
    		 PrintWriter out = response.getWriter();
    		 out.println(JSONObject.fromObject(object));  
            return false; 
	 	 }
		 String tokenId = request.getHeader("tokenId");
		if(StringUtil.isEmpty(tokenId))
		{
			tokenId = request.getParameter("tokenId");
		}
		
     	CmUserInfo cmUserInfo = null;
     	if(StringUtil.isNotEmpty(tokenId)){
     		Map<String, Object> searchMap = new HashMap<String, Object>();
 			searchMap.put("tokenId", tokenId.trim());
 			List<CmUserInfo> list = cmUserInfoService.findByMap(searchMap);
 			if (list != null && list.size() > 0) {
 				cmUserInfo = list.get(0);
 				request.setAttribute("cmUserInfo",cmUserInfo);
 			}
 		}
     	
     	
     	
		 if (!isProtected(request, currentUrl,cmUserInfo)) {
			if( checkBlack(currentUrl,cmUserInfo) ){
				 ResponseObject object = initResponseObject(); 
				 object.setCode(Constant.RESPONSE_ERROR_10008);
	    		 object.setDesc("黑名单用户,请联系客服！");
	    		 PrintWriter out = response.getWriter();
	    		 out.println(JSONObject.fromObject(object));  
	    		 return false;
			}
             return true;
         }else {
        	if(StringUtil.isNotEmpty(tokenId)){
          		if(cmUserInfo!=null){
          			log.error(currentUrl + " 接口报  =tokenId =" + tokenId + "，用户="+cmUserInfo.getId() );
          		}else{
          			log.error(currentUrl + " 接口报  =tokenId =" + tokenId + "，用户 没有找到！" );
          		}
      		}else{
      			log.error(currentUrl + " 接口报 10005====tokenId is null"  );
      		}
        	 ResponseObject object = initResponseObject();
    		 object.setCode(Constant.RESPONSE_ERROR_10005);
    		 object.setDesc("请重新登录！");
    		 PrintWriter out = response.getWriter();
    		 out.println(JSONObject.fromObject(object));  
            return false;
         }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	super.afterCompletion(request, response, handler, ex);
    }
    
    public void setPathMatcher(PathMatcher pathMatcher) {
        Assert.notNull(pathMatcher, "PathMatcher must not be null");
        this.pathMatcher = pathMatcher;
    }




    /**
     * @param checkAccessUrls the checkAccessUrls to set
     */
    public void setCheckAccessUrls(String[] checkAccessUrls) {
        Assert.notNull(checkAccessUrls, "checkAccessUrls must not be null");
        this.checkAccessUrls = checkAccessUrls;
    }


    /**
     * @param noCheckAccessUrls the noCheckAccessUrls to set
     */
    public void setNoCheckAccessUrls(String[] noCheckAccessUrls) {
        this.noCheckAccessUrls = noCheckAccessUrls;
    }

    private boolean isProtected(HttpServletRequest request, String urlPath,CmUserInfo cmUserInfo) {
        if (noCheckAccessUrls != null) {
            for (int i = 0; i < this.noCheckAccessUrls.length; i++) {
                String registeredPath = noCheckAccessUrls[i];
                if (registeredPath == null) {
                    throw new IllegalArgumentException("Entry number " + i + " in allowAccessUrls array is null");
                } else {
                    if (this.pathMatcher.match(registeredPath, urlPath)) {
                        return false;
                    }
                }
            }
        }
        if (this.checkAccessUrls != null) {
            for (int i = 0; i < this.checkAccessUrls.length; i++) {
                String registeredPath = checkAccessUrls[i];
                if (registeredPath == null) {
                    throw new IllegalArgumentException("Entry number " + i + " in allowAccessUrls array is null");
                } else {
                    if (this.pathMatcher.match(registeredPath, urlPath)) {
                    	if(StringUtil.isNotEmpty(cmUserInfo)){
                    		return false;
                		}
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    
    /**
	 * object 返回设置
	 * @param object
	 * @param code
	 */
	public ResponseObject initResponseObject() {
		ResponseObject object = new ResponseObject();
		object.setCode("");
		object.setResponse("");
		object.setDesc("");
		object.setDate(DateUtils.getCurrentDateTime("yyyyMMddHHmmss"));
		return object;
	}
	
	/**
	 * 检测用户是否是黑名单
	 * @param currentUrl
	 * @param cmUserInfo
	 * @return
	 */
	public boolean checkBlack(String currentUrl,CmUserInfo cmUserInfo){
		if(cmUserInfo != null ){
			Map<String,String> param = new HashMap<String, String>();
			param.put("content",cmUserInfo.getId().toString());
			List<BlackListUserEquipment> blacEqList = blackListUserEquipmentService.findByMap(param);
			if( blacEqList != null && blacEqList.size() > 0){
				param.clear();
				param.put("code",SysParamConstant.BLACK_API);
				List<SysParameter> sysParameterList = sysParameterService.findByMap(param);
				//要检测黑名单的接口
				if( sysParameterList != null && sysParameterList.size() > 0){
					for (SysParameter sysParameter : sysParameterList) {
						if(sysParameter.getValue().trim().equals(currentUrl)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	
	
	
}
