package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.CommentCreateRequest;
import com.xrf.iotbackend.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result createComments(
            @Valid @RequestBody CommentCreateRequest commentCreateRequest ,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        return Result.success("评论发表成功", commentService.createComment(commentCreateRequest , authorId));
    }

}
