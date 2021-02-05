/**
 * 作者：刘时明
 * 时间：2021/2/3
 */
package com.lsm1998.jedis.client;

import com.lsm1998.jedis.socket.JedisClientSocket;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class JedisClient
{
    private String host;

    private int port;

    private AtomicBoolean connectFlag;

    private JedisClientSocket socket;

    public JedisClient(String host, int port)
    {
        this.host = host;
        this.port = port;
        this.connectFlag = new AtomicBoolean(false);
    }

    private void connect() throws IOException
    {
        if (!connectFlag.getAndSet(true))
        {
            socket = new JedisClientSocket(this.host, this.port);
            socket.connect();
        }
    }
}
