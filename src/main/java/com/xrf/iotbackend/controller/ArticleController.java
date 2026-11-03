package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.dto.ArticleUpdateRequest;
import com.xrf.iotbackend.model.vo.ArticlePageVO;
import com.xrf.iotbackend.model.vo.ArticleVO;
import com.xrf.iotbackend.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Tag(name = "文章", description = "文章创建、分页查询、详情、更新与删除")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping(value = "/api/v1/articles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Operation(summary = "创建文章" , description = "标签不存在时自动创建")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result<ArticleVO> createArticle(
            @Valid @RequestBody ArticleCreateRequest articleCreateRequest,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        ArticleVO articleVO = articleService.createArticle(articleCreateRequest , authorId);
        return Result.success("文章创建成功" , articleVO);
    }

    @Operation(summary = "分页查询文章")
    @GetMapping
    public Result<ArticlePageVO> pageAll(
            @Parameter(description = "页码，从 1 开始")
            @RequestParam(defaultValue = "1") @Min(1) Integer page,
            @Parameter(description = "每页数量，1-100")
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
            @RequestParam(required = false) @Min(1) Integer category,
            @RequestParam(required = false) @Min(1) Integer tag,
            @RequestParam(defaultValue = "desc") String sort
    ){
        return Result.success(articleService.pageArticles(page , size , category , tag , sort));
    }

    @Operation(summary = "查询文章详情")
    @GetMapping("/{id}")
    public Result<ArticleVO> getArticle(@PathVariable("id") Integer articleId){
        return Result.success(articleService.getArticle(articleId));
    }

    @Operation(summary = "更新文章", description = "仅文章作者可更新")
    @PutMapping("/{id}")
    public Result<ArticleVO> updateArticle(
            @PathVariable("id") Integer articleId,
            @Valid @RequestBody ArticleUpdateRequest articleUpdateRequest,
            @RequestAttribute("currentUserId") Integer authorId
            ){
        ArticleVO articleVO = articleService.updateArticle(articleId , articleUpdateRequest , authorId);
        return Result.success(articleVO);
    }

    @Operation(summary = "删除文章", description = "仅文章作者可删除")
    @DeleteMapping("/{id}")
    public Result<Void> deleteArticle(
            @PathVariable("id") Integer articleId,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        articleService.deleteArticle(articleId , authorId);
        return Result.successMessage("文章删除成功");
    }
}
