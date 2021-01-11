package com.lsm1998.jedis.common.hash;

public class Dict<E>
{
    // 哈希表数组
    private DictEntry<E>[] table;

    private DictType type;

    // 当前大小
    private int size;

    // 哈希表大小掩码，等于size-1
    private int sizeMask;

    // 哈希表节点数量
    private int used;

    // 私有数据
    private E privData;

    public Dict(DictType type, E privData)
    {
        this.type = type;
        this.privData = privData;
        this.table = null;
        this.size = 0;
        this.sizeMask = 0;
        this.used = 0;
    }
}
