package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/add")
    public Result addComment(String content,Integer articleId) {
        commentService.add(content,articleId);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result deleteComment(Integer commentId) {
        commentService.delete(commentId);
        return Result.success();
    }

}
