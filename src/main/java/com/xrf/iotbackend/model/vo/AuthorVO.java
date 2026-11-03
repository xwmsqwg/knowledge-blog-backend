package com.xrf.iotbackend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "作者信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorVO {

    @Schema(description = "作者用户 ID", example = "1")
    private Integer id;
    @Schema(description = "作者用户名", example = "userName")
    private String username;

}
