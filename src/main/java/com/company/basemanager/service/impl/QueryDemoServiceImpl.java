package com.company.basemanager.service.impl;

import com.company.basemanager.entity.QueryDemo;
import com.company.basemanager.mapper.QueryDemoMapper;
import com.company.basemanager.service.QueryDemoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * mybatis-plus 查询demo表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-08-16
 */
@Service
public class QueryDemoServiceImpl extends ServiceImpl<QueryDemoMapper, QueryDemo> implements QueryDemoService {

}
