package com.xrf.iotbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "更新文章请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleUpdateRequest {

    @NotBlank(message = "标题不能为空")
    @Size(min = 1 , max = 200, message = "标题不能为空，且长度不能超过200个字符")
    private String title;
    
    @NotBlank(message = "正文不能为空")
    @Size(min = 1 , message = "正文不能为空")
    private String content;

    @Schema(description = "分类 ID", example = "1")
    @NotNull(message = "分类不能为空")
    @Positive(message = "分类ID必须为正整数")
    private Integer categoryId;

    @Schema(description = "标签列表：不传保留原标签，[] 清空，null 返回 40001")
    @JsonSetter(nulls = Nulls.FAIL)
    private List<
            @NotBlank(message = "标签名称不能为空")
            @Size(max = 50, message = "标签名称不能超过50个字符")
            String
            > tags;
    
}
