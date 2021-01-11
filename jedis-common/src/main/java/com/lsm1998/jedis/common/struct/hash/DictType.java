package com.lsm1998.jedis.common.hash;

public class DictType<E>
{
    /**
     * 计算哈希值
     *
     * @param key
     * @return
     */
    public int hashFunction(E key)
    {
        return key == null ? 0 : key.hashCode();
    }

    public boolean keyCompare()
    {
        return false;
    }
}
