package com.lsm1998.jedis.cmd.impl.hash;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class HSetCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        return null;
    }

    @Override
    public String argsCond()
    {
        return "+1";
    }
}
