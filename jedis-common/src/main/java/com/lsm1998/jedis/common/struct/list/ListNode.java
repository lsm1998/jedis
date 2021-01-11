package com.lsm1998.jedis.common.struct.list;

/**
 * 链表节点
 * @param <E>
 */
public class ListNode<E>
{
    protected ListNode<E> prev;
    protected ListNode<E> next;
    protected E value;

    @Override
    public String toString()
    {
        return "ListNode{" +
                "value=" + value +
                '}';
    }

    public static <E> ListNode<E> of(E value)
    {
        ListNode<E> node = new ListNode<>();
        node.value = value;
        return node;
    }
}
