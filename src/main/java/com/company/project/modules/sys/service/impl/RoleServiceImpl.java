package com.company.project.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.company.project.modules.sys.entity.RoleEntity;
import com.company.project.modules.sys.mapper.RoleMapper;
import com.company.project.modules.sys.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-11-26
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, RoleEntity> implements RoleService {

    @Override
    public List<RoleEntity> listRolesByUserId(long userId) {
        return baseMapper.listRolesByUserId(userId);
    }
}
