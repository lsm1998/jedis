/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.list;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.struct.list.LinkedDList;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.util.Arrays;

public abstract class ListCommand implements RedisCommand
{
    @Override
    public RedisType typeCond()
    {
        return RedisType.REDIS_LIST;
    }

    protected EncodingType getEncoding(String[] args)
    {
        if (args.length > 512)
        {
            return EncodingType.REDIS_ENCODING_LINKEDLIST;
        }
        for (String s : args)
        {
            if (s.length() > 64)
                return EncodingType.REDIS_ENCODING_LINKEDLIST;
        }
        return EncodingType.REDIS_ENCODING_ZIPLIST;
    }

    protected RedisObject getRedisObject(RedisClientConnect connect, String key, String[] args)
    {
        RedisObject object = connect.getObject(key);
        EncodingType encoding = getEncoding(args);
        if (object == null)
        {
            object = RedisObject.of(RedisType.REDIS_LIST, encoding, null);
            if (encoding == EncodingType.REDIS_ENCODING_LINKEDLIST)
            {
                object.setPtr(new LinkedDList<String>());
            } else
            {
                // ziplist
            }
        }
        return object;
    }

    protected int push(RedisClientConnect connect, String key, String[] args, int direction)
    {
        RedisObject object = getRedisObject(connect, key, args);
        if (object.getEncoding() == EncodingType.REDIS_ENCODING_LINKEDLIST)
        {
            LinkedDList<String> list = (LinkedDList<String>) object.getPtr();
            if (direction == 0)
            {
                Arrays.stream(args).forEach(list::addFirst);
            } else
            {
                Arrays.stream(args).forEach(list::addLast);
            }
            object.setPtr(list);
        } else
        {
            // ziplist
        }
        connect.getRedisDB().dict.put(key, object);
        return args.length;
    }
}
