/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.net;

import java.io.IOException;

public interface RedisNetServer
{
    void start() throws IOException;

    void stop() throws IOException;
}
