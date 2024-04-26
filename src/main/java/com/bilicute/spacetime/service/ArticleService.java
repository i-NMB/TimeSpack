package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state,Boolean auditingState);

    Article findById(Integer id);

    void view(Integer id);

    void like(Integer id);

    void querySelfInfo(Integer loggedInUserId);

    void check(Integer id);

    void queryAllInfo();

    void follow(Integer followedUserId);
}
