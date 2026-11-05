package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.entity.Article;
import com.xrf.iotbackend.model.entity.Comment;
import com.xrf.iotbackend.repository.ArticleRepository;
import com.xrf.iotbackend.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    // 测试评论作者可以删除自己的评论
    @Test
    void deleteComment_CommentAuthorDeleteOwnComment(){
        Comment comment = new Comment();
        comment.setId(10);
        comment.setArticleId(20);
        comment.setAuthorId(5);

        Article article = new Article();
        article.setId(20);
        article.setAuthorId(9);

        when(commentRepository.selectById(10)).thenReturn(comment);
        when(articleRepository.selectById(20)).thenReturn(article);

        commentService.deleteComment(10,5);

        verify(commentRepository).deleteById(10);
    }

    // 测试文章作者可以删除自己文章下的评论
    @Test
    void deleteComment_ArticleAuthorDeleteComment(){
        Comment comment = new Comment();
        comment.setId(10);
        comment.setArticleId(20);
        comment.setAuthorId(5);

        Article article = new Article();
        article.setId(20);
        article.setAuthorId(9);

        when(commentRepository.selectById(10)).thenReturn(comment);
        when(articleRepository.selectById(20)).thenReturn(article);

        commentService.deleteComment(10,9);

        verify(commentRepository).deleteById(10);
    }

    // 测试既非文章作者也非评论作者不可删除评论
    @Test
    void deleteComment_notCommentAuthorNotArticleAuthor_throwException(){
        Comment comment = new Comment();
        comment.setId(10);
        comment.setArticleId(20);
        comment.setAuthorId(5);

        Article article = new Article();
        article.setId(20);
        article.setAuthorId(9);

        when(commentRepository.selectById(10)).thenReturn(comment);
        when(articleRepository.selectById(20)).thenReturn(article);

        BusinessException exception = assertThrows(BusinessException.class , ()->commentService.deleteComment(10 , 999));
        assertEquals(ErrorCode.FORBIDDEN , exception.getErrorCode());
        verify(commentRepository,never()).deleteById(anyInt());
    }

}
