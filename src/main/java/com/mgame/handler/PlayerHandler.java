package com.mgame.handler;

import com.mgame.biz.Player;
import com.mgame.biz.PlayerManager;
import com.mgame.dao.entity.PlayerEntity;
import com.mgame.net.ProtoManager;
import com.mgame.service.IPlayerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import proto.ItemProto;
import proto.RoleProto;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class PlayerHandler implements AbstractHandler {
    private Logger logger = LoggerFactory.getLogger(PlayerHandler.class);

    @Autowired
    private IPlayerService playerService;

    @Override
    public void handle(Channel channel, Object message) {


    }

    private void buy(Channel channel, ItemProto.BuyReq_1001000 req) {

    }

    private void enter(Channel channel, RoleProto.EnterGameReq_1001004 req) {
        RoleProto.EnterGameResp_1001004.Builder builder = RoleProto.EnterGameResp_1001004.newBuilder();

        // 判断是否登陆
        if(!channel.hasAttr(UserHandler.userKey)) {
            builder.setCode(-1);
            ProtoManager.send(builder.build(), channel);
            return;
        }

        int playerID = req.getRoleId();
        PlayerEntity roleInfo = playerService.findById(playerID);
        // 角色不存在
        if(ObjectUtils.isEmpty(roleInfo)) {
            builder.setCode(-2);
            ProtoManager.send(builder.build(), channel);
            return;
        }
        String userID = channel.attr(UserHandler.userKey).get();
        // 角色不属于用户
        if(!roleInfo.getUserId().equals(userID)) {
            builder.setCode(-3);
            ProtoManager.send(builder.build(), channel);
            return;
        }

        // 角色载入内存
        Player player = new Player(channel);
        player.playerId = roleInfo.getPlayerId();
        player.playerEntity = roleInfo;

        // 构建返回包
        RoleProto.Role.Builder builder1 = RoleProto.Role.newBuilder();
        builder1.setAreaId(roleInfo.getAreaId());
        builder1.setCoin(roleInfo.getCoin());
        builder1.setEx(roleInfo.getEx());
        builder1.setExp(roleInfo.getExp());
        builder1.setEy(roleInfo.getEy());
        builder1.setEz(roleInfo.getEz());
        builder1.setGem(roleInfo.getGem());
        builder1.setHp(roleInfo.getHp());
        builder1.setId(roleInfo.getPlayerId());
        builder1.setMaxHp(roleInfo.getMaxHp());
        builder1.setMaxMp(roleInfo.getMaxMp());
        builder1.setMp(roleInfo.getMp());
        builder1.setName(roleInfo.getPlayerName());

        builder.setRole(builder1);

//        builder.setItems();
        ProtoManager.send(builder.build(), channel);
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
