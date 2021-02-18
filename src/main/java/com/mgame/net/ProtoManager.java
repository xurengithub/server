package com.mgame.net;

import com.mgame.SpringContextHelper;
import com.mgame.handler.AbstractHandler;
import com.mgame.handler.PlayerHandler;
import com.mgame.handler.SynHandler;
import com.mgame.handler.UserHandler;
import com.mgame.service.UserService;
import com.mgame.utils.ClassUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import proto.RoleProto;
import proto.RoleProto.LoginResp_1001001;

import com.google.protobuf.Message;

@SuppressWarnings("rawtypes")
public class ProtoManager {

    private static final Logger log = LoggerFactory.getLogger(NettyTcpServer.class);

	private static Map<Integer, Class<?>> reqMap = null;
	private static Map<Integer, Class<?>> respMap = null;
	private static Map<Integer, Class<?>> messageType = null;

    static {
        String packageName = "proto";
        Class clazz = Message.class;
        try {
            reqMap = ClassUtils.getClasses(packageName, clazz, "Req_");
            respMap = ClassUtils.getClasses(packageName, clazz, "Resp_");
            messageType.put(1001001, UserHandler.class);//登陆
            messageType.put(1001002, UserHandler.class);//注册
            messageType.put(1001003, UserHandler.class);//创建角色
            messageType.put(1001004, UserHandler.class);//心跳
            messageType.put(1001005, SynHandler.class);//同步
            messageType.put(1001006, SynHandler.class);//攻击
            messageType.put(1001007, PlayerHandler.class);//进入游戏
            messageType.put(1001008, PlayerHandler.class);//买物品
            messageType.put(1001009, PlayerHandler.class);//离线
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static ByteBuf wrapBuffer(Message msg) {
        ByteBufAllocator alloc = ByteBufAllocator.DEFAULT;
        int protocol = 0;
        Set<Entry<Integer, Class<?>>> set = respMap.entrySet();
        for (Entry<Integer, Class<?>> entry : set) {
            if (entry.getValue().isInstance(msg)) {
                protocol = entry.getKey();
                break;
            }
        }
        byte[] data = msg.toByteArray();
        // 消息长度=协议号4位+数据体长度
        int length = data.length + 4;
        // 数据包=消息长度+协议号+数据体
        // 数据包长度=4+消息长度
        // ByteBuf buffer = Unpooled.buffer(length + 4);
        ByteBuf buffer = alloc.buffer(length + 4);
       /* buffer.writeInt(length);
        buffer.writeInt(protocol);
        buffer.writeBytes(data);*/
        //HEAD_TCP = (byte)0x80
        buffer.writeByte((byte)0x80);
        buffer.writeShort(length);
        buffer.writeInt(protocol);
        buffer.writeBytes(data);

        if (buffer.readableBytes() > 4096) {
//            LogUtil.warn(ProtobufCenter.toString(protocol) + " " + buffer.readableBytes() + " too big");
        }
        return buffer;
    }

    public static Map<Integer, Class<?>> getReqMap() {
    	return reqMap;
    }

    public static void handleProto(Packet packet, Channel channel){
//    	int cmd = packet.getCmd();
//    	if(cmd == 1001001){
//    		LoginResp(channel);
//    	}

        int cmd = packet.getCmd();
    	//打印协议
    	Class<?> clz = getReqMap().get(packet.getCmd());
		try {
			Method method = clz.getMethod("parseFrom", byte[].class);
			Object object = method.invoke(clz, packet.getBytes());

			if(!messageType.containsKey(cmd)) {
                log.error("未识别协议...");
                return;
            }

            AbstractHandler handler = (AbstractHandler) SpringContextHelper.getBean2(messageType.get(cmd));
            handler.handle(channel, object);

			ProtoPrinter.print(object);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }

    //发送协议
	public static void send(Message msg, Channel channel) {
		if (channel == null || msg == null || !channel.isWritable()) {
			return;
		}
		channel.writeAndFlush(ProtoManager.wrapBuffer(msg));
	}

	private static void LoginResp(Channel channel) {
		LoginResp_1001001.Builder builder = LoginResp_1001001.newBuilder();
		builder.setAccount("xiaosheng996");
		builder.setRid("100000001");
		send(builder.build(), channel);
	}
}
