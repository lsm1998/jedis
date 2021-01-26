/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis.common.socket;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReplyData<E extends Serializable> implements Serializable
{
    private ReplyType type;

    private E data;

    public static <E extends Serializable> ReplyData<E> of(ReplyType type, E data)
    {
        ReplyData<E> replyData=new ReplyData<>();
        replyData.setData(data);
        replyData.setType(type);
        return replyData;
    }
}
