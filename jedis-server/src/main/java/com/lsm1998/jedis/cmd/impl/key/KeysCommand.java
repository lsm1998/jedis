package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.BaseRedisCommand;
import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

public class KeysCommand extends BaseRedisCommand
{
    @Override
    public Serializable baseHandler(RedisClientConnect connect, String args) throws ExecuteException
    {
        String pattern = args.replace("*", ".*.");
        ArrayList<String> result = new ArrayList<>();
        RedisDB redisDB = connect.getRedisDB();
        long now = System.currentTimeMillis();
        redisDB.dict.forEach((k, v) ->
        {
            if (Pattern.matches(pattern, k))
            {
                Long expire = redisDB.expires.get(k);
                if (expire == null || expire > now)
                {
                    result.add(k);
                }
            }
        });
        return result;
    }
}
