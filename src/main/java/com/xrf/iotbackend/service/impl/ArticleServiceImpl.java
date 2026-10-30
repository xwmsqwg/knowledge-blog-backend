package com.xrf.iotbackend.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.entity.*;
import com.xrf.iotbackend.model.vo.*;
import com.xrf.iotbackend.repository.*;
import com.xrf.iotbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Override
    @Transactional
    public ArticleVO createArticle(ArticleCreateRequest articleCreateRequest, Integer authorId) {
        User author = userRepository.selectById(authorId);
        if(author == null){
            throw new BusinessException(
                    ErrorCode.UNAUTHORIZED,
                    "当前用户不存在或登录已失效"
            );
        }
        Category category = categoryRepository.selectById(articleCreateRequest.getCategoryId());
        if(category == null){
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "分类不存在"
            );
        }
        String title = articleCreateRequest.getTitle().trim();
        String content = articleCreateRequest.getContent().trim();
        String coverUrl = normalizeCoverUrl(articleCreateRequest.getCoverUrl());
        Article article = new Article();
        article.setTitle(title);
        article.setContent(content);
        article.setCoverUrl(coverUrl);
        article.setCategoryId(category.getId());
        article.setAuthorId(authorId);
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        articleRepository.insert(article);

        List<TagSimpleVO> tags = saveTags(
                article.getId(),
                articleCreateRequest.getTags()
        );

        return new ArticleVO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCoverUrl(),
                new AuthorVO(
                        author.getId(),
                        author.getUsername()
                ),
                new CategorySimpleVO(
                        category.getId(),
                        category.getName()
                ),
                tags,
                List.of(),
                article.getCreateTime(),
                article.getUpdateTime()
        );
    }

    @Override
    public ArticlePageVO pageArticles(Integer page, Integer size, Integer categoryId, Integer tagId, String sort) {
        boolean isAsc = "asc".equalsIgnoreCase(sort);
        long nextData = (long)(page - 1) * size;
        long total = articleRepository.countArticlePage(categoryId, tagId);
        List<ArticleListVO> records = articleRepository.selectArticlePage(categoryId , tagId , nextData , size , isAsc);
        for(ArticleListVO record : records){
            record.setTags(articleTagRepository.selectTagsByArticleId(record.getId()));
        }
        return new ArticlePageVO(records , total , page, size);
    }

    private List<TagSimpleVO> saveTags(Integer articleId, List<String> rawTagNames){
        List<TagSimpleVO> tags = new ArrayList<>();
        if(rawTagNames == null || rawTagNames.isEmpty()){
            return tags;
        }
        Set<String> uniqueTagNames = new LinkedHashSet<>();
        for (String rawTagName : rawTagNames) {
            uniqueTagNames.add(rawTagName.trim().toLowerCase(Locale.ROOT));
        }

        for(String tagName : uniqueTagNames){
            Tag tag = tagRepository.selectOne(Wrappers.<Tag>lambdaQuery().eq(Tag::getName , tagName));
            if(tag == null){
                tag = new Tag();
                tag.setName(tagName);
                tag.setCreateTime(LocalDateTime.now());
                tagRepository.insert(tag);
            }
            ArticleTag articleTag = new ArticleTag();
            articleTag.setArticleId(articleId);
            articleTag.setTagId(tag.getId());
            articleTagRepository.insert(articleTag);

            tags.add(new TagSimpleVO(tag.getId() , tag.getName()));
        }
        return tags;
    }

    private String normalizeCoverUrl(String resCoverUrl){
        if(resCoverUrl == null){
            return null;
        }
        String coverUrl = resCoverUrl.trim();
        try {
            URI uri = URI.create(coverUrl);
            if (!("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()))
                    || uri.getHost() == null) {
                throw new BusinessException(
                        ErrorCode.VALIDATION_ERROR,
                        "封面地址必须是有效的 http 或 https URL"
                );
            }
        } catch (Exception exception) {
            throw new BusinessException(
                    ErrorCode.VALIDATION_ERROR,
                    "封面地址格式错误"
            );
        }
        return coverUrl;
    }

}
