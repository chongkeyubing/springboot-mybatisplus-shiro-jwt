package com.company.project.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * MD5加密工具类
 *
 * @author libaogang
 * @since 2019-08-16 7:56
 */
public class MD5Util {

    private static final String ALGORITH_NAME = "md5";

    //加密次数
    private static final int HASH_ITERATIONS = 2;

    //加密盐
    private static final String SALT = "test";

    /**
     * 基于shiro的MD5加密方法
     *
     * @param password 密码
     * @return java.lang.String 加密后的密码
     * @author libaogang
     * @since 2019-08-16 8:29
     */
    public static String encrypt(String password) {
        SimpleHash result = new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(SALT), HASH_ITERATIONS);
        return result.toString();
    }

    public static void main(String[] args) {
        String password = encrypt("123456");
        System.out.println(password);
    }
}