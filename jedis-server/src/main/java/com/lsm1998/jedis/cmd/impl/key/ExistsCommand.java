/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class ExistsCommand extends KeyCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        return connect.getRedisDB().dict.containsKey(key) ? 1 : 0;
    }

    @Override
    public String argsLenCond()
    {
        return "0";
    }
}
