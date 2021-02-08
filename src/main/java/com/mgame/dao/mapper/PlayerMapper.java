package com.mgame.dao.mapper;

import com.mgame.dao.entity.PlayerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {
    int insert(PlayerEntity player);
    int update(PlayerEntity player);
    PlayerEntity findById(long playerId);
    PlayerEntity findByName(String playerName);
    List<PlayerEntity> findPlayersInfoByUserId(String userId);
}
