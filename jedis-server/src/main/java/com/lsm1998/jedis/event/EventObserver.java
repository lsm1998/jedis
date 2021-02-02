package com.lsm1998.jedis.event;

public interface EventObserver
{
    <E> void eventHandler(EventBean<E> event);

    <E> boolean match(EventBean<E> event);
}
