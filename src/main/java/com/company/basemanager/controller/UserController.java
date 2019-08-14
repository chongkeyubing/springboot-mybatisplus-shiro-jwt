package com.company.basemanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.basemanager.entity.User;
import com.company.basemanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-08-14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping("/list")
    public List<User> add(int page,int limit){
        QueryWrapper<User> query = Wrappers.query();
        query.gt("age",30);

        Page<User> pageParam = new Page<>(page, limit);

        IPage<User> users = userService.page(pageParam,query);

        return users.getRecords();
    }

}
