package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.vo.ArticleVO;
import com.xrf.iotbackend.service.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // 创建文章
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result createArticle(
            @Valid @RequestBody ArticleCreateRequest articleCreateRequest,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        ArticleVO articleVO = articleService.createArticle(articleCreateRequest , authorId);
        return Result.success("文章创建成功" , articleVO);
    }

    //分页查询所有文章
    @GetMapping
    public Result pageAll(
        @RequestParam(defaultValue = "1") @Min(1) Integer page,
        @RequestParam(defaultValue = "10") @Min(1) @Max(100) Integer size,
        @RequestParam(required = false) @Min(1) Integer category,
        @RequestParam(required = false) @Min(1) Integer tag,
        @RequestParam(defaultValue = "desc") String sort
    ){
        return Result.success(articleService.pageArticles(page , size , category , tag , sort));
    }

    //查询文章详情
    @GetMapping("/{id}")
    public Result getArticle(@PathVariable("id") Integer articleId){
        return Result.success(articleService.getArticle(articleId));
    }

}
