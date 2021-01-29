package com.lsm1998.jedis.connect;

import com.lsm1998.jedis.server.RedisServer;

public class ConnectFactoryImpl implements ConnectFactory
{
    protected ConnectFactoryImpl()
    {
    }

    @Override
    public RedisClientConnect getConnect()
    {
        RedisClientConnect client = new RedisClientConnect();
        client.setRedisServer(RedisServer.getInstance());
        return client;
    }
}
