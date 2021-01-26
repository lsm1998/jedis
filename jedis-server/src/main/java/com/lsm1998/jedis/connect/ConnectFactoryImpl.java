package com.lsm1998.jedis.connect;

public class ConnectFactoryImpl implements ConnectFactory
{
    @Override
    public RedisClientConnect getConnect()
    {
        return null;
    }

    public ConnectFactory build()
    {
        return this;
    }
}
