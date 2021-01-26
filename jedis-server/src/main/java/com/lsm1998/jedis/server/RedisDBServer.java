package com.lsm1998.jedis.server;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.config.RedisConfig;

public class RedisDBServer
{
    protected RedisDBServer()
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
