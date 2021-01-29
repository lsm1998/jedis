/**
 * 作者：刘时明
 * 时间：2021/1/28
 */
package com.lsm1998.jedis.common.struct.set;

import com.lsm1998.jedis.common.exception.ExecuteException;
import com.lsm1998.jedis.common.utils.ByteUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 整数集
 */
public class IntSet
{
    public static final int INT_SET_ENC_INT64 = 1 << 3;
    public static final int INT_SET_ENC_INT32 = 1 << 2;
    public static final int INT_SET_ENC_INT16 = 1 << 1;

    private static final byte[] EMPTY_DATA = new byte[0];

    // 编码方式
    private int encoding;

    // 集合长度
    private int length;

    // 数据数组
    private byte[] contents;

    public IntSet()
    {
        this.length = 0;
        this.encoding = INT_SET_ENC_INT16;
        contents = EMPTY_DATA;
    }

    /**
     * 添加元素
     *
     * @param val
     * @return
     */
    public int add(long val)
    {
        int encoding = intSetValueEncoding(val);
        // 是否需要转换编码
        if (this.encoding < encoding)
        {
            intSetUpgrade(encoding);
        }
        int index = this.findInsertIndex(val);
        if (index == -1)
        {
            return 0;
        }
        return this.add(index, val);
    }

    /**
     * 删除元素
     *
     * @param val
     * @return
     */
    public int remove(long val)
    {
        int index = this.find(val);
        if (index == -1)
        {
            return 0;
        }
        this.removeOfRange(index, index + this.encoding - 1);
        return 1;
    }

    public int size()
    {
        return this.length;
    }

    private int add(int index, long val)
    {
        byte[] newContents = new byte[(this.length + 1) * this.encoding];
        System.arraycopy(this.contents, 0, newContents, 0, index);
        byte[] valBytes;
        switch (this.encoding)
        {
            case INT_SET_ENC_INT16:
                valBytes = ByteUtil.shortToBytes((short) val);
                break;
            case INT_SET_ENC_INT32:
                valBytes = ByteUtil.intToBytes((int) val);
                break;
            case INT_SET_ENC_INT64:
                valBytes = ByteUtil.longToBytes(val);
                break;
            default:
                throw new ExecuteException("不支持的编码类型！");
        }
        System.arraycopy(valBytes, 0, newContents, index, valBytes.length);
        if (index < newContents.length)
        {
            System.arraycopy(this.contents, index, newContents, index + valBytes.length, this.contents.length - index);
        }
        this.contents = newContents;
        this.length++;
        return 1;
    }

    private void removeOfRange(int start, int end)
    {
        if (start < 0 || end >= this.contents.length || start > end)
        {
            throw new ArrayIndexOutOfBoundsException();
        }
        byte[] newContents = new byte[this.contents.length - 1 - (end - start)];

        System.arraycopy(this.contents, 0, newContents, 0, start);
        if (end != this.contents.length - 1)
        {
            System.arraycopy(this.contents, end + 1, newContents, start, this.contents.length - end - 1);
        }
        this.length--;
        this.contents = newContents;
    }

    /**
     * 返回适用于传入值 val 的编码方式
     *
     * @param val
     * @return
     */
    private int intSetValueEncoding(long val)
    {
        if (val < Integer.MIN_VALUE || val > Integer.MAX_VALUE)
        {
            return INT_SET_ENC_INT64;
        } else if (val < Short.MIN_VALUE || val > Short.MAX_VALUE)
        {
            return INT_SET_ENC_INT32;
        } else
        {
            return INT_SET_ENC_INT16;
        }
    }

