/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.cmd;

public class CommandUtil
{
    public static Integer string2Int(String val)
    {
        if (val == null || val.length() > 21) return null;
        try
        {
            return Integer.parseInt(val);
        } catch (NumberFormatException e)
        {
            return null;
        }
    }
}
