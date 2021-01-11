package com.lsm1998.jedis.common.struct.list;

import java.util.function.Consumer;

/**
 * 双端链表
 *
 * @param <E>
 */
public class LinkedDList<E>
{
    private ListNode<E> head;

    private ListNode<E> tail;

    private int size;

    public int len()
    {
        return size;
    }

    public LinkedDList<E> dup()
    {
        LinkedDList<E> copyList = new LinkedDList<>();
        this.foreach(copyList::addLast);
        return copyList;
    }

    public void free()
    {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public boolean match(ListNode<E> node1, ListNode<E> node2)
    {
        if (node1 == null || node2 == null)
        {
            return node1 == node2;
        }
        return node1.value.equals(node2.value);
    }

    public ListNode<E> getFirst()
    {
        return this.head;
    }

    public ListNode<E> getLast()
    {
        return this.tail;
    }

    public ListNode<E> getPrevNode(ListNode<E> node)
    {
        checkNodeNull(node);
        return node.prev;
    }

    public ListNode<E> getNextNode(ListNode<E> node)
    {
        checkNodeNull(node);
        return node.next;
    }

    public E getValue(ListNode<E> node)
    {
        return node == null ? null : node.value;
    }

    public void addFirst(E value)
    {
        ListNode<E> node = ListNode.of(value);
        if (this.head == null)
        {
            this.head = this.tail = node;
        } else
        {
            ListNode<E> temp = this.head;
            this.head = node;
            this.head.next = temp;
            temp.prev = node;
        }
        this.size++;
    }

    public void addLast(E value)
    {
        ListNode<E> node = ListNode.of(value);
        if (this.tail == null)
        {
            this.head = this.tail = node;
        } else
        {
            ListNode<E> temp = this.tail;
            this.tail = node;
            this.tail.prev = temp;
            temp.next = node;
        }
        this.size++;
    }

    public void insertNode(ListNode<E> target, E value, boolean isNext)
    {
        checkNodeNull(target);
        ListNode<E> node = ListNode.of(value);

        if (isNext)
        {
            node.prev = target;
            node.next = target.next;
            // 给定节点是原表尾节点
            if (this.tail == target)
            {
                this.tail = node;
            }
        } else
        {
            node.next = target;
            node.prev = target.prev;
            // 给定节点是原表头节点
            if (this.head == target)
            {
                this.head = node;
            }
        }
        this.size++;
    }

    public void delNode(ListNode<E> node)
    {
        checkNodeNull(node);
        // 调整前置节点的指针
        if (node.prev != null)
        {
            node.prev.next = node.next;
        } else
        {
            this.head = node.next;
        }
        // 调整后置节点的指针
        if (node.next != null)
        {
            node.next.prev = node.prev;
        } else
        {
            this.tail = node.prev;
        }
        this.size--;
    }

    public ListNode<E> searchKey(E value)
    {
        ListNode<E> temp = this.head;
        while (temp != null)
        {
            if (temp.value == null && value == null)
            {
                return temp;
            }
            if (temp.value != null && temp.value.equals(value))
            {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public ListNode<E> get(int index)
    {
        checkIndex(index);
        int currIndex = 0;
        ListNode<E> temp;
        if (index > this.size / 2)
        {
            index = size - index - 1;
            temp = this.tail;
            while (++currIndex <= index)
            {
                temp = temp.prev;
            }
        } else
        {
            temp = this.head;
            while (++currIndex <= index)
            {
                temp = temp.next;
            }
        }
        return temp;
    }

    private void checkIndex(int index)
    {
        if (index < 0 || index >= size)
        {
            throw new RuntimeException("访问越界！");
        }
    }

    private void checkNodeNull(ListNode<E> node)
    {
        if (node == null)
        {
            throw new RuntimeException("node is null！");
        }
    }

    public void foreach(Consumer<E> consumer)
    {
        ListNode<E> temp = this.head;
        while (temp != null)
        {
            consumer.accept(temp.value);
            temp = temp.next;
        }
    }
}
