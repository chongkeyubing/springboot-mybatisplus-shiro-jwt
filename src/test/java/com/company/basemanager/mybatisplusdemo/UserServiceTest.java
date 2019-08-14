package com.company.basemanager.mybatisplusdemo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.basemanager.BasemanagerApplicationTests;
import com.company.basemanager.entity.User;
import com.company.basemanager.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * todo
 *
 * @author libaogang
 * @since 2019-08-14 21:14
 */
public class UserServiceTest extends BasemanagerApplicationTests {

    @Autowired
    private IUserService userService;

    // 此处会报错，因为数据库存在多条记录
    @Test
    public void getOne() {
        User one = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getAge, 25));
        System.out.println(one);
    }

    @Test
    public void insertBatch() {
        User user1 = new User();
        user1.setName("小可爱");
        user1.setAge(28);
        user1.setManagerId(1088248166370832385L);

        User user2 = new User();
        user2.setName("大傻瓜");
        user2.setAge(19);
        user2.setManagerId(1088248166370832385L);

        List<User> userList = Arrays.asList(user1, user2);
        boolean flag = userService.saveBatch(userList);
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void insertOrUpdateBatch() {
        User user1 = new User();
        user1.setName("李莫愁");
        user1.setAge(28);

        User user2 = new User();
        user2.setId(1134354221018144774L);
        user2.setName("张三丰");
        user2.setAge(20);
        user2.setManagerId(1088248166370832385L);

        List<User> userList = Arrays.asList(user1, user2);
        boolean flag = userService.saveOrUpdateBatch(userList);
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void selectChain() {
        List<User> userList = userService.lambdaQuery().gt(User::getAge, 25).like(User::getName, "雨").list();
        userList.forEach(System.out::println);
    }

    @Test
    public void updateChain() {
        boolean flag = userService.lambdaUpdate().eq(User::getAge, 25).set(User::getAge, 26).update();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void removeChain() {
        boolean flag = userService.lambdaUpdate().eq(User::getAge, 20).remove();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void pageChain() {
        IPage<User> users = userService.lambdaQuery()
                .gt(User::getAge, 25)
                .like(User::getName, "雨")
                .page(new Page<>(1, 1));

        users.getRecords().forEach(System.out::println);
    }

}


