package com.bilicute.spacetime.service;

import java.util.List;

public interface AttentionService {

    void concern(Integer loggedInUserId, Integer passiveId);

    void disConcern(Integer loggedInUserId, Integer passiveId);

    List<Integer> getConcern(Integer loggedInUserId);

}
