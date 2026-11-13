package com.xrf.iotbackend.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "发表评论请求")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateRequest {

    @Schema(description = "文章 ID，必填，正整数；文章必须存在", example = "1")
    @Positive
    @NotNull
    private Integer articleId;

    @Schema(description = "评论正文，必填，不能为空", example = "这是一条评论")
    @NotBlank(message = "正文不能为空")
    @Size(min = 1 , message = "正文不能为空")
    private String content;

}
