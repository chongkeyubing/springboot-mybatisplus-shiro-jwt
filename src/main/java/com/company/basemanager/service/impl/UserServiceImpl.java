package com.company.basemanager.service.impl;

import com.company.basemanager.entity.User;
import com.company.basemanager.mapper.UserMapper;
import com.company.basemanager.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-08-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
