import static org.junit.Assert.assertTrue;

import com.mgame.net.ProtoDecoder;
import com.mgame.net.ProtoEncoder;
import com.mgame.net.ProtoManager;
import com.mgame.net.ServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Test;
import proto.RoleProto;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void testEmbed() {
        ChannelInitializer initializer = new ChannelInitializer<EmbeddedChannel>() {
            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast("decoder", new ProtoDecoder(2048))
                        .addLast("server-handler", new ServerHandler())
                        .addLast("encoder", new ProtoEncoder(5120));
            }
        };

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);

        for(int j = 1; j < 100; j++) {
            ByteBuf buf = Unpooled.buffer();
            RoleProto.LoginReq_1001001.Builder builder = RoleProto.LoginReq_1001001.newBuilder();
            builder.setAccount("xuren");
            builder.setPassword("123456");
            RoleProto.LoginReq_1001001 loginReq_1001001 = builder.build();
            byte[] data = loginReq_1001001.toByteArray();
            buf.writeByte((byte)0x80);
            buf.writeShort(data.length + 4);
            buf.writeInt(1001001);
            buf.writeBytes(data);
            embeddedChannel.writeInbound(buf);
            embeddedChannel.flush();
            System.out.println("send:"+j);
        }

        for(int j = 1; j < 2; j++) {
            ByteBuf buf = Unpooled.buffer();
            RoleProto.LogoutReq_1001002.Builder builder = RoleProto.LogoutReq_1001002.newBuilder();
            builder.setRid(1008L);
            RoleProto.LogoutReq_1001002 logoutReq_1001002 = builder.build();
            byte[] data = logoutReq_1001002.toByteArray();
            buf.writeByte((byte)0x80);
            buf.writeShort(data.length + 4);
            buf.writeInt(1001002);
            buf.writeBytes(data);
            embeddedChannel.writeInbound(buf);
            embeddedChannel.flush();
            System.out.println("send:"+j);
        }

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
