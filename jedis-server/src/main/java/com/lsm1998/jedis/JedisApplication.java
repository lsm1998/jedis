package com.lsm1998.jedis;

import com.lsm1998.jedis.config.RedisConfig;
import com.lsm1998.jedis.event.EventStart;
import com.lsm1998.jedis.server.RedisServer;
import com.lsm1998.jedis.net.RedisNetServer;
import com.lsm1998.jedis.net.RedisNetServerBuild;

import java.io.IOException;

public class JedisApplication
{
    public static void main(String[] args)
    {
        RedisConfig config = RedisConfig.getInstance();

        config.load();

        EventStart.eventInit();

        RedisServer server = RedisServer.getInstance();
        server.initDB();

        RedisNetServer redisNetServer = new RedisNetServerBuild().build();
        try
        {
            redisNetServer.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
