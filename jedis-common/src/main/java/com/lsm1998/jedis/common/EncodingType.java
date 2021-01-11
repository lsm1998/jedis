package com.lsm1998.jedis.common;

/**
 * 编码类型
 */
public enum EncodingType
{
    // 简单字符串
    REDIS_ENCODING_RAW,
    // 整数字符串
    REDIS_ENCODING_INT,
    // 字典
    REDIS_ENCODING_HT,
    // 压缩map
    REDIS_ENCODING_ZIPMAP,
    // 双向链表
    REDIS_ENCODING_LINKEDLIST,
    // 压缩列表
    REDIS_ENCODING_ZIPLIST,
    // 整数集合
    REDIS_ENCODING_INTSET,
    // 跳跃列表
    REDIS_ENCODING_SKIPLIST,
    // embstr编码的字符串
    REDIS_ENCODING_EMBSTR,

//    #define REDIS_ENCODING_RAW 0     /* Raw representation */
//    #define REDIS_ENCODING_INT 1     /* Encoded as integer */
//    #define REDIS_ENCODING_HT 2      /* Encoded as hash table */
//    #define REDIS_ENCODING_ZIPMAP 3  /* Encoded as zipmap */
//    #define REDIS_ENCODING_LINKEDLIST 4 /* Encoded as regular linked list */
//    #define REDIS_ENCODING_ZIPLIST 5 /* Encoded as ziplist */
//    #define REDIS_ENCODING_INTSET 6  /* Encoded as intset */
//    #define REDIS_ENCODING_SKIPLIST 7  /* Encoded as skiplist */
//    #define REDIS_ENCODING_EMBSTR 8  /* Embedded sds string encoding */


}
