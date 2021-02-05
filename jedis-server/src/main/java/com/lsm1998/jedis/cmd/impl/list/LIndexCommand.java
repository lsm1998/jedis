/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.list;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class LIndexCommand extends ListCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getObject(key);
        if (object == null)
        {

        }
        return null;
    }

    @Override
    public String argsCond()
    {
        return null;
    }
}
