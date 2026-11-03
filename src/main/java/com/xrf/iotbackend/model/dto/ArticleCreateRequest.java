package com.xrf.iotbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "创建文章请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCreateRequest {

    @Schema(description = "文章标题，必填，1-200 个字符", example = "Spring Boot 入门")
    @NotBlank(message = "标题不能为空")
    @Size(min = 1 , max = 200, message = "标题不能为空，且长度不能超过200个字符")
    private String title;

    @Schema(description = "文章正文，必填，不能为空", example = "这是文章正文")
    @NotBlank(message = "正文不能为空")
    @Size(min = 1 , message = "正文不能为空")
    private String content;

    @Schema(description = "封面地址，可选；仅支持 http/https，最长 2048 字符；空串无效，null/不传表示无封面", example = "https://example.com/cover.jpg")
    @Size(max = 2048, message = "封面地址不能超过2048个字符")
    private String coverUrl;

    @Schema(description = "分类 ID，必填，正整数", example = "1")
    @NotNull(message = "分类不能为空")
    @Positive(message = "分类ID必须为正整数")
    private Integer categoryId;

    //JSON 中该字段的值是显式的 null，则直接抛出异常，拒绝赋值
    @Schema(description = "标签名称列表，可选；显式 null 返回 40001；元素非空且最长 50 字符，不存在时自动创建", example = "[\"Java\", \"Spring\"]")
    @JsonSetter(nulls = Nulls.FAIL)
    private List<
            @NotBlank(message = "标签名称不能为空")
            @Size(max = 50, message = "标签名称不能超过50个字符")
            String
            > tags;

}
