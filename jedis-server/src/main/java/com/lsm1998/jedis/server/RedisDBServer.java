package com.lsm1998.jedis.server;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.config.RedisConfig;

public class RedisDBServer
{
    private static final RedisDBServer server = new RedisDBServer();

    public static RedisDBServer getInstance()
    {
        return server;
    }

    private RedisDBServer()
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
            redisDB[i] = new RedisDB();
        }
    }

    public RedisDB getRedisDB(int index)
    {
        return this.redisDB[index];
    }
}
