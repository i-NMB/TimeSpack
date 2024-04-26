package com.bilicute.spacetime.service;

import com.bilicute.spacetime.pojo.Comment;

public interface CommentService {
    void add(Comment comment);
    void delete(Comment comment);
    void save(Comment comment);
}
