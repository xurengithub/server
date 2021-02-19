package com.mgame.biz;

import com.google.protobuf.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Area {

    public Map<Long, Boolean> playerIds = new ConcurrentHashMap<Long, Boolean>();
    private float x;
    private float y;
    private float weight;
    private float height;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean addPlayer(long playerId){
        Player player = PlayerManager.getPlayer(playerId);
        if(player == null){
            return false;
        }

        if(playerIds.containsKey(playerId)){
            return false;
        }
        player.area = this;
        playerIds.put(playerId, true);

//        MsgSyncPlayer syncPlayer = new MsgSyncPlayer();
//        syncPlayer.setPlayerId(player.playerEntity.getPlayerId());
//        syncPlayer.setX(player.playerEntity.getX());
//        syncPlayer.setY(player.playerEntity.getY());
//        syncPlayer.setZ(player.playerEntity.getZ());
//        syncPlayer.setEx(player.playerEntity.getEx());
//        syncPlayer.setEy(player.playerEntity.getEy());
//        syncPlayer.setEz(player.playerEntity.getEz());
//
//
//        broadcast(syncPlayer);
        return true;
    }

    public boolean removePlayer(long playerId){
        Player player = PlayerManager.getPlayer(playerId);
        if(player == null){
            return false;
        }
        if(!playerIds.containsKey(playerId)){
            return false;
        }
        playerIds.remove(playerId);
        player.area = null;
        return true;
    }

    public void broadcast(Message msg){
        for (long playerId : playerIds.keySet()){
            Player player = PlayerManager.getPlayer(playerId);
            player.send(msg);
        }
    }

    public boolean isOnline(long playerId){
        return playerIds.containsKey(playerId);
    }
}
