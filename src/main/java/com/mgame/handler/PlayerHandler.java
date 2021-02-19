package com.mgame.handler;

import com.mgame.biz.Player;
import com.mgame.biz.PlayerManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import proto.ItemProto;
import proto.RoleProto;

import java.util.ArrayList;
import java.util.List;


@Component
public class PlayerHandler implements AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(PlayerHandler.class);

    @Override
    public void handle(Channel channel, Object message) {


    }

    private void buy(Channel channel, ItemProto.BuyReq_1001000 req) {

    }

    private void enter(Channel channel, RoleProto.EnterGameReq_1001004 req) {

    }

    private void out(Channel channel, RoleProto.OutGameReq_1001005 req) {
        Player player = channel.attr(UserHandler.roleKey).get();
        player.update();
        player.close();

        PlayerManager.send(req);

        RoleProto.OutGameReq_1001005.Builder builder = RoleProto.OutGameReq_1001005.newBuilder();
        builder.setPlayerId(player.playerId);
        PlayerManager.send(builder.build());

        // 关闭链接
        ChannelFuture future = channel.closeFuture();
        future.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                logger.info("{}用户下线", player.playerId);
            }
        });
    }

}
