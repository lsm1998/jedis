package com.lsm1998.jedis.connect;

import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.server.RedisDBServer;

import java.nio.channels.SocketChannel;

public class RedisClientConnect
{
    // 当前选择的数据库下标
    private int dbIndex;
    // 当前选择的数据库
    private RedisDBServer redisDBServer;

    protected SocketChannel channel;

    public int selectDB(int index)
    {
        if (index == this.dbIndex)
        {
            return 0;
        }
        this.dbIndex = index;
        return 1;
    }

    public RedisDB getRedisDB()
    {
        return this.redisDBServer.getRedisDB(this.dbIndex);
    }
}
