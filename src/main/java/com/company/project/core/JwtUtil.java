package com.company.project.core;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * JWT工具类
 * <p>
 * jwt组成：Header(头部).Payload(载荷).Signature(签名)
 * <p>
 * 算法：base64enc({"alg":"HS256","typ":"JWT"}).base64enc({"account":"libaogang","exp":"1441594722"}).HMACSHA256(base64enc(header)+"."+base64enc(header),secret)
 * <p>
 * jwt串：eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1NjU5MTI1MjYsImFjY291bnQiOiJsaWJhb2dhbmcifQ.qzVl_-5okCyWyarE5tbSYFXe7dcnasLU9JoKXwhYug0
 *
 * @author libaogang
 * @since 2019-08-15 21:18
 */
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 30 * 60 * 1000;

    // 签名密钥
    private static final String SECRET = "test";

    // 写入payload的字段名
    public static final String CLAIM_ACCOUNT = "account";


    /**
     * 校验token是否正确
     *
     * @param token Token
     * @return boolean 是否正确
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
        } catch (Exception e) {
            logger.error("JwtToken认证解密出现异常:" + e.getMessage());
            return false;
        }
    }

    /**
     * 获得Token中的信息
     *
     * @param token Token
     * @param claim 要获取的字段名
     * @return java.lang.String 返回要获取的字段值
     * @author libaogang
     * @since 2019-08-15 21:25
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            logger.error("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
            throw new ServiceException("解密Token中的公共信息出现JWTDecodeException异常:" + e.getMessage());
        }
    }

    /**
     * 生成token
     *
     * @param account 用户账号，写入token payload部分
     * @return java.lang.String 返回token
     * @author Wang926454
     * @since 2018/8/31 9:07
     */
    public static String sign(String account) {
        try {
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(SECRET);  //算法
            // 附带account信息
            return JWT.create()
                    .withClaim(CLAIM_ACCOUNT, account)   //Payload 部分
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            logger.error("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
            throw new ServiceException("JWTToken加密出现UnsupportedEncodingException异常:" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String token = sign("boss");
        System.out.println(token);

        String account = getClaim(token, CLAIM_ACCOUNT);
        System.out.println(account);

        System.out.println(verify(token));

    }
}