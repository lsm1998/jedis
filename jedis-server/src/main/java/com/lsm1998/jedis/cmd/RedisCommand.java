package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public interface RedisCommand
{
    Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException;

    String argsCond();

    RedisType typeCond();
}
