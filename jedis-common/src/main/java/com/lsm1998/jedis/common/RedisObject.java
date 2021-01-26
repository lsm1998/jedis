package com.lsm1998.jedis.common;

public class RedisObject
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
}
