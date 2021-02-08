package com.mgame.service;

import com.mgame.dao.entity.PlayerEntity;
import com.mgame.dao.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {

    @Autowired
    private PlayerMapper playerMapper;

    public int insert(PlayerEntity playerEntity) {
        return playerMapper.insert(playerEntity);
    }

    public int update(PlayerEntity playerEntity) {
        return playerMapper.update(playerEntity);
    }

    public PlayerEntity findById(long playerId) {
        return playerMapper.findById(playerId);
    }

    public PlayerEntity findByName(String playerName) {
        return playerMapper.findByName(playerName);
    }

    public List<PlayerEntity> findPlayersInfoByUserId(String userId) {
        return playerMapper.findPlayersInfoByUserId(userId);
    }
}
