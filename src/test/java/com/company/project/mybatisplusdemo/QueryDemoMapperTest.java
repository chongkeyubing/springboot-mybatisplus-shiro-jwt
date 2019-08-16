package com.company.project.mybatisplusdemo;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.company.project.ApplicationTests;
import com.company.project.entity.QueryDemo;
import com.company.project.mapper.QueryDemoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基于queryDemo表的mapper增删改查测试
 *
 * @author libaogang
 * @since 2019-08-14 20:14
 */
public class QueryDemoMapperTest extends ApplicationTests {
    @Autowired
    private QueryDemoMapper queryDemoMapper;

    @Test
    public void insert() {
        QueryDemo queryDemo = new QueryDemo();
        queryDemo.setName("张无忌");
        queryDemo.setAge(30);
        queryDemo.setCreateTime(LocalDateTime.now());
        int rows = queryDemoMapper.insert(queryDemo);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateById() {
        QueryDemo queryDemo = new QueryDemo();
        queryDemo.setId(1161624434793082882L);
        queryDemo.setEmail("zwj@baomidou.com");
        int rows = queryDemoMapper.updateById(queryDemo);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapper1() {
        UpdateWrapper<QueryDemo> queryDemoWrapper = Wrappers.update();
        queryDemoWrapper.eq("name", "李艺伟").eq("age", 28);

        QueryDemo queryDemo = new QueryDemo();
        queryDemo.setEmail("lyw2019@baomidou.com");
        queryDemo.setAge(29);

        int rows = queryDemoMapper.update(queryDemo, queryDemoWrapper);
        System.out.println("影响记录数：" + rows);
    }

    // 构造器参数和实体对象参数会重复出现
    @Test
    public void updateByWrapper2() {
        QueryDemo queryDemoWhere = new QueryDemo();
        queryDemoWhere.setName("李艺伟");

        UpdateWrapper<QueryDemo> queryDemoWrapper = Wrappers.update(queryDemoWhere);
        queryDemoWrapper.eq("name", "李艺伟").eq("age", 28);

        QueryDemo queryDemo = new QueryDemo();
        queryDemo.setAge(29);

        int rows = queryDemoMapper.update(queryDemo, queryDemoWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapper3() {
        UpdateWrapper<QueryDemo> queryDemoWrapper = Wrappers.update();
        queryDemoWrapper.eq("name", "李艺伟").eq("age", 29).set("age", 30);

        QueryDemo queryDemo = new QueryDemo();
        queryDemo.setAge(29);

        int rows = queryDemoMapper.update(null, queryDemoWrapper);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapperLambda() {
        LambdaUpdateWrapper<QueryDemo> lambdaUpdate = Wrappers.lambdaUpdate();
        lambdaUpdate.eq(QueryDemo::getName, "李艺伟").eq(QueryDemo::getAge, 30).set(QueryDemo::getAge, 31);

        int rows = queryDemoMapper.update(null, lambdaUpdate);
        System.out.println("影响记录数：" + rows);
    }

    @Test
    public void updateByWrapperLambdaChain() {
        boolean flag = new LambdaUpdateChainWrapper<QueryDemo>(queryDemoMapper).eq(QueryDemo::getName, "李艺伟").eq(QueryDemo::getAge, 31)
                .set(QueryDemo::getAge, 32).update();

        System.out.println("是否成功：" + flag);
    }

    @Test
    public void deleteById() {
        int rows = queryDemoMapper.deleteById(1134013564424658946L);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("name", "张无忌1");
        columnMap.put("age", 31);

        int rows = queryDemoMapper.deleteByMap(columnMap);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteBatchByIds() {
        int rows = queryDemoMapper.deleteBatchIds(Arrays.asList(1134345992649379841L, 1134346040569274369L));
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void deleteByWrapper() {
        LambdaQueryWrapper<QueryDemo> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(QueryDemo::getAge, 27).or().gt(QueryDemo::getAge, 41);
        int rows = queryDemoMapper.delete(lambdaQuery);
        System.out.println("删除条数：" + rows);
    }

    @Test
    public void selectById() {
        long id = 1094590409767661570L;
        QueryDemo queryDemo = queryDemoMapper.selectById(id);
        System.out.println(queryDemo);
    }

    @Test
    public void selectByIds() {
        List<Long> idList = Arrays.asList(1088248166370832385L, 1088250446457389058L, 1094590409767661570L);
        List<QueryDemo> queryDemoList = queryDemoMapper.selectBatchIds(idList);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectByMap() {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", "张无忌");
        paramMap.put("age", 31);
        List<QueryDemo> queryDemoList = queryDemoMapper.selectByMap(paramMap);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 姓名中含雨并且年龄小于40
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWarpper1() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.like("name", "雨").lt("age", 40);

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 姓名中含雨并且年龄大于等于20且小于等于40并且email不为空
     * name like '%雨%' and age between 20 and 40 and email is not null
     */
    @Test
    public void selectByWarpper2() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.like("name", "雨").between("age", 20, 40).isNotNull("email");

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 姓名为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
     * name like '王%' or age >= 25 order by age desc,id asc
     */
    @Test
    public void selectByWarpper3() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 创建时间为2019年2月14日并且直属上级名字为王姓
     * date_format(create_time,'%Y-%m-%d') and manager_id in (select id from queryDemo where name like '王%')
     */
    @Test
    public void selectByWarpper4() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        // 此处写法存在sql注入问题，不建议使用
        // queryDemoQuery.apply("date_format(create_time,'%Y-%m-%d')='2019-02-14' or true or true").inSql("manager_id",
        // "select id from queryDemo where name like '王%'");

        queryDemoQuery.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14").inSql("manager_id",
                "select id from queryDemo where name like '王%'");

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 名字为王姓并且（年龄小于40或邮箱不为空）
     * name like '王%' and (age < 40 or email is not null)
     */
    @Test
    public void selectByWarpper5() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 名字为王姓或者（年龄小于40并且大于20并且邮箱不为空）
     * name like '王%' or (age < 40 and age > 20 and email is not null)
     */
    @Test
    public void selectByWarpper6() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.likeRight("name", "王").or(wq -> wq.lt("age", 40).gt("age", 20).isNotNull("email"));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * （年龄小于40或邮箱不为空）并且名字为王姓
     * (age < 40 or email is not null) and name like '王%'
     */
    @Test
    public void selectByWarpper7() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.nested(wq -> wq.lt("age", 40).or().isNotNull("email")).likeRight("name", "王");

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 年龄为30,31,34,35
     * age in (30,31,34,35)
     */
    @Test
    public void selectByWarpper8() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.in("age", Arrays.asList(30, 31, 34, 35));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 只返回满足条件的其中一条语句即可
     * limit 1
     */
    @Test
    public void selectByWarpper9() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.in("age", Arrays.asList(30, 31, 34, 35)).last("limit 1");

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 姓名中含雨并且年龄小于40(只返回id和name两列)
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWarpper10_1() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.select("id", "name").like("name", "雨").lt("age", 40);

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 姓名中含雨并且年龄小于40(只返回id、name、age、email列)
     * name like '%雨%' and age < 40
     */
    @Test
    public void selectByWarpper10_2() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.like("name", "雨").lt("age", 40).select(QueryDemo.class,
                info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。并且只取年龄总和小于500的组
     * select avg(age) avg_age, max(age) max_age, min(age) min_age from queryDemo
     * group by manager_id having sum(age) < 500
     */
    @Test
    public void selectByWarpper11() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.select("avg(age) avg_age", "max(age) max_age", "min(age) min_age").groupBy("manager_id")
                .having("sum(age) < {0}", 500);

        List<Map<String, Object>> queryDemoList = queryDemoMapper.selectMaps(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectByWarpperObjs() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();
        queryDemoQuery.select("id", "name").like("name", "雨").lt("age", 40);

        List<Object> queryDemoList = queryDemoMapper.selectObjs(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectByWarpperCount() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();
        queryDemoQuery.like("name", "雨").lt("age", 40);

        Integer count = queryDemoMapper.selectCount(queryDemoQuery);
        System.out.println("总记录数：" + count);
    }

    @Test
    public void selectByWarpperOne() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();
        queryDemoQuery.like("name", "刘红雨").lt("age", 40);

        QueryDemo queryDemo = queryDemoMapper.selectOne(queryDemoQuery);
        System.out.println(queryDemo);
    }

    @Test
    public void selectByWarpperEntity() {
        QueryDemo queryDemoWhere = new QueryDemo();
        queryDemoWhere.setName("刘红雨");
        queryDemoWhere.setAge(32);

        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>(queryDemoWhere);
        // queryDemoQuery.like("name", "雨").lt("age", 40);

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectByWarpperAllEq() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        params.put("age", null);

        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();
        // queryDemoQuery.allEq(params, false);
        queryDemoQuery.allEq((k, v) -> !k.equals("name"), params);

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectByWarpperMaps() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();
        queryDemoQuery.select("name", "age").like("name", "雨").lt("age", 40);

        List<Map<String, Object>> queryDemoList = queryDemoMapper.selectMaps(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectLambda1() {
        // 条件构造器
        LambdaQueryWrapper<QueryDemo> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.like(QueryDemo::getName, "雨").lt(QueryDemo::getAge, 40);

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(lambdaQueryWrapper);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectLambda2() {
        // 条件构造器
        LambdaQueryWrapper<QueryDemo> lambdaQuery = new LambdaQueryWrapper<>();
        lambdaQuery.likeRight(QueryDemo::getName, "王").and(age -> age.lt(QueryDemo::getAge, 40).or().isNotNull(QueryDemo::getEmail));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(lambdaQuery);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectLambda3() {
        // 条件构造器
        List<QueryDemo> queryDemoList = new LambdaQueryChainWrapper<QueryDemo>(queryDemoMapper).like(QueryDemo::getName, "雨")
                .ge(QueryDemo::getAge, 20).list();
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectMy() {
        LambdaQueryWrapper<QueryDemo> lambda = new QueryWrapper<QueryDemo>().lambda();
        lambda.likeRight(QueryDemo::getName, "王").and(age -> age.lt(QueryDemo::getAge, 40).or().isNotNull(QueryDemo::getEmail));

        List<QueryDemo> queryDemoList = queryDemoMapper.selectAll(lambda);
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectListPage() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.ge("age", 26);

        Page<QueryDemo> page = new Page<>(1, 3);

        IPage<QueryDemo> queryDemoPage = queryDemoMapper.selectPage(page, queryDemoQuery);
        System.out.println("总页数：" + queryDemoPage.getPages());
        System.out.println("总记录数：" + queryDemoPage.getTotal());

        List<QueryDemo> queryDemoList = queryDemoPage.getRecords();
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectMapsPage() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.ge("age", 26);

        Page<QueryDemo> page = new Page<>(1, 3);

        IPage<Map<String, Object>> queryDemoPage = queryDemoMapper.selectMapsPage(page, queryDemoQuery);
        System.out.println("总页数：" + queryDemoPage.getPages());
        System.out.println("总记录数：" + queryDemoPage.getTotal());

        List<Map<String, Object>> queryDemoList = queryDemoPage.getRecords();
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selectPageRecords() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.ge("age", 26);

        Page<QueryDemo> page = new Page<>(1, 3, false);

        IPage<Map<String, Object>> queryDemoPage = queryDemoMapper.selectMapsPage(page, queryDemoQuery);
        System.out.println("总页数：" + queryDemoPage.getPages());
        System.out.println("总记录数：" + queryDemoPage.getTotal());

        List<Map<String, Object>> queryDemoList = queryDemoPage.getRecords();
        queryDemoList.forEach(System.out::println);
    }

    @Test
    public void selecMypage() {
        // 条件构造器
        QueryWrapper<QueryDemo> queryDemoQuery = Wrappers.query();
        queryDemoQuery.ge("age", 26);

        Page<QueryDemo> page = new Page<>(1, 3);

        IPage<QueryDemo> queryDemoPage = queryDemoMapper.selectQueryDemoPage(page, queryDemoQuery);
        System.out.println("总页数：" + queryDemoPage.getPages());
        System.out.println("总记录数：" + queryDemoPage.getTotal());

        List<QueryDemo> queryDemoList = queryDemoPage.getRecords();
        queryDemoList.forEach(System.out::println);
    }

    /**
     * 多个查询参数，其中为空则不做条件
     */
    @Test
    public void testCondition() {
        String name = "王";
        String email = "";
        condition(name, email);
    }

    private void condition(String name, String email) {
        QueryWrapper<QueryDemo> queryDemoQuery = new QueryWrapper<>();

        // if (StringUtils.isNotEmpty(name)) {
        // queryDemoQuery.like("name", name);
        // }
        // if (StringUtils.isNotEmpty(email)) {
        // queryDemoQuery.like("email", email);
        // }

        queryDemoQuery.like(StringUtils.isNotEmpty(name), "name", name).like(StringUtils.isNotEmpty(email), "email", email);
        List<QueryDemo> queryDemoList = queryDemoMapper.selectList(queryDemoQuery);
        queryDemoList.forEach(System.out::println);
    }
}