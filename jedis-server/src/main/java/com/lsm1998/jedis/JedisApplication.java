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

        // 加载配置
        config.load();

        // 事件初始化
        EventStart.eventInit();

        RedisServer server = RedisServer.getInstance();
        // 数据库初始化
        server.initDB();

        RedisNetServer redisNetServer = new RedisNetServerBuild().build();
        try
        {
            // 网络服务启动
            redisNetServer.start();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
