package com.mgame.dao.mapper;

import com.mgame.dao.entity.ItemEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {
    int insert(ItemEntity item);
    int update(ItemEntity item);
    int deleteItems(Long playerId, int itemId);
    List<ItemEntity> findItemsByPlayerId(Long playerId);
    List<ItemEntity> findItemsByItemId(int itemId);
}
