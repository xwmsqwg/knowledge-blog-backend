package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.LoginRequest;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.vo.LoginVO;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证", description = "用户注册与登录")
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Result<UserVO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        UserVO userVO = authService.register(registerRequest);
        return Result.success("注册成功", userVO);
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginVO loginVO = authService.login(loginRequest);
        return Result.success(loginVO);
    }
}

