package com.mgame.service;

import com.mgame.dao.entity.ItemEntity;

import java.util.List;

public interface IItemService {
    int insert(ItemEntity itemEntity);

    int update(ItemEntity itemEntity);

    int deleteItems(Long playerId, int itemId);

    List<ItemEntity> findItemsByPlayerId(Long playerId);

    List<ItemEntity> findItemsByItemId(int itemId);
}
