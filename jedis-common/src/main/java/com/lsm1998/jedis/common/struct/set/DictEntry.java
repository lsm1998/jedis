/**
 * 作者：刘时明
 * 时间：2021/2/2
 */
package com.lsm1998.jedis.common.struct.set;

import java.io.Serializable;

public class DictEntry
{
    public String key;
    public Serializable value;

    public static DictEntry of(String key)
    {
        return of(key, null);
    }

    public static DictEntry of(String key, Serializable value)
    {
        DictEntry entry = new DictEntry();
        entry.key = key;
        entry.value = value;
        return entry;
    }
}
