package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.connect.RedisClientConnect;
import com.lsm1998.jedis.exception.ExecuteException;

import java.io.Serializable;

public interface RedisCommand
{
    Serializable handler(RedisClientConnect connect, String cmd, String[] args) throws ExecuteException;

    String argsCond();
}
