package com.mgame.handler;


import com.mgame.biz.Player;
import com.mgame.biz.PlayerManager;
import com.mgame.dao.entity.PlayerEntity;
import com.mgame.dao.entity.UserInfoEntity;
import com.mgame.net.ProtoManager;
import com.mgame.service.IPlayerService;
import com.mgame.service.IUserService;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import proto.RoleProto;
import proto.SyncProto;

import java.util.List;


@Component
public class UserHandler implements AbstractHandler {

    public static final AttributeKey<String> userKey = AttributeKey.valueOf("userID");
    @Autowired
    private IUserService userService;
    @Autowired
    private IPlayerService playerService;

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

    private void login(Channel channel, RoleProto.LoginReq_1001001 req) {
        String account = req.getAccount();
        String password = req.getPassword();

        boolean isSucc = userService.verifyAccountAndPassword(account, password);

        RoleProto.LoginResp_1001001.Builder builder = RoleProto.LoginResp_1001001.newBuilder();
        if(!isSucc) {
            builder.setCode(1);
        }

        UserInfoEntity userInfo = userService.getUserInfo(account);
        String userID = userInfo.getId();
        List<PlayerEntity> roleList = playerService.findPlayersInfoByUserId(userID);
        for (int i = 0; i < roleList.size(); i++) {
            PlayerEntity roleInfo = roleList.get(i);

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

            builder.setRoles(i, builder1);
        }
        builder.setCode(0);

        channel.attr(userKey).set(userID);
        ProtoManager.send(builder.build(), channel);

    }

    private void register(Channel channel, RoleProto.RegisterReq_1001002 req) {
        String account = req.getAccount();
        String password = req.getPassword();

        boolean isSucc = userService.register(account, password);
        if(!isSucc) {
            RoleProto.RegisterResp_1001002.Builder builder = RoleProto.RegisterResp_1001002.newBuilder();
            builder.setCode(1);

            ProtoManager.send(builder.build(), channel);
        }
    }

    private void createPlayer(Channel channel, RoleProto.CreateRoleReq_1001003 req) {
        RoleProto.CreateRoleResp_1001003.Builder builder = RoleProto.CreateRoleResp_1001003.newBuilder();
        if(!channel.hasAttr(userKey)) {
            builder.setCode(1);
            ProtoManager.send(builder.build(), channel);
            return;
        }

        String userID = channel.attr(userKey).get();
        String roleName = req.getName();
        PlayerEntity roleInfo = playerService.findByName(roleName);
        if(!ObjectUtils.isEmpty(roleInfo)) {
            builder.setCode(1);
            ProtoManager.send(builder.build(), channel);
            return;
        }

        PlayerEntity playerEntity = new PlayerEntity();

        playerEntity.setUserId(userID);
        playerEntity.setCoin(100);
        playerEntity.setGem(0);
        playerEntity.setExp(0);
        playerEntity.setLevel(0);
        playerEntity.setPlayerName(roleName);
        playerEntity.setScene("noviceCun");
        playerEntity.setAreaId(0);
        playerEntity.setX(100);
        playerEntity.setY(100);
        playerEntity.setZ(100);
        playerEntity.setMp(100);
        playerEntity.setHp(100);
        playerService.insert(playerEntity);


        Player player = new Player(channel);
        player.playerId = playerEntity.getPlayerId();
        player.playerEntity = playerEntity;
        player.areaId = playerEntity.getAreaId();
        player.scene = playerEntity.getScene();

        PlayerManager.addPlayer(playerEntity.getPlayerId(), player);
        //获取区域信息并添加入area

        RoleProto.CreateRoleResp_1001003.Builder builder1 = RoleProto.CreateRoleResp_1001003.newBuilder();
        builder1.setCode(0);
        ProtoManager.send(builder1.build(), channel);
    }

    private void ping(Channel channel, SyncProto.PingReq_1001000 req) {
        SyncProto.PongResp_1001000.Builder builder = SyncProto.PongResp_1001000.newBuilder();
        ProtoManager.send(builder.build(), channel);
    }


}
