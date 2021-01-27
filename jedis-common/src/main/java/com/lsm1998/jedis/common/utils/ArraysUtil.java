/**
 * 作者：刘时明
 * 时间：2021/1/25
 */
package com.lsm1998.jedis.common.utils;

import java.lang.reflect.Array;

public class ArraysUtil
{
    /**
     * 删除数组元素
     *
     * @param index
     * @param array
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> E[] remove(int index, E[] array, Class<?> clazz)
    {
        if (index < 0 || index >= array.length)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        E[] newArray = creatArray(clazz, array.length - 1);
        System.arraycopy(array, 0, newArray, 0, index);
        if (index != array.length - 1)
        {
            System.arraycopy(array, index + 1, newArray, index, newArray.length - index);
        }
        return newArray;
    }

    /**
     * 追加数组元素
     *
     * @param index
     * @param value
     * @param array
     * @param clazz
     * @param <E>
     * @return
     */
    public static <E> E[] append(int index, E value, E[] array, Class<?> clazz)
    {
        if (index < 0 || index > array.length)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        E[] newArray = creatArray(clazz, array.length + 1);
        newArray[index] = value;
        System.arraycopy(array, index, newArray, index + 1, array.length - index);
        if (index > 0)
        {
            System.arraycopy(array, 0, newArray, 0, index);
        }
        return newArray;
    }

    private static <E> E[] creatArray(Class<?> clazz, int capacity)
    {
        return (E[]) Array.newInstance(clazz, capacity);
    }
}
