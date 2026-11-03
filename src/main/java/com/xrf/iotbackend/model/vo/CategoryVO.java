package com.xrf.iotbackend.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "分类信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryVO {

    @Schema(description = "分类 ID", example = "1")
    private Integer id;
    @Schema(description = "分类名称", example = "后端")
    private String name;
    @Schema(description = "该分类下文章数", example = "3")
    private Integer articleCount;
    @Schema(description = "创建者用户 ID", example = "1")
    private Integer creatorId;
    @Schema(description = "创建时间", type = "string", format = "yyyy-MM-dd HH:mm:ss", example = "2026-11-03 12:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}
