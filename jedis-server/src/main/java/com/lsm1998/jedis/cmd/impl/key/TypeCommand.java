/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class TypeCommand extends KeyCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getObject(key);
        if (object == null)
        {
            return "none";
        }
        switch (object.getType())
        {
            case REDIS_SET:
                return "set";
            case REDIS_HASH:
                return "hash";
            case REDIS_LIST:
                return "list";
            case REDIS_ZSET:
                return "zset";
            case REDIS_STRING:
                return "string";
            default:
                throw new RuntimeException("type not supported！");
        }
    }

    @Override
    public String argsLenCond()
    {
        return "0";
    }
}
