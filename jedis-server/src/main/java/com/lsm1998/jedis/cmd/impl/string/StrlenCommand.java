/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class StrlenCommand extends StringCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject redisObject = connect.getObject(key);
        if (redisObject == null) return null;
        Object ptr = redisObject.getPtr();
        return ptr == null ? null : ptr.toString().length();
    }

    @Override
    public String argsLenCond()
    {
        return "0";
    }
}
