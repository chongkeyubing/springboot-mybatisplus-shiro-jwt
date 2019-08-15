package com.company.basemanager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.basemanager.entity.User;
import com.company.basemanager.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
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

    @Resource
    private UserService userService;

    /**
     * todo
     * @author libaogang
     * @param page 页码
     * @param limit 数量
     * @return java.util.List<com.company.basemanager.entity.User>
     * @since 2019-08-15 12:06
     */
    @RequestMapping("/list")
    public List<User> add(int page,int limit){
        QueryWrapper<User> query = Wrappers.query();
        query.gt("age",30);

        Page<User> pageParam = new Page<>(page, limit);

        IPage<User> users = userService.page(pageParam,query);

        return users.getRecords();
    }

}
