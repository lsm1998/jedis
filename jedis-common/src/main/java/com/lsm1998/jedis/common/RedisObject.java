package com.lsm1998.jedis.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class RedisObject implements Serializable
{
    // 数据类型
    private RedisType type;

    // 编码
    private EncodingType encoding;

    // 最后被访问时间
    private long lru;

    // 引用计数
    private int refCount;

    // 指向实际值
    private Object ptr;

    public static RedisObject of(RedisType type, EncodingType encoding, Object ptr)
    {
        RedisObject object = new RedisObject();
        object.type = type;
        object.encoding = encoding;
        object.ptr = ptr;
        object.lru = System.currentTimeMillis();
        return object;
    }
}
