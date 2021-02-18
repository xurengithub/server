package com.mgame.handler;


import com.mgame.net.ProtoManager;
import com.mgame.service.UserService;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proto.RoleProto;
import proto.SyncProto;

import java.util.List;
import java.util.Random;


@Component
public class UserHandler implements AbstractHandler {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Override
    public void handle(Channel channel, Object message) {
        if(message instanceof RoleProto.LoginResp_1001001) {
            login(channel, (RoleProto.LoginReq_1001001)message);
        }
        if(message instanceof RoleProto.RegisterReq_1001002) {
            register(channel, (RoleProto.RegisterReq_1001002)message);
        }
        if(message instanceof RoleProto.CreateRoleReq_1001003){
            createPlayer(channel, (RoleProto.CreateRoleReq_1001003)message);
        }
        if(message instanceof SyncProto.PingReq_1001000) {
            createPlayer(channel, (RoleProto.CreateRoleReq_1001003) message);
        }
    }

    private void login(Channel channel, RoleProto.LoginReq_1001001 res) {
        boolean isSucc = userService.verifyAccountAndPassword(res.getAccount(), res.getPassword());

    }

    private void register(Channel channel, RoleProto.RegisterReq_1001002 res) {
        boolean isSucc = userService.register(res.getAccount(), res.getPassword());
        if(!isSucc) {
            RoleProto.RegisterResp_1001002.Builder builder = RoleProto.RegisterResp_1001002.newBuilder();
            builder.setCode(1);

            ProtoManager.send(builder.build(), channel);
        }
    }

    private void createPlayer(Channel channel, RoleProto.CreateRoleReq_1001003 res) {

    }

    private void ping(Channel channel, SyncProto.PingReq_1001000 res) {

    }


}
