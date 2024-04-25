package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import com.bilicute.spacetime.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: ArticleController
 * @作者: i囡漫笔
 * @描述: 文章controller响应
 * @创建时间: 2024-04-24 19:20
 */

@RestController
@RequestMapping("/article")
@Validated
@Slf4j
public class ArticleController {

    private static ArticleService articleService;
    @Autowired
    private void setArticleService(ArticleService articleService){
        ArticleController.articleService = articleService;
    }

    @PostMapping
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        log.info("创建文章：用户"+ QuickMethods.getLoggedInUserName()+"\t文章标题："+article.getTitle());
        return Result.success();
    }

    private static CommentService commentService;
    @Autowired
    private void setCommentService(CommentService commentService) {
        ArticleController.commentService = commentService;
    }
    @PostMapping("/{articleId}/comments")
    public Result addComment(@PathVariable Integer articleId, @RequestBody @Validated Comment comment) {
        comment.setArticleId(articleId);
        commentService.add(comment);
        log.info("添加评论：用户" + QuickMethods.getLoggedInUserName() + "\t评论内容：" + comment.getContent());
        return Result.success();
    }
    @GetMapping("/page")
    public Result getArticlesByPage(@RequestParam("start") int start, @RequestParam("pageSize") int pageSize) {
        List<Article> articles = articleService.getArticlesByPage(start, pageSize);
        return Result.success(articles);
    }
}
