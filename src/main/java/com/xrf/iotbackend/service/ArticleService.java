package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.dto.ArticleUpdateRequest;
import com.xrf.iotbackend.model.vo.ArticlePageVO;
import com.xrf.iotbackend.model.vo.ArticleVO;

public interface ArticleService {

    ArticleVO createArticle(ArticleCreateRequest articleCreateRequest , Integer creatorId);

    ArticlePageVO pageArticles(Integer page,
                               Integer size,
                               Integer categoryId,
                               Integer tagId,
                               String sort);

    ArticleVO getArticle(Integer articleId);

    ArticleVO updateArticle(Integer articleId ,
                       ArticleUpdateRequest articleUpdateRequest,
                       Integer authorId);

    void deleteArticle(Integer articleId , Integer authorId);
}
