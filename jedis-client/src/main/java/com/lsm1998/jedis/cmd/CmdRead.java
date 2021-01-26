package com.lsm1998.jedis.cmd;

import com.lsm1998.jedis.common.socket.ReplyData;
import com.lsm1998.jedis.common.socket.ReplyType;
import com.lsm1998.jedis.common.utils.BitObjectUtil;
import com.lsm1998.jedis.socket.JedisClientSocket;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.Scanner;

public class CmdRead
{
    public static void commandHandler(JedisClientSocket socket) throws IOException
    {
        socket.connect();
        Scanner scanner = new Scanner(System.in);
        StringBuilder sql = new StringBuilder();
        while (true)
        {
            System.out.print(">>");
            String line = scanner.nextLine();
            if (sql.length() > 0)
            {
                sql.append("\n");
            }
            sql.append(line);
            if (sql.toString().endsWith(";"))
            {
                // 这一条命令已经结束了
                if (!readReply(socket, sql.toString()))
                {
                    break;
                }
                sql.delete(0, sql.length());
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
                    System.out.println("not Reply!");
                    return true;
                }
                Optional<ReplyData<?>> optional = BitObjectUtil.bytesToObject(bytes);
                if (optional.isPresent())
                {
                    ReplyData<?> replyData = optional.get();
                    if (replyData.getType() == ReplyType.REPLY_LONG)
                    {
                        printReset(replyData.getData());
                    } else if (replyData.getType() == ReplyType.REPLY_ERROR)
                    {
                        System.out.printf("sql error，%s \n", replyData.getData());
                    } else if (replyData.getType() == ReplyType.REPLY_STRING)
                    {
                        printReset(replyData.getData());
                    }
                }
                break;
        }
        return true;
    }

    private static void printReset(Serializable rs)
    {
        System.out.println("服务端回复：" + rs);
    }
}
