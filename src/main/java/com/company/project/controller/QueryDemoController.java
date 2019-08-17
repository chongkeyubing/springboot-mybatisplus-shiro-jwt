package com.company.project.controller;


import com.company.project.core.Result;
import com.company.project.core.ResultUtil;
import com.company.project.entity.QueryDemo;
import com.company.project.service.QueryDemoService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * mybatis-plus 查询demo表 前端控制器
 * </p>
 *
 * @author libaogang
 * @since 2019-08-16
 */
@RestController
@RequestMapping("/query-demo")
public class QueryDemoController {

    @Resource
    private QueryDemoService queryDemoService;
    
    @RequestMapping("/list")
    public Result list() {
        List<QueryDemo> list = queryDemoService.list();
        return ResultUtil.success(list);
    }
}
