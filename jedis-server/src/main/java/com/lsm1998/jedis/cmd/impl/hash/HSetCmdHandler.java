package com.lsm1998.jedis.cmd.impl.hash;

import com.lsm1998.jedis.cmd.RedisCmdHandler;
import com.lsm1998.jedis.connect.RedisClientConnect;

public class HSetCmdHandler implements RedisCmdHandler
{
    @Override
    public Object handler(RedisClientConnect connect,String key, String[] args)
    {
        return null;
    }
}
