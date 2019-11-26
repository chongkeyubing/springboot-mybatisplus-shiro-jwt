package com.company.project.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.modules.sys.entity.PermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
public interface PermissionMapper extends BaseMapper<PermissionEntity> {

    /**
     * 根据角色id列表查询权限
     *
     * @param roleIds 角色id列表
     * @return java.util.List<com.company.project.modules.sys.entity.PermissionEntity>
     * @author libaogang
     * @date 2019-11-26 14:25:32
     */
    List<PermissionEntity> listPermissionsByRoleIds(@Param("roleIds") List<Long> roleIds);
}
