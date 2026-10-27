package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.RegisterRequest;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Result register(@Valid @RequestBody RegisterRequest registerRequest){
        UserVO userVO = authService.register(registerRequest);
        return Result.success("注册成功" , userVO);
    }

}
