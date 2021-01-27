package com.lsm1998.jedis.connect;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class ConnectMap
{
    private final Map<SocketChannel, RedisClientConnect> map;

    private final ConnectFactory factory;

    public ConnectMap()
    {
        this.map = new HashMap<>();
        this.factory = new ConnectFactoryImpl();
    }

    public void joinConnect(SocketChannel channel)
    {
        RedisClientConnect connect = this.factory.getConnect();
        connect.channel = channel;
        this.map.put(channel, connect);
    }

    public void leaveConnect(SocketChannel channel)
    {
        this.map.remove(channel);
    }

    public RedisClientConnect getClientConnect(SocketChannel channel)
    {
        return this.map.get(channel);
    }
}
