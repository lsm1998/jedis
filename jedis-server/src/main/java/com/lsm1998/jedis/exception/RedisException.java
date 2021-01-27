/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.exception;

public abstract class RedisException extends Exception
{
    public RedisException(String msg)
    {
        super(msg);
    }
}
