/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.set;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisType;

public abstract class SetCommand implements RedisCommand
{
    @Override
    public RedisType typeCond()
    {
        return RedisType.REDIS_SET;
    }
}
