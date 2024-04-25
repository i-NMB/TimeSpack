package com.bilicute.spacetime.controller;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Result;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
