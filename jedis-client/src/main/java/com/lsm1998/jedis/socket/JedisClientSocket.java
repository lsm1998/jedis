/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.socket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class JedisClientSocket
{
    private Selector selector;
    private SocketChannel channel;
    private final ByteBuffer buffer;
    private final String host;
    private final int port;
    private final ByteArrayOutputStream result;

    public JedisClientSocket(String host, int port)
    {
        this.host = host;
        this.port = port;
        this.buffer = ByteBuffer.allocate(1024);
        this.result = new ByteArrayOutputStream();
    }

    public void connect() throws IOException
    {
        selector = Selector.open();
        InetSocketAddress isa = new InetSocketAddress(host, port);
        channel = SocketChannel.open(isa);
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
    }

    public void close() throws IOException
    {
        this.channel.close();
        this.selector.close();
    }

    public byte[] client2Server(String msg)
    {
        try
        {
            channel.write(ByteBuffer.wrap(msg.getBytes()));
            return getResult();
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    private byte[] getResult() throws IOException
    {
        while (selector.select() > 0)
        {
            for (SelectionKey key : selector.selectedKeys())
            {
                selector.selectedKeys().remove(key);
                if (key.isReadable())
                {
                    SocketChannel channel = (SocketChannel) key.channel();
                    buffer.clear();
                    result.reset();
                    int len;

                    while ((len = channel.read(buffer)) > 0)
                    {
                        buffer.flip();
                        result.write(buffer.array(), 0, len);
                    }
                    buffer.clear();
                    return result.toByteArray();
                }
            }
        }
        return null;
    }
}
