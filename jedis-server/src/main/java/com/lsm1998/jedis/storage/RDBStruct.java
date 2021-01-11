package com.lsm1998.jedis.storage;

public class RDBStruct
{
    // 5个字节，魔数，类似于Class文件的'ca fe ba be'，没啥实际含义
    private String magic;

    // 4个字节，版本号
    private String version;


}
