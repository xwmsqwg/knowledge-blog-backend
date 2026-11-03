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

    @Schema(description = "文章标题，必填，1-200 个字符", example = "Spring Boot 入门")
    @NotBlank(message = "标题不能为空")
    @Size(min = 1 , max = 200, message = "标题不能为空，且长度不能超过200个字符")
    private String title;
    
    @Schema(description = "文章正文，必填，不能为空", example = "这是文章正文")
    @NotBlank(message = "正文不能为空")
    @Size(min = 1 , message = "正文不能为空")
    private String content;

    @Schema(description = "分类 ID，必填，正整数", example = "1")
    @NotNull(message = "分类不能为空")
    @Positive(message = "分类ID必须为正整数")
    private Integer categoryId;

    @Schema(description = "标签列表，可选；不传保留原标签，[] 清空，显式 null 返回 40001；元素非空且最长 50 字符", example = "[\"Java\", \"Spring\"]")
    @JsonSetter(nulls = Nulls.FAIL)
    private List<
            @NotBlank(message = "标签名称不能为空")
            @Size(max = 50, message = "标签名称不能超过50个字符")
            String
            > tags;
    
}
