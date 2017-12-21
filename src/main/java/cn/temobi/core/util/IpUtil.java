package cn.temobi.core.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	public static String getIp(HttpServletRequest request)
	{
		String ip = request.getHeader("X-Real-IP");
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
			if (!StringUtil.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
				// 多次反向代理后会有多个IP值，第一个为真实IP。
				int index = ip.indexOf(',');
				if (index != -1) {
					ip = ip.substring(0, index);
				}
			} else {
				ip = request.getRemoteAddr();
			}	
		}
		return ip;
	}
}
