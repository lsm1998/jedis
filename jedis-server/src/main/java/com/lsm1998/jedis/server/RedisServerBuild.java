/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.server;

public class RedisServerBuild
{
    public RedisServer build()
    {
        return new RedisServerImpl();
    }
}
