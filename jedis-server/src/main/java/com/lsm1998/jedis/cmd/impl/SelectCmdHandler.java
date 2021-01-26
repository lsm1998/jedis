package com.lsm1998.jedis.cmd.impl;

import com.lsm1998.jedis.cmd.RedisCmdHandler;
import com.lsm1998.jedis.connect.RedisClientConnect;

public class SelectCmdHandler implements RedisCmdHandler
{
    @Override
    public Object handler(RedisClientConnect connect,String key, String[] args)
    {
        if (args.length != 1)
        {
            return -1;
        }
        return connect.selectDB(Integer.parseInt(args[0]));
    }
}
