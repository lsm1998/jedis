package com.lsm1998.jedis.common.db;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.struct.Dict;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RedisDB implements Serializable
{
    // 数据库id
    public int id;

    // 存放数据库中的所有键值对
    public Dict<String, RedisObject> dict;

    // 存放过期时间
    public Dict<String, Long> expires;

    public RedisDB(int id)
    {
        this.id = id;
        this.expires = new Dict<>();
        this.dict = new Dict<>();
    }
}
