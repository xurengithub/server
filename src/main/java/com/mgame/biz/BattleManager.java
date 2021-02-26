package com.mgame.biz;

import java.util.concurrent.ConcurrentHashMap;

public class BattleManager {
    private static Object obj = new Object();
    private ConcurrentHashMap<Integer, Room> list;
    private static BattleManager manager;

    private BattleManager() {
        list = new ConcurrentHashMap<>();
    }

    public static BattleManager getInstance() {
        synchronized (obj)
        {
            if (manager == null)
            {
                manager = new BattleManager();
            }
        }
        return manager;
    }

    public void update() {

    }

}
