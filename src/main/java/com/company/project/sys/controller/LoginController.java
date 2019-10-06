package com.company.project.sys.controller;

import com.company.project.core.BusinessException;
import com.company.project.core.JwtUtil;
import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.sys.entity.User;
import com.company.project.sys.service.UserService;
import com.company.project.util.MD5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 登陆控制器
 *
 * @author libaogang
 * @since 2019-08-16 7:56
 */
@RestController
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 登陆
     *
     * @param username 账号
     * @param password 密码
     * @return Result
     * @author libaogang
     * @since 2019-08-16 8:23
     */
    @RequestMapping("/login")
    public Result login(String username, String password) {
        User user = userService.lambdaQuery().eq(User::getUsername, username).one();



        //校验账号密码
        if (null == user || !MD5Util.encrypt(password).equals(user.getPassword())) {
            throw new BusinessException("账号或者密码错误");
        }

        //校验验证码 todo
        if(false){
            throw new BusinessException("验证码错误");
        }

        String token = JwtUtil.sign(username);
        return ResultUtil.success(token);
    }


}