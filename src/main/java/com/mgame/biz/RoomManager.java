package com.mgame.biz;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RoomManager {
    private static Object obj = new Object();
    private ConcurrentHashMap list;
    private static RoomManager manager;

    private RoomManager() {
        list = new ConcurrentHashMap();
    }

    public static RoomManager getInstance() {
        synchronized (obj)
        {
            if (manager == null)
            {
                manager = new RoomManager();
            }
        }
        return manager;
    }


    public void update() {

    }
}
