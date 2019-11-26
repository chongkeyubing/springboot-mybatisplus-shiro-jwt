package com.company.project.core.shiro;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

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
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        //判断请求头是否带上 token
        if (WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER) != null) {
            try {
                executeLogin(request, response);
                return true;
            } catch (Exception e) {
                responseUnauthorized(e.getMessage(), response);
                return false;
            }
        }
        responseUnauthorized(JwtToken.TOKEN_INVALID, response);
        return false;
    }

    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader(AUTHORIZATION_HEADER);
        JwtToken jwtToken = new JwtToken(token);

        // login会执行ShiroRealm中的doGetAuthenticationInfo方法校验token
        getSubject(request, response).login(jwtToken);

        // 如果没有抛出异常则校验成功，返回true
        return true;
    }

    /**
     * 返回Unauthorized
     */
    private void responseUnauthorized(String msg, ServletResponse response) {
        HttpServletResponse resp = WebUtils.toHttp(response);
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json; charset=utf-8");
        try (PrintWriter out = resp.getWriter()) {
            ObjectMapper objectMapper = new ObjectMapper();
            String data = objectMapper.writeValueAsString(ResultUtil.fail(Result.TOKEN_EXCEPTION, msg));
            out.write(data);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

}