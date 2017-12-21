package cn.temobi.complex.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.Assert;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import cn.temobi.core.common.Constant;
import cn.temobi.core.util.StringUtil;





/**
 * @author salim
 * @created 2012-8-3
 * @package com.plaminfo.group.interceptor
 */
public class BoInterceptor extends HandlerInterceptorAdapter {

	private UrlPathHelper urlPathHelper = new UrlPathHelper();
    private String[] checkAccessUrls;
	private String[] noCheckAccessUrls;
    private PathMatcher pathMatcher = new AntPathMatcher();
    private String needLogin;
    
    public void setNeedLogin(String needLogin) {
		this.needLogin = needLogin;
	}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String currentUrl = urlPathHelper.getLookupPathForRequest(request);
        if (!isProtected(request, currentUrl)) {
            return true;
        } else if (this.pathMatcher.match(currentUrl, needLogin + "*")) {
            return true;
        } else {
            response.sendRedirect(needLogin);
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

    private boolean isProtected(HttpServletRequest request, String urlPath) {
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
                    		if(!StringUtil.isEmpty(request.getSession().getAttribute(Constant.SESSION_USER_INFO))){
                    			return false;
                    		}
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
