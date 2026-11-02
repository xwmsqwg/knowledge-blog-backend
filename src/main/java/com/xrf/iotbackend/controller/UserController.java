package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.vo.UserVO;
import com.xrf.iotbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "用户", description = "当前登录用户信息")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "获取当前登录用户")
    @GetMapping("/me")
    public Result getCurrentUser(@RequestAttribute("currentUserId") Integer userId){
        UserVO userVO = userService.getCurrUser(userId);
        return Result.success(userVO);
    }

}
