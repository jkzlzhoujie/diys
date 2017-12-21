package cn.temobi.core.util;

import org.springframework.util.Assert;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author salim
 * @created 2012-3-22
 * @package com.plaminfo.core.util
 */
public class CookieUtils {
    /**
     * 每页条数cookie名称
     */
    public static final String COOKIE_PAGE_SIZE = "_cookie_page_size";
    /**
     * 默认每页条数
     */
    public static final int DEFAULT_SIZE = 20;
    /**
     * 最大每页条数
     */
    public static final int MAX_SIZE = 200;

    public static final int MAX_AGE = 31536000;

    public static final String CUSTOMER_LOGINUSER = "userName";
    public static final String CUSTOMER_ID = "_cId";
    public static final String CUSTOMER_PASSWORD = "securityCode";
    public static final String CUSTOMER_EMAIL = "userEmail";

    /**
     * 获得cookie的每页条数
     * <p/>
     * 使用_cookie_page_size作为cookie name
     *
     * @param request HttpServletRequest
     * @return default:20 max:200
     */
    public static int getPageSize(HttpServletRequest request) {
        Assert.notNull(request);
        Cookie cookie = getCookie(request, COOKIE_PAGE_SIZE);
        int count = 0;
        if (cookie != null) {
            try {
                count = Integer.parseInt(cookie.getValue());
            } catch (Exception e) {
            }
        }
        if (count <= 0) {
            count = DEFAULT_SIZE;
        } else if (count > MAX_SIZE) {
            count = MAX_SIZE;
        }
        return count;
    }

    /**
     * 获得cookie
     *
     * @param request HttpServletRequest
     * @param name    cookie name
     * @return if exist return cookie, else return null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Assert.notNull(request);
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie c : cookies) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }
        return null;
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        if (null != cookie)
            return cookie.getValue();
        return "";
    }

    /**
     * 根据部署路径，将cookie保存在根目录。
     *
     * @param request
     * @param response
     * @param name
     * @param value
     * @param expiry
     * @return
     */
    public static Cookie addCookie(HttpServletRequest request,
                                   HttpServletResponse response, String name, String value,
                                   Integer expiry) {
        Cookie cookie = new Cookie(name, value);
        if (expiry != null) {
            cookie.setMaxAge(expiry);
        } else
            cookie.setMaxAge(MAX_AGE);
        //String ctx = request.getContextPath();
        cookie.setPath("/");
        // cookie.setPath(StringUtil.isBlank(ctx) ? "/" : ctx);
        response.addCookie(cookie);
        return cookie;
    }

    /**
     * 取消cookie
     *
     * @param response
     * @param name
     * @param domain
     */
    public static void cancleCookie(HttpServletResponse response, String name,
                                    String domain) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        if (!StringUtil.isBlank(domain)) {
            cookie.setDomain(domain);
        }
        response.addCookie(cookie);
    }

    public static void updateCookie(HttpServletRequest request,
                                    HttpServletResponse response, String name, String value,
                                    Integer expiry) {
        Cookie cookies[] = request.getCookies();
        Cookie c = null;
        for (int i = 0; i < cookies.length; i++) {
            c = cookies[i];
            if (c.getName().equals(name)) {
                c.setValue(value);
                if (expiry != null)
                    c.setMaxAge(expiry);
                else
                    c.setMaxAge(MAX_AGE);
                response.addCookie(c);     //修改后，要更新到浏览器中
            }
        }
    }

    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie cookies[] = request.getCookies();
        Cookie c = null;
        for (int i = 0; i < cookies.length; i++) {
            c = cookies[i];
            if (c.getName().equals(name)) {
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
    }
    }