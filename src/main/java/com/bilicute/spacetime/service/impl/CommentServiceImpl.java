package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.CommentMapper;
import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.pojo.PageBean;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.CommentService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        comment.setCreateUser(QuickMethods.getLoggedInUserId());
        comment.setCreateTime(LocalDateTime.now());
        comment.setAuditingState(false);
        comment.setLikes(0);
        commentMapper.add(comment);
    }

    @Override
    public void delete(Integer commentId) {
        commentMapper.delete(commentId);
    }


    @Override
    public Integer getCommentNumber(Integer articleId) {
        return commentMapper.getCommentNumber(articleId);
    }

    @Override
    public PageBean<Comment> list(Integer pageNum, Integer pageSize, Integer articleId, Boolean auditingState) {
        //创建PageBean对象，用来封装查询好的数据
        PageBean<Comment> articlePageBean = new PageBean<>();
        //开启分页查询(借助pageHelper)
        PageHelper.startPage(pageNum,pageSize);
        //获取userID
//        Map<String,Object> userMap = ThreadLocalUtil.get();
//        Integer userId = (Integer) userMap.get("id");
        //调用Mapper完成查询
        List<Comment> as = commentMapper.list(articleId,auditingState);
        //Page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数镉
        Page<Comment> p = (Page<Comment>) as;
        //把数据填充到PageBean对象中
        articlePageBean.setTotal(p.getTotal());
        articlePageBean.setItems(p.getResult());
        return articlePageBean;
    }

    @Override
    public Comment findById(Integer commentId) {
        return commentMapper.findById(commentId);
    }

    @Override
    public void check(Integer id) {
        commentMapper.check(id,true);
    }

}