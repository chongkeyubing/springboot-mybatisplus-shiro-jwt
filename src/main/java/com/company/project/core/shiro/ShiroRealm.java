package com.company.project.core.shiro;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.impl.PublicClaims;
import com.company.project.core.Constant;
import com.company.project.core.JwtUtil;
import com.company.project.modules.sys.entity.PermissionEntity;
import com.company.project.modules.sys.entity.RoleEntity;
import com.company.project.modules.sys.entity.UserEntity;
import com.company.project.modules.sys.service.PermissionService;
import com.company.project.modules.sys.service.RoleService;
import com.company.project.modules.sys.service.UserService;
import com.company.project.util.CurrentUserUtil;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * 自定义realm
 */
public class ShiroRealm extends AuthorizingRealm {

    /**
     * token刷新响应头字段
     */
    private static final String REFRESH_TOKEN = "refreshToken";

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @Resource
    private PermissionService permissionService;

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
        } catch (TokenExpiredException e) {
            // 如果在免登录时间内，则续签token
            long expire = JwtUtil.getClaim(token, PublicClaims.EXPIRES_AT).asLong() * 1000;

            if (System.currentTimeMillis() - expire < JwtUtil.REFRESH_TIME) {
                Long userId = JwtUtil.getClaim(token, Constant.USER_ID).asLong();

                // 添加刷新token响应头，告诉前台替换token
                HttpServletResponse response = WebContextUtil.getResponse();
                response.setHeader(REFRESH_TOKEN, Boolean.TRUE.toString());
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, JwtUtil.sign(userId));
            } else {
                throw new AuthenticationException(JwtToken.TOKEN_EXPIRED);
            }
        } catch (Exception e) {
            throw new AuthenticationException(JwtToken.TOKEN_INVALID);
        }

        Long userId = JwtUtil.getClaims(token).get(Constant.USER_ID).asLong();

        UserEntity user = userService.getById(userId);
        if (null == user) {
            throw new AuthenticationException(JwtToken.TOKEN_INVALID);
        }
        // 用户被锁定
//            if (0 == user.getStatus()) {
//                throw new AuthenticationException("token invalid");
//            }

        this.putUserToRequest(user);
        return new SimpleAuthenticationInfo(token, token, this.getName());

    }

    /**
     * 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {

        //String token = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        long userId = CurrentUserUtil.getCurrentUserId();

        List<String> roleList = new ArrayList<>();
        List<String> permissionList = new ArrayList<>();

        // 超级管理员拥有至高无上的权限
        if (userId == Constant.SUPER_ADMIN_ID) {
            // 所有角色
            List<RoleEntity> roleEntities = roleService.lambdaQuery()
                    .select(RoleEntity::getPermessionFlag)
                    .list();
            for (RoleEntity roleEntity : roleEntities) {
                roleList.add(roleEntity.getPermessionFlag());
            }

            // 所有操作权限
            List<PermissionEntity> permissionEntities = permissionService.lambdaQuery()
                    .select(PermissionEntity::getPermessionFlag)
                    .eq(PermissionEntity::getType, Constant.PermissionType.OPERATE.getValue())
                    .list();

            for (PermissionEntity permissionEntity : permissionEntities) {
                permissionList.add(permissionEntity.getPermessionFlag());
            }
        } else {
            // 根据用户id查询角色
            List<RoleEntity> roleEntities = roleService.listRolesByUserId(userId);
            List<Long> roleIds = new ArrayList<>(roleEntities.size());

            for (RoleEntity roleEntity : roleEntities) {
                roleIds.add(roleEntity.getRoleId());
                roleList.add(roleEntity.getPermessionFlag());
            }

            // 根据角色列表查询操作权限
            List<PermissionEntity> permissionEntities = permissionService.listPermissionsByRoleIds(roleIds);
            for (PermissionEntity permissionEntity : permissionEntities) {
                permissionList.add(permissionEntity.getPermessionFlag());
            }
        }

        authorizationInfo.setRoles(new HashSet<>(roleList));
        authorizationInfo.setStringPermissions(new HashSet<>(permissionList));
        return authorizationInfo;
    }

    /**
     * 将用户存入request中，方便后续直接获取，避免频繁解密token查询用户
     *
     * @param user 用户实体
     * @author libaogang
     * @since 2019-10-31 22:58:43
     */
    private void putUserToRequest(UserEntity user) {
        HttpServletRequest httpServletRequest = WebContextUtil.getRequest();
        httpServletRequest.setAttribute(Constant.CURRENT_USER, user);
    }
}
