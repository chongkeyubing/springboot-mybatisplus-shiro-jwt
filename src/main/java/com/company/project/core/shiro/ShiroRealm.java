package com.company.project.core.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.auth0.jwt.interfaces.Claim;
import com.company.project.core.Constant;
import com.company.project.core.JwtUtil;
import com.company.project.modules.sys.entity.User;
import com.company.project.modules.sys.service.UserService;
import com.company.project.util.WebContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


/**
 * 自定义realm
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

        try {
            JwtUtil.verify(token);

            Map<String, Claim> claims = JwtUtil.getClaims(token);
            Long userId = claims.get(Constant.USER_ID).asLong();
            String realname = claims.get(Constant.REALNAME).asString();

            User user = userService.getById(userId);
            if (null == user) {
                throw new AuthenticationException(JwtToken.TOKEN_INVALID);
            }
            // 用户被锁定
//            if (0 == user.getStatus()) {
//                throw new AuthenticationException("token invalid");
//            }

            putUserInfoToRequest(userId, realname);
        } catch (TokenExpiredException e) {
            // 如果在免登录时间内，则续签token
            long expire = JwtUtil.getClaim(token, PublicClaims.EXPIRES_AT).asLong() * 1000;
            if (System.currentTimeMillis() - expire < JwtUtil.REFRESH_TIME) {
                Map<String, Claim> claims = JwtUtil.getClaims(token);
                Long userId = claims.get(Constant.USER_ID).asLong();
                String realname = claims.get(Constant.REALNAME).asString();
                putUserInfoToRequest(userId, realname);

                HttpServletResponse response = WebContextUtil.getResponse();
                response.setHeader("refreshToken", "true");
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtUtil.sign(userId, realname));
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

    /**
     * 将用户id和真实姓名存入request中，方便后续直接获取，避免频繁解密token
     *
     * @param userId   用户id
     * @param realname 真实姓名
     * @author libaogang
     * @since 2019-10-31 22:58:43
     */
    private void putUserInfoToRequest(Long userId, String realname) {
        HttpServletRequest httpServletRequest = WebContextUtil.getRequest();
        httpServletRequest.setAttribute(Constant.USER_ID, userId);
        httpServletRequest.setAttribute(Constant.REALNAME, realname);
    }
}
