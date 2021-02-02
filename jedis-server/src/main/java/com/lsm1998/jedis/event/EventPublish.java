/*
 * 作者：刘时明
 * 时间：2019/12/21-23:49
 * 作用：
 */
package com.lsm1998.jedis.event;

import java.util.HashSet;
import java.util.Set;

public class EventPublish
{
    private static final EventPublish eventPublish = new EventPublish();

    private EventPublish()
    {
    }

    public static EventPublish getInstance()
    {
        return eventPublish;
    }

    // 存储对应的观察者
    private final Set<EventObserver> observerSet = new HashSet<>();

    // 注册一个观察者
    public void registerObserver(EventObserver o)
    {
        observerSet.add(o);
    }

    // 删除一个观察者
    public void removeObserver(EventObserver o)
    {
        observerSet.remove(o);
    }

    // 被观察者对其对应观察者的一次更新通知
    public <E> void notify(EventBean<E> value)
    {
        for (EventObserver o : observerSet)
        {
            if (o.match(value))
            {
                o.eventHandler(value);
            }
        }
    }
}
