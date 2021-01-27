package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class GetCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.get(args[0]);
//        switch (redisObject.getEncoding())
//        {
//            case REDIS_ENCODING_RAW:
//            case REDIS_ENCODING_EMBSTR:
//
//                break;
//            case REDIS_ENCODING_INT:
//                Object ptr = redisObject.getPtr();
//                break;
//        }
        if (redisObject == null) return null;
        return (Serializable) redisObject.getPtr();
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
