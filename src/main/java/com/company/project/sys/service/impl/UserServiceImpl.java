package com.company.project.sys.service.impl;

import com.company.project.sys.entity.User;
import com.company.project.sys.mapper.UserMapper;
import com.company.project.sys.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author libaogang
 * @since 2019-08-24
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
