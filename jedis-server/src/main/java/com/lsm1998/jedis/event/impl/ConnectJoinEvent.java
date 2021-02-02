package com.lsm1998.jedis.event.impl;

import com.lsm1998.jedis.event.AbstractObserver;
import com.lsm1998.jedis.event.EventBean;
import com.lsm1998.jedis.event.EventType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectJoinEvent extends AbstractObserver
{
    public ConnectJoinEvent()
    {
        super(new EventType[]{EventType.EVENT_CONNECT_JOIN});
    }

    @Override
    public <E> void eventHandler(EventBean<E> event)
    {
        log.debug("一个连接加入");
    }
}
