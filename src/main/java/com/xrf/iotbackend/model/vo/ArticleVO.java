package com.xrf.iotbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleVO {

    private Integer id;
    private String title;
    private String content;
    private String coverUrl;
    private AuthorVO author;
    private CategorySimpleVO category;
    private List<TagSimpleVO> tags;
    private List<CommentVO> comments;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
