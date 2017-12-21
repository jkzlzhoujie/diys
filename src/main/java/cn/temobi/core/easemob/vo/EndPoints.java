package cn.temobi.core.easemob.vo;

import java.net.URL;

import cn.temobi.core.easemob.utils.HTTPClientUtils;

/**
 * HTTPClient EndPoints
 * 
 * @author Lynch 2014-09-15
 *
 */
public interface EndPoints {

	static final URL TOKEN_APP_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/token");

	static final URL USERS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/users");

	static final URL MESSAGES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/messages");

	static final URL CHATGROUPS_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatgroups");

	static final URL CHATFILES_URL = HTTPClientUtils.getURL(Constants.APPKEY.replace("#", "/") + "/chatfiles");

}
