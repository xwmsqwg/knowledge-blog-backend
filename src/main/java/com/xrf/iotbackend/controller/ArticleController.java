package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.vo.ArticleVO;
import com.xrf.iotbackend.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result createArticle(
            @Valid @RequestBody ArticleCreateRequest articleCreateRequest,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        ArticleVO articleVO = articleService.createArticle(articleCreateRequest , authorId);
        return Result.success("文章创建成功" , articleVO);
    }

}
