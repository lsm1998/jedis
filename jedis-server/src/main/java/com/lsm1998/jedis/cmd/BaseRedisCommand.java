/**
 * 作者：刘时明
 * 时间：2021/2/2
 */
package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

/**
 * 基本命令，命令不带key
 */
public abstract class BaseRedisCommand implements RedisCommand
{
    @Override
    public String argsCond()
    {
        return "0";
    }

    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        return this.baseHandler(connect, key);
    }

    public abstract Serializable baseHandler(RedisClientConnect connect, String args) throws ExecuteException;
}
