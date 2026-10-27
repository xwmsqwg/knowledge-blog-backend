package com.xrf.iotbackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.xrf.iotbackend.repository")
@SpringBootApplication
public class IotBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(IotBackendApplication.class, args);
    }

}
