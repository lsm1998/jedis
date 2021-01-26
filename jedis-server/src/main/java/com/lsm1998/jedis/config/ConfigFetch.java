/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.config;

import java.io.*;
import java.rmi.RemoteException;

public class ConfigFetch implements Closeable
{
    private final BufferedReader reader;

    protected ConfigFetch(InputStream inputStream)
    {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public void close() throws IOException
    {
        this.reader.close();
    }

    public String[] next() throws IOException
    {
        String line = this.reader.readLine();
        if (line == null) return null;
        line = line.trim();
        if ("".equals(line) || line.startsWith("#"))
        {
            return next();
        } else
        {
            String[] result = line.split("=");
            if (result.length != 2)
            {
                throw new RemoteException("缺少'='或过多'='");
            }
            return result;
        }
    }
}
