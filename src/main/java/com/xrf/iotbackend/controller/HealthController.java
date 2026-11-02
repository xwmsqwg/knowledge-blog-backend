package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "健康检查", description = "服务健康状态")
@RestController
public class HealthController {

    @Autowired
    private ApplicationAvailability applicationAvailability;

    @Operation(summary = "健康检查")
    @GetMapping("/health")
    public Result health() {
        boolean isUp = applicationAvailability.getReadinessState() == ReadinessState.ACCEPTING_TRAFFIC;
        return Result.success(isUp ? "service is up" : "service is down", Map.of("status", isUp ? "UP" : "DOWN"));
    }
}