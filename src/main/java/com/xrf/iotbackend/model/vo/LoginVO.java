package com.xrf.iotbackend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "登录响应")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO {

    @Schema(description = "JWT；后续请求放在 Authorization: Bearer <token>")
    private String token;
    @Schema(description = "登录用户信息")
    private UserVO user;

}
