/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.common.socket;

public enum ReplyType
{
    // String类型
    REPLY_STRING,
    // 列表集合
    REPLY_LIST,
    // 数值
    REPLY_LONG,
    // 错误
    REPLY_ERROR,
}
