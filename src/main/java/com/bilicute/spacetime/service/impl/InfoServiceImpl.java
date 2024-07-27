package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.InfoMapper;
import com.bilicute.spacetime.pojo.Article;
import com.bilicute.spacetime.pojo.Comment;
import com.bilicute.spacetime.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @所属包名: com.bilicute.spacetime.service.impl
 * @类名: InfoServiceImpl
 * @作者: i囡漫笔
 * @描述: 信息处理实现方法
 * @创建时间: 2024-05-09 01:07
 */

@Service
public class InfoServiceImpl implements InfoService {

    private static InfoMapper infoMapper;
    @Autowired
    private void setInfoMapper(InfoMapper infoMapper){
        InfoServiceImpl.infoMapper = infoMapper;
    }

    @Override
    public Article getMaxViewArticle(Integer userId) {
        return infoMapper.getMaxViewArticle(userId);
    }

    @Override
    public Article getMaxLikeArticle(Integer userId) {
        return infoMapper.getLikeViewArticle(userId);
    }

    @Override
    public Comment getMaxLikeComment(Integer userId) {
        return infoMapper.getLikeViewComment(userId);
    }

    @Override
    public Integer allArticle(Integer userId) {
        return infoMapper.allArticle(userId);
    }

    @Override
    public Integer allComment(Integer userId) {
        return infoMapper.allComment(userId);
    }

    @Override
    public Integer allViewByArticle(Integer userId) {
        return infoMapper.allViewByArticle(userId);
    }

    @Override
    public Integer allLikeByComment(Integer userId) {
        return infoMapper.allLikeByComment(userId);
    }

    @Override
    public Integer allLikeByArtiCle(Integer userId) {
        return infoMapper.allLikeByArtiCle(userId);
    }
}
