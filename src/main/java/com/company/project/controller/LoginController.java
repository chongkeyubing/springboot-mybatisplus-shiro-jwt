package com.company.project.controller;

import com.company.project.core.JwtUtil;
import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.sys.entity.User;
import com.company.project.sys.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
     * @param account  账号
     * @param password 密码
     * @return Result
     * @author libaogang
     * @since 2019-08-16 8:23
     */
    @RequestMapping("/login")
    public Result login(String account, String password, HttpServletResponse response) {
        //校验账号密码
        User user = userService.lambdaQuery().eq(User::getAccount, account).one();
        if (null == user || !password.equals(user.getPassword())) {
            return ResultUtil.fail("账号或密码错误");
        }

        //登陆成功，响应头塞入token
        String token = JwtUtil.sign(account);
        response.setHeader("Authorization", token);
        return ResultUtil.success();
    }

}