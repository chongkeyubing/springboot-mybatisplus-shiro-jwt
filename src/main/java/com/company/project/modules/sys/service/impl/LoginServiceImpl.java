package com.company.project.modules.sys.service.impl;

import com.company.project.core.JwtUtil;
import com.company.project.core.exception.BusinessException;
import com.company.project.modules.sys.entity.UserEntity;
import com.company.project.modules.sys.service.LoginService;
import com.company.project.modules.sys.service.UserService;
import com.company.project.util.MD5Util;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 登录
 *
 * @author libaogang
 * @since 2019-11-27 上午 9:01
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserService userService;

    @Override
    public String login(String username, String password) {
        UserEntity user = userService.lambdaQuery()
                .select(UserEntity::getUserId, UserEntity::getRealname, UserEntity::getPassword)
                .eq(UserEntity::getUsername, username).one();

//        if (null == user || !SecureUtil.md5(password).equals(user.getPassword())) {
//            throw new BusinessException("账号或者密码错误");
//        }

        if (null == user || !MD5Util.encrypt(password).equals(user.getPassword())) {
            throw new BusinessException("账号或者密码错误");
        }

//        //校验验证码 todo
//        if (false) {
//            throw new BusinessException("验证码错误");
//        }

        return JwtUtil.sign(user.getUserId());
    }
}