package com.lsm1998.jedis.common.command;

import lombok.Getter;

public enum RedisCmdType
{
    CMD_SET("set"),
    CMD_GET("get");

    @Getter
    private final String value;

    RedisCmdType(String value)
    {
        this.value = value;
    }
}
