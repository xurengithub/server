package com;//import org.apache.log4j.PropertyConfigurator;

import com.mgame.net.NettyTcpServer;
import org.apache.log4j.PropertyConfigurator;

public class App {
    public static void main( String[] args ){
    	// 装入log4j配置信息
    	PropertyConfigurator.configure("src/main/resources/log4j.properties");
    	
        NettyTcpServer tcpServer = new NettyTcpServer();
        tcpServer.bind("127.0.0.1", 38996);
        tcpServer.start();
    }
}
