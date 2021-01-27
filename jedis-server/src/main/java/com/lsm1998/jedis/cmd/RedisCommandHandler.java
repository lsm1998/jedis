/**
 * 作者：刘时明
 * 时间：2021/1/27
 */
package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.cmd.impl.SelectCommand;
import com.lsm1998.jedis.cmd.impl.string.GetCommand;
import com.lsm1998.jedis.cmd.impl.string.SetCommand;
import com.lsm1998.jedis.cmd.proxy.ProxyInstanceFactory;
import com.lsm1998.jedis.common.socket.ReplyData;
import com.lsm1998.jedis.common.socket.ReplyType;
import com.lsm1998.jedis.connect.RedisClientConnect;
import com.lsm1998.jedis.exception.ArgsException;
import com.lsm1998.jedis.exception.RedisException;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RedisCommandHandler
{
    private static final Map<String, RedisCommand> commandMap = new HashMap<>(64);

    static
    {
        commandMap.put("select", ProxyInstanceFactory.getInstance(new SelectCommand()));
        commandMap.put("set", ProxyInstanceFactory.getInstance(new SetCommand()));
        commandMap.put("get", ProxyInstanceFactory.getInstance(new GetCommand()));
    }

    public static <E extends Serializable> ReplyData<E> call(RedisClientConnect connect)
    {
        ReplyData<E> reply = null;
        RedisCommand command = commandMap.get(connect.getCmd());
        if (command == null)
        {
            reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_ERROR, "不能识别的命令");
        } else
        {
            try
            {
                Serializable result = command.handler(connect, connect.getCmd(), connect.getArgs());
                if (result == null)
                {
                    reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_NULL, "(nil)");
                } else if (result instanceof Collection)
                {
                    reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_LIST, result);
                } else if (result instanceof Number)
                {
                    reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_LONG, result);
                } else if (result instanceof String)
                {
                    reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_STRING, result);
                }
            } catch (Exception e)
            {
                if (e instanceof RedisException)
                {
                    reply = (ReplyData<E>) ReplyData.of(ReplyType.REPLY_ERROR, e.getMessage());
                } else
                {
                    e.printStackTrace();
                }
            }
        }
        return reply;
    }
}
