/**
 * 作者：刘时明
 * 时间：2021/2/5
 */
package com.lsm1998.jedis.cmd.impl.string;

import com.lsm1998.jedis.cmd.RedisCommand;
import com.lsm1998.jedis.common.EncodingType;
import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;
import com.lsm1998.jedis.common.utils.CommandUtil;
import com.lsm1998.jedis.constant.RedisProperties;

import static com.lsm1998.jedis.common.RedisType.REDIS_STRING;

public abstract class StringCommand implements RedisCommand
{
    @Override
    public RedisType typeCond()
    {
        return RedisType.REDIS_STRING;
    }

    protected void setRedisObject(RedisObject val, String value)
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
}
