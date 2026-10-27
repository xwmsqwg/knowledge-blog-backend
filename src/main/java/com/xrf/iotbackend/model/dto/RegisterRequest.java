package com.xrf.iotbackend.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "用户名不能为空")
    @Size(min = 3 , max = 32 , message = "用户名长度必须在3到32个字符之间")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 6 , message = "密码至少为6位")
    private String password;

    @NotBlank(message = "邮箱不能为空")
    @Email(message = "必须填写合法邮箱格式")
    @Size(max = 254 , message = "邮箱长度不能超过254个字符")
    private String email;

}
