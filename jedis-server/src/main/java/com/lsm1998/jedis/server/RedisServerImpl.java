/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.server;

import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.server.handler.ConnectionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class RedisServerImpl implements RedisServer
{
    private Selector selector;
    private final ConnectionHandler handler;

    public RedisServerImpl()
    {
        this.handler = new ConnectionHandler();
    }

    @Override
    public void start() throws IOException
    {
        selector = Selector.open();
        RedisConfig.DefRedisConfig defRedisConfig = RedisConfig.defRedisConfig;
        this.start(defRedisConfig.getPort());
    }

    private void start(int port) throws IOException
    {
        InetSocketAddress isa = new InetSocketAddress("127.0.0.1", port);
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(isa);
        // 设置非阻塞
        server.configureBlocking(false);
        server.register(selector, SelectionKey.OP_ACCEPT);
        log.info("Redis start! port=[{}]", port);
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

    }
}
