package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCmdHandler;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.connect.RedisClientConnect;

public class SetCmdHandler implements RedisCmdHandler
{
    @Override
    public Object handler(RedisClientConnect connect, String key, String[] args)
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.get(key);
        if (redisObject == null)
        {

        }
        return null;
    }
}
