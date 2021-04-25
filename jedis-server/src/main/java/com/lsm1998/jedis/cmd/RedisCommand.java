package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public interface RedisCommand
{
    /**
     * 执行方法
     *
     * @param connect
     * @param key
     * @param args
     * @return
     * @throws ExecuteException
     */
    Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException;

    /**
     * 参数数量限制
     *
     * @return
     */
    String argsLenCond();

    /**
     * key对应的类型限制
     *
     * @return
     */
    RedisType typeCond();
}
