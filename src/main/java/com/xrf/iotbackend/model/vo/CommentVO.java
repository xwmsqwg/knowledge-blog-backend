package com.xrf.iotbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "评论信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {

    @Schema(description = "评论 ID", example = "1")
    private Integer id;
    @Schema(description = "文章 ID", example = "1")
    private Integer articleId;
    @Schema(description = "评论正文", example = "这是一条评论")
    private String content;
    @Schema(description = "评论作者")
    private AuthorVO author;
    @Schema(description = "创建时间", type = "string", format = "yyyy-MM-dd HH:mm:ss", example = "2026-11-03 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
