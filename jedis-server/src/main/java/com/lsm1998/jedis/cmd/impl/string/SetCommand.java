package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.common.utils.CommandUtil;
import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.db.RedisDB;
import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.connect.RedisClientConnect;
import com.lsm1998.jedis.constant.RedisProperties;

import java.io.Serializable;

import static com.lsm1998.jedis.common.RedisType.REDIS_STRING;

public class SetCommand implements RedisCommand
{
    @Override
    public Serializable handler(RedisClientConnect connect, String key, String[] args) throws ExecuteException
    {
        RedisDB redisDB = connect.getRedisDB();
        RedisObject redisObject = redisDB.dict.get(key);
        if (redisObject == null)
        {
            redisObject = new RedisObject();
        } else
        {
            if (!CommandUtil.checkType(redisObject, REDIS_STRING))
            {
                throw new ExecuteException("数据类型错误，目标key不是String类型");
            }
        }
        setRedisObject(redisObject, args[0]);
        redisDB.dict.put(key, redisObject);
        return 1;
    }

    private void setRedisObject(RedisObject val, String value)
    {
        val.setLru(System.currentTimeMillis());
        val.setType(REDIS_STRING);
        // 尝试整数编码
        Long intVal = CommandUtil.string2Int(value);
        if (intVal != null)
        {
            val.setEncoding(EncodingType.REDIS_ENCODING_INT);
            val.setPtr(intVal);
        } else if (value.length() < RedisProperties.REDIS_ENCODING_EMBSTR_SIZE_LIMIT) // 尝试简单字符串编码
        {
            val.setEncoding(EncodingType.REDIS_ENCODING_EMBSTR);
            val.setLru(System.currentTimeMillis());
            val.setPtr(value);
        } else
        {
            // 在Reids中，RAW和EMBSTR的区别在于RAW中Sdshdr和RedisObject分开存储的
            // 这里都直接使用String，不搞花里胡哨
            val.setEncoding(EncodingType.REDIS_ENCODING_RAW);
            val.setLru(System.currentTimeMillis());
            val.setPtr(value);
        }
    }

    @Override
    public String argsCond()
    {
        return "1";
    }
}
