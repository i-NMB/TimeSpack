package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.CommentMapper;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService {
    private static CommentMapper commentMapper;
    @Autowired
    private void setCommentMapper(CommentMapper commentMapper) {
        CommentServiceImpl.commentMapper = commentMapper;
    }
    @Override
    public void add(Comment comment) {
        comment.setCreateTime(LocalDateTime.now());
        commentMapper.add(comment);
    }
}