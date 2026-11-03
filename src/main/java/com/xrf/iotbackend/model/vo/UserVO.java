package com.xrf.iotbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "用户信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    @Schema(description = "用户 ID", example = "1")
    private Integer id;
    @Schema(description = "用户名", example = "userName")
    private String username;
    @Schema(description = "邮箱", example = "user@example.com")
    private String email;
    @Schema(description = "注册时间", type = "string", format = "yyyy-MM-dd HH:mm:ss", example = "2026-11-03 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
