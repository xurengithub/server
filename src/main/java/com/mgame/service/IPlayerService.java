package com.mgame.service;

import com.mgame.dao.entity.PlayerEntity;

import java.util.List;

public interface IPlayerService {
    int insert(PlayerEntity playerEntity);

    int update(PlayerEntity playerEntity);

    PlayerEntity findById(long playerId);

    PlayerEntity findByName(String playerName);

    List<PlayerEntity> findPlayersInfoByUserId(String userId);
}
