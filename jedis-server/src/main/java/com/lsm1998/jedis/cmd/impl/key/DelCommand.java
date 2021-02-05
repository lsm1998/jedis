/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class DelCommand extends KeyCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        if (connect.getRedisDB().dict.containsKey(key))
        {
            connect.getRedisDB().expires.put(key, System.currentTimeMillis());
            return 1;
        }
        return 0;
    }

    @Override
    public String argsCond()
    {
        return "0";
    }
}
