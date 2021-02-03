package com.lsm1998.jedis.server;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.constant.SysProperties;
import com.lsm1998.jedis.storage.RedisStorage;
import com.lsm1998.jedis.storage.impl.JDKStorage;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RedisServer
{
    private static final RedisServer server = new RedisServer();

    public static RedisServer getInstance()
    {
        return server;
    }

    private RedisStorage redisStorage;

    private RedisServer()
    {
    }

    private RedisConfig.DefRedisConfig config;

    // 数据库数量
    @Getter
    private int dbNum;

    // 数据库实例数组
    private RedisDB[] redisDB;

    public void initDB()
    {
        this.config = RedisConfig.defRedisConfig;

        this.initDatabases();
    }

    private void initDatabases()
    {
        String storageType = config.getStorage();
        switch (storageType)
        {
            case "jdk":
            case "JDK":
                redisStorage = new JDKStorage(config);
                break;
            default:
                break;
        }
        this.dbNum = config.getDatabases();
        if (redisStorage != null)
        {
            this.redisDB = redisStorage.load();
        }
        if (this.redisDB == null)
        {
            this.redisDB = new RedisDB[this.dbNum];
            for (int i = 0; i < this.dbNum; i++)
            {
                redisDB[i] = new RedisDB(i);
            }
        }
    }

    public RedisDB getRedisDB(int index)
    {
        return this.redisDB[index];
    }

    public void save()
    {
        if (this.redisStorage != null)
        {
            this.redisStorage.save(this.redisDB);
        }
    }
}
