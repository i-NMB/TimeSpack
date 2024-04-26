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
    public Result addComment(@RequestBody Comment comment) {
        commentService.add(comment);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result deleteComment(@RequestBody Comment comment) {
        commentService.delete(comment);
        return Result.success();
    }

    @PostMapping("/save") // 新增处理保存评论的请求映射
    public Result saveComment(@RequestBody Comment comment) {
        commentService.save(comment);
        return Result.success();
    }
}
