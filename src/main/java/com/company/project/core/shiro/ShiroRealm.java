package com.company.project.core.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
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

import javax.annotation.Resource;

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
     * 验证token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // 前台传过来的token
        String token = (String) authenticationToken.getPrincipal();

        try {
            // 从token中获取username
            String username = JwtUtil.getClaim(token, JwtUtil.CLAIM_USERNAME);
            // 根据账号查询用户信息
            User user = userService.lambdaQuery().eq(User::getUsername, username).one();
            if (null == user) {
                throw new AuthenticationException(JwtToken.TOKEN_INVALID);
            }
            // 用户被锁定
//            if (0 == user.getStatus()) {
//                throw new AuthenticationException("token invalid");
//            }
            JwtUtil.verify(token);

        } catch (TokenExpiredException e) {
            throw new AuthenticationException(JwtToken.TOKEN_EXPIRED);
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
