/**
 * 作者：刘时明
 * 时间：2021/1/28
 */
package com.lsm1998.jedis.common.struct.list;

/**
 * 压缩列表
 */
public class ZipList
{
    private static final byte ZIP_LIST_END = 0xF;

    private int zlBytes;

    private int zlTail;

    private short zlLen;

    private ZLenTry[] entry;

    private byte zlEnd;
}
