package com.mgame.net.udp;

import com.mgame.net.NettyTcpServer;
import com.mgame.net.ProtoDecoder;
import com.mgame.net.ProtoEncoder;
import com.mgame.net.ServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class UDPServer {
    private static final Logger log = LoggerFactory.getLogger(NettyTcpServer.class);

    private final EventLoopGroup bossGroup;
    private final Bootstrap bootstrap;
    public UDPServer() {
        bossGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(bossGroup)
                .channel(NioDatagramChannel.class)
                .handler(new UdpServerHandler());
    }

    public void bind(String ip, int port) {
        InetSocketAddress address = new InetSocketAddress(ip, port);
        try {
            ChannelFuture future = bootstrap.bind(address).sync();
            log.info("Netty udp Server started..");

            future.channel().closeFuture().sync();
            log.info("Netty udp Server closed..");
        } catch (InterruptedException e) {
            log.error("bind "+ip+":"+port+" failed", e);
            shutdown();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }

    public void start() {
        log.info("Netty Tcp Server started..");
    }

    public void shutdown() {
        try {
            bossGroup.shutdownGracefully().sync();
        } catch (InterruptedException e) {
            log.error("shutdown boss group failed", e);
        }
    }

    public static void main(String[] args) {
        UDPServer server = new UDPServer();
        server.bind("127.0.0.1", 8888);
        server.start();
    }
}
