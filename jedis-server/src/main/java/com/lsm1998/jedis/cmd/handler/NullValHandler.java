/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.handler;

/**
 * 此接口代表，如果key对应的RedisObject为null，则返回nullVal()
 */
public interface NullValHandler
{
    default Object nullVal()
    {
        return null;
    }
}
