package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class MoveCommand extends KeyCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        int index;
        try
        {
            index = Integer.parseInt(args[0]);
        } catch (Exception e)
        {
            throw new RuntimeException("Move fail，db index error！");
        }
        RedisDB redisDB = connect.getRedisDB();
        if (index == redisDB.id)
        {
            return "ok";
        }
        if (index < 0 || index > connect.getRedisServer().getDbNum() - 1)
        {
            throw new RuntimeException("Move fail，db index error！");
        }
        // move key-value
        RedisObject object = connect.getObject(key);
        connect.getRedisDB().dict.remove(key);
        connect.getRedisServer().getRedisDB(index).dict.put(key, object);

        // move expires time
        Long expiresTime = connect.getRedisDB().expires.get(key);
        if (expiresTime != null)
        {
            connect.getRedisDB().expires.remove(key);
            connect.getRedisServer().getRedisDB(index).expires.put(key, expiresTime);
        }
        return null;
    }

    @Override
    public String argsLenCond()
    {
        return "1";
    }
}
