package com.lsm1998.jedis.cmd.impl.hash;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisType;
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
    public String argsLenCond()
    {
        return "+1";
    }

    @Override
    public RedisType typeCond()
    {
        return RedisType.REDIS_HASH;
    }
}
