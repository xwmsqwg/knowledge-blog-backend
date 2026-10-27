package com.xrf.iotbackend.utils;

import com.xrf.iotbackend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtils {

    @Autowired
    private JwtProperties jwtProperties;

    //生成Jwt
    public String generateJwt(Map<String, Object> claims) {
        //读取配置文件的密钥和过期时间
        String secret = jwtProperties.getSecret();
        long expirationMs = jwtProperties.getExpirationMs();
        //把字符串密钥转换成 HMAC 签名密钥
        SecretKey KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                //用户信息放入 JWT 的 Payload
                .addClaims(claims) // 自定义信息
                //使用算法进行签名，保证jwt没被篡改
                .signWith(KEY, Jwts.SIG.HS256)
                //设置过期时间
                .expiration(new Date(System.currentTimeMillis() + expirationMs))
                .compact();
    }
    // 解析 JWT，失败返回 null
    public Claims parseJWT(String jwt) {
        String secret = jwtProperties.getSecret();
        SecretKey KEY = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        try {
            return Jwts.parser()
                    .verifyWith(KEY)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }
}
