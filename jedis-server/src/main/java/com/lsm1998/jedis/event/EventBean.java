package com.lsm1998.jedis.event;

import lombok.Data;

import java.util.Date;

@Data
public class EventBean<E>
{
    private EventType type;

    private E data;

    private Date eventDate;

    public static <E> EventBean<E> of(EventType type, E data)
    {
        EventBean<E> eventBean = new EventBean<>();
        eventBean.setEventDate(new Date());
        eventBean.setData(data);
        eventBean.setType(type);
        return eventBean;
    }
}
