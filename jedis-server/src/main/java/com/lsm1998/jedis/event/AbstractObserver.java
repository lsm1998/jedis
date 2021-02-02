package com.lsm1998.jedis.event;

public abstract class AbstractObserver implements EventObserver
{
    private final EventType[] topics;

    public AbstractObserver(EventType[] topics)
    {
        this.topics = topics;
    }

    public <E> boolean match(EventBean<E> event)
    {
        for (EventType type : topics)
        {
            if (event.getType() == type)
            {
                return true;
            }
        }
        return false;
    }
}
