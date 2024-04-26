package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Comment;

public interface CommentService {
    void add(String content, Integer articleId);
    void delete(Comment comment);
    void save(Comment comment);
}
