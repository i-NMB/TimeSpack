package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.PageBean;

public interface CommentService {
    void add(String content, Integer articleId);
    void delete(Integer commentId);

    Integer getCommentNumber(Integer articleId);

    PageBean<Comment> list(Integer pageNum, Integer pageSize, Integer articleId, Boolean auditingState);

    Comment findById(Integer commentId);

    void check(Integer id);
}
