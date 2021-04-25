/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.list;

import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class RPushCommand extends ListCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        return push(connect, key, args, 1);
    }

    @Override
    public String argsLenCond()
    {
        return "+1";
    }
}
