package com.xrf.iotbackend.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVO {

    private List<ArticleListVO> records;
    private Long total;
    private Integer page;
    private Integer size;

}
