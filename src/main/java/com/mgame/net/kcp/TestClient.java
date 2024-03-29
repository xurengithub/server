/**
 * 客户端
 */
package com.mgame.net.kcp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.util.ResourceLeakDetector;
import org.beykery.jkcp.Kcp;
import org.beykery.jkcp.KcpClient;
import org.beykery.jkcp.KcpOnUdp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * @author beykery
 */
public class TestClient extends KcpClient {

    @Override
    public void handleReceive(ByteBuf bb, KcpOnUdp kcp) {
        String content = bb.toString(Charset.forName("utf-8"));
        System.out.println("conv:" + kcp.getKcp().getConv() + " recv:" + content + " kcp-->" + kcp);
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer(2048);
        buf.writeBytes(content.getBytes(Charset.forName("utf-8")));
        kcp.send(buf);
        bb.release();
    }

    /**
     * kcp异常，之后此kcp就会被关闭
     *
     * @param ex
     * @param kcp
     */
    @Override
    public void handleException(Throwable ex, KcpOnUdp kcp) {
        System.out.println(ex);
    }

    @Override
    public void handleClose(KcpOnUdp kcp) {
        super.handleClose(kcp);
        System.out.println("服务器离开:" + kcp);
        System.out.println("waitSnd:" + kcp.getKcp().waitSnd());
    }

    @Override
    public void out(ByteBuf msg, Kcp kcp, Object user) {
        super.out(msg, kcp, user);
    }

    /**
     * tcpdump udp port 2225 -x -vv -s0 -w 1112.pcap
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.DISABLED);
        TestClient tc = new TestClient();
        tc.noDelay(1, 20, 2, 1);
        tc.setMinRto(10);
        tc.wndSize(32, 32);
        tc.setTimeout(10 * 1000);
        tc.setMtu(512);
        // tc.setConv(121106);//默认conv随机

        tc.connect(new InetSocketAddress("localhost", 2222));
        tc.start();
        String content = "sdfkasd你好。。。。。。。";
        ByteBuf bb = PooledByteBufAllocator.DEFAULT.buffer(1500);
        bb.writeBytes(content.getBytes(Charset.forName("utf-8")));
        tc.send(bb);
    }
}
