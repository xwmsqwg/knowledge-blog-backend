package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name = "系统信息")
@RestController
@RequestMapping("/api/v1/info")
public class VersionController {

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private BuildProperties buildProperties;
    // 时间格式化
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Operation(summary = "获取系统信息")
    @GetMapping
    public Result<Map<String , Object>> getInfo(){
        // 获取启动时间
        LocalDateTime startTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(applicationContext.getStartupDate()), ZoneId.of("Asia/Shanghai"));

        Map<String , Object> info = Map.of(
                "系统版本" , buildProperties.getVersion(),
                "启动时间" , FMT.format(startTime),
                "技术栈信息", List.of(
                        "JDK " + System.getProperty("java.version"),
                        "Spring Boot " + SpringBootVersion.getVersion(),
                        "MyBatis-Plus 3.5.17",
                        "MySQL 8.0+"
                )
        );
        log.info("获取系统信息成功");
        return Result.success(info);
    }
}
