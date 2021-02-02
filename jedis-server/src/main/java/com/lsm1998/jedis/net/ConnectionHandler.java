/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.net;

import com.lsm1998.jedis.cmd.RedisCommandHandler;
import com.lsm1998.jedis.common.socket.ReplyData;
import com.lsm1998.jedis.common.socket.ReplyType;
import com.lsm1998.jedis.common.utils.BitObjectUtil;
import com.lsm1998.jedis.connect.ConnectMap;
import com.lsm1998.jedis.connect.RedisClientConnect;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Slf4j
public class ConnectionHandler
{
    private final Charset charset;

    private final ConnectMap connectMap;

    public ConnectionHandler()
    {
        this.charset = StandardCharsets.UTF_8;
        this.connectMap = new ConnectMap();
    }

    public void accept(SelectionKey key, ServerSocketChannel server, Selector selector) throws IOException
    {
        SocketChannel channel = server.accept();
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        key.interestOps(SelectionKey.OP_ACCEPT);
        connectMap.joinConnect(channel);
    }

    public void read(SelectionKey key) throws IOException
    {
        StringBuilder command = new StringBuilder();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try
        {
            int len;
            while ((len = socketChannel.read(buffer)) > 0)
            {
                buffer.flip();
                command.append(charset.decode(buffer));
            }
            if (len == -1)
            {
                // 返回-1代表客户端连接已经断开
                this.socketClose(key, socketChannel);
                return;
            }
        } catch (IOException e)
        {
            // 客户端断开连接，这里会报错提示远程主机强迫关闭了一个现有的连接。
            this.socketClose(key, socketChannel);
            return;
        }
        this.handlerData(command.toString(), socketChannel);
    }

    public void close() throws IOException
    {

    }

    private void handlerData(String command, SocketChannel dest) throws IOException
    {
        RedisClientConnect client = connectMap.getClientConnect(dest);
        if (client == null)
        {
            throw new RuntimeException("client is null!");
        }
        ReplyData<Serializable> replyData;
        if (!client.parse(command))
        {
            replyData = ReplyData.of(ReplyType.REPLY_ERROR, "参数解析错误");
        } else
        {
            replyData = RedisCommandHandler.call(client);
        }
        if (replyData == null)
        {
            return;
        }
        byte[] bytes = BitObjectUtil.objectToBytes(replyData).orElse(new byte[]{});
        dest.write(ByteBuffer.wrap(bytes));
    }

    private void socketClose(SelectionKey key, SocketChannel socketChannel) throws IOException
    {
        key.cancel();
        connectMap.leaveConnect(socketChannel);
        socketChannel.close();
    }
}
