package com.company.project.modules.sys.controller;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.modules.sys.service.LoginService;
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
    private LoginService loginService;

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
        String token = loginService.login(username, password);
        return ResultUtil.success(token);
    }


}