package com.lsm1998.jedis.event;

public enum EventType
{
    // 启动
    EVENT_START,
    // 退出
    EVENT_STOP,

    // 客户端连接加入
    EVENT_CONNECT_JOIN,
    // 客户端连接退出
    EVENT_CONNECT_LEAVE,
}
