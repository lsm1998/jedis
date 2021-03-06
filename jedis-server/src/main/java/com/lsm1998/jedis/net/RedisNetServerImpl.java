/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.net;

import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.event.EventBean;
import com.lsm1998.jedis.event.EventPublish;
import com.lsm1998.jedis.event.EventType;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

@Slf4j
public class RedisNetServerImpl implements RedisNetServer
{
    private Selector selector;
    private final ConnectionHandler handler;

    private final EventPublish publish;

    public RedisNetServerImpl()
    {
        this.handler = new ConnectionHandler();
        this.publish = EventPublish.getInstance();
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
        publish.notify(EventBean.of(EventType.EVENT_START, port));
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
        handler.close();
        selector.close();
    }
}
