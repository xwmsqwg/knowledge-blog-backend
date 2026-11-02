package com.xrf.iotbackend.service;

import com.xrf.iotbackend.model.dto.CommentCreateRequest;
import com.xrf.iotbackend.model.vo.CommentVO;

public interface CommentService {

    CommentVO createComment(CommentCreateRequest commentCreateRequest , Integer authorId);

    void deleteComment(Integer commentId , Integer currentAuthor);

}
