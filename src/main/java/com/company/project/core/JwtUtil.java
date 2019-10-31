package com.company.project.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * <p>JWT工具类</p>
 * <p>jwt组成：Header(头部).Payload(载荷).Signature(签名)</p>
 * <p>算法：base64enc({"alg":"HS256","typ":"JWT"}).base64enc({"account":"libaogang","exp":"1441594722"}).HMACSHA256(base64enc(header)+"."+base64enc(header),secret)</p>
 *
 * @author libaogang
 * @since 2019-08-15 21:18
 */
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30 * 1000;

    // 免登录时间一周
    public static final long REFRESH_TIME = 7 * 24 * 60 * 60 * 1000;

    // 签名密钥
    private static final String SECRET = "test";

    // 写入payload的字段名
    public static final String CLAIM_USER_ID = "userId";

    public static final String CLAIM_REAL_NAME = "realname";

    /**
     * 生成token
     *
     * @param userId   用户，写入token payload部分
     * @param realname 真实姓名，写入token payload部分
     * @return java.lang.String 返回token
     * @author libaogang
     * @since 2019-08-15 21:25
     */
    public static String sign(Long userId, String realname) {
        try {
            Date expire = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);  //算法
            return JWT.create()
                    .withClaim(Constant.USER_ID, userId)      //Payload 部分
                    .withClaim(Constant.REALNAME, realname)
                    .withExpiresAt(expire)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持secret的编码类型:" + e.getMessage(), e);
            throw new BusinessException("不支持secret的编码类型:" + e.getMessage());
        }
    }

    /**
     * 校验token(包括是否过期)
     *
     * @param token Token
     * @return boolean 校验结果
     * @author libaogang
     * @since 2019-08-15 21:22
     */
    public static boolean verify(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持secret的编码类型:" + e.getMessage(), e);
            throw new BusinessException("不支持secret的编码类型:" + e.getMessage());
        }
    }

    /**
     * 获得Token中自定义字段
     *
     * @param token Token
     * @param claim 要获取的字段名
     * @return java.lang.Obejct 返回要获取的字段值
     * @author libaogang
     * @since 2019-08-15 21:25
     */
    public static Claim getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim);
        } catch (Exception e) {
            LOGGER.error("解码token中的公共信息异常:" + e.getMessage(), e);
            throw new BusinessException("解码token中的公共信息异常:" + e.getMessage());
        }
    }

    /**
     * 获取token中所有自定义字段
     *
     * @author libaogang
     * @since 2019-10-31 23:02:56
     * @param token Token
     * @return java.util.Map<java.lang.String,com.auth0.jwt.interfaces.Claim>
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaims();
        } catch (Exception e) {
            LOGGER.error("解码token中的公共信息异常:" + e.getMessage(), e);
            throw new BusinessException("解码token中的公共信息异常:" + e.getMessage());
        }
    }


    public static void main(String[] args) {
        String token = sign(1L, "超级管理员");
        System.out.println(token);

        Long userId = getClaim(token, Constant.USER_ID).asLong();
        System.out.println(userId);

        Map<String, Claim> claims = getClaims(token);
        Long userId1 = claims.get(Constant.USER_ID).asLong();
        String realname = claims.get(Constant.REALNAME).asString();
        System.out.println(userId1 + realname );

        System.out.println(verify(token));

    }
}