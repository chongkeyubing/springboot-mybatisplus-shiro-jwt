package com.company.project.modules.sys.controller;

import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.modules.sys.entity.UserEntity;
import com.company.project.modules.sys.service.UserService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 系统用户 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-10-17
 */
@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/getUserById")
    public Result getUserById(@RequestParam Long userId) {
        UserEntity user = userService.getById(userId);
        return ResultUtil.success(user);
    }

    @RequestMapping("/save")
    public Result save(@RequestBody UserEntity user) {
        userService.save(user);
        return ResultUtil.success();
    }

    @RequestMapping("/update")
    public Result update(@RequestBody UserEntity user){
        userService.updateById(user);
        return ResultUtil.success();
    }
}
