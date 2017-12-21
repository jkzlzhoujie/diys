package cn.temobi.core.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author salim
 * @created 2012-3-22
 * @package com.plaminfo.core.util
 */
public class HttpSessionUtils extends BaseUtils {
    private static final long serialVersionUID = -165232876456390707L;

    public static Object getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        return  (session != null ? session.getAttribute(name) : null);
    }

    public static void setAttribute(HttpServletRequest request,
                                    String name, Object value) {
        HttpSession session = request.getSession();
        session.setAttribute(name, value);
    }

    public static String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }

    public static void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
