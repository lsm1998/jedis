/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.parser;

import com.lsm1998.jedis.config.ClientConfig;
import org.apache.commons.cli.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConfigParser
{
    private static ConfigParser parser;

    private static final Lock lock = new ReentrantLock();

    private ConfigParser()
    {
    }

    public static ConfigParser getInstance()
    {
        if (parser == null)
        {
            try
            {
                lock.lock();
                if (parser == null)
                {
                    parser = new ConfigParser();
                }
            } finally
            {
                lock.unlock();
            }
        }
        return parser;
    }

    public ClientConfig parser(String[] args) throws ParseException
    {
        ClientConfig config = new ClientConfig();
        // 解析命令行
        CommandLineParser parser = new BasicParser();
        Options options = new Options();
        options.addOption("h", "host", true, "服务端地址");
        options.addOption("p", "port", true, "服务端端口");
        CommandLine commandLine = parser.parse(options, args);
        if (commandLine.hasOption("h"))
        {
            config.setHost(commandLine.getOptionValue("h"));
        }
        if (commandLine.hasOption("p"))
        {
            config.setPort(Integer.parseInt(commandLine.getOptionValue("p")));
        }
        return config;
    }
}
