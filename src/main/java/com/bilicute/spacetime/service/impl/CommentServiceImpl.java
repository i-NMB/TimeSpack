package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.CommentMapper;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.quickMethods.QuickMethods;
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
    public void add(String content, Integer articleId) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setState("已发布");
        comment.setArticleId(articleId);
        comment.setCreatUser(QuickMethods.getLoggedInUserId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setAuditingState(false);
        comment.setLikes(0);
        commentMapper.add(comment);
    }

    @Override
    public void delete(Integer commentId) {
        commentMapper.delete(commentId);
    }

}