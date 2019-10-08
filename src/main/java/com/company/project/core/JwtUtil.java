package com.company.project.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JWT工具类
 * <p>jwt组成：Header(头部).Payload(载荷).Signature(签名)</p>
 * <p>算法：base64enc({"alg":"HS256","typ":"JWT"}).base64enc({"account":"libaogang","exp":"1441594722"}).HMACSHA256(base64enc(header)+"."+base64enc(header),secret)</p>
 * <p>jwt串：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjU5MTI1MjYsImFjY291bnQiOiJsaWJhb2dhbmcifQ.qzVl_-5okCyWyarE5tbSYFXe7dcnasLU9JoKXwhYug0</p>
 * </p>
 *
 * @author libaogang
 * @since 2019-08-15 21:18
 */
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    // 过期时间30分钟
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    // 签名密钥
    private static final String SECRET = "test";

    // 写入payload的字段名
    public static final String CLAIM_USER_ID = "userId";

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
     * 获得Token中的信息
     *
     * @param token Token
     * @param claim 要获取的字段名
     * @return java.lang.Obejct 返回要获取的字段值
     * @author libaogang
     * @since 2019-08-15 21:25
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (Exception e) {
            LOGGER.error("解码token中的公共信息异常:" + e.getMessage(), e);
            throw new BusinessException("解码token中的公共信息异常:" + e.getMessage());
        }
    }

    /**
     * 生成token
     *
     * @param userId 用户ID，写入token payload部分
     * @return java.lang.String 返回token
     * @author libaogang
     * @since 2019-08-15 21:25
     */
    public static String sign(Long userId) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);  //算法
            return JWT.create()
                    .withClaim(CLAIM_USER_ID, userId.toString())   //Payload 部分
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("不支持secret的编码类型:" + e.getMessage(), e);
            throw new BusinessException("不支持secret的编码类型:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String token = sign(1L);
        System.out.println(token);

        Long userId =  Long.parseLong(getClaim(token, CLAIM_USER_ID));
        System.out.println(userId);

        System.out.println(verify(token));

    }
}