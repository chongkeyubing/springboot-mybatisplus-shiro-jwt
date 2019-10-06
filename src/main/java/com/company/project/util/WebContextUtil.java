package com.company.project.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取request和session
 *
 * @author libaogang
 * @since 2019-08-16 7:56
 */
public class WebContextUtil {
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

//    public static HttpSession getSession() {
//        return getRequest().getSession();
//    }
}