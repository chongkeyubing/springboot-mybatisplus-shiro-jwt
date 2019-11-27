package com.company.project.modules.sys.service;

/**
 * 登录
 *
 * @author libaogang
 * @since 2019-11-27 上午 9:00
 */
public interface LoginService {

    /**
     * 登录
     *
     * @author libaogang
     * @param username 用户名
     * @param password 密码
     * @return java.lang.String 返回token
     * @date 2019-11-27 09:03:09
     */
    String login(String username, String password);

}