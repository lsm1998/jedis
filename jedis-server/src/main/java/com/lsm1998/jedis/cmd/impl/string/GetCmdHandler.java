package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCmdHandler;
import com.lsm1998.jedis.connect.RedisClientConnect;

public class GetCmdHandler implements RedisCmdHandler
{
    @Override
    public Object handler(RedisClientConnect connect,String key, String[] args)
    {
        return null;
    }
}
