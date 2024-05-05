package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.PageBean;

import java.util.Map;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state,Boolean auditingState);

    PageBean<Article> listWeighting(Integer pageNum, Integer pageSize, Integer categoryId,String state);

    Article findById(Integer id);

    void view(Integer id);

    void like(Integer id);

    Map<String,Integer> querySelfInfo(Integer loggedInUserId);

    void check(Integer id);

    void queryAllInfo();

    void delete(Integer id);

    void update(Article article);

//    void incrementViewCount(Integer id);




}
