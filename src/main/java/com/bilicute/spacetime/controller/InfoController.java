package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import com.bilicute.spacetime.service.InfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * @所属包名: com.bilicute.spacetime.controller
 * @类名: InfoController
 * @作者: i囡漫笔
 * @描述: 查询信息
 * @创建时间: 2024-05-09 01:03
 */

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/info")
@Validated
@Slf4j
public class InfoController {
    private static InfoService infoService;
    @Autowired
    private void setInfoService(InfoService infoService){
        InfoController.infoService = infoService;
    }

    @GetMapping("/maxViewArt")
    public Result<Article> maxViewArticle(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Article article = infoService.getMaxViewArticle(userId);
        if (article == null) {
            Article articleErr = new Article();
            articleErr.setTitle("没有文章");
            articleErr.setContent("emm……你好像还没有发布过文章");
            return Result.success(articleErr);
        }
        return Result.success(article);
    }
    @GetMapping("/maxLikeArt")
    public Result<Article> maxLikeArticle(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Article article = infoService.getMaxLikeArticle(userId);
        if (article == null) {
            Article articleErr = new Article();
            articleErr.setTitle("没有文章");
            articleErr.setContent("emm……你好像还没有发布过文章");
            return Result.success(articleErr);
        }
        return Result.success(article);
    }
    @GetMapping("/maxLikeCom")
    public Result<Comment> maxLikeComment(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Comment comment = infoService.getMaxLikeComment(userId);
        if (comment == null) {
            Comment commentErr = new Comment();
            commentErr.setContent("你还没有发布评论噢，快去评论叭！");
            return Result.success(commentErr);
        }
        return Result.success(comment);
    }

    @GetMapping("/allArt")
    public Result<Integer> allArticle(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Integer articleNum = infoService.allArticle(userId);
        return Result.success(articleNum);
    }

    @GetMapping("/allCom")
    public Result<Integer> allComment(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Integer commentNum = infoService.allComment(userId);
        return Result.success(commentNum);
    }

    @GetMapping("/allViewByArt")
    public Result<Integer> allViewByArticle(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Integer allViewByArticleNum = infoService.allViewByArticle(userId);
        return Result.success(Objects.requireNonNullElse(allViewByArticleNum, 0));
    }

    @GetMapping("/allLikeByCom")
    public Result<Integer> allLikeByComment(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Integer allViewByCommentNum = infoService.allLikeByComment(userId);
        return Result.success(Objects.requireNonNullElse(allViewByCommentNum, 0));
    }

    @GetMapping("/allLikeByArt")
    public Result<Integer> allLikeByArtiCle(){
        Integer userId = QuickMethods.getLoggedInUserId();
        Integer allLikeByArtiCleNum = infoService.allLikeByArtiCle(userId);
        return Result.success(Objects.requireNonNullElse(allLikeByArtiCleNum, 0));
    }
}
