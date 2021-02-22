package com.mgame.handler;

import com.mgame.biz.Player;
import com.mgame.biz.PlayerManager;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;
import proto.SyncProto;

@Component
public class SynHandler implements AbstractHandler{


    @Override
    public void handle(Channel channel, Object message) {
        if(message instanceof SyncProto.PositionSyncReq_1001006) {
            syncPlayer(channel, (SyncProto.PositionSyncReq_1001006) message);
        }
        if(message instanceof SyncProto.AttackReq_1001007) {
            acAttack(channel, (SyncProto.AttackReq_1001007) message);
        }
    }

    private void syncPlayer(Channel channel, SyncProto.PositionSyncReq_1001006 req){

        Player player = channel.attr(UserHandler.roleKey).get();

//        player.playerEntity.setX(req.getX());
//        player.playerEntity.setY(req.getY());
//        player.playerEntity.setZ(req.getZ());
//        player.playerEntity.setEx(req.getEx());
//        player.playerEntity.setEy(req.getEy());
//        player.playerEntity.setEz(req.getEz());
//        req.setPlayerId(player.playerId);
        PlayerManager.send(req);
    }

    private void acAttack(Channel channel, SyncProto.AttackReq_1001007 req) {
        Player player = channel.attr(UserHandler.roleKey).get();
        PlayerManager.send(req);
    }
}
