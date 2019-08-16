package com.company.basemanager.util;

import com.company.basemanager.core.SysConstant;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * 加密工具类
 *
 * @author libaogang
 * @since 2019-08-16 7:56
 */
public class EncryptUtil {

    /**
     * 基于shiro的MD5加密方法
     *
     * @param password       密码
     * @param salt           加密盐值
     * @param hashIterations 加密次数
     * @return java.lang.String 加密后的密码
     * @author libaogang
     * @since 2019-08-16 8:29
     */
    public static String md5(String password, String salt, int hashIterations) {
        //加密方式
        String hashAlgorithmName = "MD5";

        //盐：即使相同的密码不同的盐加密后的结果也不同
        ByteSource byteSalt = ByteSource.Util.bytes(salt);
        SimpleHash result = new SimpleHash(hashAlgorithmName, password, byteSalt, hashIterations);
        return result.toString();
    }

    public static void main(String[] args) {
        String password = md5("123456", SysConstant.SALT, 1024);
        System.out.println(password);
    }
}