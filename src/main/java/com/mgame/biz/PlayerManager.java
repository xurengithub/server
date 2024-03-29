package com.mgame.biz;

import com.google.protobuf.Message;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {
    public static Map<Integer,Player> players = new ConcurrentHashMap<Integer, Player>();

    public static boolean isOnline(int playerId){
        return players.containsKey(playerId);
    }

    public static Player getPlayer(long playerId){
        if(players.containsKey(playerId)){
            return players.get(playerId);
        }

        return null;
    }

    public static void addPlayer(int playerId, Player player){
        players.put(playerId,player);
    }

    public static void addPlayer(Player player) {

    }

    public static void removePlayer(long playerId){
        players.remove(playerId);
    }

    public synchronized static void send(Message message) {
        for (Player player : players.values()) {
            player.send(message);
        }
    }
}
