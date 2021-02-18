package com.mgame.handler;

import io.netty.channel.Channel;

/**
 * 协议处理器
 */
public interface AbstractHandler {
    public void handle(Channel session, Object message);
}
