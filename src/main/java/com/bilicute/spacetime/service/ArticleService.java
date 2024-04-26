package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Category;

import java.util.List;




public interface ArticleService {
    void add(Article article);
    public default void createArticle(Article article, Category category) {
        // 在文章对象中设置分类
        article.setCategory(category);
        // 其他创建文章的逻辑...
    }

    // 获取文章时，可以一并获取其分类信息
    public default Article getArticleWithCategory(Integer articleId) {
        Article article = retrieveArticle(articleId); // 假设这个方法从数据库中检索文章
        Category category = retrieveCategory(article.getCategoryId()); // 假设这个方法从数据库中检索分类
        article.setCategory(category); // 将分类信息设置到文章对象中
        return article;
    }

    Article retrieveArticle(Integer articleId);

    Category retrieveCategory(Integer categoryId);

    List<Article> getArticlesByPage(int start, int pageSize);
}