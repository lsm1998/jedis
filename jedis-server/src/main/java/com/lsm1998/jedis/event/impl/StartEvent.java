package com.lsm1998.jedis.event.impl;

import com.lsm1998.jedis.constant.SysProperties;
import com.lsm1998.jedis.event.AbstractObserver;
import com.lsm1998.jedis.event.EventBean;
import com.lsm1998.jedis.event.EventType;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Slf4j
public class StartEvent extends AbstractObserver
{
    public StartEvent()
    {
        super(new EventType[]{EventType.EVENT_START});
    }

    @Override
    public <E> void eventHandler(EventBean<E> event)
    {
        // show banner
        try (InputStream inputStream = StartEvent.class.getResourceAsStream(SysProperties.BANNER_NAME);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                System.out.println(line);
            }
        } catch (IOException e)
        {
            log.error("打印banner失败，err={}", e.getMessage());
        }
        log.info("Jedis启动完毕,port=[{}]", event.getData());
    }
}
