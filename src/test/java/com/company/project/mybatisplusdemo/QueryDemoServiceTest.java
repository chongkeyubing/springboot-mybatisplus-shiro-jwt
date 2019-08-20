package com.company.project.mybatisplusdemo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.ApplicationTests;
import com.company.project.entity.QueryDemo;
import com.company.project.service.QueryDemoService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * 基于queryDemo表的service增删改查测试
 *
 * @author libaogang
 * @since 2019-08-14 21:14
 */
public class QueryDemoServiceTest extends ApplicationTests {

    @Autowired
    private QueryDemoService queryDemoService;

    @Test
    public void insertBatch() {
        QueryDemo queryDemo1 = new QueryDemo();
        queryDemo1.setName("小可爱");
        queryDemo1.setAge(28);

        QueryDemo queryDemo2 = new QueryDemo();
        queryDemo2.setName("大傻瓜");
        queryDemo2.setAge(19);

        List<QueryDemo> queryDemoList = Arrays.asList(queryDemo1, queryDemo2);
        boolean flag = queryDemoService.saveBatch(queryDemoList);
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void insertOrUpdateBatch() {
        QueryDemo queryDemo1 = new QueryDemo();
        queryDemo1.setName("李莫愁");
        queryDemo1.setAge(28);

        QueryDemo queryDemo2 = new QueryDemo();
        queryDemo2.setId(1134354221018144774L);
        queryDemo2.setName("张三丰");
        queryDemo2.setAge(20);

        List<QueryDemo> queryDemoList = Arrays.asList(queryDemo1, queryDemo2);
        boolean flag = queryDemoService.saveOrUpdateBatch(queryDemoList);
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void selectChain() {
        List<QueryDemo> queryDemoList = queryDemoService.lambdaQuery().gt(QueryDemo::getAge, 25).like(QueryDemo::getName, "雨").list();
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void updateChain() {
        boolean flag = queryDemoService.lambdaUpdate().eq(QueryDemo::getAge, 25).set(QueryDemo::getAge, 26).update();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void removeChain() {
        boolean flag = queryDemoService.lambdaUpdate().eq(QueryDemo::getAge, 20).remove();
        System.out.println("是否成功：" + flag);
    }

    @Test
    public void pageChain() {
        IPage<QueryDemo> queryDemos = queryDemoService.lambdaQuery()
                .gt(QueryDemo::getAge, 25)
                .like(QueryDemo::getName, "雨")
                .page(new Page<>(1, 1));

        queryDemos.getRecords().forEach(System.out::println);
    }

}


