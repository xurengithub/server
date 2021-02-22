package com.mgame.biz;


import com.google.protobuf.Message;
import com.mgame.dao.entity.ItemEntity;
import com.mgame.dao.entity.PlayerEntity;
import com.mgame.net.ProtoManager;
import io.netty.channel.Channel;

import java.util.List;

public class Player {

    public int playerId;
    public Channel channel;

    public PlayerEntity playerEntity;
    public Player(Channel channel){
        this.channel = channel;
    }

    public void send(Message msg){
        ProtoManager.send(msg, channel);
    }

    public boolean isValid() {
        if(playerId == 0) {
            return false;
        }
        return true;
    }

    public void update(){
    }

    public void close(){
        PlayerManager.removePlayer(playerId);
        playerId = 0;
        channel = null;
        playerEntity = null;
    }
}