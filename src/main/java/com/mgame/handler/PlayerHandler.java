package com.mgame.handler;

import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//import game.playerino.player.Player;

@Component
public class PlayerHandler implements AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(PlayerHandler.class);

    @Override
    public void handle(Channel channel, Object message) {


    }

}
