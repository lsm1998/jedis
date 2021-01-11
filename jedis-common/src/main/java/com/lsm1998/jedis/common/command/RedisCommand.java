package com.lsm1998.jedis.common.command;

public class RedisCommand
{
    // 命令名字
    protected String name;

    // 实现函数
    protected RedisCommandProc proc;

    // 参数个数
    protected int arity;

    // 字符串表示的 FLAG
    protected String sFlags;

    // 实际 FLAG
    protected int flags;


    // 指定哪些参数是key
    protected int firstKey;
    protected int lastKey;
    protected int keyStep;

    // 统计信息
    // microseconds 记录了命令执行耗费的总毫微秒数
    // calls 是命令被执行的总次数
    protected long microseconds, calls;
}
