/**
 * 作者：刘时明
 * 时间：2021/1/26
 */
package com.lsm1998.jedis;

import com.lsm1998.jedis.cmd.CmdRead;
import com.lsm1998.jedis.config.ClientConfig;
import com.lsm1998.jedis.parser.ConfigParser;
import com.lsm1998.jedis.socket.JedisClientSocket;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class JedisCli
{
    public static void main(String[] args) throws ParseException
    {
        ConfigParser parser = ConfigParser.getInstance();
        ClientConfig config = parser.parser(args);

        JedisClientSocket socket = new JedisClientSocket(config.getHost(),config.getPort());

        CmdRead.commandHandler(socket);
    }
}
