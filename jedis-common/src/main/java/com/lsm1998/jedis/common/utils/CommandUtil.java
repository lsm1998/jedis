/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.common.utils;

import com.lsm1998.jedis.common.RedisObject;
import com.lsm1998.jedis.common.RedisType;

public class CommandUtil
{
    public static Long string2Int(String val)
    {
        if (val == null || val.length() > 21) return null;
        try
        {
            return Long.parseLong(val);
        } catch (NumberFormatException e)
        {
            return null;
        }
    }

    public static boolean checkType(RedisObject object, RedisType type)
    {
        return object.getType() == type;
    }
}
