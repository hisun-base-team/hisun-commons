package com.hisun.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * 获取请求ip
 * 
 * @author Jason
 * 
 */
public class WrapWebUtils extends org.springframework.web.util.WebUtils {

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	public static String getRemoteIp() {
		return getRemoteIp(getRequest());
	}

	private static String getRemoteIp(HttpServletRequest request) {
		String ip = "";
		if(request != null){
//			String ip = request.getHeader("X-Real-IP");
//			if (StringUtils.isNotEmpty(ip))
//				return ip;
//			ip = request.getHeader("Cdn_Src_Ip");
//			if (StringUtils.isNotEmpty(ip))
//				return ip;
//
//			ip = request.getHeader("X-Forwarded-For");
//			if (StringUtils.isNotEmpty(ip)) {
//				String[] proxys = ip.split(", ");
//				for (String proxy : proxys) {
//					if (!(proxy.startsWith("127.") || proxy.startsWith("172.")
//							|| proxy.startsWith("192.") || proxy.startsWith("10."))) {
//						ip = proxy;
//						break;
//					}
//				}
//				return ip;
//			}
//
//			ip = request.getHeader("WL-Proxy-Client-IP");
//			if (StringUtils.isEmpty(ip)) {
				ip = request.getRemoteAddr();
//			}
		}
		return ip;
	}
}
