package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.ArticleCreateRequest;
import com.xrf.iotbackend.model.dto.ArticleUpdateRequest;
import com.xrf.iotbackend.model.entity.Article;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.ArticleListVO;
import com.xrf.iotbackend.model.vo.ArticlePageVO;
import com.xrf.iotbackend.model.vo.ArticleVO;
import com.xrf.iotbackend.model.vo.TagSimpleVO;
import com.xrf.iotbackend.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private TagRepository tagRepository;
    @Mock
    private ArticleTagRepository articleTagRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    // 测试分类不存在时无法创建文章
    @Test
    void createArticle_categoryNotFound_throwsBusinessException(){
        User user = new User();
        user.setId(9);

        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest();
        articleCreateRequest.setCategoryId(10);

        when(userRepository.selectById(9)).thenReturn(user);
        when(categoryRepository.selectById(10)).thenReturn(null);
        BusinessException exception = assertThrows(BusinessException.class,()->articleService.createArticle(articleCreateRequest , 9));
        assertEquals(ErrorCode.NOT_FOUND , exception.getErrorCode());
        verify(articleRepository,never()).insert(any(Article.class));
    }

    // 测试分页查询文章并返回每篇文章的标签
    @Test
    void pageArticles_returnsRecordsWithTag(){
        Integer page = 2;
        Integer size = 2;
        Integer categoryId = 3;
        Integer tagId = 4;

        ArticleListVO firstArticle = new ArticleListVO();
        firstArticle.setId(11);

        ArticleListVO secondArticle = new ArticleListVO();
        secondArticle.setId(12);

        List<ArticleListVO> records = List.of(firstArticle , secondArticle);
        List<TagSimpleVO> firstTags = List.of(
                new TagSimpleVO(1, "java")
        );
        List<TagSimpleVO> secondTags = List.of(
                new TagSimpleVO(2, "spring")
        );

        when(articleRepository.countArticlePage(categoryId , tagId)).thenReturn(5L);

        when(articleRepository.selectArticlePage(
                categoryId, tagId, 2L, size, true
        )).thenReturn(records);

        when(articleTagRepository.selectTagsByArticleId(11))
                .thenReturn(firstTags);
        when(articleTagRepository.selectTagsByArticleId(12))
                .thenReturn(secondTags);

        ArticlePageVO result = articleService.pageArticles(
                page, size, categoryId, tagId, "asc"
        );

        assertEquals(5L, result.getTotal());
        assertEquals(page, result.getPage());
        assertEquals(size, result.getSize());
        assertEquals(records, result.getRecords());
        assertEquals(firstTags, result.getRecords().get(0).getTags());
        assertEquals(secondTags, result.getRecords().get(1).getTags());

        verify(articleRepository).countArticlePage(categoryId, tagId);
        verify(articleRepository).selectArticlePage(categoryId, tagId, 2L, size, true);
        verify(articleTagRepository).selectTagsByArticleId(11);
        verify(articleTagRepository).selectTagsByArticleId(12);
    }

    // 测试获取文章详情返回标签列表
    @Test
    void getArticle_returnTags(){
        ArticleVO articleVO = new ArticleVO();
        articleVO.setId(20);

        List<TagSimpleVO> tags = List.of(
                new TagSimpleVO(4 , "java"),
                new TagSimpleVO(9 , "PYTHON")
        );

        when(articleRepository.selectByArticleId(20)).thenReturn(articleVO);
        when(articleTagRepository.selectTagsByArticleId(20)).thenReturn(tags);

        ArticleVO result = articleService.getArticle(20);
        assertEquals(tags , result.getTags());
    }

    // 测试更新时非作者
    @Test
    void updateArticle_otherAuthor_throwsForbidden(){
        Article article = new Article();
        article.setId(7);
        article.setAuthorId(10);

        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest();

        when(articleRepository.selectById(7)).thenReturn(article);

        BusinessException exception = assertThrows(BusinessException.class , ()->articleService.updateArticle(7 , articleUpdateRequest , 9));
        assertEquals(ErrorCode.FORBIDDEN ,  exception.getErrorCode());
        verify(articleRepository , never()).updateArticle(anyInt() , anyString() , anyString() , anyInt());
    }

    //测试更新时文章不存在
    @Test
    void updateArticle_notFound_throwsBusinessException(){
        ArticleUpdateRequest articleUpdateRequest = new ArticleUpdateRequest();

        when(articleRepository.selectById(7)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class , ()->articleService.updateArticle(7 , articleUpdateRequest , 8));

        assertEquals(ErrorCode.NOT_FOUND , exception.getErrorCode());

        verify(articleRepository , never()).updateArticle(anyInt() , anyString(), anyString(), anyInt());
    }

    // 测试删除时文章不存在
    @Test
    void deleteArticle_notFound_throwsBusinessException() {
        when(articleRepository.selectById(7)).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> articleService.deleteArticle(7, 9));

        assertEquals(ErrorCode.NOT_FOUND, exception.getErrorCode());
        verify(articleRepository, never()).deleteById(anyInt());
    }

    // 测试非作者不能删除文章
    @Test
    void deleteArticle_otherAuthor_throwsForbidden() {
        Article article = new Article();
        article.setId(7);
        article.setAuthorId(10);

        when(articleRepository.selectById(7)).thenReturn(article);

        BusinessException exception = assertThrows(BusinessException.class, () -> articleService.deleteArticle(7, 9));

        assertEquals(ErrorCode.FORBIDDEN, exception.getErrorCode());
        verify(articleRepository, never()).deleteById(anyInt());
    }

    // 测试作者成功删除文章
    @Test
    void deleteArticle_success_deletesArticle() {
        Article article = new Article();
        article.setId(7);
        article.setAuthorId(9);

        when(articleRepository.selectById(7)).thenReturn(article);

        articleService.deleteArticle(7, 9);

        verify(articleRepository).deleteById(7);
    }

}
