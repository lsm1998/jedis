/**
 * 作者：刘时明
 * 时间：2021/1/31
 */
package com.lsm1998.jedis.cmd.impl.set;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.common.struct.set.DictEntry;
import com.lsm1998.jedis.common.struct.set.HT;
import com.lsm1998.jedis.common.struct.set.IntSet;
import com.lsm1998.jedis.common.utils.CommandUtil;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class SAddCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        boolean intSetFlag = true;
        long[] longArrays = new long[args.length];
        for (int i = 0; i < args.length; i++)
        {
            Long temp;
            if ((temp = CommandUtil.string2Int(args[i])) == null)
            {
                intSetFlag = false;
                break;
            }
            longArrays[i] = temp;
        }
        RedisObject object = RedisObject.of(RedisType.REDIS_SET, null, null);
        if (intSetFlag)
        {
            object.setEncoding(EncodingType.REDIS_ENCODING_INTSET);
            object.setPtr(intSetInit(longArrays));
        } else
        {
            object.setEncoding(EncodingType.REDIS_ENCODING_HT);
            object.setPtr(htInit(args));
        }
        connect.getRedisDB().dict.put(key, object);
        return args.length;
    }

    private HT htInit(String[] args)
    {
        HT ht = new HT();
        for (String val : args)
        {
            ht.add(DictEntry.of(val));
        }
        return ht;
    }

    private IntSet intSetInit(long[] longArrays)
    {
        IntSet set = new IntSet();
        for (long val : longArrays)
        {
            set.add(val);
        }
        return set;
    }

    @Override
    public String argsCond()
    {
        return "+1";
    }

    @Override
    public RedisType typeCond()
    {
        return RedisType.REDIS_SET;
    }
}
