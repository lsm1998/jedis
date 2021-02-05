/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.key;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.RedisType;

public abstract class KeyCommand implements RedisCommand
{
    @Override
    public RedisType typeCond()
    {
        return null;
    }
}
