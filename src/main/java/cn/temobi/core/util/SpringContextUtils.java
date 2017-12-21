package cn.temobi.core.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextUtils implements ApplicationContextAware, DisposableBean {
    private static ApplicationContext applicationContext = null;

    private static final Log logger = LogFactory.getLog(SpringContextUtils.class);

    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        logger.debug("注入ApplicationContext到SpringContextUtils:" + applicationContext);

        if (SpringContextUtils.applicationContext != null) {
            logger.warn("SpringContextUtils中的ApplicationContext被覆盖, 原有ApplicationContext为:"
                    + SpringContextUtils.applicationContext);
        }

        SpringContextUtils.applicationContext = applicationContext; //NOSONAR
    }

    /**
     * 实现DisposableBean接口,在Context关闭时清理静态变量.
     */

    public void destroy() throws Exception {
        SpringContextUtils.clearContextHolder();
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContextInjected();
        return applicationContext;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContextInjected();
        return (T) applicationContext.getBean(name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getBean(Class<T> requiredType) {
        checkApplicationContextInjected();
        return applicationContext.getBean(requiredType);
    }

    /**
     * 清除SpringContextHolder中的ApplicationContext为Null.
     */
    public static void clearContextHolder() {
        logger.debug("清除SpringContextUtils中的ApplicationContext:" + applicationContext);
        applicationContext = null;
    }

    /**
     * 检查ApplicationContext不为空.
     */
    private static void checkApplicationContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextUtils");
        }
    }
}

