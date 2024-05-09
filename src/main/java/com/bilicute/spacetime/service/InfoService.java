package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;

public interface InfoService {
    Article getMaxViewArticle(Integer userId);

    Article getMaxLikeArticle(Integer userId);

    Comment getMaxLikeComment(Integer userId);

    Integer allArticle(Integer userId);

    Integer allComment(Integer userId);

    Integer allViewByArticle(Integer userId);

    Integer allLikeByComment(Integer userId);

    Integer allLikeByArtiCle(Integer userId);
}
