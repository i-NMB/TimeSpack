package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.ArticleMapper;
import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Category;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @所属包名: com.bilicute.spacetime.service.impl
 * @类名: ArticleServiceImpl
 * @作者: i囡漫笔
 * @描述: 文章实现类
 * @创建时间: 2024-04-24 19:26
 */

@Service
public class ArticleServiceImpl implements ArticleService {
    private static ArticleMapper articleMapper;
    @Autowired
    private void setArticleMapper(ArticleMapper articleMapper){
        ArticleServiceImpl.articleMapper = articleMapper;
    }
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        //获取当前登陆用户的id
        Integer userID = QuickMethods.getLoggedInUserId();
        //创建
        article.setCreateUser(userID);
        article.setAuditingState(false);
        article.setLikes(0);
        article.setView(0);
        articleMapper.add(article);
    }

    @Override
    public void createArticle(Article article, Category category) {

    }

    @Override
    public Article getArticleWithCategory(Integer articleId) {
        return null;
    }

    @Override
    public Article retrieveArticle(Integer articleId) {
        return null;
    }

    @Override
    public Category retrieveCategory(Integer categoryId) {
        return null;
    }



    @Override
    public List<Article> getArticlesByPage(int start, int pageSize) {
        return articleMapper.getArticlesByPage(start, pageSize);
    }


}
