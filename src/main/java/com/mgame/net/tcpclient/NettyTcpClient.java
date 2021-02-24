package com.mgame.net.tcpclient;

import com.google.protobuf.Message;
import com.mgame.net.Packet;
import com.mgame.net.ProtoDecoder;
import com.mgame.net.ProtoEncoder;
import com.mgame.net.ProtoManager;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;


public class NettyTcpClient {
	private static NettyTcpClient ins = null;
	private static Channel channel;
	
	public static NettyTcpClient instance(){
		if(ins == null){
			ins = new NettyTcpClient();
		}
		return ins;
	}
	
	public void conect(String host, int port){
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.group(group);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("decoder", new ProtoDecoder(5120));
				pipeline.addLast("encoder", new ProtoEncoder(2048));
				pipeline.addLast("serverHandler", new ClientHandler());
			}
		});

		ChannelFuture future;
		try {
			future = bootstrap.connect(new InetSocketAddress(host, port)).sync();
			System.out.println("----channel:"+future.channel());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//future.channel().closeFuture().awaitUninterruptibly();
	}
	
	public void send(Message msg) {
		if (channel == null || msg == null || !channel.isWritable()) {
			return;
		}
		int cmd = ProtoManager.getMessageID(msg);
		Packet packet = new Packet(Packet.HEAD_TCP, cmd, msg.toByteArray());
		channel.writeAndFlush(packet);
	}
	
	public void setChannel(Channel ch){
		channel = ch;
	}
	
	public Channel getChannel(){
		return channel;
	}
}
