package com.lsm1998.jedis.server;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.constant.SysProperties;

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

    private RedisServer()
    {
    }

    private RedisConfig.DefRedisConfig config;

    // 数据库数量
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
        this.dbNum = config.getDatabases();
        this.redisDB = new RedisDB[this.dbNum];
        for (int i = 0; i < this.dbNum; i++)
        {
            redisDB[i] = new RedisDB(i);
        }
    }

    public RedisDB getRedisDB(int index)
    {
        return this.redisDB[index];
    }
}
