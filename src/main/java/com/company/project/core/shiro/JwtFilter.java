package com.company.project.core.shiro;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * JwtFilter
 *
 * @author libaogang
 * @since 2019-08-15 20:55
 */
public class JwtFilter extends BasicHttpAuthenticationFilter {

    private Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        //判断请求的请求头是否带上 token
        if (WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER) != null) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确。
            //因为token机制下每次请求都要验证token，相当于每次请求都是登陆
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                LOGGER.error("token校验失败：" + e.getMessage());
                return false;
            }
        }
        //如果请求头不存在 token
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER);
        JwtToken jwtToken = new JwtToken(token);
        // 提交给realm进行登入，如果错误他会抛出异常并被捕获
        getSubject(request, response).login(jwtToken);
        // 如果没有抛出异常则代表登入成功，返回true
        return true;
    }

    /**
     * 当请求被拒绝，即isAccessAllowed返回false时
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        try {
            //将非法请求转发到 /unauthorized
            request.getRequestDispatcher("/unauthorized").forward(request, response);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        return false;
    }

}