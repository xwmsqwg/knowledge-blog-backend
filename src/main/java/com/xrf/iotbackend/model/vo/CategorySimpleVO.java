package com.xrf.iotbackend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "分类精简信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySimpleVO {

    @Schema(description = "分类 ID", example = "1")
    private Integer id;
    @Schema(description = "分类名称", example = "后端")
    private String name;

}
