/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

/**
 * 当 key 不存在时，返回 -2
 * 当 key 存在但没有设置剩余生存时间时，返回 -1
 * 否则，以秒为单位，返回 key 的剩余生存时间
 */
public class TtlCommand extends KeyCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisDB redisDB = connect.getRedisDB();
        if (!redisDB.dict.containsKey(key))
        {
            return -2;
        }
        Long ttl = redisDB.expires.get(key);
        return ttl == null ? -1 : System.currentTimeMillis() - ttl / 1000;
    }

    @Override
    public String argsCond()
    {
        return "0";
    }
}
