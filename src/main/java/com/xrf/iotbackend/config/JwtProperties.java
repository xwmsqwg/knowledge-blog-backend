package com.xrf.iotbackend.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
// 将 application 中 security.jwt 前缀下的配置绑定到本类字段
@ConfigurationProperties(prefix = "security.jwt")
public class JwtProperties {

    // 密钥
    private String secret;
    // 过期时间
    private long expirationMs;

}
