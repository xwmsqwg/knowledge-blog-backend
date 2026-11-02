package com.xrf.iotbackend.service.impl;

import com.xrf.iotbackend.common.api.ErrorCode;
import com.xrf.iotbackend.exception.BusinessException;
import com.xrf.iotbackend.model.dto.CommentCreateRequest;
import com.xrf.iotbackend.model.entity.Article;
import com.xrf.iotbackend.model.entity.Comment;
import com.xrf.iotbackend.model.entity.User;
import com.xrf.iotbackend.model.vo.AuthorVO;
import com.xrf.iotbackend.model.vo.CommentVO;
import com.xrf.iotbackend.repository.ArticleRepository;
import com.xrf.iotbackend.repository.CommentRepository;
import com.xrf.iotbackend.repository.UserRepository;
import com.xrf.iotbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public CommentVO createComment(
            CommentCreateRequest commentCreateRequest,
            Integer authorId
    ) {
        Article article = articleRepository.selectById(commentCreateRequest.getArticleId());
        if(article == null){
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "文章不存在"
            );
        }
        User author = userRepository.selectById(authorId);
        Comment comment = new Comment();
        comment.setArticleId(article.getId());
        comment.setAuthorId(authorId);
        comment.setContent(commentCreateRequest.getContent().trim());
        comment.setCreateTime(LocalDateTime.now());
        commentRepository.insert(comment);
        return new CommentVO(
                comment.getId(),
                comment.getArticleId(),
                comment.getContent(),
                new AuthorVO(
                        author.getId(),
                        author.getUsername()
                ),
                comment.getCreateTime()
        );
    }

    @Override
    public void deleteComment(Integer commentId, Integer currentAuthor) {
        Comment comment = commentRepository.selectById(commentId);
        if(comment == null){
            throw new BusinessException(
                    ErrorCode.NOT_FOUND,
                    "该评论不存在"
            );
        }
        Article article = articleRepository.selectById(comment.getArticleId());
        if(!comment.getAuthorId().equals(currentAuthor) && !article.getAuthorId().equals(currentAuthor)){
            throw new BusinessException(
                    ErrorCode.FORBIDDEN,
                    "越权操作"
            );
        }
        commentRepository.deleteById(commentId);
    }
}
