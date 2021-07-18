/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm.game.net;

import com.lsm.game.connect.ConnectMap;
import com.lsm1998.game.protocol.HeadPackReq;
import com.lsm1998.game.utils.BitUtil;
import com.lsm1998.game.utils.PackUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

@Slf4j
public class ConnectionHandler
{
    private final ConnectMap connectMap;

    public ConnectionHandler()
    {
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
        SocketChannel socketChannel = (SocketChannel) key.channel();
        HeadPackReq packReq = readHeadPack(socketChannel);
        if (packReq == null)
        {
            // 返回-1代表客户端连接已经断开
            this.socketClose(key, socketChannel);
            return;
        }
        ByteBuffer bodyBuffer = ByteBuffer.allocate(packReq.getBodyLen());
        if (packReq.getBodyLen() != socketChannel.read(bodyBuffer))
        {
            throw new RuntimeException("数据格式错误");
        }
        System.out.println(new String(bodyBuffer.array(), 0, packReq.getBodyLen()));
    }

    private HeadPackReq readHeadPack(SocketChannel socketChannel) throws IOException
    {
        ByteBuffer headBuffer = ByteBuffer.allocate(HeadPackReq.REQ_HEAD_PACK_SIZE);
        // 先读数据头
        int len = socketChannel.read(headBuffer);
        if (len == -1)
        {
            return null;
        }
        if (len != 8)
        {
            throw new RuntimeException("数据格式错误");
        }
        headBuffer.flip();
        return PackUtil.serializeHeadPackReq(headBuffer.array());
    }

    private void socketClose(SelectionKey key, SocketChannel socketChannel) throws IOException
    {
        key.cancel();
        connectMap.leaveConnect(socketChannel);
        socketChannel.close();
    }
}
