/**
 * 作者：刘时明
 * 时间：2021/1/28
 */
package com.lsm1998.jedis.common.struct.list;

import com.lsm1998.jedis.common.EncodingType;

public class ZLenTry
{
    // 前置节点的长度
    public int prevRawLen;
    // 编码 prevRawLen 所需的字节大小
    public int prevRawLenSize;
    // 当前节点值的长度
    public int len;
    // 编码 len 所需的字节大小
    public int lenSize;

    // 当前节点 header 的大小
    // 等于 prevRawLenSize + lenSize
    public int headerSize;

    // 当前节点值所使用的编码类型
    public EncodingType encoding;
    // 指向当前节点的指针
    public Object p;
}
