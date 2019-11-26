package com.company.project.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.company.project.modules.sys.entity.RoleEntity;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    /**
     * 根据用户id查询角色
     *
     * @author libaogang
     * @param userId 用户id
     * @return java.util.List<com.company.project.modules.sys.entity.RoleEntity>
     * @date 2019-11-26 14:18:04
     */
    List<RoleEntity> listRolesByUserId(long userId);

}
