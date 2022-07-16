package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.common.socket.ReplyData;
import com.lsm1998.jedis.common.socket.ReplyType;
import com.lsm1998.jedis.common.utils.BitObjectUtil;
import com.lsm1998.jedis.socket.JedisClientSocket;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class CmdRead
{
    public static void commandHandler(JedisClientSocket socket)
    {
        try
        {
            socket.connect();
        } catch (IOException e)
        {
            System.err.println("连接服务端失败！！！");
            return;
        }
        Scanner scanner = new Scanner(System.in);
        while (true)
        {
            System.out.print("Redis>>");
            String line = scanner.nextLine();
            // 这一条命令已经结束了
            if (!readReply(socket, line))
            {
                break;
            }
        }
    }

    private static boolean readReply(JedisClientSocket socket, String cmd)
    {
        switch (cmd)
        {
            case "version;":
                System.out.println("1.0.0");
                break;
            case "quit;":
                return false;
            default:
                byte[] bytes = socket.client2Server(cmd);
                if (bytes == null || bytes.length == 0)
                {
                    System.out.println("<nil>");
                    return true;
                }
                Optional<ReplyData<?>> optional = BitObjectUtil.bytesToObject(bytes);
                if (optional.isPresent())
                {
                    ReplyData<?> replyData = optional.get();
                    if (replyData.getType() == ReplyType.REPLY_LONG)
                    {
                        System.out.println(replyData.getData());
                    } else if (replyData.getType() == ReplyType.REPLY_ERROR)
                    {
                        System.out.printf("cmd error，%s \n", replyData.getData());
                    } else if (replyData.getType() == ReplyType.REPLY_STRING)
                    {
                        System.out.println("\"" + replyData.getData() + "\"");
                    } else if (replyData.getType() == ReplyType.REPLY_NULL)
                    {
                        System.out.println(replyData.getData());
                    } else if (replyData.getType() == ReplyType.REPLY_LIST)
                    {
                        printlnList(replyData.getData());
                    }
                }
                break;
        }
        return true;
    }

    private static void printlnList(Serializable data)
    {
        if (data instanceof Collection)
        {
            Collection<?> c = (Collection<?>) data;
            if (c.isEmpty())
            {
                System.out.println("<nil>");
            } else
            {
                c.forEach(System.out::println);
            }
        }
    }
}
