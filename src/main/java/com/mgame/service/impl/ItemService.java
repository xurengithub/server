package com.mgame.service.impl;

import com.mgame.dao.entity.ItemEntity;
import com.mgame.dao.mapper.ItemMapper;
import com.mgame.service.IItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService implements IItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Override
    public int insert(ItemEntity itemEntity) {
        return itemMapper.insert(itemEntity);
    }

    @Override
    public int update(ItemEntity itemEntity) {
        return itemMapper.update(itemEntity);
    }

    @Override
    public int deleteItems(Long playerId, int itemId) {
        return itemMapper.deleteItems(playerId, itemId);
    }

    @Override
    public List<ItemEntity> findItemsByPlayerId(Long playerId) {
        return itemMapper.findItemsByPlayerId(playerId);
    }

    @Override
    public List<ItemEntity> findItemsByItemId(int itemId) {
        return itemMapper.findItemsByItemId(itemId);
    }
}
