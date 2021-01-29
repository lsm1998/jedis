/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.common.exception;

public abstract class RedisException extends RuntimeException
{
    public RedisException(String msg)
    {
        super(msg);
    }
}
