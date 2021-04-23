/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.list;

import com.lsm1998.jedis.cmd.handler.NullValHandler;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.common.struct.list.LinkedDList;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class LIndexCommand extends ListCommand implements NullValHandler
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getObject(key);
        if (object == null) return null;
        if (object.getEncoding() == EncodingType.REDIS_ENCODING_LINKEDLIST)
        {
            LinkedDList<String> list = (LinkedDList<String>) object.getPtr();
            return list.indexOf(args[0]);
        }
        return null;
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
