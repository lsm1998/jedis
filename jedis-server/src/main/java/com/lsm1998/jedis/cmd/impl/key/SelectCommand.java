package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class SelectCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args)
    {
        if (args.length != 1)
        {
            return -1;
        }
        return connect.selectDB(Integer.parseInt(args[0]));
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
