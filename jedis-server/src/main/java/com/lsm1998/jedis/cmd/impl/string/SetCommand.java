package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.common.utils.CommandUtil;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;
import com.lsm1998.jedis.constant.RedisProperties;

import java.io.Serializable;

import static com.lsm1998.jedis.common.RedisType.REDIS_STRING;

public class SetCommand extends StringCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.dict.get(key);
        if (redisObject == null)
        {
            redisObject = new RedisObject();
        }
        setRedisObject(redisObject, args[0]);
        redisDB.dict.put(key, redisObject);
        return 1;
    }

    @Override
    public String argsLenCond()
    {
        return "1";
    }
}
