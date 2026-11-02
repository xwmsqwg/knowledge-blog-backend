package com.xrf.iotbackend.controller;

import com.xrf.iotbackend.common.api.Result;
import com.xrf.iotbackend.model.dto.CommentCreateRequest;
import com.xrf.iotbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "评论", description = "评论发表与删除")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Operation(summary = "发表评论")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Result createComments(
            @Valid @RequestBody CommentCreateRequest commentCreateRequest ,
            @RequestAttribute("currentUserId") Integer authorId
    ){
        return Result.success("评论发表成功", commentService.createComment(commentCreateRequest , authorId));
    }

    @Operation(summary = "删除评论", description = "评论作者或文章作者可删除")
    @DeleteMapping("/{id}")
    public Result deleteComment(
            @PathVariable("id") Integer commentId ,
            @RequestAttribute("currentUserId") Integer currentAuthor
    ){
        commentService.deleteComment(commentId , currentAuthor);
        return Result.successMessage("评论删除成功");
    }

}
