package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.connect.RedisClientConnect;

public interface RedisCmdHandler
{
    Object handler(RedisClientConnect connect,String key, String[] args);
}
