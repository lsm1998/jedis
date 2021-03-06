package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.BaseRedisCommand;
import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class SelectCommand extends BaseRedisCommand
{
    @Override
    public Serializable baseHandler(RedisClientConnect connect, String args) throws ExecuteException
    {
        return connect.selectDB(Integer.parseInt(args));
    }
}
