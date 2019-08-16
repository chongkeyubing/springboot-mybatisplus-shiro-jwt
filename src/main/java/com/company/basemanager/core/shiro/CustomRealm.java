package com.company.basemanager.core.shiro;

import com.company.basemanager.core.JwtUtil;
import com.company.basemanager.sys.entity.User;
import com.company.basemanager.sys.service.UserService;
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
public class CustomRealm extends AuthorizingRealm {

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
     * 校验每一次请求中的token
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        // 前台传过来的token
        String token = (String) authenticationToken.getPrincipal();

        // 从token中获取account
        String account = JwtUtil.getClaim(token, "account");

        if (account == null) {
            throw new AuthenticationException("非法token");   // token invalid
        }

        // 根据账号查询用户信息
        User user = userService.lambdaQuery().eq(User::getAccount, account).one();

        if (user == null || !JwtUtil.verify(token)) {
            throw new AuthenticationException("非法token");
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
