package com.lsm1998.jedis.common.db;

import com.lsm1998.jedis.common.RedisObject;

import java.util.HashMap;
import java.util.Map;

public class RedisDB
{
    private final Map<String, RedisObject<Object>> map;

    public RedisDB()
    {
        this.map = new HashMap<>();
    }
}
