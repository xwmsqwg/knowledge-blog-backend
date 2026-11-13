package com.xrf.iotbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "注册请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Schema(description = "用户名，必填，3-32 个字符", example = "userName")
    @NotBlank(message = "用户名不能为空")
    @Size(min = 3 , max = 32 , message = "用户名长度必须在3到32个字符之间")
    private String username;

    @Schema(description = "密码，必填，至少 6 位，UTF-8 最多 72 字节", example = "123456")
    @NotBlank(message = "密码不能为空")
    @Size(min = 6 , message = "密码至少为6位")
    private String password;

    @Schema(description = "邮箱，必填，合法邮箱格式，最长 254 字符", example = "user@example.com")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "必须填写合法邮箱格式")
    @Size(max = 254 , message = "邮箱长度不能超过254个字符")
    private String email;

}
