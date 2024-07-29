package com.bilicute.spacetime.service.impl;

import com.bilicute.spacetime.mapper.AttentionMapper;
import com.bilicute.spacetime.service.AttentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttentionServiceImpl implements AttentionService {
    private static AttentionMapper attentionMapper;

    @Autowired
    private void setAttentionMapper(AttentionMapper attentionMapper) {
        AttentionServiceImpl.attentionMapper = attentionMapper;
    }


    @Override
    public void concern(Integer loggedInUserId, Integer passiveId) {
        attentionMapper.concern(loggedInUserId, passiveId);
    }

    @Override
    public void disConcern(Integer loggedInUserId, Integer passiveId) {
        attentionMapper.disConcern(loggedInUserId, passiveId);
    }

    @Override
    public List<Integer> getConcern(Integer loggedInUserId) {
        return attentionMapper.getConcern(loggedInUserId);
    }

}
