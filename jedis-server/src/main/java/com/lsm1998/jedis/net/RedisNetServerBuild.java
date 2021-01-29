/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.net;

public class RedisNetServerBuild
{
    public RedisNetServer build()
    {
        return new RedisNetServerImpl();
    }
}
