package com.xrf.iotbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "文章详情")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {

    @Schema(description = "文章 ID", example = "1")
    private Integer id;
    @Schema(description = "文章标题", example = "Spring Boot 入门")
    private String title;
    @Schema(description = "文章正文", example = "这是文章正文")
    private String content;
    @Schema(description = "封面地址；无封面时为 null", example = "https://example.com/cover.jpg")
    private String coverUrl;
    @Schema(description = "作者信息")
    private AuthorVO author;
    @Schema(description = "分类信息")
    private CategorySimpleVO category;
    @Schema(description = "标签列表")
    private List<TagSimpleVO> tags;
    @Schema(description = "评论列表，按创建时间升序；无评论时为 []")
    private List<CommentVO> comments;
    @Schema(description = "创建时间", type = "string", format = "yyyy-MM-dd HH:mm:ss", example = "2026-11-03 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @Schema(description = "更新时间", type = "string", format = "yyyy-MM-dd HH:mm:ss", example = "2026-11-03 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
