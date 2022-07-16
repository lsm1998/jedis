package com.lsm1998.jedis.event.impl;

import com.lsm1998.jedis.event.AbstractObserver;
import com.lsm1998.jedis.event.EventBean;
import com.lsm1998.jedis.event.EventType;
import com.lsm1998.jedis.server.RedisServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class StopEvent extends AbstractObserver
{
    public StopEvent()
    {
        super(new EventType[]{EventType.EVENT_STOP});
    }

    @Override
    public <E> void eventHandler(EventBean<E> event)
    {
        RedisServer redisServer = RedisServer.getInstance();
        redisServer.save(false);

        Date now = new Date();
        Date stopDate = (Date) event.getData();
        long runTime = now.getTime() - stopDate.getTime();

        log.info("jedis进程即将退出，运行时间：{}秒", runTime / 1000);
    }
}
