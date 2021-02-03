/**
 * 作者：刘时明
 * 时间：2021/2/3
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

/**
 * 和Redis标准命令有差异
 * <p>
 * 等价于 object encoding
 */
public class ObjectCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getRedisDB().dict.get(key);
        return object == null ? null : object.getType();
    }

    @Override
    public String argsCond()
    {
        return "0";
    }

    @Override
    public RedisType typeCond()
    {
        return null;
    }
}
