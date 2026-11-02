package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.vo.TagVO;
import com.xrf.iotbackend.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "标签", description = "标签查询")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    @Operation(summary = "查询所有标签")
    @GetMapping
    public Result getAllTags(){
        List<TagVO> tagVOS = tagService.getAllTags();
        return Result.success(tagVOS);
    }

}
