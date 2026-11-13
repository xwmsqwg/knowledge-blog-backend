package com.xrf.iotbackend.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "文章分页结果")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticlePageVO {

    @Schema(description = "当前页文章列表")
    private List<ArticleListVO> records;
    @Schema(description = "总记录数", example = "1")
    private Long total;
    @Schema(description = "当前页码，从 1 开始", example = "1")
    private Integer page;
    @Schema(description = "每页数量，1-100", example = "10")
    private Integer size;

}
