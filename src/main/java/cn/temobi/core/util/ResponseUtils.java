package cn.temobi.core.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author salim
 * @created 2012-3-22
 * @package com.plaminfo.core.util
 */
public class ResponseUtils {
    public static final Logger log = LoggerFactory
            .getLogger(ResponseUtils.class);
    //-- header 常量定义 --//
    private static final String HEADER_ENCODING = "encoding";
    private static final String HEADER_NOCACHE = "no-cache";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final boolean DEFAULT_NOCACHE = true;

    private static ObjectMapper mapper = new ObjectMapper();


    /**
     * 发送文本。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderText(HttpServletResponse response, String text) {
        render(response, "text/plain;charset=UTF-8", text);
    }

    /**
     * 发送json。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderJson(HttpServletResponse response, String text) {
        render(response, "application/json;charset=UTF-8", text);
    }

    /**
     * 发送xml。使用UTF-8编码。
     *
     * @param response HttpServletResponse
     * @param text     发送的字符串
     */
    public static void renderXml(HttpServletResponse response, String text) {
        render(response, "text/xml;charset=UTF-8", text);
    }

    /**
     * 发送内容。使用UTF-8编码。
     *
     * @param response
     * @param contentType
     * @param text
     */
    public static void render(HttpServletResponse response, String contentType,
                              String text) {
        response.setContentType(contentType);
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        try {
            response.getWriter().write(text);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    //-- 绕过jsp/freemaker直接输出文本的函数 --//

    /**
     * 直接输出内容的简便函数.
     * <p/>
     * eg.
     * render("text/plain", "hello", "encoding:GBK");
     * render("text/plain", "hello", "no-cache:false");
     * render("text/plain", "hello", "encoding:GBK", "no-cache:false");
     *
     * @param headers 可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
     */
    public static void render(HttpServletResponse response, final String contentType, final String content, final String... headers) {
        initResponseHeader(response, contentType, headers);
        try {
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 分析并设置contentType与headers.
     */
    private static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType, final String... headers) {
        //分析headers参数
        String encoding = DEFAULT_ENCODING;
        boolean noCache = DEFAULT_NOCACHE;
        for (String header : headers) {
            String headerName = StringUtils.substringBefore(header, ":");
            String headerValue = StringUtils.substringAfter(header, ":");
            if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
                encoding = headerValue;
            } else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
                noCache = Boolean.parseBoolean(headerValue);
            } else {
                throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
            }
        }

        //设置headers参数
        String fullContentType = contentType + ";charset=" + encoding;
        response.setContentType(fullContentType);
        if (noCache) {
            ServletUtils.setDisableCacheHeader(response);
        }

        return response;
    }

    /**
     * 直接输出文本.
     *
     * @see #render(HttpServletResponse, String, String, String...)
     */
    public static void renderText(HttpServletResponse response, final String text, final String... headers) {
        render(response, ServletUtils.TEXT_TYPE, text, headers);
    }

    /**
     * 直接输出HTML.
     *
     * @see #render(HttpServletResponse, String, String, String...)
     */
    public static void renderHtml(HttpServletResponse response, final String html, final String... headers) {
        render(response, ServletUtils.HTML_TYPE, html, headers);
    }

    /**
     * 直接输出XML.
     *
     * @see #render(HttpServletResponse response, String, String, String...)
     */
    public static void renderXml(HttpServletResponse response, final String xml, final String... headers) {
        render(response, ServletUtils.XML_TYPE, xml, headers);
    }

    /**
     * 直接输出JSON.
     *
     * @param jsonString json字符串.
     * @see #render(HttpServletResponse, String, String, String...)
     */
    public static void renderJson(HttpServletResponse response, final String jsonString, final String... headers) {
        render(response, ServletUtils.JSON_TYPE, jsonString, headers);
    }

    /**
     * 直接输出JSON,使用Jackson转换Java对象.
     *
     * @param data 可以是List<POJO>, POJO[], POJO, 也可以Map名值对.
     * @see #render(HttpServletResponse, String, String, String...)
     */
    public static void renderJson(HttpServletResponse response, final Object data, final String... headers) {
        initResponseHeader(response, ServletUtils.JSON_TYPE, headers);
        try {
            mapper.writeValue(response.getWriter(), data);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * 直接输出支持跨域Mashup的JSONP.
     *
     * @param callbackName callback函数名.
     * @param object       Java对象,可以是List<POJO>, POJO[], POJO ,也可以Map名值对, 将被转化为json字符串.
     */
    public static void renderJsonp(HttpServletResponse response, final String callbackName, final Object object, final String... headers) {
        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        String result = new StringBuilder().append(callbackName).append("(").append(jsonString).append(");").toString();

        //渲染Content-Type为javascript的返回内容,输出结果为javascript语句, 如callback197("{html:'Hello World!!!'}");
        render(response, ServletUtils.JS_TYPE, result, headers);
    }
}
