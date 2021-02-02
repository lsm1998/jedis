/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class AppendCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {

        return null;
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
