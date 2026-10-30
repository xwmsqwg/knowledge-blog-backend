package com.xrf.iotbackend.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("article_tags")
public class ArticleTag {

    private Integer articleId;
    private Integer tagId;

}
