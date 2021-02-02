package com.lsm1998.jedis.event;

import com.lsm1998.jedis.event.impl.ConnectJoinEvent;
import com.lsm1998.jedis.event.impl.ConnectLeaveEvent;
import com.lsm1998.jedis.event.impl.StartEvent;
import com.lsm1998.jedis.event.impl.StopEvent;

import java.util.Date;

public class EventStart
{
    public static void eventInit()
    {
        EventPublish publish = EventPublish.getInstance();

        publish.registerObserver(new StartEvent());
        publish.registerObserver(new StopEvent());

        publish.registerObserver(new ConnectLeaveEvent());
        publish.registerObserver(new ConnectJoinEvent());

        final Date now = new Date();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> publish.notify(EventBean.of(EventType.EVENT_STOP, now))));
    }
}
