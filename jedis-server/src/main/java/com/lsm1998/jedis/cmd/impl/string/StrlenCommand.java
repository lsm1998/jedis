/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.CommandUtil;
import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.common.exception.RedisException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class StrlenCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.dict.get(key);
        if (redisObject == null) return null;
        if (!CommandUtil.checkType(redisObject, RedisType.REDIS_STRING))
        {
            throw new ExecuteException("数据类型错误，目标key不是String类型");
        }
        String ptr = (String) redisObject.getPtr();
        return ptr == null ? 0 : ptr.length();
    }

    @Override
    public String argsCond()
    {
        return "0";
    }
}
