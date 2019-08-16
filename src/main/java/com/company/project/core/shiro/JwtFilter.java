package com.company.project.core.shiro;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.company.project.core.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
            //如果存在，则进入 executeLogin 方法检查 token 是否正确。
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                Throwable throwable = e.getCause();
                String msg = "token校验失败";
                if (throwable instanceof SignatureVerificationException) {
                    msg = "token非法";
                } else if (throwable instanceof TokenExpiredException) {
                    msg = "token已过期";
                } else {
                    LOGGER.error(msg + " : " + e.getMessage(), e);
                }
                responseUnauthorized(msg, response);
                return false;
            }
        }
        //如果请求头token不存在
        responseUnauthorized("未携带token", response);
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER);
        JwtToken jwtToken = new JwtToken(token);

        // login会执行CustomRealm中的doGetAuthenticationInfo方法校验token
        getSubject(request, response).login(jwtToken);

        // 如果没有抛出异常则校验成功，返回true
        return true;
    }

    /**
     * 返回Unauthorized
     */
    private void responseUnauthorized(String msg, ServletResponse response) {
        HttpServletResponse resp = WebUtils.toHttp(response);
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = resp.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(ResultUtil.fail(msg));
            out.write(data);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}