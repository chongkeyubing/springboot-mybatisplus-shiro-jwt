package com.company.project.modules.sys.controller;

import com.company.project.core.BusinessException;
import com.company.project.core.JwtUtil;
import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.modules.sys.entity.User;
import com.company.project.modules.sys.service.UserService;
import com.company.project.util.MD5Util;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public Result login(@RequestParam String username, @RequestParam String password) {
        User user = userService.lambdaQuery()
                .select(User::getUserId,User::getRealname,User::getPassword)
                .eq(User::getUsername, username).one();

        if (null == user || !MD5Util.encrypt(password).equals(user.getPassword())) {
            throw new BusinessException("账号或者密码错误");
        }

//        //校验验证码 todo
//        if (false) {
//            throw new BusinessException("验证码错误");
//        }

        String token = JwtUtil.sign(user.getUserId(),user.getRealname());
        return ResultUtil.success(token);
    }


}