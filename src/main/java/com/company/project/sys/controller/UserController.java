package com.company.project.sys.controller;


import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.sys.entity.User;
import com.company.project.sys.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-08-24
 */
@RestController
@RequestMapping("sys/user")
public class UserController {

    @Resource

    UserService userService;

    @RequestMapping("/getUser")
    public Result getUser(@RequestParam Long userId){
        User user = userService.getById(userId);

        return ResultUtil.success(user);
    }

}
