/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.common.exception;

public class ExecuteException extends RedisException
{
    public ExecuteException(String msg)
    {
        super(msg);
    }
}
