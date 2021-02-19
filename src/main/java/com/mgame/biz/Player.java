package com.mgame.biz;


import com.google.protobuf.Message;
import com.mgame.dao.entity.ItemEntity;
import com.mgame.dao.entity.PlayerEntity;
import com.mgame.net.ProtoManager;
import io.netty.channel.Channel;

import java.util.List;

public class Player {

    public int playerId;
    public String scene;
    public int areaId;
    public Area area;
    public Channel channel;

    public PlayerEntity playerEntity;
    public List<ItemEntity> itemEntityList;
    public Player(Channel channel){
        this.channel = channel;
    }

    public void send(Message msg){
        ProtoManager.send(msg, channel);
    }

    public void update(){
    }

    public void close(){

        area.removePlayer(playerId);

        PlayerManager.removePlayer(playerId);
        playerId = 0;
        scene = null;
        areaId = 0;
        area = null;
        channel = null;

        playerEntity = null;
        itemEntityList = null;

    }
}