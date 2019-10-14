package com.company.project.core.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.company.project.core.JwtUtil;
import com.company.project.sys.entity.User;
import com.company.project.sys.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * shiro自定义realm
 */
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken authenticationToken) {
        return authenticationToken instanceof JwtToken;
    }

    /**
     * 每次请求时验证token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // 前台传过来的token
        String token = (String) authenticationToken.getPrincipal();
        Long userId = JwtUtil.getClaim(token, JwtUtil.CLAIM_USER_ID).asLong();
        try {
            JwtUtil.verify(token);

            User user = userService.getById(userId);
            if (null == user) {
                throw new AuthenticationException(JwtToken.TOKEN_INVALID);
            }
            // 用户被锁定
//            if (0 == user.getStatus()) {
//                throw new AuthenticationException("token invalid");
//            }
        } catch (TokenExpiredException e) {
            // 如果在免登录时间内，则续签token
            long expire = JwtUtil.getClaim(token, PublicClaims.EXPIRES_AT).asLong() * 1000;
            if (System.currentTimeMillis() - expire  < JwtUtil.REFRESH_TIME) {
                HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                response.setHeader("refreshToken", "true");
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtUtil.sign(userId));
            } else {
                throw new AuthenticationException(JwtToken.TOKEN_EXPIRED);
            }
        } catch (Exception e) {
            throw new AuthenticationException(JwtToken.TOKEN_INVALID);
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());

    }

    /**
     * 获取授权信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        String username = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

//        //将授权信息组装到authorizationInfo
//        List<String> roles = userService.getRoles(username);
//        authorizationInfo.setRoles(new HashSet<>(roles));
//        List<String> permissions = userService.getPermissions(username);
//        authorizationInfo.setStringPermissions(new HashSet<>(permissions));

        return authorizationInfo;
    }
}
