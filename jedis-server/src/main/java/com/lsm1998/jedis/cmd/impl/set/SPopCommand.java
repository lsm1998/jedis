/**
 * 作者：刘时明
 * 时间：2021/2/4
 */
package com.lsm1998.jedis.cmd.impl.set;

import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.common.struct.set.HT;
import com.lsm1998.jedis.common.struct.set.IntSet;
import com.lsm1998.jedis.connect.RedisClientConnect;

import java.io.Serializable;

public class SPopCommand extends SetCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getObject(key);
        if (object == null)
            return null;
        if (object.getEncoding() == EncodingType.REDIS_ENCODING_INTSET)
        {
            IntSet intSet = (IntSet) object.getPtr();
        } else if (object.getEncoding() == EncodingType.REDIS_ENCODING_HT)
        {
            HT ht = (HT) object.getPtr();
        }
        return null;
    }

    @Override
    public String argsCond()
    {
        return "0";
    }
}
