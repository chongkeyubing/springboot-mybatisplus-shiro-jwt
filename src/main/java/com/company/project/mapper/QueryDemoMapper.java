package com.company.project.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.company.project.entity.QueryDemo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * mybatis-plus 查询demo表 Mapper 接口
 * </p>
 *
 * @author libaogang
 * @since 2019-08-16
 */
public interface QueryDemoMapper extends BaseMapper<QueryDemo> {

    // @Select("select * from user ${ew.customSqlSegment}")
    List<QueryDemo> selectAll(@Param(Constants.WRAPPER) Wrapper<QueryDemo> wrapper);

    IPage<QueryDemo> selectQueryDemoPage(Page<QueryDemo> page, @Param(Constants.WRAPPER) Wrapper<QueryDemo> wrapper);

}
