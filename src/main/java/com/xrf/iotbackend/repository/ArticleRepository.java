package com.xrf.iotbackend.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xrf.iotbackend.model.entity.Article;
import com.xrf.iotbackend.model.vo.ArticleListVO;
import com.xrf.iotbackend.model.vo.ArticleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleRepository extends BaseMapper<Article> {

    long countArticlePage(@Param("categoryId") Integer categoryId,
                          @Param("tagId") Integer tagId);

    List<ArticleListVO> selectArticlePage(@Param("categoryId") Integer categoryId,
                                          @Param("tagId") Integer tagId,
                                          @Param("nextData") long nextData,
                                          @Param("size") Integer size,
                                          @Param("isAsc") boolean isAsc);
    ArticleVO selectByArticleId(@Param("articleId") Integer articleId);

}
