package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.vo.ArticleVO;

public interface ArticleService {

    ArticleVO createArticle(ArticleCreateRequest articleCreateRequest , Integer creatorId);

}
