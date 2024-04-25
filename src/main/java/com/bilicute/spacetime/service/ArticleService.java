package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Article;

import java.util.List;


public interface ArticleService {
    void add(Article article);
    List<Article> getArticlesByPage(int start, int pageSize);
}