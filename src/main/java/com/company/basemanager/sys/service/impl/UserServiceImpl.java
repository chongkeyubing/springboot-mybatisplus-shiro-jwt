package com.company.basemanager.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.company.basemanager.sys.entity.User;
import com.company.basemanager.sys.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IService<User> {

}
