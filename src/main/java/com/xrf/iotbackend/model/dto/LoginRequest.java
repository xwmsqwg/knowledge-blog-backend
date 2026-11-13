package com.xrf.iotbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "登录请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @Schema(description = "用户名，必填", example = "userName")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码，必填", example = "123456")
    @NotBlank(message = "密码不能为空")
    private String password;

}
