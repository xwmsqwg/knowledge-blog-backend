package com.xrf.iotbackend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "标签信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagVO {

    @Schema(description = "标签 ID", example = "1")
    private Integer id;
    @Schema(description = "标签名称", example = "Java")
    private String name;
    @Schema(description = "引用该标签的文章数", example = "3")
    private Long articleCount;

}
