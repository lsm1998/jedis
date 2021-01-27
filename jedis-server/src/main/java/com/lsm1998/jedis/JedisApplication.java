package com.lsm1998.jedis;

import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.server.RedisDBServer;
import com.lsm1998.jedis.server.RedisServer;
import com.lsm1998.jedis.server.RedisServerBuild;

import java.io.IOException;

public class JedisApplication
{
    public static void main(String[] args)
    {
        RedisConfig config = RedisConfig.getInstance();

        config.load();

        RedisDBServer server = RedisDBServer.getInstance();
        server.initDB();

        RedisServer redisServer = new RedisServerBuild().build();
        try
        {
            redisServer.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
