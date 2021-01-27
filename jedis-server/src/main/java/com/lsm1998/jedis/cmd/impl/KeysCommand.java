package com.lsm1998.jedis.cmd.impl;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class KeysCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        List<String> result = new ArrayList<>();
        RedisDB redisDB = connect.getRedisDB();
        redisDB.forEach((k, v) ->
        {

        });
        return null;
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
