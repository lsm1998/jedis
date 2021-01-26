/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.server;

import java.io.IOException;

public interface RedisServer
{
    void start() throws IOException;

    void stop() throws IOException;
}
