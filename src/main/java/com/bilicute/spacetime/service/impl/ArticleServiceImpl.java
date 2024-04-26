package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.ArticleMapper;
import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.PageBean;
import com.bilicute.spacetime.quickMethods.QuickMethods;
import com.bilicute.spacetime.service.ArticleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @所属包名: com.bilicute.spacetime.service.impl
 * @类名: ArticleServiceImpl
 * @作者: i囡漫笔
 * @描述: 文章实现类
 * @创建时间: 2024-04-24 19:26
 */

@Service
public class ArticleServiceImpl implements ArticleService {
    private static ArticleMapper articleMapper;
    @Autowired
    private void setArticleMapper(ArticleMapper articleMapper){
        ArticleServiceImpl.articleMapper = articleMapper;
    }
    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        //获取当前登陆用户的id
        Integer userID = QuickMethods.getLoggedInUserId();
        //创建
        article.setCreateUser(userID);
        article.setAuditingState(false);
        article.setLikes(0);
        article.setView(0);
        articleMapper.add(article);


    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state,Boolean auditingState) {
        //创建PageBean对象，用来封装查询好的数据
        PageBean<Article> articlePageBean = new PageBean<>();
        //开启分页查询(借助pageHelper)
        PageHelper.startPage(pageNum,pageSize);
        //获取userID
//        Map<String,Object> userMap = ThreadLocalUtil.get();
//        Integer userId = (Integer) userMap.get("id");
        //调用Mapper完成查询
        List<Article> as = articleMapper.list(categoryId,state,auditingState);
        //Page中提供了方法，可以获取PageHelper分页查询后得到的总记录条数和当前页数镉
        Page<Article> p = (Page<Article>) as;
        //把数据填充到PageBean对象中
        articlePageBean.setTotal(p.getTotal());
        articlePageBean.setItems(p.getResult());
        return articlePageBean;
    }

    @Override
    public Article findById(Integer id) {
        return articleMapper.findByUserid(id);
    }

    @Override
    public void view(Integer id) {
        articleMapper.view(id);
    }

    @Override
    public void like(Integer id) {
        articleMapper.like(id);
    }

    @Override
    public Map<String,Integer> querySelfInfo(Integer loggedInUserId) {
        Map<String,Integer> map =  new HashMap<>();
        //TODO 查询自身所有文章的点赞和阅览
        Integer total_likes = articleMapper.queryLikeSelfInfo(loggedInUserId);
        Integer total_view = articleMapper.queryViewSelfInfo(loggedInUserId);
        map.put("likes",total_likes);
        map.put("view",total_view);
        return map;
    }

    @Override
    public void check(Integer id) {
        articleMapper.check(id,true);
    }

    @Override
    public void queryAllInfo() {
        //TODO 查询全部用户平均的所有文章的点赞和阅览
    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }


}

