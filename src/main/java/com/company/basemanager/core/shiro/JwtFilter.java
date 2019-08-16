package com.company.basemanager.core.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * JwtFilter
 *
 * @author libaogang
 * @since 2019-08-15 20:55
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        //判断请求的请求头是否带上 "token"
        if (((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER) != null) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确。
            //因为token机制下每次请求都要验证token，相当于每次请求都登陆
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                //token 错误
                // todo
            }
        }
        //如果请求头不存在 token，则可能是执行登陆操作或者是游客状态访问，无需检查 token，直接返回 true
        return true;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = ((HttpServletRequest) request).getHeader(AUTHORIZATION_HEADER);
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }
}