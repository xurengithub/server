package com.mgame.handler;


import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proto.RoleProto;

import java.util.List;
import java.util.Random;


@Component
public class UserHandler implements AbstractHandler {

    private Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Override
    public void handle(Channel channel, Object message) {
        if(message instanceof RoleProto.LoginResp_1001001) {

        }
    }


}
