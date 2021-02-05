/**
 * 作者：刘时明
 * 时间：2021/2/3
 */
package com.lsm1998.jedis.storage.impl;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.utils.ByteUtil;
import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.storage.RedisStorage;

import java.io.ByteArrayOutputStream;

public class RDBStorage implements RedisStorage
{
    private final RedisConfig.DefRedisConfig config;

    public RDBStorage(RedisConfig.DefRedisConfig config)
    {
        this.config = config;
    }

    @Override
    public RedisDB[] load()
    {
        return new RedisDB[0];
    }

    @Override
    public void save(RedisDB[] redisDBArrays)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // db版本
        outputStream.writeBytes(String.format("REDIS%04d",5).getBytes());

        for (RedisDB redisDB : redisDBArrays)
        {
            if (redisDB.dict.size() == 0) continue;
            // 写入长度
            outputStream.writeBytes(ByteUtil.intToBytes(redisDB.dict.size()));
            redisDB.dict.forEach((k, v) ->
            {
            });
        }
    }
}
