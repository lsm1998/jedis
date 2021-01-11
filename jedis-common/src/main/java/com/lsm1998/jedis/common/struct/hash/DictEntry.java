package com.lsm1998.jedis.common.hash;

/**
 * 哈希表节点
 * @param <E>
 */
public class DictEntry<E>
{
    protected E key;

    protected E val;

    protected DictEntry<E> next;
}
