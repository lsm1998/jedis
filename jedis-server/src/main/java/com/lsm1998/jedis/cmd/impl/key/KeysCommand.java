package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class KeysCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        String pattern = args[0].replace("*",".*.");
        ArrayList<String> result = new ArrayList<>();
        RedisDB redisDB = connect.getRedisDB();
        redisDB.dict.forEach((k, v) ->
        {
            if (Pattern.matches(pattern, k))
            {
                result.add(k);
            }
        });
        return result;
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
