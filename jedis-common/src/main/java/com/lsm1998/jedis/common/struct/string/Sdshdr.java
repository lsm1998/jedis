package com.lsm1998.jedis.common.struct.string;

import lombok.Data;

/**
 * redis使用sdshdr来表示一个字符串
 */
@Data
public class Sdshdr
{
    // 已使用的字节长度
    private int len;
    // 未使用的字节长度
    private int free;
    // 字节数组
    private byte[] buf;
}
