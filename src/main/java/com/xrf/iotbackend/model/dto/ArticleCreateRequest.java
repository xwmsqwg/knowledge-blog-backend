package com.xrf.iotbackend.model.dto;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCreateRequest {

    @NotBlank(message = "标题不能为空")
    @Size(min = 1 , max = 200, message = "标题不能为空，且长度不能超过200个字符")
    private String title;

    @NotBlank(message = "正文不能为空")
    @Size(min = 1 , message = "正文不能为空")
    private String content;

    @Size(max = 2048, message = "封面地址不能超过2048个字符")
    private String coverUrl;

    @NotNull(message = "分类不能为空")
    @Positive(message = "分类ID必须为正整数")
    private Integer categoryId;

    //JSON 中该字段的值是显式的 null，则直接抛出异常，拒绝赋值
    @JsonSetter(nulls = Nulls.FAIL)
    private List<
            @NotBlank(message = "标签名称不能为空")
            @Size(max = 50, message = "标签名称不能超过50个字符")
            String
            > tags;

}