    /**
     * 转换
     *
     * @param encoding
     */
    private void intSetUpgrade(int encoding)
    {
        if (this.encoding >= encoding) return;
        byte[] newContents = new byte[this.length * encoding];
        if (encoding == INT_SET_ENC_INT32) // short =》 int
        {
            for (int i = 0; i < this.contents.length; i += 2)
            {
                short shortTemp = ByteUtil.bytesToShort(new byte[]{this.contents[i], this.contents[i + 1]});
                byte[] bytes = ByteUtil.intToBytes(shortTemp);
                System.arraycopy(bytes, 0, newContents, i * 2, bytes.length);
            }
        } else if (encoding == INT_SET_ENC_INT64 && this.encoding == INT_SET_ENC_INT16) // short =》 long
        {
            for (int i = 0; i < this.contents.length; i += 2)
            {
                short shortTemp = ByteUtil.bytesToShort(new byte[]{this.contents[i], this.contents[i + 1]});
                byte[] bytes = ByteUtil.longToBytes(shortTemp);
                System.arraycopy(bytes, 0, newContents, i * 2, bytes.length);
            }
        } else if (encoding == INT_SET_ENC_INT64 && this.encoding == INT_SET_ENC_INT32) // int =》 long
        {
            for (int i = 0; i < this.contents.length; i += 4)
            {
                int intTemp = ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1],
                        this.contents[i + 2], this.contents[i + 3]});
                byte[] bytes = ByteUtil.longToBytes(intTemp);
                System.arraycopy(bytes, 0, newContents, i * 4, bytes.length);
            }
        } else
        {
            throw new ExecuteException("不支持的转换类型！");
        }
        this.encoding = encoding;
        this.contents = newContents;
    }

    /**
     * 获取要插入的位置，返回-1则代表已经存在了
     * 直接线性查找，二分最佳
     *
     * @param val
     * @return
     */
    private int findInsertIndex(long val)
    {
        for (int i = this.contents.length - this.encoding; i >= 0; i -= this.encoding)
        {
            long temp;
            if (this.encoding == INT_SET_ENC_INT16)
            {
                temp = ByteUtil.bytesToShort(new byte[]{this.contents[i], this.contents[i + 1]});
            } else if (this.encoding == INT_SET_ENC_INT32)
            {
                temp = ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3]});
            } else if (this.encoding == INT_SET_ENC_INT64)
            {
                temp = ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3],
                        this.contents[i + 4], this.contents[i + 5], this.contents[i + 6], this.contents[i + 7]});
            } else
            {
                throw new ExecuteException("不支持的编码类型！");
            }
            if (temp == val)
            {
                return -1;
            } else if (temp < val)
            {
                return i + encoding;
            }
        }
        return 0;
    }

    private int find(long val)
    {
        for (int i = 0; i < this.contents.length; i += this.encoding)
        {
            long temp;
            if (this.encoding == INT_SET_ENC_INT16)
            {
                temp = ByteUtil.bytesToShort(new byte[]{this.contents[i], this.contents[i + 1]});
            } else if (this.encoding == INT_SET_ENC_INT32)
            {
                temp = ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3]});
            } else if (this.encoding == INT_SET_ENC_INT64)
            {
                temp = ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3],
                        this.contents[i + 4], this.contents[i + 5], this.contents[i + 6], this.contents[i + 7]});
            } else
            {
                throw new ExecuteException("不支持的编码类型！");
            }
            if (temp == val)
            {
                return i;
            }
        }
        return -1;
    }

    public List<? extends Number> toList()
    {
        List<Number> list = new ArrayList<>(this.length / this.encoding);
        for (int i = 0; i < this.contents.length; i += this.encoding)
        {
            if (this.encoding == INT_SET_ENC_INT16)
            {
                list.add(ByteUtil.bytesToShort(new byte[]{this.contents[i], this.contents[i + 1]}));
            } else if (this.encoding == INT_SET_ENC_INT32)
            {
                list.add(ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3]}));
            } else if (this.encoding == INT_SET_ENC_INT64)
            {
                list.add(ByteUtil.bytesToInt(new byte[]{this.contents[i], this.contents[i + 1], this.contents[i + 2], this.contents[i + 3],
                        this.contents[i + 4], this.contents[i + 5], this.contents[i + 6], this.contents[i + 7]}));
            }
        }
        return list;
    }
}
