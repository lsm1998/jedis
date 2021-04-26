/**
 * 作者：刘时明
 * 时间：2021/2/1
 */
package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;
import com.lsm1998.jedis.constant.RedisProperties;

import java.io.Serializable;

public class AppendCommand extends StringCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisObject object = connect.getObject(key);
        if (object == null)
        {
            object = new RedisObject();
            setRedisObject(object, args[0]);
            connect.getRedisDB().dict.put(key, object);
            return args[0].length();
        }
        if (object.getEncoding() == EncodingType.REDIS_ENCODING_INT)
        {
            Long longVal = (Long) object.getPtr();
            String strVal = longVal + args[0];
            object.setPtr(strVal);
            if (strVal.length() >= RedisProperties.REDIS_ENCODING_EMBSTR_SIZE_LIMIT)
                object.setEncoding(EncodingType.REDIS_ENCODING_RAW);
            else
                object.setEncoding(EncodingType.REDIS_ENCODING_EMBSTR);
            return strVal.length();
        } else
        {
            String oldVal = (String) object.getPtr();
            String newVal = oldVal + args[0];
            if (object.getEncoding() == EncodingType.REDIS_ENCODING_EMBSTR &&
                    newVal.length() >= RedisProperties.REDIS_ENCODING_EMBSTR_SIZE_LIMIT)
            {
                object.setEncoding(EncodingType.REDIS_ENCODING_RAW);
            }
            object.setPtr(newVal);
            return newVal.length();
        }
    }

    @Override
    public String argsLenCond()
    {
        return "1";
    }
}
