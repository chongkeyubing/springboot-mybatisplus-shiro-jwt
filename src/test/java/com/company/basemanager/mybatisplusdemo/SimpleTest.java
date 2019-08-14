package com.company.basemanager.mybatisplusdemo;

import com.company.basemanager.BasemanagerApplicationTests;
import com.company.basemanager.dao.UserMapper;
import com.company.basemanager.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class SimpleTest extends BasemanagerApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insert() {
        User user = new User();
        user.setName("丁茂义");
        user.setAge(40);
        user.setEmail("dmy@baomidou.com");
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(new Date());
        int rows = userMapper.insert(user);
        System.out.println("影响记录数：" + rows);
        System.out.println("主键：" + user.getId());
    }

    @Test
    public void select() {
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}
