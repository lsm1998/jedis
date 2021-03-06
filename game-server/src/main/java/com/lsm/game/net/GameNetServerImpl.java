/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm.game.net;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class GameNetServerImpl implements GameNetServer
{
    private Selector selector;
    private final ConnectionHandler handler;

    public GameNetServerImpl()
    {
        this.handler = new ConnectionHandler();
    }

    @Override
    public void start() throws IOException
    {
        selector = Selector.open();
        this.start(8090);
    }

    private void start(int port) throws IOException
    {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", port);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(isa);
        // 设置非阻塞
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
                // 是否包含客户端请求
                if (key.isAcceptable())
                {
                    handler.accept(key, server, selector);
                }
                // 是否存在读取的数据
                if (key.isReadable())
                {
                    handler.read(key);
                }
            }
        }
    }

    @Override
    public void stop() throws IOException
    {
        selector.close();
    }
}
