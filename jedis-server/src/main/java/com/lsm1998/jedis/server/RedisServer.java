package com.lsm1998.jedis.server;

import com.lsm1998.jedis.common.db.RedisDB;

public class RedisServer
{
    // 数据库数量
    private int dbNum;

    // 数据库实例数组
    private RedisDB[] redisDB;
}
