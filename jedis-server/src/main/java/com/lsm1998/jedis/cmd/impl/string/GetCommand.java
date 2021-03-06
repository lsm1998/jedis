package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class GetCommand extends StringCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.dict.get(key);
        if (redisObject == null) return null;
        return (Serializable) redisObject.getPtr();
    }

    @Override
    public String argsLenCond()
    {
        return "0";
    }
}
