package com.mgame.service.impl;

import com.mgame.dao.entity.PlayerEntity;
import com.mgame.dao.mapper.PlayerMapper;
import com.mgame.service.IPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService implements IPlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    @Override
    public int insert(PlayerEntity playerEntity) {
        return playerMapper.insert(playerEntity);
    }

    @Override
    public int update(PlayerEntity playerEntity) {
        return playerMapper.update(playerEntity);
    }

    @Override
    public PlayerEntity findById(long playerId) {
        return playerMapper.findById(playerId);
    }

    @Override
    public PlayerEntity findByName(String playerName) {
        return playerMapper.findByName(playerName);
    }

    @Override
    public List<PlayerEntity> findPlayersInfoByUserId(String userId) {
        return playerMapper.findPlayersInfoByUserId(userId);
    }
}
