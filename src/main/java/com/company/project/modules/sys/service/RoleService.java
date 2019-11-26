package com.company.project.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.project.modules.sys.entity.RoleEntity;

import java.util.List;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
public interface RoleService extends IService<RoleEntity> {

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
