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

    // 联合主键使用自定义 SQL 或 QueryWrapper 操作，不能使用 updateById/deleteById。
    private Integer articleId;
    private Integer tagId;

}
