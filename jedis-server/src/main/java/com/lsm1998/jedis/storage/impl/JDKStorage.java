/**
 * 作者：刘时明
 * 时间：2021/2/3
 */
package com.lsm1998.jedis.storage.impl;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.storage.RedisStorage;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

@Slf4j
public class JDKStorage implements RedisStorage
{
    private final RedisConfig.DefRedisConfig config;

    public JDKStorage(RedisConfig.DefRedisConfig config)
    {
        this.config = config;
    }

    @Override
    public RedisDB[] load()
    {
        File file = new File(config.getStorageFilename());
        if (file.exists())
        {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file)))
            {
                return (RedisDB[]) inputStream.readObject();
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void save(RedisDB[] redisDBArrays)
    {
        File file = new File(config.getStorageFilename());
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file)))
        {
            outputStream.writeObject(redisDBArrays);
            log.info("JDKStorage save success！");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
