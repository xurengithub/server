package com.mgame.service;

import com.mgame.dao.entity.ItemEntity;
import com.mgame.dao.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private ItemMapper itemMapper;

    public int insert(ItemEntity itemEntity) {
        return itemMapper.insert(itemEntity);
    }

    public int update(ItemEntity itemEntity) {
        return itemMapper.update(itemEntity);
    }

    public int deleteItems(Long playerId, int itemId) {
        return itemMapper.deleteItems(playerId, itemId);
    }

    public List<ItemEntity> findItemsByPlayerId(Long playerId) {
        return itemMapper.findItemsByPlayerId(playerId);
    }

    public List<ItemEntity> findItemsByItemId(int itemId) {
        return itemMapper.findItemsByItemId(itemId);
    }
}
